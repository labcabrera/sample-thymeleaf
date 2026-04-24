package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import org.labcabrera.sample.api.shared.interfaces.http.PageResponse;
import org.springframework.data.domain.Page;

public class ProvincePage extends PageResponse<ProvinceDto> {

    public ProvincePage(Page<ProvinceDto> page) {
        super(page);
    }

}
