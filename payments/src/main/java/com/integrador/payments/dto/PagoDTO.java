package com.integrador.payments.dto;

import com.integrador.payments.model.EstadoPago;
import com.integrador.payments.model.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
public record PagoDTO(Long id,
                      Long pedidoId,
                      String dniCliente,
                      BigDecimal monto,
                      MetodoPago metodoPago,
                      EstadoPago estadoPago,
                      LocalDateTime fechaPago,
                      String numeroTarjeta,
                      String titularTarjeta,
                      String codigoAutorizacion) {
}
