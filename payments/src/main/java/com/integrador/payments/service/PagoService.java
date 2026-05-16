package com.integrador.payments.service;
import com.integrador.payments.dto.CrearPagoRequest;
import com.integrador.payments.dto.OrderItemResponse;
import com.integrador.payments.dto.PagoDTO;
import com.integrador.payments.dto.PedidoResponse;
import com.integrador.payments.model.EstadoPago;
import com.integrador.payments.model.MetodoPago;
import com.integrador.payments.model.OrderClient;
import com.integrador.payments.model.Pago;
import com.integrador.payments.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {
    private final PagoRepository pagoRepository;
    private final OrderClient orderClient;
    @Transactional
    public PagoDTO registrarPago(CrearPagoRequest request) {

        PedidoResponse pedido=orderClient.obtenerPedidoPorMesa(request.numeroMesa());

        if (pedido == null) {
            throw new RuntimeException("Pedido no encontrado");
        }

        // calcular total
        BigDecimal total = pedido.items().stream()
                .map(OrderItemResponse::precio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Pago.PagoBuilder builder = Pago.builder()
                .pedidoId(pedido.id())
                .dniCliente(request.dniCliente())
                .monto(total)
                .metodoPago(request.metodoPago())
                .estadoPago(EstadoPago.APROBADO)
                .fechaPago(LocalDateTime.now());

        if (request.metodoPago() == MetodoPago.TARJETA) {
            builder.numeroTarjeta(request.numeroTarjeta())
                    .titularTarjeta(request.titularTarjeta())
                    .codigoAutorizacion(generarCodigoAutorizacion());
        }

        Pago pago = builder.build();

        Pago guardado = pagoRepository.save(pago);

        return toDTO(guardado);
    }

    @Transactional(readOnly = true)
    public PagoDTO obtenerPago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con id: " + id));
        return toDTO(pago);
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorPedido(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorDni(String dniCliente) {
        return pagoRepository.findByDniCliente(dniCliente).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorMetodo(MetodoPago metodoPago) {
        return pagoRepository.findByMetodoPago(metodoPago).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorEstado(EstadoPago estadoPago) {
        return pagoRepository.findByEstadoPago(estadoPago).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarTodos() {
        return pagoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    private String generarCodigoAutorizacion() {
        // Generación simple para ejemplo
        return "AUTH-" + System.currentTimeMillis();
    }

    private PagoDTO toDTO(Pago pago) {
        return new PagoDTO(
                pago.getId(),
                pago.getPedidoId(),
                pago.getDniCliente(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getEstadoPago(),
                pago.getFechaPago(),
                pago.getNumeroTarjeta(),
                pago.getTitularTarjeta(),
                pago.getCodigoAutorizacion()
        );
    }
}