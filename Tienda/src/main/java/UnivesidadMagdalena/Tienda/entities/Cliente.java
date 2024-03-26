package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends BaseEntity{
    private String nombre;
    private String email;
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
}
