package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.detalleEnvio.DetalleEnvioDto;
import UnivesidadMagdalena.Tienda.entities.DetalleEnvio;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import UnivesidadMagdalena.Tienda.exception.DetalleEnvioNotFoundException;
import UnivesidadMagdalena.Tienda.repository.DetalleEnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalleEnvioServiceImplTest {

    @Mock
    private DetalleEnvioRepository detalleEnvioRepository;

    @InjectMocks
    private DetalleEnvioServiceImpl detalleEnvioService;

    DetalleEnvio detalleEnvio;

    @BeforeEach
    void setUp() {
        detalleEnvio = DetalleEnvio.builder()
                .id(1L)
                .transportadora("Camion de Tienda")
                .direccion("Santa Marta")
                .build();
    }

    @Test
    void givenDetalleEnvio_whenGuardarDetalleEnvio_thenReturnDetalleEnvioGuardado() {
        given(detalleEnvioRepository.save(any())).willReturn(detalleEnvio);

        DetalleEnvioDto detalleEnvioDto = DetalleEnvioDto.builder()
                .transportadora("Camion de Tienda")
                .direccion("Santa Marta")
                .build();

        DetalleEnvioDto result = detalleEnvioService.guardarDetalleEnvio(detalleEnvioDto);

        assertNotNull(result);
        assertEquals(detalleEnvio.getId(), result.id());
        assertEquals(detalleEnvio.getTransportadora(), result.transportadora());
        assertEquals(detalleEnvio.getDireccion(), result.direccion());
    }

    @Test
    void givenDetalleEnvioId_whenBuscarDetalleEnvioPorId_thenReturnDetalleEnvio() {
        Long detalleEnvioId = 1L;
        given(detalleEnvioRepository.findById(detalleEnvioId)).willReturn(Optional.of(detalleEnvio));

        DetalleEnvioDto result = detalleEnvioService.buscarDetalleEnvioPorId(detalleEnvioId);

        assertNotNull(result);
        assertEquals(detalleEnvio.getTransportadora(), result.transportadora());
        assertEquals(detalleEnvio.getDireccion(), result.direccion());
    }

    @Test
    void givenDetalleEnvioId_whenBuscarDetalleEnvioPorId_thenThrowDetalleEnvioNotFoundException() {
        given(detalleEnvioRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(DetalleEnvioNotFoundException.class, () -> {
            detalleEnvioService.buscarDetalleEnvioPorId(1L);
        });
    }

    @Test
    void givenTransportadora_whenFindByTransportadora_thenReturnDetalleEnvioDto() {
        String transportadora = "Camion de Tienda";
        given(detalleEnvioRepository.findByTransportadora(transportadora)).willReturn(detalleEnvio);

        DetalleEnvioDto result = detalleEnvioService.findByTransportadora(transportadora);

        assertNotNull(result);
        assertEquals(detalleEnvio.getTransportadora(), result.transportadora());
        assertEquals(detalleEnvio.getDireccion(), result.direccion());
    }

    @Test
    void givenEstadoPedido_whenBuscarDetalleenvioPorEstadoDePedido_thenReturnDetalleEnvioDto() {
        Status estadoPedido = Status.PENDIENTE;
        given(detalleEnvioRepository.buscarDetalleenvioPorEstadoDePedido(estadoPedido)).willReturn(detalleEnvio);

        DetalleEnvioDto result = detalleEnvioService.buscarDetalleenvioPorEstadoDePedido(estadoPedido);

        assertNotNull(result);
        assertEquals(detalleEnvio.getTransportadora(), result.transportadora());
        assertEquals(detalleEnvio.getDireccion(), result.direccion());
    }

    @Test
    void givenId_whenRemoverDetalleEnvio_thenNothing() {
        Long detalleEnvioId = 1L;
        given(detalleEnvioRepository.findById(detalleEnvioId)).willReturn(Optional.of(detalleEnvio));
        willDoNothing().given(detalleEnvioRepository).delete(any());

        assertDoesNotThrow(() -> {
            detalleEnvioService.removerDetalleEnvio(detalleEnvioId);
        });

        verify(detalleEnvioRepository, times(1)).delete(any());
    }


}
