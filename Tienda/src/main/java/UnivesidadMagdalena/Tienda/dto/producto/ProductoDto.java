package UnivesidadMagdalena.Tienda.dto.producto;

import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;

import java.util.Collections;
import java.util.List;

public record ProductoDto(Long id,
                          String nombre,
                          Integer price,
                          Integer stock,
                          List<PedidoDto> pedidos,
                          List<ItemPedidoDto> itemPedidos)
{
    public List<PedidoDto> pedidos() {
        return Collections.unmodifiableList(pedidos);
    }

    public List<ItemPedidoDto> itemPedidos() {
        return Collections.unmodifiableList(itemPedidos);
    }
}
