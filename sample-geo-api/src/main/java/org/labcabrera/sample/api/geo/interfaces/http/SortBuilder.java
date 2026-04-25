package org.labcabrera.sample.api.geo.interfaces.http;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortBuilder {

    public Sort buildSort(List<String> sortParams) {
        if (sortParams == null || sortParams.isEmpty()) {
            return Sort.unsorted();
        }
        if (sortParams.size() % 2 != 0) {
            throw new IllegalArgumentException("Sort parameters must be pairs: property then direction (even number of elements)");
        }
        List<Sort.Order> orders = new ArrayList<>();
        for (int i = 0; i < sortParams.size(); i += 2) {
            String property = sortParams.get(i);
            String dirStr = sortParams.get(i + 1);
            Sort.Direction direction = (dirStr == null || dirStr.isBlank()) ? Sort.Direction.ASC : Sort.Direction.fromString(dirStr);
            orders.add(new Sort.Order(direction, property));
        }
        return Sort.by(orders);
    }
}
