package com.tata.clientespersonas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tata.clientespersonas.dto.ClienteDTO;
import com.tata.clientespersonas.exception.ClienteDuplicadoException;
import com.tata.clientespersonas.model.Cliente;
import com.tata.clientespersonas.repository.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente createCliente(Cliente cliente) {
        validarIdentificacion(cliente.getIdentificacion());

        // Verificar si ya existe un cliente con la misma identificación
        if (clienteRepository.existsByIdentificacion(cliente.getIdentificacion())) {
            throw new ClienteDuplicadoException("Cliente con la identificación " 
                + cliente.getIdentificacion() + " ya existe.");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente cliente) {
        validarIdentificacion(cliente.getIdentificacion());

        if (clienteRepository.existsById(id)) {
            cliente.setClienteid(id);
            return clienteRepository.save(cliente);
        }
        throw new EntityNotFoundException("Cliente no encontrado");
    }

    public void deleteCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Cliente no encontrado");
        }
    }

    public Cliente getCliente(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                       .map(ClienteDTO::from)
                       .collect(Collectors.toList());
    }
    // Método para validar la identificación del cliente
    private void validarIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede ser nula o vacía.");
        }
    }
}
