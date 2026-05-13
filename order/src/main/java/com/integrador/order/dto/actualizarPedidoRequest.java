package com.integrador.order.dto;
import jakarta.validation.constraints.Positive;

import java.util.List;
public record actualizarPedidoRequest(
        @Positive Integer numeroMesa,
        List<crearOrderItemRequest> items,
        String estado // RECIBIDO | EN_PREPARACION | FINALIZADO
) {
}
