package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "pagos")
@Builder
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "pago")
    private Pedido pedido;
    private Integer totalPago;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;

}
