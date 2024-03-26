package UnivesidadMagdalena.Tienda.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto extends BaseEntity{
        private String nombre;
        private Integer price;
        private Integer stock;
}
