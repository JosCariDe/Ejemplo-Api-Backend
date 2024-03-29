package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.ItemPedido;
import UnivesidadMagdalena.Tienda.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Long> {
    @Query("SELECT u FROM ItemPedido u WHERE u.pedido.id = :idPedido")
    List<ItemPedido> buscarItemsPorIdPedido(@Param("idPedido") Long idPedido);

    List<ItemPedido> findByProducto(Producto producto);

    @Query("SELECT SUM(u.cantidad * u.precioUnitario) FROM ItemPedido u WHERE u.producto = :producto")
    Integer calcularTotalVentasPorProducto(@Param("producto") Producto productoId);
}
