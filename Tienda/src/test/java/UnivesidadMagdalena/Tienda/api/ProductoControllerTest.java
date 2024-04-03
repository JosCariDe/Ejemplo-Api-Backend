package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;
import UnivesidadMagdalena.Tienda.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.ArgumentMatchers;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest No escanea todos los componentes necesarios
public class ProductoControllerTest {
    private static final String API_PATH = "/api/v1/productos";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductoService productoService;
    @Autowired
    private ObjectMapper objectMapper;
    private Producto producto;
    private ProductoDto productoDto;
    private ProductoToSaveDto productoToSaveDto;

    @Test
    public void givenProducto_whenSaveProducto_thenReturnSavedProducto() throws Exception {
        //given
        ProductoDto productoDto = ProductoDto.builder()
                .id(1L)
                .nombre("Panela")
                .price(1500)
                .stock(120)
                .itemPedidos(Collections.emptyList())
                .itemPedidos(Collections.emptyList()).build();
        //Mocking
        when(productoService.guardarProducto(any())).thenReturn(productoDto);
        //when(productoService.guardarProducto(ArgumentMatchers.any(ProductoToSaveDto.class)))
          //      .thenReturn(productoDto);
        //WHEN
        ResultActions response  = mockMvc.perform(post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoDto)));

        //THEN
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre",is(productoDto.nombre())));


    }

    @Test
    void obtenerTodosLosProductos_ReturnsListOfProducts() throws Exception {
        // Arrange
        List<ProductoDto> productos = new ArrayList<>();
        productos.add((ProductoDto.builder()
                .id(1L)
                .nombre("Cereal")
                .stock(25)
                .price(8500)
                .itemPedidos(Collections.emptyList())
                .itemPedidos(Collections.emptyList())
                .build()));

        productos.add(ProductoDto.builder()
                .id(2L)
                .nombre("Panela")
                .price(5000)
                .itemPedidos(Collections.emptyList())
                .itemPedidos(Collections.emptyList())
                .stock(50).build());

        when(productoService.getAllProducto()).thenReturn(productos);

        // Act & Assert
        mockMvc.perform(get(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Cereal"))
                .andExpect(jsonPath("$[0].price").value(8500))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Panela"))
                .andExpect(jsonPath("$[1].price").value(5000));


    }

    @Test
    public void givenProducto_whenBuscarProductoPorId_thenReturnProducto() throws Exception {
        //given
        Long idProducto = 1L;
        ProductoDto productoDto = ProductoDto.builder()
                .id(1L)
                .nombre("Panela Dulce")
                .price(1500)
                .stock(120)
                .itemPedidos(Collections.emptyList()).build();
        when(productoService.buscarProductoPorId(idProducto)).thenReturn(productoDto);

        // when
        ResultActions response = mockMvc.perform(get(API_PATH + "/{id}", idProducto));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is(productoDto.nombre())))
                .andExpect(jsonPath("$.stock", is(productoDto.stock())));
    }

    @Test
    public void givenProductoActualizado_whenActualizarProducto_thenReturnProducto() throws Exception {
        // given
        Long idProducto = 1L;
        ProductoDto productoDto = ProductoDto.builder()
                .id(1L)
                .nombre("Panela Dulce")
                .price(1500)
                .stock(120).build();

        ProductoToSaveDto productoActualizar = ProductoToSaveDto.builder()
                .id(1L)
                .nombre("Panela Salada")
                .price(2000)
                .stock(120).build();

        ProductoDto productoActualizado = ProductoDto.builder()
                .id(1L)
                .nombre("Panela Salada")
                .price(2000)
                .stock(120).build();

        when(productoService.buscarProductoPorId(idProducto)).thenReturn( productoDto);
        when(productoService.actualizarProducto(idProducto,productoActualizar)).thenReturn(productoActualizado);

        //when

        ResultActions response = mockMvc.perform(put(API_PATH + "/{id}", idProducto)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoActualizado)));

        //then

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre", is(productoActualizado.nombre())))
                .andExpect(jsonPath("$.price", is(productoActualizado.price())))
                .andExpect(jsonPath("$.stock", is(productoActualizado.stock())));
    }

    @Test
    public void givenIdProducto_whenEliminarProducto_thenReturn200() throws Exception {
        //given - precondition or setup
        long idProducto = 1L;
        willDoNothing().given(productoService).removerProducto(idProducto);

        // when - action or the behavior we are going to test
        ResultActions response = mockMvc.perform(delete(API_PATH + "/{id}", idProducto));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void buscarProductosPorTermino_ReturnsListOfProducts() throws Exception {

        String searchTerm = "anel"; //Panela
        List<ProductoDto> productos = new ArrayList<>();
        productos.add((ProductoDto.builder()
                .id(1L)
                .nombre("Panela")
                .stock(120)
                .price(1500)
                .itemPedidos(Collections.emptyList())
                .build()));

        when(productoService.buscarPorTerminoDeBusqueda(any(String.class))).thenReturn(productos);


        mockMvc.perform(get(API_PATH + "/search")
                        .param("searchTerm", searchTerm)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Panela"))
                .andExpect(jsonPath("$[0].stock").value(120))
                .andExpect(jsonPath("$[0].price").value(1500));
    }

    @Test
    void buscarProductosPorTermino_WithNoResults_ReturnsNotFound() throws Exception {

        String searchTerm = "panel";
        when(productoService.buscarPorTerminoDeBusqueda(any(String.class))).thenThrow(new ProductoNotFoundException("No se encontraron productos"));


        mockMvc.perform(get(API_PATH + "/search")
                        .param("searchTerm", searchTerm)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());;
    }

    @Test
    void obtenerProductosEnStock_ReturnsListOfProducts() throws Exception {

        List<ProductoDto> productos = new ArrayList<>();
        productos.add((ProductoDto.builder()
                .id(1L)
                .nombre("Panel")
                .stock(120)
                .price(1500)
                .itemPedidos(Collections.emptyList())
                .build()));

        when(productoService.buscarEnStock()).thenReturn(productos);

        mockMvc.perform(get(API_PATH + "/instock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Panel"))
                .andExpect(jsonPath("$[0].stock").value(120))
                .andExpect(jsonPath("$[0].price").value(1500));
    }

    @Test
    void obtenerProductosEnStock_WithNoResults_ReturnsNotFound() throws Exception {

        when(productoService.buscarEnStock()).thenThrow(new ProductoNotFoundException("No hay productos en stock"));


        mockMvc.perform(get(API_PATH + "/instock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}


















