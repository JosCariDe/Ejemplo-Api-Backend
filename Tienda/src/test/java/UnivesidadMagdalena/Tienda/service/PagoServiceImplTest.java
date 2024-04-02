package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.pago.PagoDto;
import UnivesidadMagdalena.Tienda.dto.pago.PagoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Pago;
import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import UnivesidadMagdalena.Tienda.exception.PagoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceImplTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoServiceImpl pagoService;

    Pago pago;

    @BeforeEach
    void setUp() {
        pago = Pago.builder()
                .id(1L)
                .metodoPago(MetodoPago.TARJETA_CREDITO)
                .fechaPago(LocalDateTime.of(2024, 4, 1, 0, 0)) // Cambiado a 1/04/2024
                .totalPago(1500)
                .build();
    }

    @Test
    void givenPago_whenGuardarPago_thenReturnPagoGuardado() {
        given(pagoRepository.save(any())).willReturn(pago);

        PagoToSaveDto pagoAGuardar = PagoToSaveDto.builder()
                .metodoPago(MetodoPago.TARJETA_CREDITO)
                .fechaPago(LocalDateTime.of(2024, 4, 1, 0, 0)) // Cambiado a 1/04/2024
                .totalPago(1500)
                .build();

        PagoDto pagoDto = pagoService.guardarPago(pagoAGuardar);

        assertNotNull(pagoDto);
        assertEquals(1L, pagoDto.id());
        assertEquals(MetodoPago.TARJETA_CREDITO, pagoDto.metodoPago());
        assertEquals(LocalDateTime.of(2024, 4, 1, 0, 0), pagoDto.fechaPago()); // Cambiado a 1/04/2024
        assertEquals(1500, pagoDto.totalPago());
    }

    @Test
    void givenPagoId_whenBuscarPagoPorId_thenReturnPago() {
        Long pagoId = 1L;
        given(pagoRepository.findById(pagoId)).willReturn(Optional.of(pago));

        PagoDto pagoDto = pagoService.buscarPagoPorId(pagoId);

        assertNotNull(pagoDto);
        assertEquals(MetodoPago.TARJETA_CREDITO, pagoDto.metodoPago());
        assertEquals(LocalDateTime.of(2024, 4, 1, 0, 0), pagoDto.fechaPago()); // Cambiado a 1/04/2024
        assertEquals(1500, pagoDto.totalPago());
    }

    @Test
    void givenPagoId_whenBuscarPagoPorId_thenThrowPagoNotFoundException() {
        given(pagoRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(PagoNotFoundException.class, () -> {
            pagoService.buscarPagoPorId(1L);
        });
    }

    @Test
    void givenPagoId_whenRemoverPago_thenNothing() {
        Long pagoId = 1L;
        given(pagoRepository.findById(pagoId)).willReturn(Optional.of(pago));
        willDoNothing().given(pagoRepository).delete(any());

        assertDoesNotThrow(() -> {
            pagoService.removerPago(pagoId);
        });

        verify(pagoRepository, times(1)).delete(any());
    }

    @Test
    void givenRangoFechas_whenBuscarPorFechaPagoEntre_thenReturnListOfPagoDto() {
        LocalDateTime fechaInicio = LocalDateTime.of(2024, 4, 1, 0, 0); // Cambiado a 1/04/2024
        LocalDateTime fechaFin = LocalDateTime.of(2024, 4, 2, 0, 0); // Cambiado a 2/04/2024
        List<Pago> pagos = Collections.singletonList(pago);
        given(pagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin)).willReturn(pagos);

        List<PagoDto> pagoDtos = pagoService.buscarPorFechaPagoEntre(fechaInicio, fechaFin);

        assertNotNull(pagoDtos);
        assertFalse(pagoDtos.isEmpty());
        assertEquals(1, pagoDtos.size());
        assertEquals(MetodoPago.TARJETA_CREDITO, pagoDtos.get(0).metodoPago());
        assertEquals(LocalDateTime.of(2024, 4, 1, 0, 0), pagoDtos.get(0).fechaPago()); // Cambiado a 1/04/2024
        assertEquals(1500, pagoDtos.get(0).totalPago());
    }

    @Test
    void givenIdAndMetodoPago_whenBuscarPorIdYMetodoPago_thenReturnListOfPagoDto() {
        Long id = 1L;
        MetodoPago metodoPago = MetodoPago.TARJETA_CREDITO;
        List<Pago> pagos = Collections.singletonList(pago);
        given(pagoRepository.findByIdAndMetodoPago(id, metodoPago)).willReturn(pagos);

        List<PagoDto> pagoDtos = pagoService.buscarPorIdYMetodoPago(id, metodoPago);

        assertNotNull(pagoDtos);
        assertFalse(pagoDtos.isEmpty());
        assertEquals(1, pagoDtos.size());
        assertEquals(MetodoPago.TARJETA_CREDITO, pagoDtos.get(0).metodoPago());
        assertEquals(LocalDateTime.of(2024, 4, 1, 0, 0), pagoDtos.get(0).fechaPago()); // Cambiado a 1/04/2024
        assertEquals(1500, pagoDtos.get(0).totalPago());
    }

}
