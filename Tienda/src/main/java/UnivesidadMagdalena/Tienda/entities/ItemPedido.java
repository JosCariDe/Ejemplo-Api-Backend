package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itemsPedidos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Builder
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id")
    private Producto producto;
    private Integer cantidad;
    private Integer precioUnitario;


}
