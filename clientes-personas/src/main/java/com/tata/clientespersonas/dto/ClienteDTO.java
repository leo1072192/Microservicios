package com.tata.clientespersonas.dto;
import java.math.BigDecimal;

import com.tata.clientespersonas.model.Cliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {
    private String nombres;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;

    // Método estático de conversión
    public static ClienteDTO from(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombres(cliente.getNombre());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEstado(cliente.getEstado());
        dto.setContrasena(cliente.getContrasena());

        // Asigna otros campos según sea necesario
        return dto;
    }


}
