package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import UnivesidadMagdalena.Tienda.exception.PedidoNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
    // Métodos básicos
    PedidoDto guardarPedido(PedidoDto pedidoDto);
    PedidoDto actualizarPedido(Long id, PedidoDto pedidoDto);
    PedidoDto buscarPedidoPorId(Long id) throws PedidoNotFoundException;
    void removerPedido(Long id);
    List<PedidoDto> getAllPedidos();

    // Métodos específicos
    List<PedidoDto> findByFechaPedidoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PedidoNotFoundException;
    List<PedidoDto> findByClienteAndStatus(Cliente cliente, Status status) throws PedidoNotFoundException;
    List<PedidoDto> findPedidosConArticulosByCliente(Long idCliente) throws PedidoNotFoundException;
}
