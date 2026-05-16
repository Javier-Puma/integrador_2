package com.integrador.order.controller;
import com.integrador.order.dto.actualizarPedidoRequest;
import com.integrador.order.dto.crearPedidoRequest;
import com.integrador.order.dto.pedidoDTO;
import com.integrador.order.model.categoria;
import com.integrador.order.model.estado;
import com.integrador.order.service.pedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class pedidoController {
    private final pedidoService pedidoService;

    // Crear pedido
    @PostMapping
    public pedidoDTO crearPedido(@Valid @RequestBody crearPedidoRequest request) {
        return pedidoService.crearPedido(request);
    }

    // Modificar pedido (número de mesa, items, estado)
    @PutMapping("/{id}")
    public pedidoDTO actualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody actualizarPedidoRequest request
    ) {
        return pedidoService.actualizarPedido(id, request);
    }
    @GetMapping("/mesa/{numeroMesa}")
    public pedidoDTO obtenerPorMesa(@PathVariable Integer numeroMesa) {
        return pedidoService.obtenerPorMesa(numeroMesa);
    }
    // Listar pedidos filtrados por categoría (PLATILLO / BEBIDA / POSTRE)
    @GetMapping("/por-categoria")
    public List<pedidoDTO> listarPorCategoria(@RequestParam("categoria") categoria categoria) {
        return pedidoService.listarPedidosPorCategoria(categoria);
    }

    // Listar pedidos filtrados por estado (RECIBIDO / EN_PREPARACION / FINALIZADO)
    @GetMapping("/por-estado")
    public List<pedidoDTO> listarPorEstado(@RequestParam("estado") estado estado) {
        return pedidoService.listarPedidosPorEstado(estado);
    }

    // Listar todos los pedidos
    @GetMapping
    public List<pedidoDTO> listarTodos() {
        return pedidoService.listarTodos();
    }
}