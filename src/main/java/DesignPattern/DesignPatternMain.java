package DesignPattern;

import DesignPattern.Creational.Factory.CreatProductA;
import DesignPattern.Creational.Factory.CreatProductB;
import DesignPattern.Creational.Factory.Creator;
import DesignPattern.Creational.Factory.Product;

public class DesignPatternMain {
    public static void main(String[] args) {
        Creator creator = new CreatProductA();
        Product product = creator.factoryMethod();
        product.display();
        Creator creator1 = new CreatProductB();
        Product product1 = creator1.factoryMethod();
        product1.display();
    }
}
