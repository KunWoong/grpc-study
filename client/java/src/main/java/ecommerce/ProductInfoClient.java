package ecommerce;

import ecommerce.product.ProductInfoGrpc;
import ecommerce.product.ProductInfoGrpc.ProductInfoBlockingStub;
import ecommerce.product.ProductInfoOuterClass;
import ecommerce.product.ProductInfoOuterClass.Product;
import ecommerce.product.ProductInfoOuterClass.ProductID;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ProductInfoClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ProductInfoBlockingStub stub = ProductInfoGrpc.newBlockingStub(channel);

        ProductID productID = stub.addProduct(
                Product.newBuilder()
                        .setName("Name from client")
                        .setDescription("description from client")
                        .setPrice(1000.0F)
                        .build()
        );

        System.out.println("productID = " + productID);

        Product product = stub.getProduct(productID);

        System.out.println("product = " + product);

        channel.shutdown();
    }
}
