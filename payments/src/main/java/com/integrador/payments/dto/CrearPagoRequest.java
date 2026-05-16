package com.integrador.payments.dto;
import com.integrador.payments.model.MetodoPago;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
public record CrearPagoRequest(
        @NotNull
        @Positive
        Integer numeroMesa,

        @NotBlank
        String dniCliente,

        @NotNull
        MetodoPago metodoPago,

        String numeroTarjeta,

        String titularTarjeta

) {
}
