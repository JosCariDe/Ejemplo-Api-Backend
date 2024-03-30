package UnivesidadMagdalena.Tienda.dto.detalleEnvio;

import UnivesidadMagdalena.Tienda.entities.DetalleEnvio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DetalleEnvioMapper {

    DetalleEnvioDto detalleEnvioToDetalleEnvioDto(DetalleEnvio detalleEnvio);
    DetalleEnvio detalleEnvioDtoToDetalleEnvio(DetalleEnvioDto detalleEnvioDto);

    @Mapping(target = "pedido", ignore = true)
    DetalleEnvio detalleEnvioToSaveToDetalleEnvio(DetalleEnvioToSaveDto detalleEnvioToSaveDto);
    List<DetalleEnvioDto> detalleEnviosToDetalleEnviosDto(List<DetalleEnvio> detalleEnvioList);
    List<DetalleEnvio> detalleEnviosDtoToDetalleEnvios(List<DetalleEnvioDto> detalleEnvioDtoList);

}
