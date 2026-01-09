package oop._05_abstraction;

/**
 * Demonstrates Abstract Classes and Methods
 * - Abstract class cannot be instantiated
 * - Abstract methods must be overridden
 * - Can have both abstract and concrete methods
 * - Can have constructors and fields
 */
public class ShapeExample {
    public static void main(String[] args) {
        System.out.println("=== Abstract Class Demo ===\n");

        // Cannot instantiate abstract class
        // Shape shape = new Shape("Red");  // ERROR

        // Create concrete objects
        Shape circle = new Circle("Red", 5.0);
        Shape rectangle = new Rectangle("Blue", 4.0, 6.0);
        Shape triangle = new Triangle("Green", 3.0, 4.0);

        // Polymorphic array
        Shape[] shapes = {circle, rectangle, triangle};

        for (Shape shape : shapes) {
            System.out.println("--- " + shape.getClass().getSimpleName() + " ---");
            shape.displayColor();  // Concrete method from abstract class
            System.out.println("Area: " + shape.area());  // Abstract method implemented
            System.out.println("Perimeter: " + shape.perimeter());  // Abstract method
            shape.describe();  // Concrete method
            System.out.println();
        }

        // Calculate total area
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.area();
        }
        System.out.println("Total Area of all shapes: " + totalArea);
    }
}

// Abstract class
abstract class Shape {
    // Fields (allowed in abstract class)
    protected String color;
    protected final double PI = 3.14159;

    // Constructor (allowed in abstract class)
    Shape(String color) {
        this.color = color;
        System.out.println("Shape constructor called");
    }

    // Abstract methods (no body) - must be implemented by child classes
    abstract double area();
    abstract double perimeter();

    // Concrete method (with body) - inherited by child classes
    void displayColor() {
        System.out.println("Color: " + color);
    }

    // Concrete method
    void describe() {
        System.out.println("This is a geometric shape");
    }

    // Getter
    String getColor() {
        return color;
    }

    // Setter
    void setColor(String color) {
        this.color = color;
    }
}

// Concrete class 1
class Circle extends Shape {
    private double radius;

    Circle(String color, double radius) {
        super(color);  // Call abstract class constructor
        this.radius = radius;
    }

    // Must implement all abstract methods
    @Override
    double area() {
        return PI * radius * radius;
    }

    @Override
    double perimeter() {
        return 2 * PI * radius;
    }

    // Can override concrete methods too
    @Override
    void describe() {
        System.out.println("Circle with radius " + radius);
    }
}

// Concrete class 2
class Rectangle extends Shape {
    private double length;
    private double width;

    Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }

    @Override
    double perimeter() {
        return 2 * (length + width);
    }

    @Override
    void describe() {
        System.out.println("Rectangle with length " + length + " and width " + width);
    }
}

// Concrete class 3
class Triangle extends Shape {
    private double base;
    private double height;

    Triangle(String color, double base, double height) {
        super(color);
        this.base = base;
        this.height = height;
    }

    @Override
    double area() {
        return 0.5 * base * height;
    }

    @Override
    double perimeter() {
        // Assuming equilateral for simplicity
        return 3 * base;
    }

    @Override
    void describe() {
        System.out.println("Triangle with base " + base + " and height " + height);
    }
}
