package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.pago.PagoDto;
import UnivesidadMagdalena.Tienda.dto.pago.PagoToSaveDto;
import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import UnivesidadMagdalena.Tienda.exception.PagoNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoService {
    PagoDto guardarPago(PagoToSaveDto pagoDto);
    PagoDto actualizarPago(Long id, PagoToSaveDto pagoDto) throws PagoNotFoundException;
    PagoDto buscarPagoPorId(Long id) throws PagoNotFoundException;
    void removerPago(Long id);
    List<PagoDto> getAllPagos();
    List<PagoDto> buscarPorFechaPagoEntre(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PagoNotFoundException;
    List<PagoDto> buscarPorIdYMetodoPago(Long id, MetodoPago metodoPago) throws PagoNotFoundException;
}
