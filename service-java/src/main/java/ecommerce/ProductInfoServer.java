package ecommerce;

import ecommerce.handler.ProductInfoImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ProductInfoServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        Server server = ServerBuilder.forPort(port)
                .addService(new ProductInfoImpl())
                .build()
                .start();

        System.out.println("Server started, listening on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            if (server != null) {
                server.shutdown();
            }
            System.out.println("Server shut down");
        }));

        server.awaitTermination();

    }

}
