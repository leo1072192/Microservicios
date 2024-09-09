package com.tata.clientespersonas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tata.clientespersonas.model.Cliente;
import com.tata.clientespersonas.repository.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente cliente) {
        if (clienteRepository.existsById(id)) {
            cliente.setClienteid(id);;
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
        return clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
}
