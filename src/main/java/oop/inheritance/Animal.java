package oop.inheritance;

public class Animal {
    protected String name;
    protected String color;

    public Animal(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Animal() {
    }

    protected void makeSound() {
        System.out.println("Animal makes sound");
    }

    protected void eat() {
        System.out.println("Animal eats");
    }

    // final method — inherited by child, but CANNOT be overridden
    public final void breathe() {
        System.out.println(name + " is breathing");
    }

    // static method — belongs to the class, not the object
    // child can hide it with its own static method, but cannot override it
    public static void describe() {
        System.out.println("I am an Animal");
    }
}
