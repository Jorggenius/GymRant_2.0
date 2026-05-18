package com.appMoviles.GymRat.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ActualizarMedidasRequest {

    @NotNull
    @Positive
    private Double peso;

    @NotNull
    @Positive
    private Double estatura;

    @NotNull
    @Min(1)
    private Integer edad;
}