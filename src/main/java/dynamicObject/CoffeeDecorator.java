package dynamicObject;


public abstract class CoffeeDecorator implements Coffee {
    private final Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
    @Override
    public String getIngredient(){
        return coffee.getIngredient();
    }
}
