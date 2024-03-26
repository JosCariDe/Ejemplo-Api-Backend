package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.entities.enumClass.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    private LocalDateTime fechaPedido;
    private Status status;
}
