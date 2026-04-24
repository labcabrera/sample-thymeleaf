package org.labcabrera.sample.api.geo.interfaces.http.dtos;

import org.labcabrera.sample.api.shared.interfaces.http.PageResponse;
import org.springframework.data.domain.Page;

public class MunicipalityPage extends PageResponse<MunicipalityDto> {

    public MunicipalityPage(Page<MunicipalityDto> page) {
        super(page);
    }

}
