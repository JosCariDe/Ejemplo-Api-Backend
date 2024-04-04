package UnivesidadMagdalena.Tienda.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import UnivesidadMagdalena.Tienda.dto.detalleEnvio.DetalleEnvioDto;
import UnivesidadMagdalena.Tienda.service.DetalleEnvioService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DetalleEnvioControllerTest {

    private static final String API_PATH = "/api/v1/detalle-envio";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetalleEnvioService detalleEnvioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerDetalleEnvioPorId_ReturnsDetalleEnvio() throws Exception {
        // Given
        DetalleEnvioDto detalleEnvioDto = DetalleEnvioDto.builder()
                .id(1L)
                .direccion("Santa Marta")
                .transportadora("Camion")
                .numeroGuia(123456L)
                .build();
        when(detalleEnvioService.buscarDetalleEnvioPorId(anyLong())).thenReturn(detalleEnvioDto);

        // When & Then
        mockMvc.perform(get(API_PATH + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccion").value("Santa Marta"))
                .andExpect(jsonPath("$.transportadora").value("Camion"))
                .andExpect(jsonPath("$.numeroGuia").value(123456))
                .andDo(print());
    }

    @Test
    void obtenerDetalleEnvioPorPedido_ReturnsDetalleEnvio() throws Exception {
        // Given
        DetalleEnvioDto detalleEnvioDto = DetalleEnvioDto.builder()
                .id(1L)
                .direccion("Santa Marta")
                .transportadora("Camion")
                .numeroGuia(123456L)
                .build();
        when(detalleEnvioService.buscarDetallesEnvioPorIdPedido(anyLong())).thenReturn(detalleEnvioDto);

        // When & Then
        mockMvc.perform(get(API_PATH + "/order/{orderId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccion").value("Santa Marta"))
                .andExpect(jsonPath("$.transportadora").value("Camion"))
                .andExpect(jsonPath("$.numeroGuia").value(123456))
                .andDo(print());
    }

    @Test
    void obtenerDetalleEnvioPorTransportadora_ReturnsDetalleEnvio() throws Exception {
        // Given
        DetalleEnvioDto detalleEnvioDto = DetalleEnvioDto.builder()
                .id(1L)
                .direccion("Santa Marta")
                .transportadora("Camion")
                .numeroGuia(123456L)
                .build();
        when(detalleEnvioService.findByTransportadora(anyString())).thenReturn(detalleEnvioDto);

        // When & Then
        mockMvc.perform(get(API_PATH + "/carrier")
                        .param("name", "Camion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccion").value("Santa Marta"))
                .andExpect(jsonPath("$.transportadora").value("Camion"))
                .andExpect(jsonPath("$.numeroGuia").value(123456))
                .andDo(print());
    }

    @Test
    void guardarDetalleEnvio_ReturnsCreatedStatusAndDetalleEnvio() throws Exception {
        // Given
        DetalleEnvioDto detalleEnvioDto = DetalleEnvioDto.builder()
                .direccion("Santa Marta")
                .transportadora("Camion")
                .numeroGuia(123456L)
                .build();
        DetalleEnvioDto detalleEnvioCreado = DetalleEnvioDto.builder()
                .id(1L)
                .direccion("Santa Marta")
                .transportadora("Camion")
                .numeroGuia(123456L)
                .build();
        when(detalleEnvioService.guardarDetalleEnvio(any(DetalleEnvioDto.class))).thenReturn(detalleEnvioCreado);

        // When & Then
        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalleEnvioDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccion").value("Santa Marta"))
                .andExpect(jsonPath("$.transportadora").value("Camion"))
                .andExpect(jsonPath("$.numeroGuia").value(123456))
                .andDo(print());
    }

    @Test
    void obtenerTodosDetallesEnvio_ReturnsListOfDetalleEnvio() throws Exception {
        // Given
        List<DetalleEnvioDto> detallesEnvio = new ArrayList<>();
        detallesEnvio.add(DetalleEnvioDto.builder()
                .id(1L)
                .direccion("Santa Marta")
                .transportadora("Camion")
                .numeroGuia(123456L)
                .build());
        detallesEnvio.add(DetalleEnvioDto.builder()
                .id(2L)
                .direccion("Bogota")
                .transportadora("Avion")
                .numeroGuia(789012L)
                .build());
        when(detalleEnvioService.getAllDetalleEnvio()).thenReturn(detallesEnvio);

        // When & Then
        mockMvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].direccion").value("Santa Marta"))
                .andExpect(jsonPath("$[0].transportadora").value("Camion"))
                .andExpect(jsonPath("$[0].numeroGuia").value(123456))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].direccion").value("Bogota"))
                .andExpect(jsonPath("$[1].transportadora").value("Avion"))
                .andExpect(jsonPath("$[1].numeroGuia").value(789012))
                .andDo(print());
    }


    @Test
    void actualizarDetalleEnvio_ReturnsOkStatusAndDetalleEnvio() throws Exception {
        // Given
        Long detalleEnvioId = 1L;
        DetalleEnvioDto detalleEnvioDto = DetalleEnvioDto.builder()
                .id(detalleEnvioId)
                .direccion("Santa Marta Actualizada")
                .transportadora("Camion Actualizada")
                .numeroGuia(123456L)
                .build();
        when(detalleEnvioService.actualizarDetalleEnvio(eq(detalleEnvioId), any(DetalleEnvioDto.class))).thenReturn(detalleEnvioDto);

        // When & Then
        mockMvc.perform(put(API_PATH + "/{id}", detalleEnvioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalleEnvioDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(detalleEnvioId))
                .andExpect(jsonPath("$.direccion").value("Santa Marta Actualizada"))
                .andExpect(jsonPath("$.transportadora").value("Camion Actualizada"))
                .andExpect(jsonPath("$.numeroGuia").value(123456))
                .andDo(print());
    }

    @Test
    void eliminarDetalleEnvio_ReturnsNoContentStatus() throws Exception {
        // Given
        Long detalleEnvioId = 1L;

        // When & Then
        mockMvc.perform(delete(API_PATH + "/{id}", detalleEnvioId))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}
