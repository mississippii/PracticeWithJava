package DesignPattern.Creational.Factory;

public class CreatProductB extends Creator{
    @Override
    public Product factoryMethod() {
        return new ProductB();
    }
}
