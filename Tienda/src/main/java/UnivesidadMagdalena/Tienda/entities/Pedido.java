package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.entities.enumClass.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    private LocalDateTime fechaPedido;
    private Status status;

    @ManyToMany(mappedBy = "pedidos")
    private List<Producto> productos;
}
