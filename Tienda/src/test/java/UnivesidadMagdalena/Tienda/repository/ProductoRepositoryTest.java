package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProductoRepositoryTest extends AbstractIntegrationDBTest{
    ProductoRepository productoRepository;

    @Autowired
    public ProductoRepositoryTest(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    void initMockProductos() {
        Producto producto = Producto.builder()
                .nombre("mantequilla")
                .price(1500)
                .stock(64)
                .build();
        productoRepository.save(producto);

        Producto producto1 = Producto.builder()
                .nombre("cereal")
                .price(8500)
                .stock(18)
                .build();
        productoRepository.save(producto1);
        productoRepository.flush();
    }

    @BeforeEach
    void setUp() {

        productoRepository.deleteAll();

    }

    @Test
    void givenAnProducto_whenSave_thenProductoWithId() {
        //given
        Producto producto = Producto.builder()
                .nombre("vainilla")
                .price(4500)
                .stock(36)
                .build();
        //when
        Producto productoSave = productoRepository.save(producto);
        //then
        assertThat(productoSave.getId()).isNotNull();
    }
}