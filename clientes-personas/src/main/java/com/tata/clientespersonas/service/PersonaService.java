package com.tata.clientespersonas.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tata.clientespersonas.model.Persona;
import com.tata.clientespersonas.repository.PersonaRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;

    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public Persona updatePersona(Long id, Persona persona) {
        if (personaRepository.existsById(id)) {
            persona.setClienteid(id);;
            return personaRepository.save(persona);
        }
        throw new EntityNotFoundException("Persona no encontrada");
    }

    public void deletePersona(Long id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Persona no encontrada");
        }
    }

    public Persona getPersona(Long id) {
        return personaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));
    }

    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }
}
