package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.api.ItemPedidoController;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoToSaveDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.exception.ItemPedidoNotFoundException;
import UnivesidadMagdalena.Tienda.service.ItemPedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemPedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemPedidoService itemPedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_PATH = "/api/v1/items-pedidos";

    @Test
    void obtenerItemPedidoPorId_ReturnsItemPedido() throws Exception {
        // Given
        long id = 1L;
        ItemPedidoDto itemPedidoDto = ItemPedidoDto.builder()
                .id(id)
                .cantidad(2)
                .precioUnitario(20)
                .build();
        when(itemPedidoService.buscarItemPedidoPorId(id)).thenReturn(itemPedidoDto);

        // When & Then
        mockMvc.perform(get(API_PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precioUnitario").value(20));
    }

    @Test
    void obtenerTodosItemsPedido_ReturnsListOfItemsPedido() throws Exception {
        // Given
        List<ItemPedidoDto> itemPedidoDtoList = Collections.singletonList(ItemPedidoDto.builder()
                .id(1L)
                .cantidad(2)
                .precioUnitario(20)
                .build());
        when(itemPedidoService.getAllItemPedidos()).thenReturn(itemPedidoDtoList);

        // When & Then
        mockMvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2))
                .andExpect(jsonPath("$[0].precioUnitario").value(20));
    }


    @Test
    void obtenerItemsPedidoPorPedido_ReturnsListOfItemsPedido() throws Exception {
        // Given
        long idPedido = 1L;
        List<ItemPedidoDto> itemPedidoDtoList = Collections.singletonList(ItemPedidoDto.builder()
                .id(1L)
                .cantidad(2)
                .precioUnitario(20)
                .build());
        when(itemPedidoService.buscarItemsPorIdPedido(idPedido)).thenReturn(itemPedidoDtoList);

        // When & Then
        mockMvc.perform(get(API_PATH + "/order/{idPedido}", idPedido))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2))
                .andExpect(jsonPath("$[0].precioUnitario").value(20));
    }

    @Test
    void guardarItemPedido_ReturnsCreated() throws Exception {
        // Given
        ItemPedidoToSaveDto itemPedidoToSaveDto = ItemPedidoToSaveDto.builder()
                .id(1L)
                .cantidad(2)
                .precioUnitario(20)
                .build();
        ItemPedidoDto itemPedidoDto = ItemPedidoDto.builder()
                .id(1L)
                .cantidad(2)
                .precioUnitario(20)
                .build();
        when(itemPedidoService.guardarItemPedido(itemPedidoToSaveDto)).thenReturn(itemPedidoDto);

        // When & Then
        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precioUnitario").value(20));
    }

    @Test
    void actualizarItemPedido_ReturnsOk() throws Exception {
        // Given
        long id = 1L;
        ItemPedidoToSaveDto itemPedidoToSaveDto = ItemPedidoToSaveDto.builder()
                .id(1L)
                .cantidad(2)
                .precioUnitario(20)
                .build();
        ItemPedidoDto itemPedidoDto = ItemPedidoDto.builder()
                .id(id)
                .cantidad(2)
                .precioUnitario(20)
                .build();
        when(itemPedidoService.actualizarItemPedido(id, itemPedidoToSaveDto)).thenReturn(itemPedidoDto);

        // When & Then
        mockMvc.perform(put(API_PATH + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemPedidoDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precioUnitario").value(20));
    }

    @Test
    void eliminarItemPedido_ReturnsNoContent() throws Exception {
        // Given
        long id = 1L;
        willDoNothing().given(itemPedidoService).removerItemPedido(id);

        // When  Then

        mockMvc.perform(delete(API_PATH + "/{id}", id))
                .andExpect(status().isNoContent())
        .andDo(print());
    }
}
