package UnivesidadMagdalena.Tienda.dto.pago;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteMapper;
import UnivesidadMagdalena.Tienda.entities.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    PagoMapper INSTANCE = Mappers.getMapper(PagoMapper.class);
    PagoDto pagoToPagoDto(Pago pago);
    Pago pagoDtoToPago(PagoDto pagoDto);

    @Mapping(target = "pedido", ignore = true)
    Pago pagoToSaveDtoToPago(PagoToSaveDto pagoToSaveDto);
    List<PagoDto> pagosToPagosDto(List<Pago> pagoList);
    List<Pago> pagosDtoToPagos(List<PagoDto> pagoDtoList);


}
