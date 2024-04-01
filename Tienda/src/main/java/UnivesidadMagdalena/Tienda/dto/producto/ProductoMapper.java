package UnivesidadMagdalena.Tienda.dto.producto;

import UnivesidadMagdalena.Tienda.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface ProductoMapper {

    ProductoDto productoToProductoDto(Producto producto);
    Producto productoDtoToProducto(ProductoDto productoDto);

    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "itemPedidos", ignore = true)
    Producto productoToSaveDtoToProducti(ProductoToSaveDto productoToSaveDto);

    List<ProductoDto> productosToProductosDto(List<Producto> productoList);
    List<Producto> productosDtoToProductos(List<ProductoDto> productoDtoList);

}
