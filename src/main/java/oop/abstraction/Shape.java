package oop.abstraction;

public abstract class Shape {
    protected String color;

    // protected — child classes call this via super()
    protected Shape(String color) {
        this.color = color;
    }

    // Abstract methods — every shape MUST implement these
    public abstract double area();
    public abstract double perimeter();

    // Concrete methods — shared by all shapes, no need to override
    public void displayColor() {
        System.out.println("Color: " + color);
    }

    public void describe() {
        System.out.println("This is a geometric shape");
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // final method — every shape inherits this, but NO subclass can override it
    public final void printType() {
        System.out.println("I am a Shape");
    }

    // static method — belongs to Shape class, not to any object
    // subclasses can hide it with their own static method, but cannot override it
    public static void category() {
        System.out.println("Category: Geometric Shape");
    }
}
