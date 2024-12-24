package dynamicObject;

public class SaltedCaramelFudge extends CoffeeDecorator{
    public SaltedCaramelFudge(Cofee coffee) {
        super(coffee);
    }

    @Override
    public String getIngredient() {
        return super.getIngredient() + ", Salted Caramel";
    }
}
