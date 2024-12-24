package dynamicObject;

public class CofeeApp {
    public static void main(String[] args) {
        Cofee cofee = new VanillaAlmondExtract(new SaltedCaramelFudge(new CofeeBean()));
        System.out.println(cofee.getIngredient());
    }
}
