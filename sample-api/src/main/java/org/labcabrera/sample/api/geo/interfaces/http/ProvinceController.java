package org.labcabrera.sample.api.geo.interfaces.http;

import java.net.URI;
import java.util.List;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateProvinceCommand;
import org.labcabrera.sample.api.geo.application.cqrs.commands.DeleteProvinceCommand;
import org.labcabrera.sample.api.geo.application.cqrs.commands.UpdateProvinceCommand;
import org.labcabrera.sample.api.geo.application.cqrs.queries.GetProvinceByIdQuery;
import org.labcabrera.sample.api.geo.application.cqrs.queries.GetProvincesByRsqlQuery;
import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.CreateProvinceDto;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.ProvinceDto;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.ProvincePage;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.UpdateProvinceDto;
import org.labcabrera.sample.api.geo.interfaces.http.mappers.ProvinceDtoMapper;
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
@RequestMapping("/api/v1/provinces")
@Tag(name = "Provinces", description = "Endpoints for managing provinces")
public class ProvinceController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final ProvinceDtoMapper mapper;

    @Operation(operationId = "getProvinceById", summary = "Get province by id", description = "Get province by id", responses = {
        @ApiResponse(responseCode = "200", description = "Province", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProvinceDto.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @GetMapping("/{provinceId}")
    public ResponseEntity<ProvinceDto> getById(@PathVariable(name = "provinceId") String provinceId) {
        var query = new GetProvinceByIdQuery(provinceId);
        Province province = queryBus.dispatch(query);
        var dto = mapper.toDto(province);
        return ResponseEntity.ok(dto);
    }

    @Operation(operationId = "getProvincesByRsql", summary = "Get provinces by RSQL", description = "Filter provinces using an RSQL expression with optional pagination", responses = {
        @ApiResponse(responseCode = "200", description = "Paged provinces", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProvincePage.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid RSQL expression", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @GetMapping
    public ResponseEntity<ProvincePage> getByRsql(
        @Parameter(name = "q", description = "RSQL expression to filter provinces", in = ParameterIn.QUERY) @RequestParam(value = "q", required = false, defaultValue = "") String rsql,
        @Parameter(name = "page", description = "Zero-based page index (0..N)", in = ParameterIn.QUERY) @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @Parameter(name = "size", description = "The size of the page to be returned", in = ParameterIn.QUERY) @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
        @Parameter(name = "sort", description = "Sorting criteria in the format: property,(asc|desc). Multiple sort criteria supported.", in = ParameterIn.QUERY) @RequestParam(value = "sort", required = false) List<String> sort) {

        Pageable pageable = Pageable.ofSize(size != null ? size : 20).withPage(page != null ? page : 0);
        var query = new GetProvincesByRsqlQuery(rsql, pageable);
        Page<Province> resultPage = queryBus.dispatch(query);
        Page<ProvinceDto> pageDto = resultPage.map(mapper::toDto);
        return ResponseEntity.ok(new ProvincePage(pageDto));
    }

    @Operation(operationId = "createProvince", summary = "Create province", description = "Creates a new province with the provided data", responses = {
        @ApiResponse(responseCode = "201", description = "Province", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProvinceDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid province data", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
        @ApiResponse(responseCode = "409", description = "Province already exists (code or name)", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @PostMapping
    public ResponseEntity<ProvinceDto> create(@Validated @RequestBody CreateProvinceDto request) {
        var command = new CreateProvinceCommand(request.code(), request.name(), request.countryCode());
        Province province = commandBus.dispatch(command);
        var dto = mapper.toDto(province);
        return ResponseEntity.created(URI.create("/api/v1/provinces/" + province.id())).body(dto);
    }

    @PatchMapping("/{provinceId}")
    @Operation(operationId = "updateProvince", summary = "Update province", description = "Updates an existing province", responses = {
        @ApiResponse(responseCode = "200", description = "Province", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProvinceDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid province data", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    public ResponseEntity<ProvinceDto> update(@PathVariable(name = "provinceId") String provinceId,
        @RequestBody UpdateProvinceDto request) {
        var command = new UpdateProvinceCommand(provinceId, request.code(), request.name(), request.countryCode());
        Province province = commandBus.dispatch(command);
        var dto = mapper.toDto(province);
        return ResponseEntity.ok(dto);
    }

    @Operation(operationId = "deleteProvince", summary = "Delete province", description = "Deletes a province by id", responses = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "404", description = "Not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
    }, security = {
        @SecurityRequirement(name = "oidc")
    })
    @DeleteMapping("/{provinceId}")
    public ResponseEntity<Void> delete(@PathVariable(name = "provinceId") String provinceId) {
        var command = new DeleteProvinceCommand(provinceId);
        commandBus.dispatch(command);
        return ResponseEntity.noContent().build();
    }

}
