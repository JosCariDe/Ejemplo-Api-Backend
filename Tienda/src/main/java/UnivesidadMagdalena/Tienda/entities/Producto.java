package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Builder
public class Producto {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
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
