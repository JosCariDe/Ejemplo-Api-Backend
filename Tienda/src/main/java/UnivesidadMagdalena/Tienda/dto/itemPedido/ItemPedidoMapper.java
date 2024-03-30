package UnivesidadMagdalena.Tienda.dto.itemPedido;

import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemPedidoMapper {

    ItemPedidoDto itemPedidoToItemPedidoDto(ItemPedido itemPedido);
    ItemPedido itemPedidoDtoToItemPedido(ItemPedidoDto itemPedidoDto);

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "producto", ignore = true)
    ItemPedido itemPedidoToSaveDtoToItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto);
    List<ItemPedidoDto> itemPedidosToItemPedidosDto(List<ItemPedido> itemPedidoList);
    List<ItemPedido> itemPedidosDtoToItemPedidos(List<ItemPedidoDto> itemPedidoDtoList);

}
