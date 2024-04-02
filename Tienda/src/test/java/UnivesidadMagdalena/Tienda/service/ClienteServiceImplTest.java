package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteMapper;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.exception.ClienteNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ClienteRepository;
import UnivesidadMagdalena.Tienda.service.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Jose Narvaez")
                .email("josenarvaez@gmail.com")
                .direccion("Ciudad Santa Marta")
                .build();
    }

    @Test
    void givenCliente_whenGuardarCliente_thenReturnClienteGuardado() {
        given(clienteRepository.save(any())).willReturn(cliente);

        ClienteToSaveDto clienteAGuardar = ClienteToSaveDto.builder()
                .nombre("Jose Narvaez")
                .email("josenarvaez@gmail.com")
                .direccion("Ciudad Santa Marta")
                .build();

        ClienteDto clienteDto = clienteService.guardarCliente(clienteAGuardar);

        assertNotNull(clienteDto);
        assertEquals(1L, clienteDto.id());
        assertEquals("Jose Narvaez", clienteDto.nombre());
        assertEquals("josenarvaez@gmail.com", clienteDto.email());
        assertEquals("Ciudad Santa Marta", clienteDto.direccion());
    }

    @Test
    void givenClienteId_whenBuscarClienteById_thenReturnCliente() {
        Long clienteId = 1L;
        given(clienteRepository.findById(clienteId)).willReturn(Optional.of(cliente));

        ClienteDto clienteDto = clienteService.buscarClientePorId(clienteId);

        assertNotNull(clienteDto);
        assertEquals("Jose Narvaez", clienteDto.nombre());
        assertEquals("josenarvaez@gmail.com", clienteDto.email());
        assertEquals("Ciudad Santa Marta", clienteDto.direccion());
    }

    @Test
    void givenClienteId_whenBuscarClienteById_thenThrowClienteNotFoundException() {
        given(clienteRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.buscarClientePorId(5L);
        });
    }

    @Test
    void givenClienteId_whenRemoverCliente_thenNothing() {
        Long clienteId = 1L;
        given(clienteRepository.findById(clienteId)).willReturn(Optional.of(cliente));
        willDoNothing().given(clienteRepository).delete(any());

        assertDoesNotThrow(() -> {
            clienteService.removerCliente(clienteId);
        });

        verify(clienteRepository, times(1)).delete(any());
    }

    @Test
    void givenNothing_whenGetAllClientes_thenReturnListOfClientes() {
        List<Cliente> clientes = List.of(cliente);
        given(clienteRepository.findAll()).willReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.getAllClientes();

        assertNotNull(clienteDtos);
        assertFalse(clienteDtos.isEmpty());
        assertEquals(1, clienteDtos.size());
    }

    @Test
    void givenEmail_whenFindByEmail_thenReturnListOfClientes() {
        String email = "josenarvaez@gmail.com";
        List<Cliente> clientes = List.of(cliente);
        given(clienteRepository.findByEmail(email)).willReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.findByEmail(email);

        assertNotNull(clienteDtos);
        assertFalse(clienteDtos.isEmpty());
        assertEquals(1, clienteDtos.size());
        assertEquals("josenarvaez@gmail.com", clienteDtos.get(0).email());
    }

    @Test
    void givenDireccion_whenFindByDireccion_thenReturnListOfClientes() {
        String direccion = "Ciudad Santa Marta";
        List<Cliente> clientes = List.of(cliente);
        given(clienteRepository.findByDireccion(direccion)).willReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.findByDireccion(direccion);

        assertNotNull(clienteDtos);
        assertFalse(clienteDtos.isEmpty());
        assertEquals(1, clienteDtos.size());
        assertEquals("Ciudad Santa Marta", clienteDtos.get(0).direccion());
    }

    @Test
    void givenNombre_whenBuscarPorComienzoNombreCon_thenReturnListOfClientes() {
        String nombre = "Jose";
        List<Cliente> clientes = List.of(cliente);
        given(clienteRepository.buscarPorComienzoNombreCon(nombre)).willReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.buscarPorComienzoNombreCon(nombre);

        assertNotNull(clienteDtos);
        assertFalse(clienteDtos.isEmpty());
        assertEquals(1, clienteDtos.size());
        assertTrue(clienteDtos.get(0).nombre().startsWith("Jose"));
    }
}
