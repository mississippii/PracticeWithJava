package oop.encapsulation;

import oop.inheritance.Product;

import java.util.ArrayList;

public class ShoppingCart {
    private final ArrayList<Product> products = new ArrayList<>();;

    public void addProduct(Product product) {
        products.add(product);
    }
    public double getTotalPrice() {
        double totalPrice = 0;
        for(Product product : products){
            totalPrice += product.calculatePrice();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "products=" + products +
                '}';
    }
}
