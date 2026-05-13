package com.integrador.order.dto;

import com.integrador.order.model.estado;

import java.util.List;

public record pedidoDTO(
        Long id,
        Integer numeroMesa,
        estado estado,
        List<orderItemDTO> items
) {
}
