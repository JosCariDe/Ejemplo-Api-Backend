package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.pago.PagoDto;
import UnivesidadMagdalena.Tienda.dto.pago.PagoToSaveDto;
import UnivesidadMagdalena.Tienda.exception.PagoNotFoundException;
import UnivesidadMagdalena.Tienda.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pago")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDto> obtenerPagoPorId(@PathVariable Long id) {
        try {
            PagoDto pago = pagoService.buscarPagoPorId(id);
            return ResponseEntity.ok().body(pago);
        } catch (PagoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PagoDto>> obtenerTodosPagos() {
        List<PagoDto> pagos = pagoService.getAllPagos();
        return ResponseEntity.ok().body(pagos);
    }

    @GetMapping("/order/{idPedido}")
    public ResponseEntity<PagoDto> obtenerPagosPorPedido(@PathVariable Long idPedido) {
        try {
            PagoDto pagos = pagoService.buscarPagoPorId(idPedido);
            return ResponseEntity.ok().body(pagos);
        } catch (PagoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PagoDto>> obtenerPagosPorRangoFecha(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<PagoDto> pagos = pagoService.buscarPorFechaPagoEntre(startDate, endDate);
            return ResponseEntity.ok().body(pagos);
        } catch (PagoNotFoundException e) {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping
    public ResponseEntity<PagoDto> guardarPago(@RequestBody PagoToSaveDto pagoDto) {
        try {
            PagoDto pagoCreado = pagoService.guardarPago(pagoDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(pagoCreado.id())
                    .toUri();
            return ResponseEntity.created(location).body(pagoCreado);
        } catch (PagoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDto> actualizarPago(@PathVariable Long id, @RequestBody PagoToSaveDto pagoDto) {
        try {
            PagoDto pagoActualizado = pagoService.actualizarPago(id, pagoDto);
            return ResponseEntity.ok().body(pagoActualizado);
        } catch (PagoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        try {
            pagoService.removerPago(id);
            return ResponseEntity.noContent().build();
        } catch (PagoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
