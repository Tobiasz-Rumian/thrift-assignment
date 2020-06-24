package com.example.server.handler;



import com.example.NotEnoughInStoreageException;
import com.example.OrderItem;
import com.example.ProductCart;

import java.util.HashMap;
import java.util.Map;

public class ProductCartHandler implements ProductCart.Iface {
    Map<Integer, OrderItem> basket = new HashMap<>();

    @Override
    public void addItem(OrderItem item) {
        basket.put(item.id, item);
        System.out.println("Item added");
    }

    @Override
    public void removeItem(int id) {
        basket.remove(id);
        System.out.println("Item removed");
    }

    @Override
    public void updateItem(OrderItem item) {
        basket.replace(item.id, item);
        System.out.println("Item updated");
    }

    @Override
    public void placeOrder() throws NotEnoughInStoreageException {

        for (Map.Entry<Integer, OrderItem> entry : basket.entrySet()) {
            if (ProductManagerHandler.products.get(entry.getKey()).amountInStoreage < entry.getValue().amount) {
                throw new NotEnoughInStoreageException();
            }
        }
        for (Map.Entry<Integer, OrderItem> entry : basket.entrySet()) {
            ProductManagerHandler.products.get(entry.getKey()).amountInStoreage -= entry.getValue().amount;
        }
        System.out.println("Order placed");
    }
}
