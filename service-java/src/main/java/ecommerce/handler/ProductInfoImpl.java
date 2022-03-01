package ecommerce.handler;

import ecommerce.product.ProductInfoGrpc;
import ecommerce.product.ProductInfoOuterClass.Product;
import ecommerce.product.ProductInfoOuterClass.ProductID;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase {
    private Map<String, Product> productMap = new HashMap();

    @Override
    public void addProduct(Product request, StreamObserver<ProductID> responseObserver) {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        request = request.toBuilder().setId(randomUUIDString).build();
        productMap.put(randomUUIDString, request);
        ProductID productID = ProductID.newBuilder().setValue(randomUUIDString).build();
        responseObserver.onNext(productID);
        responseObserver.onCompleted();
        System.out.println("productMap.get(randomUUIDString) = " + productMap.get(randomUUIDString));
    }

    @Override
    public void getProduct(ProductID request, StreamObserver<Product> responseObserver) {
        String id = request.getValue();
        System.out.println("id = " + id);
        if (productMap.containsKey(id)) {
            responseObserver.onNext(productMap.get(id));
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        }
    }
}
