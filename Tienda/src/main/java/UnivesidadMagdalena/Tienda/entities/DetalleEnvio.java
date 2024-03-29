package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalleEnvios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class DetalleEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "DetalleEnvio")
    private Pedido pedido;
    private String direccion;
    private String transportadora;
    private Long numeroGuia;

}
