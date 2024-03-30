package UnivesidadMagdalena.Tienda.dto.pago;

import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;

import java.time.LocalDateTime;

public record PagoToSaveDto(Long id,
                            Integer totalPago,
                            LocalDateTime fechaPago,
                            MetodoPago metodoPago) {
}
