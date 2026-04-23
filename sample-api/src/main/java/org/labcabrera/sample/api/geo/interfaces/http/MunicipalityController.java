package org.labcabrera.sample.api.geo.interfaces.http;

import java.net.URI;
import java.util.List;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateMunicipalityCommand;
import org.labcabrera.sample.api.geo.application.cqrs.commands.DeleteMunicipalityCommand;
import org.labcabrera.sample.api.geo.application.cqrs.commands.UpdateMunicipalityCommand;
import org.labcabrera.sample.api.geo.application.cqrs.queries.GetMunicipalityByIdQuery;
import org.labcabrera.sample.api.geo.application.cqrs.queries.GetMunicipalitiesByRsqlQuery;
import org.labcabrera.sample.api.geo.domain.Municipality;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.CreateMunicipalityDto;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.MunicipalityDto;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.MunicipalityPage;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.UpdateMunicipalityDto;
import org.labcabrera.sample.api.geo.interfaces.http.mappers.MunicipalityDtoMapper;
import org.labcabrera.sample.api.shared.application.CommandBus;
import org.labcabrera.sample.api.shared.application.QueryBus;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/v1/municipalities")
@Tag(name = "Municipalities", description = "Endpoints for managing municipalities")
public class MunicipalityController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final MunicipalityDtoMapper mapper;

    @Operation(operationId = "getMunicipalityById", summary = "Get municipality by id", description = "Get municipality by id", responses = {
        @ApiResponse(responseCode = "200", description = "Municipality", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = MunicipalityDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @GetMapping("/{municipalityId}")
    public ResponseEntity<MunicipalityDto> getById(@PathVariable(name = "municipalityId") String municipalityId) {
        var query = new GetMunicipalityByIdQuery(municipalityId);
        Municipality municipality = queryBus.dispatch(query);
        var dto = mapper.toDto(municipality);
        return ResponseEntity.ok(dto);
    }

    @Operation(operationId = "getMunicipalitiesByRsql", summary = "Get municipalities by RSQL", description = "Filter municipalities using an RSQL expression with optional pagination", responses = {
        @ApiResponse(responseCode = "200", description = "Paged municipalities", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = MunicipalityPage.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid RSQL expression", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @GetMapping
    public ResponseEntity<MunicipalityPage> getByRsql(
        @Parameter(name = "q", description = "RSQL expression to filter municipalities", in = ParameterIn.QUERY) @RequestParam(value = "q", required = false, defaultValue = "") String rsql,
        @Parameter(name = "page", description = "Zero-based page index (0..N)", in = ParameterIn.QUERY) @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @Parameter(name = "size", description = "The size of the page to be returned", in = ParameterIn.QUERY) @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
        @Parameter(name = "sort", description = "Sorting criteria in the format: property,(asc|desc). Multiple sort criteria supported.", in = ParameterIn.QUERY) @RequestParam(value = "sort", required = false) List<String> sort) {

        Pageable pageable = Pageable.ofSize(size != null ? size : 20).withPage(page != null ? page : 0);
        var query = new GetMunicipalitiesByRsqlQuery(rsql, pageable);
        Page<Municipality> resultPage = queryBus.dispatch(query);
        Page<MunicipalityDto> pageDto = resultPage.map(mapper::toDto);
        return ResponseEntity.ok(new MunicipalityPage(pageDto));
    }

    @Operation(operationId = "createMunicipality", summary = "Create municipality", description = "Creates a new municipality with the provided data", responses = {
        @ApiResponse(responseCode = "201", description = "Municipality", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = MunicipalityDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid municipality data", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
        @ApiResponse(responseCode = "409", description = "Municipality already exists (code or name)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @PostMapping
    public ResponseEntity<MunicipalityDto> create(@Validated @RequestBody CreateMunicipalityDto request) {
        var command = new CreateMunicipalityCommand(request.code(), request.name(), request.provinceId());
        Municipality municipality = commandBus.dispatch(command);
        var dto = mapper.toDto(municipality);
        return ResponseEntity.created(URI.create("/api/v1/municipalities/" + municipality.getId())).body(dto);
    }

    @PatchMapping("/{municipalityId}")
    @Operation(operationId = "updateMunicipality", summary = "Update municipality", description = "Updates an existing municipality", responses = {
        @ApiResponse(responseCode = "200", description = "Municipality", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = MunicipalityDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid municipality data", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    public ResponseEntity<MunicipalityDto> update(@PathVariable(name = "municipalityId") String municipalityId,
        @RequestBody UpdateMunicipalityDto request) {
        var command = new UpdateMunicipalityCommand(municipalityId, request.code(), request.name(), request.provinceId());
        Municipality municipality = commandBus.dispatch(command);
        var dto = mapper.toDto(municipality);
        return ResponseEntity.ok(dto);
    }

    @Operation(operationId = "deleteMunicipality", summary = "Delete municipality", description = "Deletes a municipality by id", responses = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @DeleteMapping("/{municipalityId}")
    public ResponseEntity<Void> delete(@PathVariable(name = "municipalityId") String municipalityId) {
        var command = new DeleteMunicipalityCommand(municipalityId);
        commandBus.dispatch(command);
        return ResponseEntity.noContent().build();
    }

}
