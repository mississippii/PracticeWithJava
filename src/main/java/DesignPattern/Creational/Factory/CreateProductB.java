package DesignPattern.Creational.Factory;

public class CreateProductB extends Creator{
    @Override
    public Product factoryMethod() {
        return new ProductB();
    }
}
