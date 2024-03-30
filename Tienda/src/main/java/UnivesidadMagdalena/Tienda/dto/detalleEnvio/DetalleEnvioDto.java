package UnivesidadMagdalena.Tienda.dto.detalleEnvio;

import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;

public record DetalleEnvioDto(Long id,
                              PedidoDto pedido,
                              String direccion,
                              String transportadora,
                              Long numeroGuia) {
}
