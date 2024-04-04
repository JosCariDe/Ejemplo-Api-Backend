package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.pago.PagoDto;
import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import UnivesidadMagdalena.Tienda.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {
    private static final String API_PATH = "/api/v1/pedidos";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void obtenerPedidoPorId_ReturnsPedido() throws Exception {
        // Given
        Long idPedido = 1L;
        PedidoDto pedidoDto = PedidoDto.builder()
                .id(1L)
                .fechaPedido(LocalDateTime.of(2023, Month.JULY, 15, 12, 30))
                .status(Status.ENTREGADO)
                .build();
        when(pedidoService.buscarPedidoPorId(idPedido)).thenReturn(pedidoDto);

        // When & Then
        mockMvc.perform(get(API_PATH + "/{id}", idPedido))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());
    }

    @Test
    void obtenerTodosPedidos_ReturnsListOfPedidos() throws Exception {
        // Given
        List<PedidoDto> pedidos = List.of(
                PedidoDto.builder()
                        .id(1L)
                        .fechaPedido(LocalDateTime.of(2023, Month.JULY, 15, 12, 30))
                        .itemPedidos(Collections.emptyList())
                        .build(),
                PedidoDto.builder()
                        .id(2L)
                        .fechaPedido(LocalDateTime.of(2023, Month.JULY, 5, 12, 30))
                        .itemPedidos(Collections.emptyList())
                        .build()
        );
        when(pedidoService.getAllPedidos()).thenReturn(pedidos);

        // When & Then
        mockMvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andDo(print());
    }

    @Test
    void obtenerPedidosPorCliente_ReturnsListOfPedidos() throws Exception {
        // Given
        ClienteDto cliente = ClienteDto.builder()
                .id(2L).build();
        Long idCliente = 1L;
        List<PedidoDto> pedidos = List.of(
                PedidoDto.builder()
                        .id(1L)
                        .cliente(cliente)
                        .fechaPedido(LocalDateTime.now())
                        .itemPedidos(Collections.emptyList())
                        .build(),
                PedidoDto.builder()
                        .id(2L)
                        .fechaPedido(LocalDateTime.now())
                        .itemPedidos(Collections.emptyList())
                        .build()
        );
        when(pedidoService.findPedidosConArticulosByCliente(idCliente)).thenReturn(Collections.singletonList(pedidos.get(0)));

        // When & Then
        mockMvc.perform(get( API_PATH+ "/cliente"+ "/{id}", idCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cliente.id").value(2))
                .andDo(print());
    }

    @Test
    void obtenerPedidosPorRangoFecha_ReturnsListOfPedidos() throws Exception {
        // Given
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        List<PedidoDto> pedidos = List.of(
                PedidoDto.builder()
                        .id(1L)
                        .fechaPedido(startDate)
                        .itemPedidos(Collections.emptyList())
                        .build(),
                PedidoDto.builder()
                        .id(2L)
                        .fechaPedido(endDate)
                        .itemPedidos(Collections.emptyList())
                        .build()
        );
        when(pedidoService.findByFechaPedidoBetween(startDate, endDate)).thenReturn(pedidos);

        // When & Then
        mockMvc.perform(get("/api/v1/pedidos/date-range")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andDo(print());
    }

    @Test
    void guardarPedido_ReturnsCreatedPedido() throws Exception {
        // Given
        PedidoDto pedidoDto = PedidoDto.builder()
                .id(1L)
                .fechaPedido(LocalDateTime.of(2023, Month.JULY, 15, 12, 30))
                .status(Status.ENTREGADO)
                .build();
        when(pedidoService.guardarPedido(any(PedidoDto.class))).thenReturn(pedidoDto);

        // When & Then
        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fechaPedido").value("2023-07-15T12:30:00"))
                .andExpect(jsonPath("$.status").value("ENTREGADO"))
                .andDo(print());
    }

    @Test
    void actualizarPedido_ReturnsUpdatedPedido() throws Exception {
        // Given
        Long idPedido = 1L;
        PedidoDto pedidoDto = PedidoDto.builder()
                .id(idPedido)
                .fechaPedido(LocalDateTime.of(2023, Month.JULY, 15, 12, 30))
                .status(Status.ENTREGADO)
                .build();
        when(pedidoService.actualizarPedido(eq(idPedido), any(PedidoDto.class))).thenReturn(pedidoDto);

        // When & Then
        mockMvc.perform(put(API_PATH + "/{id}", idPedido)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fechaPedido").value("2023-07-15T12:30:00"))
                .andExpect(jsonPath("$.status").value("ENTREGADO"))
                .andDo(print());
    }

    @Test
    void eliminarPedido_ReturnsNoContent() throws Exception {
        // Given
        Long idPedido = 1L;
        doNothing().when(pedidoService).removerPedido(idPedido);

        // When & Then
        mockMvc.perform(delete(API_PATH + "/{id}", idPedido))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}
