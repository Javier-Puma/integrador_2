package com.integrador.order.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
public record crearPedidoRequest(
        @NotNull @Positive Integer numeroMesa,
        @NotNull @Size(min = 1) List<crearOrderItemRequest> items
) {
}
