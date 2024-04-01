package UnivesidadMagdalena.Tienda.dto.pago;

import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PagoToSaveDto(Long id,
                            Integer totalPago,
                            LocalDateTime fechaPago,
                            MetodoPago metodoPago) {
}
