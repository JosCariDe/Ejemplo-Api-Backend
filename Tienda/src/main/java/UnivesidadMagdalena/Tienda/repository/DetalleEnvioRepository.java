package UnivesidadMagdalena.Tienda.repository;

import UnivesidadMagdalena.Tienda.entities.DetalleEnvio;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio,Long> {
    @Query("SELECT u FROM DetalleEnvio u WHERE u.pedido.id = :idPedido")
    DetalleEnvio buscarDetallesEnvioPorIdPedido(@Param("idPedido") Long idPedido);
    DetalleEnvio findByTransportadora(String transportadora);
    @Query("SELECT u FROM DetalleEnvio u WHERE u.pedido.status = :statusPedido")
    DetalleEnvio buscarDetalleenvioPorEstadoDePedido(@Param("statusPedido") Status estadoPedido);
}
