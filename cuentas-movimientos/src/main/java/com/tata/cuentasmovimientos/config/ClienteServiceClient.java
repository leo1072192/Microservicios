package com.tata.cuentasmovimientos.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import com.tata.cuentasmovimientos.exception.ClienteNotFoundException;
import com.tata.cuentasmovimientos.model.Cliente;


@Component
public class ClienteServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${clienteservice.url}")
    private String clienteServiceUrl;

public Cliente getClienteById(Long id) {
    String url = String.format("%s/clientes/%d", clienteServiceUrl, id);
    try {
        return restTemplate.getForObject(url, Cliente.class);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ClienteNotFoundException("Cliente no encontrado");
        }
        throw e; // Rethrow other exceptions
    }
}
}
