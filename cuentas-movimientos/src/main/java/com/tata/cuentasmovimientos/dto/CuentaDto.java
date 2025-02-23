package com.tata.cuentasmovimientos.dto;
import java.math.BigDecimal;

import com.tata.cuentasmovimientos.model.Cliente;
import com.tata.cuentasmovimientos.model.Cuenta;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CuentaDto {
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal  saldoInicial;
    private boolean estado;
    private String cliente;

        // Método estático de conversión
    public static CuentaDto from(Cuenta cuenta) {
        CuentaDto dto = new CuentaDto();
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setTipoCuenta(cuenta.getTipoCuenta());
        dto.setSaldoInicial(cuenta.getSaldoInicial());
        dto.setEstado(cuenta.isEstado());
        //dto.setCliente(cuenta.getNumeroCuenta());

        // Asigna otros campos según sea necesario
        return dto;
    }
}

