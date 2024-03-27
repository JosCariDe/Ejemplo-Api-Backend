package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.entities.enumClass.MetodoPago;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Builder
public class Pago extends BaseEntity{

    @OneToOne(mappedBy = "pago")
    private Pedido pedido;
    private Integer totalPago;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;

}
