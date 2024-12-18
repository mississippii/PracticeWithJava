package designpattern.creational.factorymethod;

public class WinterCloth implements Cloth {
    @Override
    public void averagePrice() {
        System.out.println("WinterCloth.averagePrice : "+10);
    }
}
