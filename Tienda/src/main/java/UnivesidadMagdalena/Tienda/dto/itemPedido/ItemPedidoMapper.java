package UnivesidadMagdalena.Tienda.dto.itemPedido;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteMapper;
import UnivesidadMagdalena.Tienda.dto.detalleEnvio.DetalleEnvioMapper;
import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ItemPedidoMapper {

    ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);

    ItemPedidoDto itemPedidoToItemPedidoDto(ItemPedido itemPedido);
    ItemPedido itemPedidoDtoToItemPedido(ItemPedidoDto itemPedidoDto);

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "producto", ignore = true)
    ItemPedido itemPedidoToSaveDtoToItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto);
    List<ItemPedidoDto> itemPedidosToItemPedidosDto(List<ItemPedido> itemPedidoList);
    List<ItemPedido> itemPedidosDtoToItemPedidos(List<ItemPedidoDto> itemPedidoDtoList);

}
