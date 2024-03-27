package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "itemsPedidos")
public class ItemPedido extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @OneToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id")
    private Producto producto;
    private Integer cantidad;
    private Integer precioUnitario;


}
