package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import UnivesidadMagdalena.Tienda.entities.Pedido;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoRepositoryTest extends AbstractIntegrationDBTest{

    PedidoRepository pedidoRepository;
    ClienteRepository clienteRepository;
    ProductoRepository productoRepository;
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public PedidoRepositoryTest(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ItemPedidoRepository itemPedidoRepository, ProductoRepository productoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();
        itemPedidoRepository.deleteAll();
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

        Producto producto = Producto.builder()
                .nombre("mantequilla")
                .price(2500).build();
        Producto producto1 = Producto.builder()
                .nombre("Galleta")
                .price(25000).build();
        productoRepository.saveAll(Arrays.asList(producto1,producto));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .status(Status.PENDIENTE)
                .build();

        cliente.setPedidos(Arrays.asList(pedido));
        clienteRepository.save(cliente);

        ItemPedido itemPedido = ItemPedido.builder()
                .pedido(pedido)
                .producto(producto)
                .cantidad(5)
                .precioUnitario(producto.getPrice()).build();

        ItemPedido itemPedido1 = ItemPedido.builder()
                .pedido(pedido)
                .producto(producto1)
                .cantidad(4)
                .precioUnitario(producto1.getPrice()).build();

        itemPedidoRepository.saveAll(Arrays.asList(itemPedido,itemPedido1));

        pedido.setItemPedidos(Arrays.asList(itemPedido,itemPedido1));

        pedidoRepository.save(pedido);

        List<Pedido> pedidoList = pedidoRepository.findPedidosConArticulosByCliente(cliente.getId());

        assertThat(pedidoList).isNotEmpty();
    }
}