package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoMapper;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ItemPedidoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ItemPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemPedidoServiceImplTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;

    ItemPedido itemPedido;

    @BeforeEach
    void setUp() {
        itemPedido = ItemPedido.builder()
                .id(1L)
                .cantidad(5)
                .precioUnitario(1500)
                .build();
    }

    @Test
    void givenItemPedido_whenGuardarItemPedido_thenReturnItemPedidoGuardado() {
        given(itemPedidoRepository.save(any())).willReturn(itemPedido);

        ItemPedidoToSaveDto itemPedidoAGuardar = ItemPedidoToSaveDto.builder()
                .cantidad(5)
                .precioUnitario(1500)
                .build();

        ItemPedidoDto itemPedidoDto = itemPedidoService.guardarItemPedido(itemPedidoAGuardar);

        assertNotNull(itemPedidoDto);
        assertEquals(1L, itemPedidoDto.id());
        assertEquals(5, itemPedidoDto.cantidad());
        assertEquals(1500, itemPedidoDto.precioUnitario());
    }

    @Test
    void givenItemId_whenBuscarItemPedidoById_thenReturnItemPedido() {
        Long itemId = 1L;
        given(itemPedidoRepository.findById(itemId)).willReturn(Optional.of(itemPedido));

        ItemPedidoDto itemPedidoDto = itemPedidoService.buscarItemPedidoPorId(itemId);

        assertNotNull(itemPedidoDto);
        assertEquals(5, itemPedidoDto.cantidad());
        assertEquals(1500, itemPedidoDto.precioUnitario());
    }

    @Test
    void givenItemId_whenBuscarItemPedidoById_thenThrowItemPedidoNotFoundException() {
        given(itemPedidoRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ItemPedidoNotFoundException.class, () -> {
            itemPedidoService.buscarItemPedidoPorId(1L);
        });
    }

    @Test
    void givenItemId_whenRemoverItemPedido_thenNothing() {
        Long itemId = 1L;
        given(itemPedidoRepository.findById(itemId)).willReturn(Optional.of(itemPedido));
        willDoNothing().given(itemPedidoRepository).delete(any());

        assertDoesNotThrow(() -> {
            itemPedidoService.removerItemPedido(itemId);
        });

        verify(itemPedidoRepository, times(1)).delete(any());
    }

    @Test
    void givenIdPedido_whenBuscarItemsPorIdPedido_thenReturnListOfItemPedidoDto() {
        Long idPedido = 1L;
        List<ItemPedido> itemPedidos = Collections.singletonList(itemPedido);
        given(itemPedidoRepository.buscarItemsPorIdPedido(idPedido)).willReturn(itemPedidos);

        List<ItemPedidoDto> itemPedidoDtos = itemPedidoService.buscarItemsPorIdPedido(idPedido);

        assertNotNull(itemPedidoDtos);
        assertFalse(itemPedidoDtos.isEmpty());
        assertEquals(1, itemPedidoDtos.size());
        assertEquals(5, itemPedidoDtos.get(0).cantidad());
        assertEquals(1500, itemPedidoDtos.get(0).precioUnitario());
    }

    @Test
    void givenProducto_whenFindByProducto_thenReturnListOfItemPedidoDto() {
        Producto producto = Producto.builder()
                .id(1L)
                .nombre("panela")
                .build();
        List<ItemPedido> itemPedidos = Collections.singletonList(itemPedido);
        given(itemPedidoRepository.findByProducto(producto)).willReturn(itemPedidos);

        List<ItemPedidoDto> itemPedidoDtos = itemPedidoService.findByProducto(producto);

        assertNotNull(itemPedidoDtos);
        assertFalse(itemPedidoDtos.isEmpty());
        assertEquals(1, itemPedidoDtos.size());
        assertEquals(5, itemPedidoDtos.get(0).cantidad());
        assertEquals(1500, itemPedidoDtos.get(0).precioUnitario());
    }

    @Test
    void givenProducto_whenCalcularTotalVentasPorProducto_thenReturnTotalVentas() {
        Producto producto = Producto.builder()
                .id(1L)
                .nombre("panela")
                .build();
        given(itemPedidoRepository.calcularTotalVentasPorProducto(producto)).willReturn(7500);

        Integer totalVentas = itemPedidoService.calcularTotalVentasPorProducto(producto);

        assertNotNull(totalVentas);
        assertEquals(7500, totalVentas);
    }

    @Test
    void givenProducto_whenCalcularTotalVentasPorProducto_thenThrowItemPedidoNotFoundException() {
        Producto producto = Producto.builder()
                .id(1L)
                .nombre("panela")
                .build();
        given(itemPedidoRepository.calcularTotalVentasPorProducto(producto)).willReturn(null);

        assertThrows(ItemPedidoNotFoundException.class, () -> {
            itemPedidoService.calcularTotalVentasPorProducto(producto);
        });
    }

}
