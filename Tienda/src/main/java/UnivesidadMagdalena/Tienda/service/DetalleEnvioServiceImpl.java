package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.detalleEnvio.DetalleEnvioDto;
import UnivesidadMagdalena.Tienda.dto.detalleEnvio.DetalleEnvioMapper;
import UnivesidadMagdalena.Tienda.entities.DetalleEnvio;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import UnivesidadMagdalena.Tienda.exception.DetalleEnvioNotFoundException;
import UnivesidadMagdalena.Tienda.repository.DetalleEnvioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleEnvioServiceImpl implements DetalleEnvioService {
    private final DetalleEnvioMapper detalleEnvioMapper;
    private final DetalleEnvioRepository detalleEnvioRepository;

    public DetalleEnvioServiceImpl(DetalleEnvioMapper detalleEnvioMapper, DetalleEnvioRepository detalleEnvioRepository) {
        this.detalleEnvioMapper = detalleEnvioMapper;
        this.detalleEnvioRepository = detalleEnvioRepository;
    }

    @Override
    public DetalleEnvioDto guardarDetalleEnvio(DetalleEnvioDto detalleEnvioDto) {
        DetalleEnvio detalleEnvio = detalleEnvioMapper.detalleEnvioDtoToDetalleEnvio(detalleEnvioDto);
        DetalleEnvio detalleEnvioGuardado = detalleEnvioRepository.save(detalleEnvio);
        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvioGuardado);
    }

    @Override
    public DetalleEnvioDto actualizarDetalleEnvio(Long id, DetalleEnvioDto detalleEnvioDto) throws DetalleEnvioNotFoundException {
        DetalleEnvio detalleEnvio = detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("Detalle de envío no encontrado para el ID: " + id));

        detalleEnvio.setTransportadora(detalleEnvioDto.transportadora());
        detalleEnvio.setDireccion(detalleEnvioDto.direccion());

        DetalleEnvio detalleEnvioGuardado = detalleEnvioRepository.save(detalleEnvio);
        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvioGuardado);
    }

    @Override
    public DetalleEnvioDto buscarDetalleEnvioPorId(Long id) throws DetalleEnvioNotFoundException {
        DetalleEnvio detalleEnvio =detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("Detalle de envio no encontrado con id: " + id));

        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public List<DetalleEnvioDto> getAllDetalleEnvio() {
        List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findAll();
        if(detalleEnvios.isEmpty()) throw new DetalleEnvioNotFoundException("No hay ningun Detalle de envio en BD");
        return detalleEnvioMapper.detalleEnviosToDetalleEnviosDto(detalleEnvios);
    }

    @Override
    public DetalleEnvioDto buscarDetallesEnvioPorIdPedido(Long idPedido) throws DetalleEnvioNotFoundException {
        DetalleEnvio detalleEnvio = detalleEnvioRepository.buscarDetallesEnvioPorIdPedido(idPedido);
        if (detalleEnvio == null) {
            throw new DetalleEnvioNotFoundException("Detalle de envío no encontrado para el pedido con ID: " + idPedido);
        }
        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }


    @Override
    public DetalleEnvioDto findByTransportadora(String transportadora) throws DetalleEnvioNotFoundException {
        DetalleEnvio detalleEnvio = detalleEnvioRepository.findByTransportadora(transportadora);
        if (detalleEnvio == null) {
            throw new DetalleEnvioNotFoundException("Detalle de envío no encontrado para la transportadora: " + transportadora);
        }
        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public DetalleEnvioDto buscarDetalleenvioPorEstadoDePedido(Status estadoPedido) throws DetalleEnvioNotFoundException {
        DetalleEnvio detalleEnvio = detalleEnvioRepository.buscarDetalleenvioPorEstadoDePedido(estadoPedido);
        if (detalleEnvio == null) {
            throw new DetalleEnvioNotFoundException("Detalle de envío no encontrado para el estado de pedido: " + estadoPedido);
        }
        return detalleEnvioMapper.detalleEnvioToDetalleEnvioDto(detalleEnvio);
    }

    @Override
    public void removerDetalleEnvio(Long id) {
        DetalleEnvio detalleEnvio = detalleEnvioRepository.findById(id)
                .orElseThrow(() -> new DetalleEnvioNotFoundException("No se encontró DetalleEnvio a eliminar con la id: " + id));
        detalleEnvioRepository.delete(detalleEnvio);
    }
}
