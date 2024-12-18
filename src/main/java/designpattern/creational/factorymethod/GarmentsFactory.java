package designpattern.creational.factorymethod;

public class GarmentsFactory {

    public Cloth createGarment(String type) {
        if (type.equalsIgnoreCase("Spring")) {
            return new SpringCloth();
        }
        else if (type.equalsIgnoreCase("Winter")) {
            return new WinterCloth();
        } else if (type.equalsIgnoreCase("Summer")) {
            return new SummerCloth();
        }
        return null;
    }
}
