package OOP.Inheritance;

import OOP.Abstraction.Dimension;
import OOP.Abstraction.Product;

public class DigitalProduct extends Product {
    public DigitalProduct(String name, int price, int discount, int weight, Dimension dimension) {
        super(name, price, discount, weight, dimension);
    }
}
