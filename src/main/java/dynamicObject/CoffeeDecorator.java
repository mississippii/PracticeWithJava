package dynamicObject;


public abstract class CoffeeDecorator implements Cofee   {
    private final Cofee coffee;

    public CoffeeDecorator(Cofee coffee) {
        this.coffee = coffee;
    }
    @Override
    public String getIngredient(){
        return coffee.getIngredient();
    }
}
