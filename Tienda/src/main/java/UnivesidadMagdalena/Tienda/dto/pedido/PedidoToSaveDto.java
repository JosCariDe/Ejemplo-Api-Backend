package UnivesidadMagdalena.Tienda.dto.pedido;

import UnivesidadMagdalena.Tienda.enumClass.Status;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PedidoToSaveDto(Long id,
                              LocalDateTime fechaPedido,
                              Status status) {
}
