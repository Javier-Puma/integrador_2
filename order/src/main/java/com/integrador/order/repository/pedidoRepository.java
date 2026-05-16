package com.integrador.order.repository;

import com.integrador.order.model.estado;
import com.integrador.order.model.pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface pedidoRepository extends JpaRepository<pedido, Long> {
    List<pedido> findByEstado(estado estado);
    Optional<pedido> findByNumeroMesa(Integer numeroMesa);
}
