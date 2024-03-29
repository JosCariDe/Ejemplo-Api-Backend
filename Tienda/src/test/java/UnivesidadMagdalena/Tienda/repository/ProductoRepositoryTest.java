package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

class ProductoRepositoryTest extends AbstractIntegrationDBTest {

    ProductoRepository productoRepository;

    @Autowired
    public ProductoRepositoryTest(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    private void initMockProductos(){

        Producto producto = Producto.builder()
                .nombre("Mantequilla")
                .price(2500)
                .stock(64)
                .build();
        productoRepository.save(producto);

        Producto producto1 = Producto.builder()
                .nombre("Cereal")
                .price(8500)
                .stock(24)
                .build();
        productoRepository.save(producto1);

        Producto producto2 = Producto.builder()
                .nombre("Panela")
                .price(1200)
                .stock(120)
                .build();
        productoRepository.save(producto2);

    }

    @BeforeEach
    void setUp() {

        productoRepository.deleteAll();

    }

    /*
    @Test
    void givenProducto_whenSaved_FoundById() {
        //given
        Producto testProduct = Producto.builder()
                .nombre("Galleta")
                .price(500)
                .stock(80)
                .build();
        //when
        productoRepository.save(testProduct);
        //then
        assertThat(testProduct.getId()).isNotNull();
    } */


    
    /*
    @Test
    void findByStock() {
    }

    @Test
    void buscarPorPrecioMaximoYStockMaximo() {
    } */
}