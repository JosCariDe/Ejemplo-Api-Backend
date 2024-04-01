package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ProductoRepository;
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
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    Producto producto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .nombre("nombre")
                .price(10)
                .stock(100)
                .build();
    }

    @Test
    void givenProducto_whenGuardarProducto_thenReturnProductoGuardado() {
        given(productoRepository.save(any())).willReturn(producto);

        ProductoToSaveDto productoAGuardar = ProductoToSaveDto.builder()
                .id(2L)
                .nombre("Nombre")
                .price(10)
                .stock(100)
                .build();

        ProductoDto productoDto = productoService.guardarProducto(productoAGuardar);

        assertNotNull(productoDto);
        assertEquals("Nombre", productoDto.nombre());
        assertEquals(10, productoDto.stock());
        assertEquals(100, productoDto.price());
    }

    @Test
    void givenProductId_whenBuscarProductoById_thenReturnProducto() {
        Long productoId = 1L;
        given(productoRepository.findById(productoId)).willReturn(Optional.of(producto));

        ProductoDto productoDto = productoService.buscarProductoPorId(productoId);

        assertNotNull(productoDto);
        assertEquals("Nombre", productoDto.nombre());
        assertEquals(10, productoDto.stock());
        assertEquals(100, productoDto.price());
    }

    @Test
    void givenProductId_whenBuscarProductoById_thenThrowProductoNotFoundException() {
        given(productoRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ProductoNotFoundException.class, () -> {
            productoService.buscarProductoPorId(1L);
        });
    }

    @Test
    void givenProductId_whenRemoverProducto_thenNothing() {
        Long productoId = 1L;
        willDoNothing().given(productoRepository).delete(any());

        assertDoesNotThrow(() -> {
            productoService.removerProducto(productoId);
        });

        verify(productoRepository, times(1)).delete(any());
    }

    // Aquí puedes agregar más pruebas para otros métodos del servicio de Producto

}
