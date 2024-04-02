package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteMapper;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteMapperImpl;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.exception.ClienteNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService{

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteDto guardarCliente(ClienteToSaveDto clienteDto) {
        Cliente cliente = ClienteMapper.INSTANCE.clienteToSaveDtoToCliente(clienteDto);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return ClienteMapper.INSTANCE.clienteToClienteDto(clienteGuardado);
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteDto) throws ClienteNotFoundException {
        return clienteRepository.findById(id).map(clienteInDb -> {
            clienteInDb.setNombre(clienteDto.nombre());
            clienteInDb.setEmail(clienteDto.email());
            clienteInDb.setDireccion(clienteDto.direccion());

            Cliente clienteGuardado = clienteRepository.save(clienteInDb);

            return ClienteMapper.INSTANCE.clienteToClienteDto(clienteGuardado);
        }).orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
    }

    @Override
    public ClienteDto buscarClientePorId(Long id) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
        return ClienteMapper.INSTANCE.clienteToClienteDto(cliente);
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
        return ClienteMapper.INSTANCE.clientesToClientesDto(clientes);
    }

    @Override
    public List<ClienteDto> findByEmail(String email) throws ClienteNotFoundException {
        List<Cliente> clientes = clienteRepository.findByEmail(email);
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException("No se encontraron clientes con el email: " + email);
        }
        return ClienteMapper.INSTANCE.clientesToClientesDto(clientes);
    }

    @Override
    public List<ClienteDto> findByDireccion(String direccion) throws ClienteNotFoundException {
        List<Cliente> clientes = clienteRepository.findByDireccion(direccion);
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException("No se encontraron clientes con la dirección: " + direccion);
        }
        return ClienteMapper.INSTANCE.clientesToClientesDto(clientes);
    }

    @Override
    public List<ClienteDto> buscarPorComienzoNombreCon(String nombre) throws ClienteNotFoundException {
        List<Cliente> clientes = clienteRepository.buscarPorComienzoNombreCon(nombre);
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException("No se encontraron clientes cuyos nombres comiencen con: " + nombre);
        }
        return ClienteMapper.INSTANCE.clientesToClientesDto(clientes);
    }
}
