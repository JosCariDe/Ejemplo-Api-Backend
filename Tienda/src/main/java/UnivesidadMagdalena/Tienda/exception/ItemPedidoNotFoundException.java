package UnivesidadMagdalena.Tienda.exception;

public class ItemPedidoNotFoundException extends RuntimeException{
    public ItemPedidoNotFoundException() {
    }

    public ItemPedidoNotFoundException(String message) {
        super(message);
    }

    public ItemPedidoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemPedidoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ItemPedidoNotFoundException(Throwable cause) {
        super(cause);
    }
}
