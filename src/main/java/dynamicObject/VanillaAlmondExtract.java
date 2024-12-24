package dynamicObject;

public class VanillaAlmondExtract extends CoffeeDecorator{
    public VanillaAlmondExtract(Cofee coffee) {
        super(coffee);
    }

    @Override
    public String getIngredient() {
        return super.getIngredient() + ", Vanilla Caramel";
    }
}
