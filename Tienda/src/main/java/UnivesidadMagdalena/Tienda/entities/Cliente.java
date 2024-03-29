package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
}
