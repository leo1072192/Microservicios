package com.tata.cuentasmovimientos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tata.cuentasmovimientos.config.ClienteServiceClient;
import com.tata.cuentasmovimientos.model.Cliente;
import com.tata.cuentasmovimientos.model.Cuenta;
import com.tata.cuentasmovimientos.model.Movimiento;
import com.tata.cuentasmovimientos.repository.CuentaRepository;
import com.tata.cuentasmovimientos.repository.MovimientoRepository;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;
  @Autowired
    private ClienteServiceClient clienteServiceClient; 

    public Cuenta createCuenta(Cuenta cuenta) {
        Long clienteid = cuenta.getClienteid();
          Cliente cliente = clienteid != null ? clienteServiceClient.getClienteById(clienteid) : null;

          // Verifica si ya existe una cuenta con el mismo número
        if (cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()) != null) {
            throw new IllegalArgumentException("El número de cuenta ya existe.");
        }
        if (!cuenta.getTipoCuenta().equalsIgnoreCase("Ahorros") &&
        !cuenta.getTipoCuenta().equalsIgnoreCase("Corriente")) {
        throw new IllegalArgumentException("El tipo de cuenta no encontrada.");
    }
        
        cuenta.setSaldo(cuenta.getSaldoInicial());

        return cuentaRepository.save(cuenta);
    }
    
    public Cuenta updateCuenta(Long id, Cuenta cuenta) {
        if (cuentaRepository.existsById(id)) {
            cuenta.setId(id);
            return cuentaRepository.save(cuenta);
        }
        throw new EntityNotFoundException("Cuenta no encontrada");
    }

    public void deleteCuenta(Long id) {
        if (cuentaRepository.existsById(id)) {
            cuentaRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Cuenta no encontrada");
        }
    }

    public Cuenta getCuenta(Long id) {
        return cuentaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));
    }

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    public void registrarMovimiento(Long cuentaId, Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
            .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada"));

        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal nuevoSaldo = saldoActual.add(movimiento.getValor());
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Saldo no disponible");
        }
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setSaldo(nuevoSaldo);
        movimientoRepository.save(movimiento);
    }
}
