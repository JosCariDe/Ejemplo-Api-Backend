package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "productos")
@Builder
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

        @OneToMany(mappedBy = "producto")
        private List<ItemPedido> itemPedidos;
}
