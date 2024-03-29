package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "itemsPedidos")
@Builder
@AllArgsConstructor
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
