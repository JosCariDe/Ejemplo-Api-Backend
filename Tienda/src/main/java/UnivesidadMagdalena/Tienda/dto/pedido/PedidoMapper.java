package UnivesidadMagdalena.Tienda.dto.pedido;

import UnivesidadMagdalena.Tienda.entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PedidoMapper {

    PedidoDto pedidoToPedidoDto(Pedido pedido);
    Pedido pedidoDtoToPedido(PedidoDto pedidoDto);
    List<PedidoDto> pedidosToPedidosDto(List<Pedido> pedidoList);
    List<Pedido> pedidosDtoToPedidos(List<PedidoDto> pedidoDtoList);

}
