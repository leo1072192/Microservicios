package com.tata.cuentasmovimientos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tata.cuentasmovimientos.model.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<Movimiento> findByFechaBetweenAndCuenta_NumeroCuenta(LocalDate fechaInicio, LocalDate fechaFin, String numeroCuenta);

}
