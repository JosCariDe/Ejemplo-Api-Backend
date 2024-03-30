package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import UnivesidadMagdalena.Tienda.entities.Pedido;
import UnivesidadMagdalena.Tienda.entities.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ItemPedidoRepositoryTest extends AbstractIntegrationDBTest{

    ItemPedidoRepository itemPedidoRepository;
    PedidoRepository pedidoRepository;
    ClienteRepository clienteRepository;
    ProductoRepository productoRepository;
    @Autowired
    public ItemPedidoRepositoryTest(ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @BeforeEach
    void setUp() {

        clienteRepository.deleteAll();
        pedidoRepository.deleteAll();
        itemPedidoRepository.deleteAll();

    }

    @Test
    void givenItemPedido_whenSave_thenItemPedidoWithId() {
        //given
        Cliente cliente = Cliente.builder().build();
        clienteRepository.save(cliente);
        Pedido pedido = Pedido.builder()
                .cliente(cliente).build();
        pedidoRepository.save(pedido);
        ItemPedido itemPedido = ItemPedido.builder()
                .pedido(pedido).build();
        //when
        ItemPedido itemPedidoSave = itemPedidoRepository.save(itemPedido);
        //then
        assertThat(itemPedidoSave.getId()).isNotNull();

    }

    @Test
    void buscarItemsPorIdPedido() {
        Cliente cliente = Cliente.builder().build();
        clienteRepository.save(cliente);
        Pedido pedido = Pedido.builder()
                .cliente(cliente).build();
        pedidoRepository.save(pedido);
        ItemPedido itemPedido = ItemPedido.builder()
                .pedido(pedido).build();
        //when
        itemPedidoRepository.save(itemPedido);
        List<ItemPedido> itemPedidoList = itemPedidoRepository.buscarItemsPorIdPedido(pedido.getId());

        assertThat(itemPedidoList.isEmpty()).isFalse();
    }

    @Test
    void findByProducto() {

        Cliente cliente = Cliente.builder().build();
        clienteRepository.save(cliente);

        Pedido pedido = Pedido.builder()
                .cliente(cliente).build();
        pedidoRepository.save(pedido);

        Producto producto = Producto.builder().build();
        productoRepository.save(producto);

        ItemPedido itemPedido = ItemPedido.builder()
                .pedido(pedido)
                .producto(producto)
                        .build();
        //when
        itemPedidoRepository.save(itemPedido);
        List<ItemPedido> itemPedidoList = itemPedidoRepository.findByProducto(producto);

        assertThat(itemPedidoList.isEmpty()).isFalse();
    }

    @Test
    void calcularTotalVentasPorProducto() {
        Cliente cliente = Cliente.builder().build();
        clienteRepository.save(cliente);

        Pedido pedido = Pedido.builder()
                .cliente(cliente).build();
        pedidoRepository.save(pedido);

        Producto producto = Producto.builder().build();
        productoRepository.save(producto);

        ItemPedido itemPedido = ItemPedido.builder()
                .pedido(pedido)
                .producto(producto)
                .cantidad(25)
                .precioUnitario(100)
                .build();
        //when
        itemPedidoRepository.save(itemPedido);
        Integer cantidadDePedidosPorProducto = itemPedidoRepository.calcularTotalVentasPorProducto(producto);

        assertThat(cantidadDePedidosPorProducto == 2500).isTrue();
    }
}