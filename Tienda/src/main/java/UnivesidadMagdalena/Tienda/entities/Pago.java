package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.entities.enumClass.MetodoPago;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago extends BaseEntity{
    private Pedido pedido;
    private Integer totalPago;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;

}
