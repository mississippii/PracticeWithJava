package oop.Interface;

public class Cow implements Animal{

    @Override
    public void makeSound() {
        Animal.super.makeSound();
        System.out.println("Hamba");
    }
}
