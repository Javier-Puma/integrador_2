package com.integrador.payments.dto;

import java.util.List;

public record PedidoResponse(
        Long id,
        Integer numeroMesa,
        String estado,
        List<OrderItemResponse> items
) {
}
