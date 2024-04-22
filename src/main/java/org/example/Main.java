package org.example;

import org.OOP.Product;
import org.OOP.ShoppingCart;

import java.util.StringJoiner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        Product keyboard = new Product("Mechanical",5800);
        Product Monitor = new Product("MSI Monitor",26300);
        Product mouse = new Product("Wireless",800);
        cart.addProduct(keyboard);
        cart.addProduct(Monitor);
        cart.addProduct(mouse);
        System.out.println(cart);
        System.out.println(cart.getTotalPrice());
    }
}