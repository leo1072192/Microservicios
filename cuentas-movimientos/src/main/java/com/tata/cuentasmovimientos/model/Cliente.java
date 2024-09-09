package com.tata.cuentasmovimientos.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class Cliente {
    private Long clienteid;  // Clave primaria (PK) de Persona

 
    private String nombre;

  
    private String genero;

  
    private int edad;

 
    private String identificacion;

    private String direccion;
    
    private String telefono;
}
