package designpattern.creational.factorymethod;

public class FactoryMethod {
    public static void main(String[] args) {
        GarmentsFactory garmentsFactory = new GarmentsFactory();
        Cloth x =  garmentsFactory.createGarment("Summer");
        x.averagePrice();
    }
}
