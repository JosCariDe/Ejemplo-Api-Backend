package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "productos")
@Builder
@AllArgsConstructor
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
