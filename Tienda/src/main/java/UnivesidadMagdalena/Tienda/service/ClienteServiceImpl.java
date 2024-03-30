package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteMapper;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.exception.ClienteNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{
    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteMapper clienteMapper, ClienteRepository clienteRepository) {
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDto guardarCliente(ClienteToSaveDto clienteDto) {
        Cliente cliente = clienteMapper.clienteToSaveDtoToCliente(clienteDto);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return clienteMapper.clienteToClienteDto(clienteGuardado);
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteDto) throws ClienteNotFoundException {
        return clienteRepository.findById(id).map(clienteInDb -> {
            clienteInDb.setNombre(clienteDto.nombre());
            clienteInDb.setEmail(clienteDto.email());
            clienteInDb.setDireccion(clienteDto.direccion());

            Cliente clienteGuardado = clienteRepository.save(clienteInDb);

            return clienteMapper.clienteToClienteDto(clienteGuardado);
        }).orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
    }

    @Override
    public ClienteDto buscarClientePorId(Long id) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
        return clienteMapper.clienteToClienteDto(cliente);
    }

    @Override
    public void removerCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

    @Override
    public List<ClienteDto> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.clientesToClientesDto(clientes);
    }

    @Override
    public List<ClienteDto> findByEmail(String email) throws ClienteNotFoundException {
        List<Cliente> clientes = clienteRepository.findByEmail(email);
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException("No se encontraron clientes con el email: " + email);
        }
        return clienteMapper.clientesToClientesDto(clientes);
    }

    @Override
    public List<ClienteDto> findByDireccion(String direccion) throws ClienteNotFoundException {
        List<Cliente> clientes = clienteRepository.findByDireccion(direccion);
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException("No se encontraron clientes con la dirección: " + direccion);
        }
        return clienteMapper.clientesToClientesDto(clientes);
    }

    @Override
    public List<ClienteDto> buscarPorComienzoNombreCon(String nombre) throws ClienteNotFoundException {
        List<Cliente> clientes = clienteRepository.buscarPorComienzoNombreCon(nombre);
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException("No se encontraron clientes cuyos nombres comiencen con: " + nombre);
        }
        return clienteMapper.clientesToClientesDto(clientes);
    }
}
