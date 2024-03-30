package UnivesidadMagdalena.Tienda.dto.cliente;

public record ClienteToSaveDto(Long id,
                               String nombre,
                               String email,
                               String direccion) {
}
