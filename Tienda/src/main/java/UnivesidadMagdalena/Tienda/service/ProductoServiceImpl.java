package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoMapper;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService{
    private final ProductoMapper productoMapper;
    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoMapper productoMapper, ProductoRepository productoRepository) {
        this.productoMapper = productoMapper;
        this.productoRepository = productoRepository;
    }

    @Override
    public ProductoDto guardarProducto(ProductoToSaveDto productoDto) {
        return null;
    }

    @Override
    public ProductoDto actualizarProducto(Long id, ProductoToSaveDto producto) {
        return null;
    }

    @Override
    public ProductoDto buscarProductoPorId(Long id) throws ProductoNotFoundException {
        return null;
    }

    @Override
    public ProductoDto removerProducto(Long id) {
        return null;
    }

    @Override
    public List<ProductoDto> getAllUsers() {
        return null;
    }

    @Override
    public List<ProductoDto> buscarPorTerminoDeBusqueda(String termino) throws ProductoNotFoundException {
        return null;
    }

    @Override
    public List<ProductoDto> buscarPorStock(Integer cantidad) throws ProductoNotFoundException {
        return null;
    }

    @Override
    public List<ProductoDto> buscarPorPrecioMaximoYStockMaximo(Integer precioMaximo, Integer stockMaximo) throws ProductoNotFoundException {
        return null;
    }
}
