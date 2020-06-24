package com.example.server.handler;



import com.example.Product;
import com.example.ProductManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManagerHandler implements ProductManager.Iface {
    static Map<Integer, Product> products = new HashMap<>(Map.of(
            1, new Product(1, "first", 12.3, 3),
            2, new Product(2, "second", 23.4, 12)));

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
