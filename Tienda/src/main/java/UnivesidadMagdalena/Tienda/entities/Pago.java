package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "pagos")
@Builder
@AllArgsConstructor
public class Pago extends BaseEntity{

    @OneToOne(mappedBy = "pago")
    private Pedido pedido;
    private Integer totalPago;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;

}
