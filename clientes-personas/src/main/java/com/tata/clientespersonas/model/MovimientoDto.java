package com.tata.clientespersonas.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimientoDto {

    private String numeroCuenta;
    private BigDecimal monto;
    private String tipoMovimiento;
    private BigDecimal saldoDisponible;

    // Constructor con todos los campos necesarios
    public MovimientoDto(String numeroCuenta, BigDecimal monto, String tipoMovimiento, BigDecimal saldoDisponible) {
        this.numeroCuenta = numeroCuenta;
        this.monto = monto;
        this.tipoMovimiento = tipoMovimiento;
        this.saldoDisponible = saldoDisponible;
    }
}
