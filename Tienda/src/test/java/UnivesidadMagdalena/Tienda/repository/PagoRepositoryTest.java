package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import UnivesidadMagdalena.Tienda.entities.Pago;
import UnivesidadMagdalena.Tienda.entities.Pedido;
import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class PagoRepositoryTest extends AbstractIntegrationDBTest{

    PagoRepository pagoRepository;
    PedidoRepository pedidoRepository;
    ClienteRepository clienteRepository;


    @Autowired
    public PagoRepositoryTest(PagoRepository pagoRepository, PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagoRepository = pagoRepository;
    }

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
        pedidoRepository.deleteAll();
        pagoRepository.deleteAll();

    }

    void deletePedidoRepository() {
        pedidoRepository.deleteAll();
    }

    void initMockPagos() {
        Pago pago = Pago.builder()
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.NEQUI)
                .build();
        pagoRepository.save(pago);
    }

    @Test
    void givenAnPago_whenSave_thenPagoWithId() {
        //given
        Pago pago = Pago.builder()
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.PAYPAL)
                .build();
        pagoRepository.save(pago);
        //when
        Pago pagoSave = pagoRepository.save(pago);
        //then
        assertThat(pagoSave.getId()).isNotNull();
    }



    @Test
    void findByFechaPagoBetween_ReturnsPagosBetweenDates() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.now();

        initMockPagos();

        List<Pago> pagos = pagoRepository.findByFechaPagoBetween(startDate, endDate);

        assertThat(pagos).isNotNull();
    }

    @Test
    void findByIdAndMetodoPago_ReturnsPagosByIdAndMetodoPago() {

        Long id = 1L;
        MetodoPago metodoPago = MetodoPago.NEQUI;

        initMockPagos();
        List<Pago> pagos = pagoRepository.findByIdAndMetodoPago(id, metodoPago);


        assertThat(pagos).isNotNull();

    }

    @Test
    void buscarPagoPorIdPedido_ReturnsPagoByPedidoId() {

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

        Pago pago = Pago.builder()
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.NEQUI)
                .pedido(pedido)
                .pedido(pedido)
                .build();
        pedido.setPago(pago);
        pedidoRepository.save(pedido);
        pagoRepository.save(pago);

        Long idPedido = 1L;

        Pago pagoSaved = pagoRepository.buscarPagoPorIdPedido(idPedido);

        assertThat(pagoSaved).isNotNull();
        assertThat(pagoSaved.getPedido().getId() == 1).isTrue();


    }
}