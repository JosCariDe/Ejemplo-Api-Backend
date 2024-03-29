package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "productos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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
