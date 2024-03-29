package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.enumClass.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(mappedBy = "pago")
    private Pedido pedido;
    private Integer totalPago;
    private LocalDateTime fechaPago;
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

}
