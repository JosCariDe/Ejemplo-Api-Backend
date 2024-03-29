package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

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

        Producto producto2 = Producto.builder()
                .nombre("panela")
                .price(900)
                .stock(120)
                .build();
        productoRepository.save(producto2);

        productoRepository.flush();
    }

    @BeforeEach
    void setUp() {

        productoRepository.deleteAll();
        initMockProductos();

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

    @Test
    void buscarPorTerminoDeBusquedaTest() {

        List<Producto> productoSave = productoRepository.buscarPorTerminoDeBusqueda("vai"); // "vai"nilla
        List<Producto> productoSave2 = productoRepository.buscarPorTerminoDeBusqueda("qui"); //mante"qui"lla
        assertThat(productoSave.isEmpty()).isTrue();
        assertThat(productoSave2.isEmpty()).isFalse();
    }

    @Test
    void findByStockTest() {

        List<Producto> productosSave = productoRepository.findByStock(64);
        List<Producto> productosSave2 = productoRepository.findByStock(1);
        assertThat(productosSave.isEmpty()).isFalse();
        assertThat(productosSave2.isEmpty()).isTrue();

    }

    @Test
    void buscarPorPrecioMaximoYStockMaximo() {

        List<Producto> productosSave = productoRepository.buscarPorPrecioMaximoYStockMaximo(10000,64);

        assertThat(productosSave.size() == 2).isTrue();
        
    }
}