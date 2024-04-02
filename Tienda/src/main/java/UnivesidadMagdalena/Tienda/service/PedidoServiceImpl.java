package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.pedido.PedidoDto;
import UnivesidadMagdalena.Tienda.dto.pedido.PedidoMapper;
import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.entities.Pedido;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import UnivesidadMagdalena.Tienda.exception.PedidoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    private final PedidoMapper pedidoMapper;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoMapper pedidoMapper, PedidoRepository pedidoRepository) {
        this.pedidoMapper = pedidoMapper;
        this.pedidoRepository = pedidoRepository;
    }

    // Métodos básicos

    @Override
    public PedidoDto guardarPedido(PedidoDto pedidoDto) {
        Pedido pedido = PedidoMapper.INSTANCE.pedidoDtoToPedido(pedidoDto);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return PedidoMapper.INSTANCE.pedidoToPedidoDto(pedidoGuardado);
    }

    @Override
    public PedidoDto actualizarPedido(Long id, PedidoDto pedidoDto) {
        return pedidoRepository.findById(id).map(pedidoInDb -> {
            // Actualizar los campos necesarios
            pedidoInDb.setFechaPedido(pedidoDto.fechaPedido());
            pedidoInDb.setStatus(pedidoDto.status());
            Pedido pedidoActualizado = pedidoRepository.save(pedidoInDb);
            return PedidoMapper.INSTANCE.pedidoToPedidoDto(pedidoActualizado);
        }).orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));
    }

    @Override
    public PedidoDto buscarPedidoPorId(Long id) throws PedidoNotFoundException {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));
        return PedidoMapper.INSTANCE.pedidoToPedidoDto(pedido);
    }

    @Override
    public void removerPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));
        pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoDto> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return PedidoMapper.INSTANCE.pedidosToPedidosDto(pedidos);
    }

    // Métodos específicos

    @Override
    public List<PedidoDto> findByFechaPedidoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetween(fechaInicio,fechaFin);
        if(pedidos.isEmpty()) throw new PedidoNotFoundException("No se encontró un Pedido entre las fechas digitadas");
        return PedidoMapper.INSTANCE.pedidosToPedidosDto(pedidos);
    }

    @Override
    public List<PedidoDto> findByClienteAndStatus(Cliente cliente, Status status) {
        List<Pedido> pedidos = pedidoRepository.findByClienteAndStatus(cliente, status);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos para el cliente con ID: " + cliente.getId() +
                    " y estado: " + status.toString());
        }
        return PedidoMapper.INSTANCE.pedidosToPedidosDto(pedidos);

    }

    @Override
    public List<PedidoDto> findPedidosConArticulosByCliente(Long idCliente) {
        List<Pedido> pedidos = pedidoRepository.findPedidosConArticulosByCliente(idCliente);
        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("No se encontraron pedidos con artículos para el cliente con ID: " + idCliente);
        }
        return PedidoMapper.INSTANCE.pedidosToPedidosDto(pedidos);
    }
}
