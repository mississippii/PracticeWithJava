package oop.inheritance;

public class Dog extends Animal {

    public Dog(String name, String color) {
        super(name, color);
    }

    // Overrides Animal.eat() — runtime polymorphism
    @Override
    public void eat() {
        System.out.println("Dog is eating");
    }

    // breathe() is inherited from Animal but CANNOT be overridden (final)
    // Uncommenting the lines below causes a compile error:
    // @Override
    // public void breathe() { }   // ERROR: cannot override final method

    // This is method HIDING, not overriding — static methods are not polymorphic
    // When called via Animal reference, Animal's describe() runs (not this one)
    public static void describe() {
        System.out.println("I am a Dog");
    }
}
