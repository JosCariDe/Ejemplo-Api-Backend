package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "detalleEnvios")
@Builder
@Data
@AllArgsConstructor
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
