package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "productos")
public class Producto extends BaseEntity{
        private String nombre;
        private Integer price;
        private Integer stock;

        @ManyToMany
        @JoinTable(
                name = "productos_pedido",
                joinColumns = @JoinColumn(name = "id_producto"),
                inverseJoinColumns = @JoinColumn(name = "id_pedido")
        )
        private List<Pedido> pedidos;

        @OneToOne(mappedBy = "itemPedido")
        private ItemPedido itemPedido;
}
