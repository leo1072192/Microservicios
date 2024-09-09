package com.tata.clientespersonas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)   // Define que esta clase es una superclase para herencia
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clienteid", unique = true)
    private Long clienteid;  // Clave primaria (PK) de Persona

 
    private String nombre;

  
    private String genero;

  
    private int edad;

 
    private String identificacion;

    private String direccion;
    
    private String telefono;
}
