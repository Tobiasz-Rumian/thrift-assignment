package com.example.client;

import com.example.OrderItem;
import com.example.Product;
import com.example.ProductCart;
import com.example.ProductManager;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import java.util.List;

public class ProductCartClient {
    public static void main(String[] args) {
        try {
            TSocket transport = new TSocket("localhost", 9090);
            transport.open();
            TBinaryProtocol protocol = new TBinaryProtocol(transport);
            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "ProductManager");
            ProductManager.Client productManagerClient = new ProductManager.Client(mp);
            TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol, "ProductCart");
            ProductCart.Client cartClient = new ProductCart.Client(mp2);
            perform(productManagerClient, cartClient);
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void perform(ProductManager.Client productManagerclient, ProductCart.Client cartClient) throws TException {
        List<Product> allProducts = productManagerclient.getAllProducts();
        System.out.println(allProducts);
        OrderItem it1 = new OrderItem(allProducts.get(0).getId(), 1);
        cartClient.addItem(it1);
        OrderItem it2 = new OrderItem(allProducts.get(1).getId(), 5);
        cartClient.addItem(it2);
        cartClient.removeItem(it2.id);
        it1.amount = 2;
        cartClient.updateItem(it1);
        cartClient.placeOrder();
        allProducts = productManagerclient.getAllProducts();
        System.out.println(allProducts);
    }
}
