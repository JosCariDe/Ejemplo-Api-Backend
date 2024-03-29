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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoRepositoryTest {

    PedidoRepository pedidoRepository;

    @Autowired
    public PedidoRepositoryTest(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @BeforeEach
    void setUp() {

        pedidoRepository.deleteAll();

    }

    void initMockPedidos() {
        Cliente cliente = Cliente.builder()
                .nombre("Jose")
                .build();
        Cliente cliente1 = Cliente.builder()
                .nombre("David")
                .build();

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
                .itemPedidos(itemPedidoList)
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
    }

    @Test
    void findByClienteAndStatus() {
    }

    @Test
    void findPedidosConArticulosByCliente() {
    }
}