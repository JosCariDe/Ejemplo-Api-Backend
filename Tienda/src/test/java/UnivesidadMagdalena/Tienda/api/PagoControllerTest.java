package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.pago.PagoDto;
import UnivesidadMagdalena.Tienda.dto.pago.PagoToSaveDto;
import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;
import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import UnivesidadMagdalena.Tienda.exception.PagoNotFoundException;
import UnivesidadMagdalena.Tienda.service.PagoService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PagoControllerTest {
    private static final String API_PATH = "/api/v1/pagos";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerPagoPorId_ReturnsPago() throws Exception {
        // Given
        Long idPago = 1L;
        PagoDto pagoDto = PagoDto.builder()
                .id(idPago)
                .pedido(PedidoDto.builder().id(1L).build())
                .totalPago(100)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .build();
        when(pagoService.buscarPagoPorId(idPago)).thenReturn(pagoDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/{id}", idPago))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idPago))
                .andExpect(jsonPath("$.totalPago").value(100))
                .andDo(print());
    }

    @Test
    void obtenerTodosPagos_ReturnsListOfPagos() throws Exception {
        // Given
        List<PagoDto> pagos = List.of(
                PagoDto.builder()
                        .id(1L)
                        .pedido(PedidoDto.builder().id(1L).build())
                        .totalPago(100)
                        .fechaPago(LocalDateTime.now())
                        .metodoPago(MetodoPago.EFECTIVO)
                        .build(),
                PagoDto.builder()
                        .id(2L)
                        .pedido(PedidoDto.builder().id(2L).build())
                        .totalPago(200)
                        .fechaPago(LocalDateTime.now())
                        .metodoPago(MetodoPago.EFECTIVO)
                        .build()
        );
        when(pagoService.getAllPagos()).thenReturn(pagos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].totalPago").value(100))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].totalPago").value(200))
                .andDo(print());
    }

    @Test
    void obtenerPagosPorPedido_ReturnsPago() throws Exception {
        // Given
        Long idPedido = 1L;
        PagoDto pagoDto = PagoDto.builder()
                .id(1L)
                .pedido(PedidoDto.builder().id(idPedido).build())
                .totalPago(100)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.PAYPAL)
                .build();
        when(pagoService.buscarPagoPorIdPedido(idPedido)).thenReturn(pagoDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/pedido/{idPedido}", idPedido))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.pedido.id").value(idPedido))
                .andExpect(jsonPath("$.totalPago").value(100))
                .andDo(print());
    }

    @Test
    void obtenerPagosPorRangoFecha_ReturnsListOfPagos() throws Exception {
        // Given
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        List<PagoDto> pagos = List.of(
                PagoDto.builder()
                        .id(1L)
                        .pedido(PedidoDto.builder().id(1L).build())
                        .totalPago(100)
                        .fechaPago(startDate)
                        .metodoPago(MetodoPago.NEQUI)
                        .build(),
                PagoDto.builder()
                        .id(2L)
                        .pedido(PedidoDto.builder().id(2L).build())
                        .totalPago(200)
                        .fechaPago(endDate)
                        .metodoPago(MetodoPago.EFECTIVO)
                        .build()
        );
        when(pagoService.buscarPorFechaPagoEntre(startDate, endDate)).thenReturn(pagos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/date-range")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].totalPago").value(100))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].totalPago").value(200))
                .andDo(print());
    }

    @Test
    void guardarPago_ReturnsCreated() throws Exception {
        // Given
        PagoToSaveDto pagoToSaveDto = PagoToSaveDto.builder()
                .id(1L)
                .totalPago(100)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.NEQUI)
                .build();
        PagoDto pagoDto = PagoDto.builder()
                .id(1L)
                .pedido(PedidoDto.builder().id(1L).build())
                .totalPago(100)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.NEQUI)
                .build();
        when(pagoService.guardarPago(pagoToSaveDto)).thenReturn(pagoDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoToSaveDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalPago").value(100))
                .andDo(print());
    }

    @Test
    void actualizarPago_ReturnsUpdated() throws Exception {
        // Given
        Long idPago = 1L;
        PagoToSaveDto pagoToSaveDto = PagoToSaveDto.builder()
                .id(1L)
                .totalPago(200)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .build();
        PagoDto pagoDto = PagoDto.builder()
                .id(idPago)
                .pedido(PedidoDto.builder().id(1L).build())
                .totalPago(200)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .build();
        when(pagoService.actualizarPago(idPago, pagoToSaveDto)).thenReturn(pagoDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + "/{id}", idPago)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoToSaveDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idPago))
                .andExpect(jsonPath("$.totalPago").value(200))
                .andDo(print());
    }

    @Test
    void eliminarPago_ReturnsNoContent() throws Exception {
        // Given
        Long idPago = 1L;
        // No need to mock service call as it returns void

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + "/{id}", idPago))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
