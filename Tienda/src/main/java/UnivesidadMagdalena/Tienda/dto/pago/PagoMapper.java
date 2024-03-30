package UnivesidadMagdalena.Tienda.dto.pago;

import UnivesidadMagdalena.Tienda.entities.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PagoMapper {

    PagoDto pagoToPagoDto(Pago pago);
    Pago pagoDtoToPago(PagoDto pagoDto);

    @Mapping(target = "pedido", ignore = true)
    Pago pagoToSaveDtoToPago(PagoToSaveDto pagoToSaveDto);
    List<PagoDto> pagosToPagosDto(List<Pago> pagoList);
    List<Pago> pagosDtoToPagos(List<PagoDto> pagoDtoList);


}
