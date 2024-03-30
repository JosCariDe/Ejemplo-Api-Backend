package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.cliente.ClienteToSaveDto;
import UnivesidadMagdalena.Tienda.exception.ClienteNotFoundException;

import java.util.List;

public interface ClienteService {
    ClienteDto guardarCliente(ClienteToSaveDto clienteDto);
    ClienteDto actualizarCliente(Long id, ClienteToSaveDto clienteDto);
    ClienteDto buscarClientePorId(Long id) throws ClienteNotFoundException;
    void removerCliente(Long id);
    List<ClienteDto> getAllClientes();
    List<ClienteDto> findByEmail(String email) throws ClienteNotFoundException;

    List<ClienteDto> findByDireccion(String direccion) throws ClienteNotFoundException;

    List<ClienteDto> buscarPorComienzoNombreCon(String nombre) throws ClienteNotFoundException;
}
