package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.detalleEnvio.DetalleEnvioDto;
import UnivesidadMagdalena.Tienda.entities.DetalleEnvio;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import UnivesidadMagdalena.Tienda.exception.DetalleEnvioNotFoundException;

import java.util.List;

public interface DetalleEnvioService {
    DetalleEnvioDto guardarDetalleEnvio(DetalleEnvioDto detalleEnvioDto);
    DetalleEnvioDto actualizarDetalleEnvio(Long id, DetalleEnvioDto detalleEnvioDto) throws DetalleEnvioNotFoundException;
    DetalleEnvioDto buscarDetalleEnvioPorId(Long id) throws DetalleEnvioNotFoundException;
    List<DetalleEnvioDto> getAllDetalleEnvio();
    DetalleEnvioDto buscarDetallesEnvioPorIdPedido(Long idPedido) throws DetalleEnvioNotFoundException;
    DetalleEnvioDto findByTransportadora(String transportadora) throws DetalleEnvioNotFoundException;
    DetalleEnvioDto buscarDetalleenvioPorEstadoDePedido(Status estadoPedido) throws DetalleEnvioNotFoundException;
    void removerDetalleEnvio(Long id);
}
