package com.integrador.payments.service;
import com.integrador.payments.dto.CrearPagoRequest;
import com.integrador.payments.dto.PagoDTO;
import com.integrador.payments.model.EstadoPago;
import com.integrador.payments.model.MetodoPago;
import com.integrador.payments.model.Pago;
import com.integrador.payments.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {
    private final PagoRepository pagoRepository;

    @Transactional
    public PagoDTO registrarPago(CrearPagoRequest request) {
        // Aquí podrías validar contra el microservicio de pedidos si el pedido existe
        // y calcular el monto correcto, etc. Por simplicidad, usamos el monto que llega.

        Pago.PagoBuilder builder = Pago.builder()
                .pedidoId(request.pedidoId())
                .dniCliente(request.dniCliente())
                .monto(request.monto())
                .metodoPago(request.metodoPago())
                .estadoPago(EstadoPago.APROBADO)   // asumiendo que el pago se aprueba
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