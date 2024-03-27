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

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemPedidos;

    @OneToOne
    @JoinColumn(name = "id_pago", referencedColumnName = "id")
    private Pago pago;

    @OneToOne
    @JoinColumn(name = "id_envio", referencedColumnName = "id")
    private DetalleEnvio DetalleEnvio;
}
