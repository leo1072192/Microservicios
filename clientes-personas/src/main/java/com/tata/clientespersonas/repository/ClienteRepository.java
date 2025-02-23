package com.tata.clientespersonas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tata.clientespersonas.model.Cliente;

// ClienteRepository.java
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByIdentificacion(String identificacion);
    Cliente findByNombre(String nombre); 
    boolean existsByIdentificacion(String identificacion);
}
