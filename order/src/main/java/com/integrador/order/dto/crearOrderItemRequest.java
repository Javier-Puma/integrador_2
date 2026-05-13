package com.integrador.order.dto;
import com.integrador.order.model.categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
public record crearOrderItemRequest(
        @NotBlank String nombre,
        @NotNull @Positive BigDecimal precio,
        @NotBlank String descripcion,
        @NotNull categoria categoria
) {
}
