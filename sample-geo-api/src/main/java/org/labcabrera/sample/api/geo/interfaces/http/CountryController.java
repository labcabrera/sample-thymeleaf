package org.labcabrera.sample.api.geo.interfaces.http;

import java.net.URI;
import java.util.List;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateCountryCommand;
import org.labcabrera.sample.api.geo.application.cqrs.commands.DeleteCountryCommand;
import org.labcabrera.sample.api.geo.application.cqrs.commands.UpdateCountryCommand;
import org.labcabrera.sample.api.geo.application.cqrs.queries.GetCountryByIdQuery;
import org.labcabrera.sample.api.geo.application.cqrs.queries.GetCountriesByRsqlQuery;
import org.labcabrera.sample.api.geo.domain.Country;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.CountryDto;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.CountryPage;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.CreateCountryDto;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.UpdateCountryDto;
import org.labcabrera.sample.api.geo.interfaces.http.mappers.CountryDtoMapper;
import org.labcabrera.sample.api.shared.application.CommandBus;
import org.labcabrera.sample.api.shared.application.QueryBus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.labcabrera.sample.archetype.generated.model.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/countries")
@Tag(name = "Countries", description = "Endpoints for managing countries")
public class CountryController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final CountryDtoMapper mapper;
    private final SortBuilder sortBuilder;

    @Operation(operationId = "getCountryById", summary = "Get country by id", description = "Get country by id", responses = {
        @ApiResponse(responseCode = "200", description = "Country", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc"),
        @SecurityRequirement(name = "bearerAuth")
    })
    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDto> getById(@PathVariable(name = "countryId") String countryId) {
        var query = new GetCountryByIdQuery(countryId);
        Country country = queryBus.dispatch(query);
        var dto = mapper.toDto(country);
        return ResponseEntity.ok(dto);
    }

    @Operation(operationId = "getCountriesByRsql", summary = "Get countries by RSQL", description = "Filter countries using an RSQL expression with optional pagination", responses = {
        @ApiResponse(responseCode = "200", description = "Paged countries", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CountryPage.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid RSQL expression", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @GetMapping
    public ResponseEntity<CountryPage> getByRsql(
        @Parameter(name = "q", description = "RSQL expression to filter countries", in = ParameterIn.QUERY) @RequestParam(value = "q", required = false, defaultValue = "") String rsql,
        @Parameter(name = "page", description = "Zero-based page index (0..N)", in = ParameterIn.QUERY) @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @Parameter(name = "size", description = "The size of the page to be returned", in = ParameterIn.QUERY) @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
        @Parameter(name = "sort", description = "Sorting criteria in the format: property,(asc|desc). Multiple sort criteria supported.", in = ParameterIn.QUERY) @RequestParam(value = "sort", required = false) List<String> sort) {

        Pageable pageable = PageRequest.of(page, size, sortBuilder.buildSort(sort));
        var query = new GetCountriesByRsqlQuery(rsql, pageable);
        Page<Country> resultPage = queryBus.dispatch(query);
        Page<CountryDto> pageDto = resultPage.map(mapper::toDto);
        return ResponseEntity.ok(new CountryPage(pageDto));
    }

    @Operation(operationId = "createCountry", summary = "Create country", description = "Creates a new country with the provided data", responses = {
        @ApiResponse(responseCode = "201", description = "Country", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid country data", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
        @ApiResponse(responseCode = "409", description = "Country already exists (id or name)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @PostMapping
    public ResponseEntity<CountryDto> create(@Validated @RequestBody CreateCountryDto request) {
        var command = new CreateCountryCommand(request.id(), request.name());
        Country country = commandBus.dispatch(command);
        var dto = mapper.toDto(country);
        String location = String.format("/api/v1/countries/%s", country.getId());
        return ResponseEntity.created(URI.create(location)).body(dto);
    }

    @PatchMapping("/{countryId}")
    @Operation(operationId = "updateCountry", summary = "Update country", description = "Updates an existing country", responses = {
        @ApiResponse(responseCode = "200", description = "Country", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CountryDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid country data", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    public ResponseEntity<CountryDto> update(@PathVariable(name = "countryId") String countryId,
        @RequestBody UpdateCountryDto request) {
        var command = new UpdateCountryCommand(countryId, request.name());
        Country country = commandBus.dispatch(command);
        var dto = mapper.toDto(country);
        return ResponseEntity.ok(dto);
    }

    @Operation(operationId = "deleteCountry", summary = "Delete country", description = "Deletes a country by id", responses = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @DeleteMapping("/{countryId}")
    public ResponseEntity<Void> delete(@PathVariable(name = "countryId") String countryId) {
        var command = new DeleteCountryCommand(countryId);
        commandBus.dispatch(command);
        return ResponseEntity.noContent().build();
    }

}
