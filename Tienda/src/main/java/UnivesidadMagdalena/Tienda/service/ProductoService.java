package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;

import java.util.List;

public interface ProductoService {
    ProductoDto guardarProducto(ProductoToSaveDto producto);
    ProductoDto actualizarProducto(Long id, ProductoToSaveDto producto);
    ProductoDto buscarProductoPorId(Long id) throws ProductoNotFoundException;
    ProductoDto removerProducto(Long id);
    List<ProductoDto> getAllUsers();
    List<ProductoDto> buscarPorTerminoDeBusqueda(String termino) throws ProductoNotFoundException;
    List<ProductoDto> buscarPorStock(Integer cantidad) throws ProductoNotFoundException;
    List<ProductoDto> buscarPorPrecioMaximoYStockMaximo(Integer precioMaximo, Integer stockMaximo) throws ProductoNotFoundException;
}
