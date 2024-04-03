package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;

import UnivesidadMagdalena.Tienda.exception.PedidoNotFoundException;
import UnivesidadMagdalena.Tienda.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> obtenerPedidoPorId(@PathVariable Long id) {
        try {
            PedidoDto pedido = pedidoService.buscarPedidoPorId(id);
            return ResponseEntity.ok().body(pedido);
        } catch (PedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoDto>> obtenerTodosPedidos() {
        List<PedidoDto> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok().body(pedidos);
    }

    @GetMapping("/cliente/{idCLiente}")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosPorCliente(@PathVariable Long idCliente) {
        try {
            List<PedidoDto> pedidos = pedidoService.findPedidosConArticulosByCliente(idCliente);
            return ResponseEntity.ok().body(pedidos);
        }catch (PedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosPorRangoFecha(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime endDate) {
        try {
            List<PedidoDto> pedidos = pedidoService.findByFechaPedidoBetween(startDate, endDate);
            return ResponseEntity.ok().body(pedidos);
        } catch (PedidoNotFoundException e) {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping
    public ResponseEntity<PedidoDto> guardarPedido(@RequestBody PedidoDto pedidoDto) {
        try {
            PedidoDto pedidoCreado = pedidoService.guardarPedido(pedidoDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(pedidoCreado.id())
                    .toUri();
            return ResponseEntity.created(location).body(pedidoCreado);
        } catch (PedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> actualizarPedido(@PathVariable Long id, @RequestBody PedidoDto pedidoDto) {
        try {
            PedidoDto pedidoActualizado = pedidoService.actualizarPedido(id, pedidoDto);
            return ResponseEntity.ok().body(pedidoActualizado);
        } catch (PedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        try {
            pedidoService.removerPedido(id);
            return ResponseEntity.noContent().build();
        } catch (PedidoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
