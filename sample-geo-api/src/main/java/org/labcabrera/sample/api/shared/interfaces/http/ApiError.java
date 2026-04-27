package org.labcabrera.sample.api.shared.interfaces.http;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
    String code,
    String message,
    LocalDateTime timestamp,
    List<ApiErrorDetail> details) {
}
