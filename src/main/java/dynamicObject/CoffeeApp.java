package dynamicObject;

public class CoffeeApp {
    public static void main(String[] args) {
        Coffee coffee = new VanillaAlmondExtract(new SaltedCaramelFudge(new CoffeeBean()));
        System.out.println(coffee.getIngredient());
    }
}
