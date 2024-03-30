package UnivesidadMagdalena.Tienda.dto.pedido;

import UnivesidadMagdalena.Tienda.enumClass.Status;

import java.time.LocalDateTime;

public record PedidoToSaveDto(Long id,
                              LocalDateTime fechaPedido,
                              Status status) {
}
