package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Producto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Cereal"))
                .andExpect(jsonPath("$[0].price").value(8500))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Panela"))
                .andExpect(jsonPath("$[1].price").value(5000));
    }

}


















