package com.integrador.payments.model;
import com.integrador.payments.dto.PedidoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OrderClient {

    private final WebClient webClient;

    public PedidoResponse obtenerPedidoPorMesa(Integer numeroMesa) {

        return webClient.get()
                .uri("/api/pedidos/mesa/{mesa}", numeroMesa)
                .retrieve()
                .bodyToMono(PedidoResponse.class)
                .block();
    }
}