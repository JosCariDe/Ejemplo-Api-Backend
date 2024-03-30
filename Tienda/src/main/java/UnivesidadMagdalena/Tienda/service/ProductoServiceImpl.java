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
        Producto producto = productoMapper.productoToSaveDtoToProducti(productoDto);
        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.productoToProductoDto(productoGuardado);
    }

    @Override
    public ProductoDto actualizarProducto(Long id, ProductoToSaveDto productoDto) {
        return productoRepository.findById(id).map(productoInDb -> {
            productoInDb.setNombre(productoDto.nombre());
            productoInDb.setPrice(productoDto.price());
            productoInDb.setStock(productoDto.stock());

            Producto productoGuardado = productoRepository.save(productoInDb);

            return productoMapper.productoToProductoDto(productoGuardado);
        }).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductoDto buscarProductoPorId(Long id) throws ProductoNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        return productoMapper.productoToProductoDto(producto);
    }

    @Override
    public void removerProducto(Long id) {
        Producto productos= productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Usuario no encontrado"));
        productoRepository.delete(productos);
    }

    @Override
    public List<ProductoDto> getAllUsers() {
        List<Producto> productos = productoRepository.findAll();
        return productoMapper.productosToProductosDto(productos);
    }

    @Override
    public List<ProductoDto> buscarPorTerminoDeBusqueda(String termino) throws ProductoNotFoundException {
        List<Producto> productos = productoRepository.buscarPorTerminoDeBusqueda(termino);
        if (productos.isEmpty()) throw new ProductoNotFoundException("No se encontró ningun producto con la terminacion: " + termino);
        return productoMapper.productosToProductosDto(productos);
    }

    @Override
    public List<ProductoDto> buscarPorStock(Integer cantidad) throws ProductoNotFoundException {

        List<Producto> productos = productoRepository.findByStock(cantidad);
        if(productos.isEmpty()) throw new ProductoNotFoundException("No se encontró ningun producto un stock de " + cantidad);

        return productoMapper.productosToProductosDto(productos);
    }

    @Override
    public List<ProductoDto> buscarPorPrecioMaximoYStockMaximo(Integer precioMaximo, Integer stockMaximo) throws ProductoNotFoundException {

        List<Producto> productos = productoRepository.buscarPorPrecioMaximoYStockMaximo(precioMaximo, stockMaximo);
        if(productos.isEmpty()) throw new ProductoNotFoundException("No se econtró ningun producto con un stock maximo de " + stockMaximo + ", y un precio maximo de " + precioMaximo);
        
        return productoMapper.productosToProductosDto(productos);
    }
}
