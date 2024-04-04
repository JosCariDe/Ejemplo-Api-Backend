package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.Pago;
import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago,Long> {
    List<Pago> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Pago> findByIdAndMetodoPago(Long id, MetodoPago metodoPago);
    @Query("SELECT p FROM Pago p WHERE p.pedido.id = :idPedido")
    Pago buscarPagoPorIdPedido(@Param("idPedido") Long idPedido);
}
