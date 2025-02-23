package com.tata.cuentasmovimientos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tata.cuentasmovimientos.config.ClienteServiceClient;
import com.tata.cuentasmovimientos.model.Cliente;
import com.tata.cuentasmovimientos.model.Cuenta;
import com.tata.cuentasmovimientos.model.Movimiento;
import com.tata.cuentasmovimientos.model.ReporteMovimientoDTO;
import com.tata.cuentasmovimientos.repository.CuentaRepository;
import com.tata.cuentasmovimientos.repository.MovimientoRepository;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;
   @Autowired
    private CuentaRepository cuentaRepository;
  @Autowired
    private ClienteServiceClient clienteServiceClient; 

    
    public Movimiento createMovimiento(Movimiento movimiento) {
        // Busca la cuenta por el número de cuenta
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getNumeroCuenta());
        
        if (cuenta == null) {
            throw new EntityNotFoundException("Cuenta no encontrada con número de cuenta: " + movimiento.getNumeroCuenta());
        }
    
        // Obtener el saldo antes de realizar la transacción (este será el saldo inicial para el movimiento actual)
        BigDecimal saldoInicial = cuenta.getSaldoInicial();
        
        // Verifica si el valor es positivo y ajusta según el tipo de movimiento
        BigDecimal valorMovimiento = movimiento.getValor();
    
        // Validar que el valor no sea negativo para depósitos y retiros
        if (valorMovimiento.signum() <= 0) {
            throw new IllegalArgumentException("El valor del movimiento debe ser positivo.");
        }
    
        BigDecimal saldoActual = saldoInicial;// Asignamos el saldo inicial como saldo actual temporalmente
    
        // Dependiendo del tipo de movimiento, ajusta el saldo
        if ("deposito".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            // Sumar el valor del depósito al saldo actual
            saldoActual = saldoInicial.add(valorMovimiento);
            cuenta.setSaldoInicial(saldoInicial.subtract(valorMovimiento));
        } else if ("retiro".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            // Verificar que hay saldo suficiente para el retiro
            if (saldoInicial.compareTo(valorMovimiento) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro.");
            }
            System.out.println(saldoInicial);
            System.out.println(valorMovimiento);
            // Restar el valor del retiro del saldo actual
            cuenta.setSaldoInicial(saldoInicial.add(valorMovimiento));
            saldoActual = saldoInicial.subtract(valorMovimiento);
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no reconocido: " + movimiento.getTipoMovimiento() + ". Solo se admite 'retiro' o 'deposito'.");
        }
    
        // Actualiza el saldo actual de la cuenta después de la transacción
        cuenta.setSaldo(saldoActual); 
       
        // Guarda la cuenta con el saldo actualizado
        cuentaRepository.save(cuenta);
    
        // Asigna la cuenta, el saldo inicial (antes de la transacción), y el nuevo saldo al movimiento
        movimiento.setCuenta(cuenta);
        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(saldoActual); // Establece el saldo actualizado en el movimiento
    
        // Guarda el movimiento
        return movimientoRepository.save(movimiento);
    }
    

    public Movimiento updateMovimiento(Long id, Movimiento movimiento) {
        if (movimientoRepository.existsById(id)) {
            movimiento.setId(id);
            return movimientoRepository.save(movimiento);
        }
        throw new EntityNotFoundException("Movimiento no encontrado");
    }

    public void deleteMovimiento(Long id) {
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Movimiento no encontrado");
        }
    }

    public Movimiento getMovimiento(Long id) {
        return movimientoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));
    }

    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    public List<Movimiento> getMovimientosPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return movimientoRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
    public List<Movimiento> getMovimientosPorFechasYNumeroCuenta(LocalDate fechaInicio, LocalDate fechaFin, String numeroCuenta) {
        return movimientoRepository.findByFechaBetweenAndCuenta_NumeroCuenta(fechaInicio, fechaFin, numeroCuenta);
    }

       public List<ReporteMovimientoDTO> obtenerReportePorFechasYNumeroCuenta(LocalDate fechaInicio, LocalDate fechaFin, String numeroCuenta) {
        System.out.println(fechaInicio);
        List<Movimiento> movimientos = movimientoRepository.findByFechaBetweenAndCuenta_NumeroCuenta(fechaInicio, fechaFin, numeroCuenta);
        // Busca la cuenta por el número de cuenta
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        Long clienteid = cuenta.getClienteid();

         // Consulta al microservicio de clientes
        Cliente cliente = clienteid != null ? clienteServiceClient.getClienteById(clienteid) : null;
        return movimientos.stream().map(movimiento -> {
            ReporteMovimientoDTO dto = new ReporteMovimientoDTO();
            dto.setFecha(movimiento.getFecha());
            dto.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
            dto.setTipo(movimiento.getCuenta().getTipoCuenta());
            dto.setTipoMovimiento(movimiento.getTipoMovimiento());
            dto.setSaldoInicial(movimiento.getCuenta().getSaldoInicial());
            dto.setEstado(movimiento.getCuenta().isEstado());
            dto.setMovimiento(movimiento.getValor());
            dto.setSaldoDisponible(movimiento.getSaldo());
            dto.setCliente(cliente != null ? cliente.getNombre() : "Desconocido"); // Establece el nombre del cliente
            return dto;
        }).collect(Collectors.toList());
    }
}
