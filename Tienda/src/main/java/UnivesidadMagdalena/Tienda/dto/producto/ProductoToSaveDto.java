package UnivesidadMagdalena.Tienda.dto.producto;

public record ProductoToSaveDto(Long id,
                                String nombre,
                                Integer price,
                                Integer stock) {
}
