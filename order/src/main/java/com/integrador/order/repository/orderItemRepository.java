package com.integrador.order.repository;

import com.integrador.order.model.categoria;
import com.integrador.order.model.estado;
import com.integrador.order.model.orderItem;
import com.integrador.order.model.pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface orderItemRepository extends JpaRepository<orderItem, Long> {
    List<pedido> findByCategoria(categoria categoria);
}
