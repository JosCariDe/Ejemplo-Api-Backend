package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "itemsPedidos")
public class ItemPedido extends BaseEntity{
    private Pedido pedido;
    private Producto producto;
    private Integer cantidad;
    private Integer precioUnitario;
}
