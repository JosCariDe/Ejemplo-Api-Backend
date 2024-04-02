package UnivesidadMagdalena.Tienda.dto.pago;

import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;
import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PagoDto(Long id,
                      PedidoDto pedido,
                      Integer totalPago,
                      LocalDateTime fechaPago,
                      MetodoPago metodoPago) {
}
