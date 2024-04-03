package UnivesidadMagdalena.Tienda.api;


import UnivesidadMagdalena.Tienda.dto.detalleEnvio.DetalleEnvioDto;
import UnivesidadMagdalena.Tienda.exception.DetalleEnvioNotFoundException;
import UnivesidadMagdalena.Tienda.service.DetalleEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping")
public class DetalleEnvioController {

    private final DetalleEnvioService detalleEnvioService;

    @Autowired
    public DetalleEnvioController(DetalleEnvioService detalleEnvioService) {
        this.detalleEnvioService = detalleEnvioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleEnvioDto> obtenerDetalleEnvioPorId(@PathVariable Long id) {
        try {
            DetalleEnvioDto detalleEnvio = detalleEnvioService.buscarDetalleEnvioPorId(id);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (DetalleEnvioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DetalleEnvioDto>> obtenerTodosDetallesEnvio() {
        List<DetalleEnvioDto> detallesEnvio = detalleEnvioService.getAllDetalleEnvio();
        return ResponseEntity.ok().body(detallesEnvio);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DetalleEnvioDto> obtenerDetalleEnvioPorPedido(@PathVariable Long orderId) {
        try {
            DetalleEnvioDto detalleEnvio = detalleEnvioService.buscarDetallesEnvioPorIdPedido(orderId);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (DetalleEnvioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/carrier")
    public ResponseEntity<DetalleEnvioDto> obtenerDetalleEnvioPorTransportadora(@RequestParam String name) {
        try {
            DetalleEnvioDto detalleEnvio = detalleEnvioService.findByTransportadora(name);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (DetalleEnvioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleEnvioDto> guardarDetalleEnvio(@RequestBody DetalleEnvioDto detalleEnvioDto) {
        try {
            DetalleEnvioDto detalleEnvioCreado = detalleEnvioService.guardarDetalleEnvio(detalleEnvioDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(detalleEnvioCreado.id())
                    .toUri();
            return ResponseEntity.created(location).body(detalleEnvioCreado);
        } catch (DetalleEnvioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleEnvioDto> actualizarDetalleEnvio(@PathVariable Long id, @RequestBody DetalleEnvioDto detalleEnvioDto) {
        try {
            DetalleEnvioDto detalleEnvioActualizado = detalleEnvioService.actualizarDetalleEnvio(id, detalleEnvioDto);
            return ResponseEntity.ok().body(detalleEnvioActualizado);
        } catch (DetalleEnvioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleEnvio(@PathVariable Long id) {
        try {
            detalleEnvioService.removerDetalleEnvio(id);
            return ResponseEntity.noContent().build();
        } catch (DetalleEnvioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
