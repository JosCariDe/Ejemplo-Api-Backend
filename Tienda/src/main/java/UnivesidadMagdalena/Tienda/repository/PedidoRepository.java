package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Cliente;
import UnivesidadMagdalena.Tienda.entities.Pedido;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByFechaPedidoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Pedido> findByClienteAndStatus(Cliente cliente, Status status);

    @Query("SELECT DISTINCT p FROM Pedido p JOIN FETCH p.itemPedidos ip WHERE p.cliente.id = :clienteId")
    List<Pedido> findPedidosConArticulosByCliente(@Param("clienteId") Long idCliente);
}
