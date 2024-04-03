package UnivesidadMagdalena.Tienda.dto.detalleEnvio;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteMapper;
import UnivesidadMagdalena.Tienda.entities.DetalleEnvio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalleEnvioMapper {

    DetalleEnvioMapper INSTANCE = Mappers.getMapper(DetalleEnvioMapper.class);
    DetalleEnvioDto detalleEnvioToDetalleEnvioDto(DetalleEnvio detalleEnvio);
    DetalleEnvio detalleEnvioDtoToDetalleEnvio(DetalleEnvioDto detalleEnvioDto);

    @Mapping(target = "pedido", ignore = true)
    DetalleEnvio detalleEnvioToSaveToDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto);
    List<DetalleEnvioDto> detalleEnviosToDetalleEnviosDto(List<DetalleEnvio> detalleEnvioList);
    List<DetalleEnvio> detalleEnviosDtoToDetalleEnvios(List<DetalleEnvioDto> detalleEnvioDtoList);

}
