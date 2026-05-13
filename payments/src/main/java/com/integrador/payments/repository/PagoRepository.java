package com.integrador.payments.repository;

import com.integrador.payments.model.EstadoPago;
import com.integrador.payments.model.MetodoPago;
import com.integrador.payments.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByPedidoId(Long pedidoId);

    List<Pago> findByDniCliente(String dniCliente);

    List<Pago> findByMetodoPago(MetodoPago metodoPago);

    List<Pago> findByEstadoPago(EstadoPago estadoPago);
}