package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
                .id(1L)
                .nombre("Panela")
                .price(1500)
                .stock(120)
                .build();
    }

    @Test
    void givenProducto_whenGuardarProducto_thenReturnProductoGuardado() {
        given(productoRepository.save(any())).willReturn(producto);

        ProductoToSaveDto productoAGuardar = ProductoToSaveDto.builder()
                .nombre("panela")
                .price(1500)
                .stock(120)
                .build();

        ProductoDto productoDto = productoService.guardarProducto(productoAGuardar);

        assertNotNull(productoDto);
        assertEquals(1L, productoDto.id());
        assertEquals("Panela", productoDto.nombre());
        assertEquals(120, productoDto.stock());
        assertEquals(1500, productoDto.price());
    }

    @Test
    void givenProductId_whenBuscarProductoById_thenReturnProducto() {
        Long productoId = 1L;
        given(productoRepository.findById(productoId)).willReturn(Optional.of(producto));

        ProductoDto productoDto = productoService.buscarProductoPorId(productoId);

        assertNotNull(productoDto);
        assertEquals("Panela", productoDto.nombre());
        assertEquals(120, productoDto.stock());
        assertEquals(1500, productoDto.price());
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
        given(productoRepository.findById(productoId)).willReturn(Optional.of(producto));
        willDoNothing().given(productoRepository).delete(any());

        assertDoesNotThrow(() -> {
            productoService.removerProducto(productoId);
        });

        verify(productoRepository, times(1)).delete(any());
    }

    @Test
    void givenNothing_whenGetAllProducto_thenReturnListOfProductos() {
        List<Producto> productos = List.of(producto);
        given(productoRepository.findAll()).willReturn(productos);

        List<ProductoDto> productoDtos = productoService.getAllProducto();

        assertNotNull(productoDtos);
        assertEquals(1,productoDtos.size());

    }

    @Test
    void givenTermino_whenPorTerminoDeBusqueda_thenReturnListOfProductos() {
        String termino = "Panela";
        List<Producto> productos = List.of(producto);
        given(productoRepository.buscarPorTerminoDeBusqueda(any())).willReturn(productos);

        List<ProductoDto> productoDtos = productoService.buscarPorTerminoDeBusqueda(termino);

        assertNotNull(productoDtos);
        assertEquals(1, productoDtos.size());
        assertEquals("Panela" , productoDtos.get(0).nombre());
    }

    @Test
    void givenNothing_whenBuscarEnStock_thenReturnListOfProductos() {
        List<Producto> productos = List.of(producto);
        given(productoRepository.buscarEnStock()).willReturn(productos);

        List<ProductoDto> productoDtos = productoService.buscarEnStock();

        assertNotNull(productoDtos);
        assertFalse(productoDtos.isEmpty());
        assertEquals(1, productoDtos.size());
        assertEquals("Panela", productoDtos.get(0).nombre());
    }

    @Test
    void givenPrecioMaximoAndStockMaximo_whenBuscarPorPrecioMaximoYStockMaximo_thenReturnListOfProductos() {
        Integer precioMaximo = 2000;
        Integer stockMaximo = 200;
        List<Producto> productos = List.of(producto);
        given(productoRepository.buscarPorPrecioMaximoYStockMaximo(precioMaximo, stockMaximo)).willReturn(productos);

        List<ProductoDto> productoDtos = productoService.buscarPorPrecioMaximoYStockMaximo(precioMaximo, stockMaximo);

        assertNotNull(productoDtos);
        assertFalse(productoDtos.isEmpty());
        assertEquals(1, productoDtos.size());
        assertEquals("Panela", productoDtos.get(0).nombre());
    }

}
