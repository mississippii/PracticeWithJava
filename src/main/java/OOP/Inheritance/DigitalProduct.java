package OOP.Inheritance;

import OOP.Encapsulation.Dimension;

public class DigitalProduct extends Product {
    public DigitalProduct(String name, int price, int discount, int weight, Dimension dimension) {
        super(name, price, discount, weight, dimension);
    }
}
