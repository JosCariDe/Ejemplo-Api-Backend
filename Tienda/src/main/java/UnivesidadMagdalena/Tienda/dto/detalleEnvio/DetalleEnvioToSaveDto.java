package UnivesidadMagdalena.Tienda.dto.detalleEnvio;

public record DetalleEnvioToSaveDto(Long id,
                                    String direccion,
                                    String transportadora,
                                    Long numeroGuia) {
}
