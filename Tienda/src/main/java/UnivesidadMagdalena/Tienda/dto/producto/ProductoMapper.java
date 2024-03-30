package UnivesidadMagdalena.Tienda.dto.producto;

import UnivesidadMagdalena.Tienda.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoMapper {

    ProductoDto productoToProductoDto(Producto producto);
    Producto productoDtoToProducto(ProductoDto productoDto);

    List<ProductoDto> productosToProductosDto(List<Producto> productoList);
    List<Producto> productosDtoToProductos(List<ProductoDto> productoDtoList);

}
