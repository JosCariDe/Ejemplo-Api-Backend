package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Pago;
import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class PagoRepositoryTest extends AbstractIntegrationDBTest{

    PagoRepository pagoRepository;
    @Autowired
    public PagoRepositoryTest(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @BeforeEach
    void setUp() {

        pagoRepository.deleteAll();

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
    void findByFechaPagoBetween() {


    }

    @Test
    void findByIdAndMetodoPago() {
    }
}