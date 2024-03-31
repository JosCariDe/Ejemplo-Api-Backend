package UnivesidadMagdalena.Tienda.api;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteToSaveDto;
import UnivesidadMagdalena.Tienda.exception.ClienteNotFoundException;
import UnivesidadMagdalena.Tienda.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerClientePorId(@PathVariable Long id) {
        try {
            ClienteDto cliente = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok().body(cliente);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email")
    public ResponseEntity<List<ClienteDto>> obtenerClientesPorEmail(@PathVariable String email) {
        try {
            List<ClienteDto> clientes = clienteService.findByEmail(email);
            return ResponseEntity.ok().body(clientes);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/direccion")
    public ResponseEntity<List<ClienteDto>> obtenerClientesPorDireccion(@RequestParam("direccion") String city) {
        try {
            List<ClienteDto> clientes = clienteService.findByDireccion(city);
            return ResponseEntity.ok().body(clientes);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ClienteDto> guardarCliente(@RequestBody ClienteToSaveDto cliente) {
        try {
            ClienteDto clienteDto = clienteService.guardarCliente(cliente);
            return ResponseEntity.ok().body(clienteDto);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable Long id, @RequestBody ClienteToSaveDto cliente) {
        try {
            ClienteDto clienteDto = clienteService.actualizarCliente(id, cliente);
            return ResponseEntity.ok().body(clienteDto);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.removerCliente(id);
            return ResponseEntity.noContent().build();
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
