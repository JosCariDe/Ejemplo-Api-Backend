package UnivesidadMagdalena.Tienda.service;

import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoToSaveDto;
import UnivesidadMagdalena.Tienda.entities.Producto;
import UnivesidadMagdalena.Tienda.exception.ItemPedidoNotFoundException;

import java.util.List;

public interface ItemPedidoService {
    ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedidoDto);
    ItemPedidoDto actualizarItemPedido(Long id, ItemPedidoToSaveDto itemPedidoDto) throws ItemPedidoNotFoundException;
    ItemPedidoDto buscarItemPedidoPorId(Long id) throws ItemPedidoNotFoundException;
    void removerItemPedido(Long id);
    List<ItemPedidoDto> getAllItemPedidos();
    List<ItemPedidoDto> buscarItemsPorIdPedido(Long idPedido) throws ItemPedidoNotFoundException;

    List<ItemPedidoDto> findByProducto(Producto producto) throws ItemPedidoNotFoundException;

    Integer calcularTotalVentasPorProducto(Producto producto) throws ItemPedidoNotFoundException;
}
