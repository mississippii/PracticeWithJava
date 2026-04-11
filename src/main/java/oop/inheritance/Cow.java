package oop.inheritance;

public class Cow extends Animal{
    public Cow(String name, String color) {
        super(name, color);
    }

    @Override
    public void eat(){
        System.out.println("Cow is eating");
    }
}
