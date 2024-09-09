package com.tata.cuentasmovimientos.controller;

import com.tata.cuentasmovimientos.model.Cuenta;
import com.tata.cuentasmovimientos.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    
    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) {
        if (cuenta.getNumeroCuenta() == null || cuenta.getTipoCuenta() == null) {
            return ResponseEntity.badRequest().build();
        }
        Cuenta nuevaCuenta = cuentaRepository.save(cuenta);
        return ResponseEntity.ok(nuevaCuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        if (!cuentaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cuenta.setId(id);
        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
        return ResponseEntity.ok(cuentaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        if (!cuentaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cuentaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
