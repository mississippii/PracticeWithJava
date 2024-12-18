package designpattern.creational.factorymethod;

public class SummerCloth implements Cloth {
    @Override
    public void averagePrice() {
        System.out.println("Summer Cloth Average Price : "+15);
    }
}
