package com.tata.clientespersonas.controller.test;

import com.tata.clientespersonas.model.Cliente;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteTests {

    @Test
    public void testClienteEntity() {
        Cliente cliente = new Cliente();
        cliente.setNombre("John Doe");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("123 Street");
        cliente.setTelefono("555-1234");
        cliente.setContrasena("password123");
        cliente.setEstado(true);

        // Test values
        assertThat(cliente.getNombre()).isEqualTo("John Doe");
        assertThat(cliente.getGenero()).isEqualTo("M");
        assertThat(cliente.getEdad()).isEqualTo(30);
        assertThat(cliente.getIdentificacion()).isEqualTo("123456789");
        assertThat(cliente.getDireccion()).isEqualTo("123 Street");
        assertThat(cliente.getTelefono()).isEqualTo("555-1234");
        assertThat(cliente.getContrasena()).isEqualTo("password123");
        assertThat(cliente.getEstado()).isTrue();
    }
}
