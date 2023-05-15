public class ConnectionEx {

    private static final Logger logger = Loggers.getLogger(ConnectionEx.class);

    private Connection connection;

    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    private Sinks.Many<ByteBuf> requests = Sinks.many().unicast().onBackpressureBuffer();


    private ByteBufAllocator alloc;

    public ConnectionEx(Connection connection) {
        this.connection = connection;
        this.alloc = this.connection.outbound().alloc();
        if (logger.isTraceEnabled()) {
            connection.addHandlerFirst(
                    LoggingHandler.class.getSimpleName(),
                    new LoggingHandler(ConnectionEx.class, LogLevel.TRACE));
        }
        doHandle(connection.inbound(),connection.outbound());
    }


    private void doCancel(){

    }

    private void doHandle(NettyInbound inbound, NettyOutbound outbound){
        inbound.receive().subscribe(byteBuf -> {
            if (ClientManager.contains(connection.toString())) {
                ByteBuf copy = byteBuf.copy();
                Connection clientConn = ClientManager.get(connection.toString());
                clientConn.outbound().send(Mono.just(copy)).then().subscribe();
            } else {
                String data = byteBuf.toString(Charset.defaultCharset());
                System.out.println(data);
                if (data.startsWith("CONNECT")) {
                    String[] arr = data.split("\r\n");
                    String host = arr[1];
                    host = host.replace("Host: ", "");
                    host = host.replace(":443", "");
                    ReactorNettyProxyClient client = new ReactorNettyProxyClient(host, 443);
                    client.connect().subscribe(conn -> {
                        ClientManager.put(connection.toString(), conn);
                        requests.asFlux().concatMap(bf -> {
                            return outbound.sendObject(bf);
                        }).subscribe();

                        conn.inbound().receive().subscribe(b -> {
                            ByteBuf b2 = b.copy();
                            requests.tryEmitNext(b2);
                        });
                    });
                    String resp = "HTTP/1.1 200 Connection Established\r\n\r\n";
                    byte[] respArr = resp.getBytes(StandardCharsets.UTF_8);
                    ByteBuf bf = Unpooled.wrappedBuffer(respArr);
                    requests.tryEmitNext(bf);
                } else if (data.startsWith("GET")) {
                    String[] arr = data.split("\r\n");
                    String host = arr[1];
                    host = host.replace("Host: ", "");
                    ReactorNettyProxyClient client = new ReactorNettyProxyClient(host, 80);
                    client.connect().subscribe(conn -> {
                        ClientManager.put(connection.toString(), conn);
                        requests.asFlux().concatMap(bf -> {
                            return outbound.sendObject(bf);
                        }).subscribe();

                        conn.inbound().receive().subscribe(b -> {
                            ByteBuf b2 = b.copy();
                            requests.tryEmitNext(b2);
                        });
                        byte[] reqArr = data.getBytes(StandardCharsets.UTF_8);
                        ByteBuf bf = Unpooled.wrappedBuffer(reqArr);
                        conn.outbound().send(Mono.just(bf)).then().subscribe();
                    });
                }
            }
        });
    }


    public Mono<Void> close() {
        return Mono.defer(
            () -> {
                if (this.isClosed.compareAndSet(false, true)) {

                    Channel channel = this.connection.channel();
                    if (!channel.isOpen()) {
                        this.connection.dispose();
                        return this.connection.onDispose();
                    }
                }
                return Mono.empty();
            });
    }

    private void handleConnectionError(Throwable throwable) {

    }


    @Override
    public String toString() {
        return "Client{isClosed=" + isClosed + '}';
    }



    private void handleIncomingFrames(ByteBuf frame) {
    }

}
