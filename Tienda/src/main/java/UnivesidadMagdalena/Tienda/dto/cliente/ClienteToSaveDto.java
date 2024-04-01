package UnivesidadMagdalena.Tienda.dto.cliente;

import lombok.Builder;

@Builder
public record ClienteToSaveDto(Long id,
                               String nombre,
                               String email,
                               String direccion) {
}
