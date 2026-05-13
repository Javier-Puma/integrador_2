package com.integrador.payments.dto;
import com.integrador.payments.model.MetodoPago;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
public record CrearPagoRequest(
        @NotNull @Positive Long pedidoId,
        @NotBlank String dniCliente,
        @NotNull @Positive BigDecimal monto,
        @NotNull MetodoPago metodoPago,

        String numeroTarjeta,
        String titularTarjeta
) {
}
