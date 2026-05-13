package com.integrador.order.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_mesa", nullable = false)
    private Integer numeroMesa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private estado estado = com.integrador.order.model.estado.RECIBIDO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<orderItem> items = new ArrayList<>();

    public void addItem(orderItem item) {
        item.setPedido(this);
        this.items.add(item);
    }

    public void clearItems() {
        this.items.forEach(i -> i.setPedido(null));
        this.items.clear();
    }
}