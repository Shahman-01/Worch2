package com.worch.model.dto.response;

import java.util.List;
import java.util.Map;

public record ValidationErrorResponse(
    Map<String, List<String>> errors
) {
}