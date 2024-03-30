package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import UnivesidadMagdalena.Tienda.entities.Pedido;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoRepositoryTest extends AbstractIntegrationDBTest{

    PedidoRepository pedidoRepository;
    ClienteRepository clienteRepository;

    @Autowired
    public PedidoRepositoryTest(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @BeforeEach
    void setUp() {

        clienteRepository.deleteAll();
        pedidoRepository.deleteAll();
        initMockPedidos();

    }

    void initMockPedidos() {
        Cliente cliente = Cliente.builder()
                .nombre("Jose")
                .build();
        clienteRepository.save(cliente);
        Cliente cliente1 = Cliente.builder()
                .nombre("David")
                .build();
        clienteRepository.save(cliente1);
        List<ItemPedido> itemPedidoList = new ArrayList<>();


        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .status(Status.PENDIENTE)
                .itemPedidos(itemPedidoList)
                .build();
        pedidoRepository.save(pedido);

        Pedido pedido1 = Pedido.builder()
                .cliente(cliente1)
                .status(Status.ENTREGADO)
                .build();
        pedidoRepository.save(pedido1);

        pedidoRepository.flush();


    }

    @Test
    void givenAnPedido_whenSave_thenPedidoWithId() {
        //given
        Cliente cliente = Cliente.builder()
                .nombre("Jose")
                .build();
        clienteRepository.save(cliente);
        List<ItemPedido> itemPedidoList = new ArrayList<>();
        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .status(Status.ENTREGADO)
                .itemPedidos(itemPedidoList)
                .build();
        //when
        Pedido pedidoSave = pedidoRepository.save(pedido);
        //then
        assertThat(pedidoSave.getId()).isNotNull();

    }



    @Test
    void findByFechaPedidoBetween() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime fechaFin = LocalDateTime.now();
        List<Pedido> pedidoList = pedidoRepository.findByFechaPedidoBetween(fechaInicio, fechaFin);

        assertThat(pedidoList.isEmpty()).isFalse();

    }

    @Test
    void findByClienteAndStatus() {
        Cliente cliente = Cliente.builder()
                .nombre("Pacheco")
                .build();
        clienteRepository.save(cliente);

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .status(Status.PENDIENTE)
                .build();
        pedidoRepository.save(pedido);
        List<Pedido> pedidoList = pedidoRepository.findByClienteAndStatus(cliente, Status.PENDIENTE);
        assertThat(pedidoList).isNotEmpty();
    }

    @Test
    void findPedidosConArticulosByCliente() {

        Cliente cliente = Cliente.builder()
                .nombre("Pacheco")
                .build();
        clienteRepository.save(cliente);

        List<ItemPedido> itemPedidoList = new ArrayList<>();

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .status(Status.PENDIENTE)
                .itemPedidos(itemPedidoList)
                .build();
        pedidoRepository.save(pedido);

        List<Pedido> pedidoList = pedidoRepository.findPedidosConArticulosByCliente(cliente);

        assertThat(pedidoList).isNotEmpty();
    }
}