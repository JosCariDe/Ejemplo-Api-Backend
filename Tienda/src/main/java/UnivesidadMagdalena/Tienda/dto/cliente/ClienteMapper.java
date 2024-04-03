package UnivesidadMagdalena.Tienda.dto.cliente;

import UnivesidadMagdalena.Tienda.dto.producto.ProductoMapper;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    Cliente clienteDtoToCliente(ClienteDto clienteDto);


    @Mapping(target = "pedidos", ignore = true)
    Cliente clienteToSaveDtoToCliente(ClienteToSaveDto clienteToSaveDto);

    ClienteDto clienteToClienteDto(Cliente cliente);

    List<ClienteDto> clientesToClientesDto(List<Cliente> clienteList);

    List<Cliente> clientesDtoToClientes(List<ClienteDto> clienteDtoList);
}
