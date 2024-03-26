package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.entities.enumClass.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido extends BaseEntity{
    private Cliente cliente;
    private LocalDateTime fechaPedido;
    private Status status;
}
