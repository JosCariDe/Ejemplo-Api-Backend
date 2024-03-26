package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
public class Cliente extends BaseEntity{
    private String nombre;
    private String email;
    private String direccion;
}
