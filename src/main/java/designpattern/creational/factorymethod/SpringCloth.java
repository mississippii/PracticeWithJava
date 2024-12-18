package designpattern.creational.factorymethod;

public class SpringCloth implements Cloth {
    @Override
    public void averagePrice() {
        System.out.println("SpringCloth.averagePrice : "+20);
    }
}
