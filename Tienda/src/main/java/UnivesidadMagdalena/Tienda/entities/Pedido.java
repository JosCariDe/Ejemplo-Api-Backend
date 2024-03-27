package UnivesidadMagdalena.Tienda.entities;

import UnivesidadMagdalena.Tienda.entities.enumClass.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "pedidos")
@Builder
@AllArgsConstructor
public class Pedido extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    private LocalDateTime fechaPedido;
    private Status status;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemPedidos;

    @OneToOne
    @JoinColumn(name = "id_pago", referencedColumnName = "id")
    private Pago pago;

    @OneToOne
    @JoinColumn(name = "id_envio", referencedColumnName = "id")
    private DetalleEnvio DetalleEnvio;
}
