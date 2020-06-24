package com.example.server;

import com.example.ProductCart;
import com.example.ProductManager;
import com.example.server.handler.ProductCartHandler;
import com.example.server.handler.ProductManagerHandler;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class ProductCartServer {

    public static ProductCartHandler handlerCart;
    public static ProductManagerHandler handlerManager;

    public static ProductCart.Processor processorCart;
    public static ProductManager.Processor processorManager;

    public static void main(String[] args) {
        try {
            handlerCart = new ProductCartHandler();
            handlerManager = new ProductManagerHandler();
            processorCart = new ProductCart.Processor(handlerCart);
            processorManager = new ProductManager.Processor(handlerManager);
            new Thread(() -> simple(processorCart, processorManager)).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void simple(ProductCart.Processor productCartProcessor, ProductManager.Processor productManagerProcessor) {
        try {
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            TServerTransport t = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(t).processor(processor));
            processor.registerProcessor("ProductCart", productCartProcessor);
            processor.registerProcessor("ProductManager", productManagerProcessor);
            System.out.println("starting server port:" + 9090);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
