package UnivesidadMagdalena.Tienda.dto.pedido;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteMapper;
import UnivesidadMagdalena.Tienda.entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);
    PedidoDto pedidoToPedidoDto(Pedido pedido);
    Pedido pedidoDtoToPedido(PedidoDto pedidoDto);

    @Mapping(target = "cliente",ignore = true)
    @Mapping(target = "itemPedidos", ignore = true)
    @Mapping(target = "pago",ignore = true)
    @Mapping(target = "detalleEnvio", ignore = true)
    Pedido pedidoToSaveDtoToPedido(PedidoToSaveDto pedidoToSaveDto);
    List<PedidoDto> pedidosToPedidosDto(List<Pedido> pedidoList);
    List<Pedido> pedidosDtoToPedidos(List<PedidoDto> pedidoDtoList);

}
