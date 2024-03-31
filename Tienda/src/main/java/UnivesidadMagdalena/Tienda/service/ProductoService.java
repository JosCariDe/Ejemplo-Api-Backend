package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;

import java.util.List;

public interface ProductoService {
    ProductoDto guardarProducto(ProductoToSaveDto producto);
    ProductoDto actualizarProducto(Long id, ProductoToSaveDto producto);
    ProductoDto buscarProductoPorId(Long id) throws ProductoNotFoundException;
    void removerProducto(Long id);
    List<ProductoDto> getAllProducto();
    List<ProductoDto> buscarPorTerminoDeBusqueda(String termino) throws ProductoNotFoundException;
    List<ProductoDto> buscarEnStock() throws ProductoNotFoundException;
    List<ProductoDto> buscarPorPrecioMaximoYStockMaximo(Integer precioMaximo, Integer stockMaximo) throws ProductoNotFoundException;
}
