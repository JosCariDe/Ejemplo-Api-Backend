package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.pago.PagoDto;
import UnivesidadMagdalena.Tienda.dto.pago.PagoMapper;
import UnivesidadMagdalena.Tienda.dto.pago.PagoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Pago;
import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import UnivesidadMagdalena.Tienda.exception.PagoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    @Override
    public PagoDto guardarPago(PagoToSaveDto pagoDto) {
        Pago pago = PagoMapper.INSTANCE.pagoToSaveDtoToPago(pagoDto);
        Pago pagoGuardado = pagoRepository.save(pago);
        return PagoMapper.INSTANCE.pagoToPagoDto(pagoGuardado);
    }

    @Override
    public PagoDto actualizarPago(Long id, PagoToSaveDto pagoDto) throws PagoNotFoundException {
        return pagoRepository.findById(id).map(pagoInDb -> {
            // Actualizar los campos del pago con los valores del DTO
            pagoInDb.setMetodoPago(pagoDto.metodoPago());
            pagoInDb.setFechaPago(pagoDto.fechaPago());
            pagoInDb.setTotalPago(pagoDto.totalPago());
            Pago pagoActualizado = pagoRepository.save(pagoInDb);
            return PagoMapper.INSTANCE.pagoToPagoDto(pagoActualizado);
        }).orElseThrow(() -> new PagoNotFoundException("Pago no encontrado"));
    }

    @Override
    public PagoDto buscarPagoPorId(Long id) throws PagoNotFoundException {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado"));
        return PagoMapper.INSTANCE.pagoToPagoDto(pago);
    }

    @Override
    public PagoDto buscarPagoPorIdPedido(Long idPedido) throws PagoNotFoundException {
        Pago pago = pagoRepository.buscarPagoPorIdPedido(idPedido);
        if(pago.getId() == null)
            throw new PagoNotFoundException("Pago no encontrado con el id del Pedido: " + idPedido);
        return PagoMapper.INSTANCE.pagoToPagoDto(pago);
    }

    @Override
    public void removerPago(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado"));
        pagoRepository.delete(pago);
    }

    @Override
    public List<PagoDto> getAllPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        return PagoMapper.INSTANCE.pagosToPagosDto(pagos);
    }

    @Override
    public List<PagoDto> buscarPorFechaPagoEntre(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PagoNotFoundException {
        List<Pago> pagos = pagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin);
        if (pagos.isEmpty()) {
            throw new PagoNotFoundException("No se encontraron pagos en el rango de fechas proporcionado");
        }
        return PagoMapper.INSTANCE.pagosToPagosDto(pagos);
    }

    @Override
    public List<PagoDto> buscarPorIdYMetodoPago(Long id, MetodoPago metodoPago) throws PagoNotFoundException {
        List<Pago> pagos = pagoRepository.findByIdAndMetodoPago(id, metodoPago);
        if (pagos.isEmpty()) {
            throw new PagoNotFoundException("No se encontraron pagos con el ID " + id + " y el método de pago " + metodoPago);
        }
        return PagoMapper.INSTANCE.pagosToPagosDto(pagos);
    }
}
