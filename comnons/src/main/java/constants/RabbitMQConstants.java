package constants;

public class RabbitMQConstants {

    public static final String EXG_NAME_MARKETPLACE = "marketplace.direct";     //Exchange
    public static final String QUEUE_PRODUCT_LOG = "fila-product.log";   //Queue
    public static final String RK_PRODUCT_LOG = "rk-product.log";      //Routing key

    public static final String QUEUE_A = "fila-A";
    public static final String QUEUE_B = "fila-B";
    public static final String QUEUE_C = "fila-C";
    public static final String QUEUE_DLQ = "fila-dlq";
    public static final String QUEUE_DLQ_PARKING_LOT = "fila-dlq-parking-lot";

    public static final String EXG_DIRECT = "exg-rabbit.direct";
    public static final String DLX_EXG_DIRECT = "dlx-exchange.direct";
    public static final String EXG_FANOUT = "exg-rabbit.fanout";

    public static final String RK_A = "routing-key-A";
    public static final String RK_B = "routing-key-B";
    public static final String RK_C = "routing-key-C";
    public static final String RK_DLX = "routing-key-dlx";
}
