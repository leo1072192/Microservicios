package com.tata.clientespersonas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tata.clientespersonas.model.Persona;

// PersonaRepository.java
public interface PersonaRepository extends JpaRepository<Persona, Long> {}
