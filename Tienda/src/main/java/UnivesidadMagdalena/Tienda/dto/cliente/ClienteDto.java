package UnivesidadMagdalena.Tienda.dto.cliente;

import UnivesidadMagdalena.Tienda.entities.Pedido;

import java.util.List;

public record ClienteDto(Long id,
                         String nombre,
                         String email,
                         String direccion,
                         List<Pedido> pedidos) {
}
