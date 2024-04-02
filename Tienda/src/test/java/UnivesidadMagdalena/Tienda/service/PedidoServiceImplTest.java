package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.entities.Pedido;
import UnivesidadMagdalena.Tienda.enumClass.Status;

import UnivesidadMagdalena.Tienda.exception.PedidoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.PedidoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    Pedido pedido;

    @BeforeEach
    void setUp() {
        Cliente cliente = Cliente.builder().
                id(1L).build();
        pedido = Pedido.builder()
                .id(1L)
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .status(Status.PENDIENTE)
                .build();
    }

    @Test
    void givenPedido_whenGuardarPedido_thenReturnPedidoGuardado() {
        given(pedidoRepository.save(any())).willReturn(pedido);
        ItemPedidoDto itemPedidoDto = ItemPedidoDto.builder().build();
        ClienteDto cliente = ClienteDto.builder().id(1L).build();

        PedidoDto pedidoAGuardar = PedidoDto.builder()
                .id(1L)
                .cliente(cliente)
                .itemPedidos(List.of(itemPedidoDto))
                .fechaPedido(LocalDateTime.now())
                .status(Status.PENDIENTE)
                .build();

        PedidoDto pedidoDto = pedidoService.guardarPedido(pedidoAGuardar);

        assertNotNull(pedidoDto);
        assertEquals(1L, pedidoDto.id());
        assertEquals(Status.PENDIENTE, pedidoDto.status());
    }

    @Test
    void givenPedidoId_whenBuscarPedidoById_thenReturnPedido() {
        Long pedidoId = 1L;
        given(pedidoRepository.findById(pedidoId)).willReturn(Optional.of(pedido));

        PedidoDto pedidoDto = pedidoService.buscarPedidoPorId(pedidoId);

        assertNotNull(pedidoDto);
        assertEquals(Status.PENDIENTE, pedidoDto.status());
    }

    @Test
    void givenPedidoId_whenBuscarPedidoById_thenThrowPedidoNotFoundException() {
        given(pedidoRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(PedidoNotFoundException.class, () -> {
            pedidoService.buscarPedidoPorId(1L);
        });
    }

    @Test
    void givenPedidoId_whenRemoverPedido_thenNothing() {
        Long pedidoId = 1L;
        given(pedidoRepository.findById(pedidoId)).willReturn(Optional.of(pedido));
        willDoNothing().given(pedidoRepository).delete(any());

        assertDoesNotThrow(() -> {
            pedidoService.removerPedido(pedidoId);
        });

        verify(pedidoRepository, times(1)).delete(any());
    }

    @Test
    void givenNothing_whenGetAllPedidos_thenReturnListOfPedidos() {
        List<Pedido> pedidos = List.of(pedido);
        given(pedidoRepository.findAll()).willReturn(pedidos);

        List<PedidoDto> pedidoDtos = pedidoService.getAllPedidos();

        assertNotNull(pedidoDtos);
        assertFalse(pedidoDtos.isEmpty());
        assertEquals(1, pedidoDtos.size());
    }

    @Test
    void givenPedidoAndStatus_whenActualizarPedido_thenPedidoActualizado() {
        Long pedidoId = 1L;
        PedidoDto pedidoDto = PedidoDto.builder()
                .id(pedidoId)
                .fechaPedido(LocalDateTime.now())
                .status(Status.ENTREGADO)
                .build();

        given(pedidoRepository.findById(pedidoId)).willReturn(Optional.of(pedido));
        given(pedidoRepository.save(any())).willReturn(pedido);

        PedidoDto pedidoActualizado = pedidoService.actualizarPedido(pedidoId, pedidoDto);

        assertNotNull(pedidoActualizado);
        assertEquals(pedidoDto.status(), pedidoActualizado.status());
    }

    @Test
    void givenClienteAndStatus_whenFindByClienteAndStatus_thenReturnPedidos() {
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Jose Narvaez")
                .email("jose.narvaez@gmail.com")
                .direccion("Ciudad Santa Marta")
                .build();

        given(pedidoRepository.findByClienteAndStatus(cliente, Status.PENDIENTE)).willReturn(List.of(pedido));

        List<PedidoDto> pedidos = pedidoService.findByClienteAndStatus(cliente, Status.PENDIENTE);

        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
    }

    @Test
    void givenClienteId_whenFindPedidosConArticulosByCliente_thenReturnPedidos() {
        Long clienteId = 1L;

        given(pedidoRepository.findPedidosConArticulosByCliente(clienteId)).willReturn(List.of(pedido));

        List<PedidoDto> pedidos = pedidoService.findPedidosConArticulosByCliente(clienteId);

        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
    }

    @Test
    void givenFechaInicioAndFechaFin_whenFindByFechaPedidoBetween_thenReturnPedidos() {
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fechaFin = LocalDateTime.now();

        given(pedidoRepository.findByFechaPedidoBetween(fechaInicio, fechaFin)).willReturn(List.of(pedido));

        List<PedidoDto> pedidos = pedidoService.findByFechaPedidoBetween(fechaInicio, fechaFin);

        assertNotNull(pedidos);
        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
    }

}
