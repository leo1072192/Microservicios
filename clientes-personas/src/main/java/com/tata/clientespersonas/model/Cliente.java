package com.tata.clientespersonas.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente extends Persona {

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    
    @Transient // Marca esta lista como transitoria, ya que no se almacena en la base de datos
    private List<CuentaDto> cuentas;
}
