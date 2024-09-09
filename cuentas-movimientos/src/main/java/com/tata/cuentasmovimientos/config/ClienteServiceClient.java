package com.tata.cuentasmovimientos.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tata.cuentasmovimientos.model.Cliente;


@Component
public class ClienteServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${clienteservice.url}")
    private String clienteServiceUrl;

    public Cliente getClienteById(Long id) {
        String url = String.format("%s/clientes/%d", clienteServiceUrl, id);
        return restTemplate.getForObject(url, Cliente.class);
    }
}
