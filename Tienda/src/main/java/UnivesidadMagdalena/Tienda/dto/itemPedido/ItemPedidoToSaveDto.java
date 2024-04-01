package UnivesidadMagdalena.Tienda.dto.itemPedido;

import lombok.Builder;

@Builder
public record ItemPedidoToSaveDto(Long id,
                                  Integer cantidad,
                                  Integer precioUnitario) {
}
