package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoMapper;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public ProductoDto guardarProducto(ProductoToSaveDto productoDto) {
        Producto producto = ProductoMapper.INSTANCE.productoToSaveDtoToProducti(productoDto);
        Producto productoGuardado = productoRepository.save(producto);
        return ProductoMapper.INSTANCE.productoToProductoDto(productoGuardado);
    }

    @Override
    public ProductoDto actualizarProducto(Long id, ProductoToSaveDto productoDto) {
        return productoRepository.findById(id).map(productoInDb -> {
            productoInDb.setNombre(productoDto.nombre());
            productoInDb.setPrice(productoDto.price());
            productoInDb.setStock(productoDto.stock());

            Producto productoGuardado = productoRepository.save(productoInDb);

            return ProductoMapper.INSTANCE.productoToProductoDto(productoGuardado);
        }).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductoDto buscarProductoPorId(Long id) throws ProductoNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        return ProductoMapper.INSTANCE.productoToProductoDto(producto);
    }

    @Override
    public void removerProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

    @Override
    public List<ProductoDto> getAllProducto() {
        List<Producto> productos = productoRepository.findAll();
        return ProductoMapper.INSTANCE.productosToProductosDto(productos);
    }

    @Override
    public List<ProductoDto> buscarPorTerminoDeBusqueda(String termino) throws ProductoNotFoundException {
        List<Producto> productos = productoRepository.buscarPorTerminoDeBusqueda(termino);
        if (productos.isEmpty()) throw new ProductoNotFoundException("No se encontró ningun producto con la terminacion: " + termino);
        return ProductoMapper.INSTANCE.productosToProductosDto(productos);
    }

    @Override
    public List<ProductoDto> buscarEnStock() throws ProductoNotFoundException {
        List<Producto> productos = productoRepository.buscarEnStock();
        if (productos.isEmpty()) throw new ProductoNotFoundException("No se encontró ningun producto en stock de la tienda ");
        return ProductoMapper.INSTANCE.productosToProductosDto(productos);
    }

    @Override
    public List<ProductoDto> buscarPorPrecioMaximoYStockMaximo(Integer precioMaximo, Integer stockMaximo) throws ProductoNotFoundException {
        List<Producto> productos = productoRepository.buscarPorPrecioMaximoYStockMaximo(precioMaximo, stockMaximo);
        if (productos.isEmpty()) throw new ProductoNotFoundException("No se econtró ningun producto con un stock maximo de " + stockMaximo + ", y un precio maximo de " + precioMaximo);
        return ProductoMapper.INSTANCE.productosToProductosDto(productos);
    }
}
