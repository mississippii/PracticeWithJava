package DesignPattern.Creational.Factory;

public class CreatProductA extends Creator{
    @Override
    public Product factoryMethod() {
        return new ProductA();
    }
}
