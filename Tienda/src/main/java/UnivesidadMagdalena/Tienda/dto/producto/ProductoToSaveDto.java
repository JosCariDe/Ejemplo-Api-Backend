package UnivesidadMagdalena.Tienda.dto.producto;

import lombok.Builder;

@Builder
public record ProductoToSaveDto(Long id,
                                String nombre,
                                Integer price,
                                Integer stock) {
}
