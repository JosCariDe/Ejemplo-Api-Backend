package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;
import UnivesidadMagdalena.Tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>> otbenerTodosLosProductos() {
        List<ProductoDto> productos = productoService.getAllProducto();
        return ResponseEntity.ok().body(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtenerProductoPorId(@PathVariable Long id) {
        try {
            ProductoDto producto = productoService.buscarProductoPorId(id);
            return ResponseEntity.ok().body(producto);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductoDto>> buscarProductosPorTermino(@RequestParam String searchTerm) {
        try {
            List<ProductoDto> productos = productoService.buscarPorTerminoDeBusqueda(searchTerm);
            return ResponseEntity.ok().body(productos);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/instock")
    public ResponseEntity<List<ProductoDto>> obtenerProductosEnStock() {
        try {
            List<ProductoDto> productos = productoService.buscarEnStock();
            return ResponseEntity.ok().body(productos);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductoDto> guardarProducto(@RequestBody ProductoToSaveDto productoDto) {
        try {
            ProductoDto productoCreado = productoService.guardarProducto(productoDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(productoCreado.id())
                    .toUri();
            return ResponseEntity.created(location).body(productoCreado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoToSaveDto productoDto) {
        try {
            ProductoDto productoDtoObtenido = productoService.buscarProductoPorId(id);
            if (productoDtoObtenido.id() != null){
                ProductoDto productoActualizado = productoService.actualizarProducto(id, productoDto);
                return ResponseEntity.ok().body(productoActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.removerProducto(id);
            return ResponseEntity.noContent().build();
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
