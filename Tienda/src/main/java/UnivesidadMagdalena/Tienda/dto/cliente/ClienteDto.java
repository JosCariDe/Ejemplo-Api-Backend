package UnivesidadMagdalena.Tienda.dto.cliente;

import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;
import UnivesidadMagdalena.Tienda.entities.Pedido;

import java.util.Collections;
import java.util.List;

public record ClienteDto(Long id,
                         String nombre,
                         String email,
                         String direccion,
                         List<PedidoDto> pedidos)
{
    public List<PedidoDto> pedidos() {

        return Collections.unmodifiableList(pedidos);
    }

}
