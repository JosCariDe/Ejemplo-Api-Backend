package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "detalleEnvios")
@Builder
@Data
@AllArgsConstructor
public class DetalleEnvio extends BaseEntity {

    @OneToOne(mappedBy = "DetalleEnvio")
    private Pedido pedido;
    private String direccion;
    private String transportadora;
    private Long numeroGuia;

}
