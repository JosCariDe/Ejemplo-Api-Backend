import UnivesidadMagdalena.Tienda.dto.producto.ProductoDto;
import UnivesidadMagdalena.Tienda.dto.producto.ProductoToSaveDto;
import UnivesidadMagdalena.Tienda.exception.ProductoNotFoundException;
import UnivesidadMagdalena.Tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long id) {
        try {
            ProductoDto producto = productoService.buscarProductoPorId(id);
            return ResponseEntity.ok(producto);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> buscarProductosPorTermino(@RequestParam String searchTerm) {
        try {
            List<ProductoDto> productos = productoService.buscarPorTerminoDeBusqueda(searchTerm);
            return ResponseEntity.ok(productos);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/instock")
    public ResponseEntity<?> obtenerProductosEnStock() {
        try {
            List<ProductoDto> productos = productoService.buscarPorStock(1); // Se asume que 1 representa que están en stock
            return ResponseEntity.ok(productos);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody ProductoToSaveDto productoDto) {
        try {
            ProductoDto productoGuardado = productoService.guardarProducto(productoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el producto");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoToSaveDto productoDto) {
        try {
            ProductoDto productoActualizado = productoService.actualizarProducto(id, productoDto);
            return ResponseEntity.ok(productoActualizado);
        } catch (ProductoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
