package UnivesidadMagdalena.Tienda.dto.producto;

import UnivesidadMagdalena.Tienda.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.shaded.freemarker.core.Comment;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    ProductoDto productoToProductoDto(Producto producto);
    Producto productoDtoToProducto(ProductoDto productoDto);

    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "itemPedidos", ignore = true)
    Producto productoToSaveDtoToProducti(ProductoToSaveDto productoToSaveDto);

    List<ProductoDto> productosToProductosDto(List<Producto> productoList);
    List<Producto> productosDtoToProductos(List<ProductoDto> productoDtoList);

}
