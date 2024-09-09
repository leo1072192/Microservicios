package com.tata.clientespersonas.service;

import com.tata.clientespersonas.model.CuentaDto;
import com.tata.clientespersonas.model.MovimientoDto;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CuentaServiceClient {

    @Value("${microservice.cuenta.url}")
    private String cuentaServiceUrl;

    private final WebClient webClient;

    public CuentaServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(cuentaServiceUrl).build();
    }

    public Mono<CuentaDto> crearCuenta(CuentaDto cuentaDto) {
        return webClient.post()
                .uri("/cuentas/crear")
                .bodyValue(cuentaDto)
                .retrieve()
                .bodyToMono(CuentaDto.class);
    }

    public Mono<CuentaDto> obtenerCuentaPorNumero(String numeroCuenta) {
        return webClient.get()
                .uri("/cuentas/{numeroCuenta}", numeroCuenta)
                .retrieve()
                .bodyToMono(CuentaDto.class);
    }

    public Mono<MovimientoDto> registrarMovimiento(MovimientoDto movimientoDto) {
        return webClient.post()
                .uri("/movimientos/registrar")
                .bodyValue(movimientoDto)
                .retrieve()
                .bodyToMono(MovimientoDto.class);
    }
}
