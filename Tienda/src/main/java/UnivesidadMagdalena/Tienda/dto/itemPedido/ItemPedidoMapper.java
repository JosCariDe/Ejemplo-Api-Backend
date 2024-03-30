package UnivesidadMagdalena.Tienda.dto.itemPedido;

import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemPedidoMapper {

    ItemPedidoDto itemPedidoToItemPedidoDto(ItemPedido itemPedido);
    ItemPedido itemPedidoDtoToItemPedido(ItemPedidoDto itemPedidoDto);
    List<ItemPedidoDto> itemPedidosToItemPedidosDto(List<ItemPedido> itemPedidoList);
    List<ItemPedido> itemPedidosDtoToItemPedidos(List<ItemPedidoDto> itemPedidoDtoList);

}
