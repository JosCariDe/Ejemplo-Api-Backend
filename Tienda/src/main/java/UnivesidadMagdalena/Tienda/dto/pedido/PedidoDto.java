package UnivesidadMagdalena.Tienda.dto.pedido;

import UnivesidadMagdalena.Tienda.dto.cliente.ClienteDto;
import UnivesidadMagdalena.Tienda.dto.detalleEnvio.DetalleEnvioDto;
import UnivesidadMagdalena.Tienda.dto.itemPedido.ItemPedidoDto;
import UnivesidadMagdalena.Tienda.dto.pago.PagoDto;
import UnivesidadMagdalena.Tienda.enumClass.Status;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Builder
public record PedidoDto(Long id,
                        ClienteDto cliente,
                        LocalDateTime fechaPedido,
                        Status status,
                        List<ItemPedidoDto> itemPedidos,
                        PagoDto pago,
                        DetalleEnvioDto detalleEnvio)
{

    //public List<ItemPedidoDto> itemPedidos() {
      //  return Collections.unmodifiableList(itemPedidos);
    //}

}
