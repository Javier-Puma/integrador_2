package com.integrador.order.service;
import com.integrador.order.dto.actualizarPedidoRequest;
import com.integrador.order.dto.crearPedidoRequest;
import com.integrador.order.dto.orderItemDTO;
import com.integrador.order.dto.pedidoDTO;
import com.integrador.order.model.categoria;
import com.integrador.order.model.estado;
import com.integrador.order.model.orderItem;
import com.integrador.order.model.pedido;
import com.integrador.order.repository.pedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class pedidoService {
    private final pedidoRepository pedidoRepository;

    @Transactional
    public pedidoDTO crearPedido(crearPedidoRequest request) {
        pedido pedido1 = pedido.builder()
                .numeroMesa(request.numeroMesa())
                .estado(estado.RECIBIDO)
                .build();

        request.items().forEach(itemReq -> {
            orderItem item = orderItem.builder()
                    .nombre(itemReq.nombre())
                    .precio(itemReq.precio())
                    .descripcion(itemReq.descripcion())
                    .categoria(itemReq.categoria())
                    .build();
            pedido1.addItem(item);
        });

        pedido guardado = pedidoRepository.save(pedido1);
        return toDTO(guardado);
    }

    @Transactional
    public pedidoDTO actualizarPedido(Long id, actualizarPedidoRequest request) {
        pedido pedido1 = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + id));

        if (request.numeroMesa() != null) {
            pedido1.setNumeroMesa(request.numeroMesa());
        }

        if (request.estado() != null) {
            estado nuevoEstado = estado.valueOf(request.estado().toUpperCase());
            pedido1.setEstado(nuevoEstado);
        }

        if (request.items() != null) {
            pedido1.clearItems();
            request.items().forEach(itemReq -> {
                orderItem item = orderItem.builder()
                        .nombre(itemReq.nombre())
                        .precio(itemReq.precio())
                        .descripcion(itemReq.descripcion())
                        .categoria(itemReq.categoria())
                        .build();
                pedido1.addItem(item);
            });
        }

        // No es necesario llamar explícitamente a save por estar dentro de @Transactional,
        // pero se puede hacer para claridad.
        pedido actualizado = pedidoRepository.save(pedido1);
        return toDTO(actualizado);
    }

    @Transactional(readOnly = true)
    public List<pedidoDTO> listarPedidosPorCategoria(categoria categoria) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getItems().stream().anyMatch(i -> i.getCategoria() == categoria))
                .map(this::toDTO)
                .toList();
    }
    @Transactional(readOnly = true)
    public pedidoDTO obtenerPorMesa(Integer numeroMesa) {

        pedido pedido = pedidoRepository.findByNumeroMesa(numeroMesa)
                .orElseThrow(() ->
                        new EntityNotFoundException("No existe pedido para la mesa: " + numeroMesa));

        return toDTO(pedido);
    }
    @Transactional(readOnly = true)
    public List<pedidoDTO> listarPedidosPorEstado(estado estado) {
        return pedidoRepository.findByEstado(estado).stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<pedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    private pedidoDTO toDTO(pedido pedido) {
        var itemsDTO = pedido.getItems().stream()
                .map(i -> new orderItemDTO(
                        i.getId(),
                        i.getNombre(),
                        i.getPrecio(),
                        i.getDescripcion(),
                        i.getCategoria()
                ))
                .toList();

        return new pedidoDTO(
                pedido.getId(),
                pedido.getNumeroMesa(),
                pedido.getEstado(),
                itemsDTO
        );
    }
}