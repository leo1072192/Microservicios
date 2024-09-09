package com.tata.cuentasmovimientos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tata.cuentasmovimientos.model.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {


    Cuenta findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByClienteid(Long clienteid);
}