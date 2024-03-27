package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalleEnvios")
public class DetalleEnvio extends BaseEntity {

    @OneToOne(mappedBy = "DetalleEnvio")
    private Pedido pedido;
    private String direccion;
    private String transportadora;
    private Long numeroGuia;

}
