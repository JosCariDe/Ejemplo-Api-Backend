package UnivesidadMagdalena.Tienda.api;


import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoToSaveDto;
import UnivesidadMagdalena.Tienda.exception.ItemPedidoNotFoundException;
import UnivesidadMagdalena.Tienda.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items-pedidos")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @Autowired
    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoDto> obtenerItemPedidoPorId(@PathVariable Long id) {
        try {
            ItemPedidoDto itemPedido = itemPedidoService.buscarItemPedidoPorId(id);
            return ResponseEntity.ok().body(itemPedido);
        } catch (ItemPedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoDto>> obtenerTodosItemsPedido() {
        List<ItemPedidoDto> itemsPedido = itemPedidoService.getAllItemPedidos();
        return ResponseEntity.ok().body(itemsPedido);
    }

    @GetMapping("/order/{idPedido}")
    public ResponseEntity<List<ItemPedidoDto>> obtenerItemsPedidoPorPedido(@PathVariable Long idPedido) {
        try {
            List<ItemPedidoDto> itemsPedido = itemPedidoService.buscarItemsPorIdPedido(idPedido);
            return ResponseEntity.ok().body(itemsPedido);
        } catch (ItemPedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{id-producto}")
    public ResponseEntity<List<ItemPedidoDto>> obtenerItemsPedidoPorProducto(@PathVariable Long idProducto) {
        try {
            List<ItemPedidoDto> itemsPedido = itemPedidoService.buscarItemsPorIdPedido(idProducto);
            return ResponseEntity.ok().body(itemsPedido);
        } catch (ItemPedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ItemPedidoDto> guardarItemPedido(@RequestBody ItemPedidoToSaveDto itemPedidoDto) {
        try {
            ItemPedidoDto itemPedidoCreado = itemPedidoService.guardarItemPedido(itemPedidoDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(itemPedidoCreado.id())
                    .toUri();
            return ResponseEntity.created(location).body(itemPedidoCreado);
        } catch (ItemPedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoDto> actualizarItemPedido(@PathVariable Long id, @RequestBody ItemPedidoToSaveDto itemPedidoDto) {
        try {
            ItemPedidoDto itemPedidoActualizado = itemPedidoService.actualizarItemPedido(id, itemPedidoDto);
            return ResponseEntity.ok().body(itemPedidoActualizado);
        } catch (ItemPedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItemPedido(@PathVariable Long id) {
        try {
            itemPedidoService.removerItemPedido(id);
            return ResponseEntity.noContent().build();
        } catch (ItemPedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
