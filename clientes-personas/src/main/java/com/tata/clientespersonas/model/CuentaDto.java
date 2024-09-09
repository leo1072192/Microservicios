package com.tata.clientespersonas.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaDto {

    private String numeroCuenta;
    private String tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private String cliente; // Suponiendo que esto sea el nombre del cliente

    // Constructor con todos los campos necesarios
    public CuentaDto(String numeroCuenta, String tipo, BigDecimal saldoInicial, Boolean estado, String cliente) {
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.cliente = cliente;
    }
}
