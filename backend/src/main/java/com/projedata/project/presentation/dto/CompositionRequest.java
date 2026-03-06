package com.projedata.project.presentation.dto;
import jakarta.validation.constraints.NotNull;


public record CompositionRequest(

    @NotNull
    Long rawMaterialId,
    @NotNull
    Double requiredQuantity

) {
}
