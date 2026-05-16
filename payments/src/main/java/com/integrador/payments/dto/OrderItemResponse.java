package com.integrador.payments.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        String nombre,
        BigDecimal precio,
        String descripcion,
        String categoria
) {
}
