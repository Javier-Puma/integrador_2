package com.integrador.payments.controller;
import com.integrador.payments.dto.CrearPagoRequest;
import com.integrador.payments.dto.PagoDTO;
import com.integrador.payments.model.EstadoPago;
import com.integrador.payments.model.MetodoPago;
import com.integrador.payments.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    // Registrar pago (efectivo o tarjeta)
    @PostMapping
    public PagoDTO registrarPago(@Valid @RequestBody CrearPagoRequest request) {
        return pagoService.registrarPago(request);
    }

    // Obtener un pago por id
    @GetMapping("/{id}")
    public PagoDTO obtenerPago(@PathVariable Long id) {
        return pagoService.obtenerPago(id);
    }

    // Listar pagos de un pedido
    @GetMapping("/por-pedido")
    public List<PagoDTO> listarPorPedido(@RequestParam("pedidoId") Long pedidoId) {
        return pagoService.listarPorPedido(pedidoId);
    }

    // Listar pagos por DNI del cliente
    @GetMapping("/por-dni")
    public List<PagoDTO> listarPorDni(@RequestParam("dni") String dniCliente) {
        return pagoService.listarPorDni(dniCliente);
    }

    // Listar pagos por método (EFECTIVO / TARJETA)
    @GetMapping("/por-metodo")
    public List<PagoDTO> listarPorMetodo(@RequestParam("metodo") MetodoPago metodoPago) {
        return pagoService.listarPorMetodo(metodoPago);
    }

    // Listar pagos por estado (PENDIENTE / APROBADO / RECHAZADO)
    @GetMapping("/por-estado")
    public List<PagoDTO> listarPorEstado(@RequestParam("estado") EstadoPago estadoPago) {
        return pagoService.listarPorEstado(estadoPago);
    }

    // Listar todos los pagos
    @GetMapping
    public List<PagoDTO> listarTodos() {
        return pagoService.listarTodos();
    }
}
