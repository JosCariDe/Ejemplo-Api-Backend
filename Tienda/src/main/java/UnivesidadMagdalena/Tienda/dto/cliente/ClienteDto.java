package UnivesidadMagdalena.Tienda.dto.cliente;

import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

@Builder
public record ClienteDto(Long id,
                         String nombre,
                         String email,
                         String direccion,
                         List<PedidoDto> pedidos) {

    public static ClienteDto createWithPedidos(Long id, String nombre, String email, String direccion, List<PedidoDto> pedidos) {
        return new ClienteDto(id, nombre, email, direccion, pedidos);
    }

    /*public List<PedidoDto> pedidos() {
        return Collections.unmodifiableList(pedidos);
    } */
}