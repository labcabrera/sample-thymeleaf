package org.labcabrera.sample.api.geo.interfaces.http;

import java.net.URI;
import java.util.List;

import org.labcabrera.sample.api.geo.application.cqrs.commands.CreateProvinceCommand;
import org.labcabrera.sample.api.geo.application.cqrs.commands.DeleteProvinceCommand;
import org.labcabrera.sample.api.geo.application.cqrs.commands.UpdateProvinceCommand;
import org.labcabrera.sample.api.geo.application.cqrs.queries.GetProvinceByIdQuery;
import org.labcabrera.sample.api.geo.application.cqrs.queries.GetProvincesByRsqlQuery;
import org.labcabrera.sample.api.geo.domain.Province;
import org.labcabrera.sample.api.geo.interfaces.http.dtos.ProvinceDto;
import org.labcabrera.sample.api.geo.interfaces.http.mappers.ProvinceDtoMapper;
import org.labcabrera.sample.api.shared.application.CommandBus;
import org.labcabrera.sample.api.shared.application.QueryBus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
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

    @GetMapping("/{provinceId}")
    public ResponseEntity<ProvinceDto> getById(@PathVariable String provinceId) {
        var query = new GetProvinceByIdQuery(provinceId);
        Province province = queryBus.dispatch(query);
        var dto = mapper.toDto(province);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ProvinceDto>> getByRsql(
        @RequestParam(required = false) String rsql,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @RequestParam(required = false) List<String> sort) {

        Pageable pageable = Pageable.ofSize(size != null ? size : 20).withPage(page != null ? page : 0);
        var query = new GetProvincesByRsqlQuery(rsql, pageable);
        Page<Province> resultPage = queryBus.dispatch(query);
        var pageDto = resultPage.map(mapper::toDto);
        return ResponseEntity.ok(pageDto);
    }

    @PostMapping
    public ResponseEntity<ProvinceDto> create(@Validated @RequestBody CreateProvinceCommand request) {
        Province province = commandBus.dispatch(request);
        var dto = mapper.toDto(province);
        return ResponseEntity.created(URI.create("/api/v1/provinces/" + province.getId())).body(dto);
    }

    @PutMapping("/{provinceId}")
    public ResponseEntity<ProvinceDto> update(@PathVariable String provinceId, @RequestBody UpdateProvinceCommand request) {
        var command = new UpdateProvinceCommand(provinceId, request.code(), request.name(), request.countryCode());
        Province province = commandBus.dispatch(command);
        var dto = mapper.toDto(province);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{provinceId}")
    public ResponseEntity<Void> delete(@PathVariable String provinceId) {
        var command = new DeleteProvinceCommand(provinceId);
        commandBus.dispatch(command);
        return ResponseEntity.noContent().build();
    }

}
