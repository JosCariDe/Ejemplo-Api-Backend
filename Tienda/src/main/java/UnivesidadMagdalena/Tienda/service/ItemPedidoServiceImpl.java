package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoMapper;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ItemPedidoNotFoundException;
import UnivesidadMagdalena.Tienda.repository.ItemPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPedidoServiceImpl implements ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    public ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedidoDto) {
        ItemPedido itemPedido = ItemPedidoMapper.INSTANCE.itemPedidoToSaveDtoToItemPedido(itemPedidoDto);
        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedido);
        return ItemPedidoMapper.INSTANCE.itemPedidoToItemPedidoDto(itemPedidoGuardado);
    }

    @Override
    public ItemPedidoDto actualizarItemPedido(Long id, ItemPedidoToSaveDto itemPedidoDto) throws ItemPedidoNotFoundException {
        return itemPedidoRepository.findById(id).map(itemPedidoInDb -> {
            itemPedidoInDb.setCantidad(itemPedidoDto.cantidad());
            itemPedidoInDb.setPrecioUnitario(itemPedidoDto.precioUnitario());
            // Actualizar otros campos si es necesario

            ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedidoInDb);

            return ItemPedidoMapper.INSTANCE.itemPedidoToItemPedidoDto(itemPedidoGuardado);
        }).orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido no encontrado"));
    }

    @Override
    public ItemPedidoDto buscarItemPedidoPorId(Long id) throws ItemPedidoNotFoundException {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ItemPedidoNotFoundException("ItemPedido no encontrado"));
        return ItemPedidoMapper.INSTANCE.itemPedidoToItemPedidoDto(itemPedido);
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
        return ItemPedidoMapper.INSTANCE.itemPedidosToItemPedidosDto(itemPedidos);
    }

    @Override
    public List<ItemPedidoDto> buscarItemsPorIdPedido(Long idPedido) throws ItemPedidoNotFoundException {
        List<ItemPedido> itemPedidos = itemPedidoRepository.buscarItemsPorIdPedido(idPedido);
        if (itemPedidos.isEmpty()) {
            throw new ItemPedidoNotFoundException("No se encontraron ItemPedidos para el Pedido con ID: " + idPedido);
        }
        return ItemPedidoMapper.INSTANCE.itemPedidosToItemPedidosDto(itemPedidos);
    }

    @Override
    public List<ItemPedidoDto> findByProducto(Producto producto) throws ItemPedidoNotFoundException {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findByProducto(producto);
        if (itemPedidos.isEmpty()) {
            throw new ItemPedidoNotFoundException("No se encontraron ItemPedidos para el Producto: " + producto.getNombre());
        }
        return ItemPedidoMapper.INSTANCE.itemPedidosToItemPedidosDto(itemPedidos);
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
