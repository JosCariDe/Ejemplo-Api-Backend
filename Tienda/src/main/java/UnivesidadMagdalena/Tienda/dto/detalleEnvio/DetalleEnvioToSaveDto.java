package UnivesidadMagdalena.Tienda.dto.detalleEnvio;

import lombok.Builder;

@Builder
public record DetalleEnvioToSaveDto(Long id,
                                    String direccion,
                                    String transportadora,
                                    Long numeroGuia) {
}
