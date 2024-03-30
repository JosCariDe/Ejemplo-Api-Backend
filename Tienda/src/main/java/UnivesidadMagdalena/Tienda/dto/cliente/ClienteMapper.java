package UnivesidadMagdalena.Tienda.dto.cliente;

import UnivesidadMagdalena.Tienda.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClienteMapper {

    Cliente clienteDtoToCliente(ClienteDto clienteDto);

    ClienteDto clienteToClienteDto(Cliente cliente);

    List<ClienteDto> clientesToClientesDto(List<Cliente> clienteList);

    List<Cliente> clientesDtoToClientes(List<ClienteDto> clienteDtoList);
}
