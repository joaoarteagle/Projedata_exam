package com.projedata.project.presentation.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(
        String name,
        BigDecimal price,
        List<CompositionRequest> composition
) {
}
