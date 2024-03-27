package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itemsPedidos")
public class ItemPedido extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    private Producto producto;
    private Integer cantidad;
    private Integer precioUnitario;


}
