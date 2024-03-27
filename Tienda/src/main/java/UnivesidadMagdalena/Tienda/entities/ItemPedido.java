package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "itemsPedidos")
@Builder
public class ItemPedido extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id")
    private Producto producto;
    private Integer cantidad;
    private Integer precioUnitario;


}
