package com.tata.cuentasmovimientos.controller;

import com.tata.cuentasmovimientos.model.Cuenta;
import com.tata.cuentasmovimientos.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

@PostMapping
public ResponseEntity<?> createCuenta(@RequestBody Cuenta cuenta) {
    try {
        if (cuenta.getNumeroCuenta() == null || cuenta.getTipoCuenta() == null) {
            return buildErrorResponse("El número de cuenta y el tipo de cuenta son obligatorios", HttpStatus.BAD_REQUEST);
        }
        if (cuenta.getClienteid() == null) {
            return buildErrorResponse("El clienteId es obligatorio", HttpStatus.BAD_REQUEST);
        }
        if (cuenta.getSaldoInicial().compareTo(BigDecimal.ZERO) < 0) {
            return buildErrorResponse("El saldo inicial no puede ser negativo", HttpStatus.BAD_REQUEST);
        }

        Cuenta nuevaCuenta = cuentaService.createCuenta(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCuenta);
    } catch (IllegalArgumentException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        try {
            Cuenta cuentaActualizada = cuentaService.updateCuenta(id, cuenta);
            return ResponseEntity.ok(cuentaActualizada);
        } catch (EntityNotFoundException e) {
            return buildErrorResponse("Cuenta no encontrada", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable Long id) {
        try {
            cuentaService.deleteCuenta(id);
            Map<String, String> response = new HashMap<>();
            response.put("status", "OK");
            response.put("message", "Cuenta eliminada con éxito");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return buildErrorResponse("Cuenta no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // Método para construir la respuesta de error
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
