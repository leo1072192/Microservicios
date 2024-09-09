package com.tata.cuentasmovimientos.controller;


import com.tata.cuentasmovimientos.model.Movimiento;
import com.tata.cuentasmovimientos.model.ReporteMovimientoDTO;

import com.tata.cuentasmovimientos.service.MovimientoService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    
    
  
    
    
    @Autowired
    private MovimientoService movimientoService;
     @PostMapping
    public ResponseEntity<?> createMovimiento(@RequestBody Movimiento movimiento) {
        try {
            // Utiliza el servicio para manejar la creación del movimiento
            Movimiento nuevoMovimiento = movimientoService.createMovimiento(movimiento);
            return ResponseEntity.ok(nuevoMovimiento);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Cuenta no encontrada: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en la creación del movimiento: " + e.getMessage());
        }
    }


   @GetMapping("/reporte")
    public List<ReporteMovimientoDTO> obtenerReporte(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam("numeroCuenta") String numeroCuenta) {
        
        return movimientoService.obtenerReportePorFechasYNumeroCuenta(fechaInicio, fechaFin, numeroCuenta);
    }
}
