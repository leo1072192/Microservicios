package com.tata.clientespersonas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tata.clientespersonas.dto.ClienteDTO;
import com.tata.clientespersonas.exception.ClienteDuplicadoException;
import com.tata.clientespersonas.model.Cliente;
import com.tata.clientespersonas.service.ClienteService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getCliente(id);
            return ResponseEntity.ok(cliente);
        } catch (EntityNotFoundException e) {
            return buildErrorResponse("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<ClienteDTO> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente newCliente = clienteService.createCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
        } catch (ClienteDuplicadoException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            Cliente updatedCliente = clienteService.updateCliente(id, cliente);
            return ResponseEntity.ok(updatedCliente);
        } catch (EntityNotFoundException e) {
            return buildErrorResponse("Cliente no encontrado", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
    try {
        clienteService.deleteCliente(id);
        // Crear la respuesta de éxito
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Cliente eliminado con éxito");
        return ResponseEntity.ok(response); // Cambiado a ok para incluir el mensaje
    } catch (EntityNotFoundException e) {
        return buildErrorResponse("Cliente no encontrado", HttpStatus.NOT_FOUND);
    }
}
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
