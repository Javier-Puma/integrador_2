package com.integrador.order.dto;

import com.integrador.order.model.categoria;
import java.math.BigDecimal;

public record orderItemDTO(
        Long id,
        String nombre,
        BigDecimal precio,
        String descripcion,
        categoria categoria
) {
}