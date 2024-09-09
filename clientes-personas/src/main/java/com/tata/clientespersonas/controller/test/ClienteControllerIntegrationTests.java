// package com.tata.clientespersonas.controller.test;

// import com.tata.clientespersonas.model.Cliente;
// import com.tata.clientespersonas.repository.ClienteRepository;
// import com.tata.clientespersonas.controller.ClienteController;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import static org.mockito.Mockito.when;

// @WebMvcTest(ClienteController.class)
// public class ClienteControllerIntegrationTests {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private ClienteRepository clienteRepository;

//     @Autowired
//     private ObjectMapper objectMapper;

//     private Cliente cliente;

//     @BeforeEach
//     public void setup() {
//         cliente = new Cliente();
//         cliente.setClienteid(1L);
//         cliente.setNombre("John Doe");
//         cliente.setGenero("M");
//         cliente.setEdad(30);
//         cliente.setIdentificacion("123456789");
//         cliente.setDireccion("123 Street");
//         cliente.setTelefono("555-1234");
//         cliente.setContrasena("password123");
//         cliente.setEstado(true);
//     }

//     @Test
//     public void testGetClienteById() throws Exception {
//         when(clienteRepository.findById(1L)).thenReturn(java.util.Optional.of(cliente));

//         mockMvc.perform(MockMvcRequestBuilders.get("/clientes/1")
//                 .accept(MediaType.APPLICATION_JSON))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("John Doe"));
//     }

//     @Test
//     public void testCreateCliente() throws Exception {
//         when(clienteRepository.save(cliente)).thenReturn(cliente);

//         mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(cliente)))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("John Doe"));
//     }
// }
