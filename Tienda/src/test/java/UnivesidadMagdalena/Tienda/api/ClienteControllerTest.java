package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteToSaveDto;

import UnivesidadMagdalena.Tienda.service.ClienteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;



import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.mockito.Mockito.when;



import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest No escanea todos los componentes necesarios
public class ClienteControllerTest {
    private static final String API_PATH = "/api/v1/clientes";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteService clienteService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerClientePorId_ReturnsCliente() throws Exception {
        // Arrange
        Long idCliente = 1L;
        ClienteDto clienteDto = ClienteDto.builder()
                .id(1L)
                .nombre("Jose")
                .direccion("Santa Marta")
                .email("jose@gmail.com")
                .pedidos(Collections.emptyList())
                .build();
        when(clienteService.buscarClientePorId(idCliente)).thenReturn(clienteDto);

        // Act & Assert
        mockMvc.perform(get(API_PATH + "/{id}", idCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Jose"))
                .andExpect(jsonPath("$.direccion").value("Santa Marta"))
                .andExpect(jsonPath("$.email").value("jose@gmail.com"))
                .andDo(print());
    }


    @Test
    void obtenerTodosLosClientes_ReturnsListOfClientes() throws Exception {
        // Arrange
        List<ClienteDto> clientes = List.of(
                ClienteDto.builder()
                        .id(1L)
                        .nombre("Jose")
                        .direccion("Santa Marta")
                        .email("jose@gmail.com")
                        .pedidos(Collections.emptyList())
                        .build(),
                ClienteDto.builder()
                        .id(2L)
                        .nombre("Maria")
                        .direccion("Bogota")
                        .email("maria@gmail.com")
                        .pedidos(Collections.emptyList())
                        .build()
        );
        when(clienteService.getAllClientes()).thenReturn(clientes);

        // Act & Assert
        mockMvc.perform(get(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Jose"))
                .andExpect(jsonPath("$[0].direccion").value("Santa Marta"))
                .andExpect(jsonPath("$[0].email").value("jose@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Maria"))
                .andExpect(jsonPath("$[1].direccion").value("Bogota"))
                .andExpect(jsonPath("$[1].email").value("maria@gmail.com"))
                .andDo(print());
    }


    @Test
    void obtenerClientesPorEmail_ReturnsListOfClientes() throws Exception {
        // Arrange
        String email = "jose@gmail.com";
        List<ClienteDto> clientes = new ArrayList<>();
        clientes.add(ClienteDto.builder()
                .id(1L)
                .nombre("Jose")
                .direccion("Santa Marta")
                .email("jose@gmail.com")
                .pedidos(Collections.emptyList())
                .build());

        when(clienteService.findByEmail(email)).thenReturn(clientes);

        // Act & Assert
        mockMvc.perform(get(API_PATH +"/email" +  "/{email}", email).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Jose"))
                .andExpect(jsonPath("$[0].direccion").value("Santa Marta"))
                .andExpect(jsonPath("$[0].email").value("jose@gmail.com"))
                .andDo(print());
    }

    @Test
    void obtenerClientesPorDireccion_ReturnsListOfClientes() throws Exception {
        // Arrange
        String direccion = "Santa Marta";
        List<ClienteDto> clientes = List.of(
                ClienteDto.builder()
                        .id(1L)
                        .nombre("Jose")
                        .direccion("Santa Marta")
                        .email("jose@gmail.com")
                        .pedidos(Collections.emptyList())
                        .build()
        );
        when(clienteService.findByDireccion(direccion)).thenReturn(clientes);

        // Act & Assert
        mockMvc.perform(get("/api/v1/clientes/direccion")
                        .param("direccion", direccion))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Jose"))
                .andExpect(jsonPath("$[0].direccion").value("Santa Marta"))
                .andExpect(jsonPath("$[0].email").value("jose@gmail.com"))
                .andDo(print());
    }

    @Test
    void guardarCliente_ReturnsCreatedCliente() throws Exception {
        // Arrange
        ClienteToSaveDto clienteToSaveDto = ClienteToSaveDto.builder()
                .nombre("Jose")
                .direccion("Santa Marta")
                .email("jose@gmail.com")
                .build();
        ClienteDto clienteDto = ClienteDto.builder()
                .id(1L)
                .nombre("Jose")
                .direccion("Santa Marta")
                .email("jose@gmail.com")
                .pedidos(Collections.emptyList())
                .build();
        when(clienteService.guardarCliente(any(ClienteToSaveDto.class))).thenReturn(clienteDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteToSaveDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Jose"))
                .andExpect(jsonPath("$.direccion").value("Santa Marta"))
                .andExpect(jsonPath("$.email").value("jose@gmail.com"))
                .andDo(print());
    }

    @Test
    void actualizarCliente_ReturnsUpdatedCliente() throws Exception {
        // Arrange
        Long idCliente = 1L;
        ClienteToSaveDto clienteToSaveDto = ClienteToSaveDto.builder()
                .nombre("Jose")
                .direccion("Santa Marta")
                .email("jose@gmail.com")
                .build();
        ClienteDto clienteDto = ClienteDto.builder()
                .id(1L)
                .nombre("Jose")
                .direccion("Santa Marta")
                .email("jose@gmail.com")
                .pedidos(Collections.emptyList())
                .build();
        when(clienteService.actualizarCliente(idCliente, clienteToSaveDto)).thenReturn(clienteDto);

        // Act & Assert
        mockMvc.perform(put(API_PATH + "/{id}", idCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteToSaveDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Jose"))
                .andExpect(jsonPath("$.direccion").value("Santa Marta"))
                .andExpect(jsonPath("$.email").value("jose@gmail.com"))
                .andDo(print());
    }

    @Test
    void eliminarCliente_ReturnsNoContent() throws Exception {
        // Given
        Long idCliente = 1L;
        willDoNothing().given(clienteService).removerCliente(idCliente);

        // When
        ResultActions response = mockMvc.perform(delete(API_PATH + "/{id}", idCliente));

        // Then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}