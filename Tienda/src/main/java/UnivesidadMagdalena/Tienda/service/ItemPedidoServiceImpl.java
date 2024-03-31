package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoMapper;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ItemPedidoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {
    private final ItemPedidoMapper itemPedidoMapper;
    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoServiceImpl(ItemPedidoMapper itemPedidoMapper, ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoMapper = itemPedidoMapper;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedidoDto) {
        ItemPedido itemPedido = itemPedidoMapper.itemPedidoToSaveDtoToItemPedido(itemPedidoDto);
        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedido);
        return itemPedidoMapper.itemPedidoToItemPedidoDto(itemPedidoGuardado);
    }

    @Override
    public ItemPedidoDto actualizarItemPedido(Long id, ItemPedidoToSaveDto itemPedidoDto) throws ItemPedidoNotFoundException {
        return itemPedidoRepository.findById(id).map(itemPedidoInDb -> {
            itemPedidoInDb.setCantidad(itemPedidoDto.cantidad());
            itemPedidoInDb.setPrecioUnitario(itemPedidoDto.precioUnitario());
            // Actualizar otros campos si es necesario

            ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedidoInDb);

            return itemPedidoMapper.itemPedidoToItemPedidoDto(itemPedidoGuardado);
        }).orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido no encontrado"));
    }

    @Override
    public ItemPedidoDto buscarItemPedidoPorId(Long id) throws ItemPedidoNotFoundException {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido no encontrado"));
        return itemPedidoMapper.itemPedidoToItemPedidoDto(itemPedido);
    }

    @Override
    public void removerItemPedido(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido no encontrado"));
        itemPedidoRepository.delete(itemPedido);
    }

    @Override
    public List<ItemPedidoDto> getAllItemPedidos() {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
        return itemPedidoMapper.itemPedidosToItemPedidosDto(itemPedidos);
    }
    @Override
    public List<ItemPedidoDto> buscarItemsPorIdPedido(Long idPedido) throws ItemPedidoNotFoundException {
        List<ItemPedido> itemPedidos = itemPedidoRepository.buscarItemsPorIdPedido(idPedido);
        if (itemPedidos.isEmpty()) {
            throw new ItemPedidoNotFoundException("No se encontraron ItemPedidos para el Pedido con ID: " + idPedido);
        }
        return itemPedidoMapper.itemPedidosToItemPedidosDto(itemPedidos);
    }

    @Override
    public List<ItemPedidoDto> findByProducto(Producto producto) throws ItemPedidoNotFoundException {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findByProducto(producto);
        if (itemPedidos.isEmpty()) {
            throw new ItemPedidoNotFoundException("No se encontraron ItemPedidos para el Producto: " + producto.getNombre());
        }
        return itemPedidoMapper.itemPedidosToItemPedidosDto(itemPedidos);
    }

    @Override
    public Integer calcularTotalVentasPorProducto(Producto producto) throws ItemPedidoNotFoundException {
        Integer totalVentas = itemPedidoRepository.calcularTotalVentasPorProducto(producto);
        if (totalVentas == null) {
            throw new ItemPedidoNotFoundException("No se encontraron ventas para el Producto: " + producto.getNombre());
        }
        return totalVentas;
    }
}
