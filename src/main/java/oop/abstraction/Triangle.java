package oop.abstraction;

public class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(String color, double base, double height) {
        super(color);
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }

    // Treats as right triangle: hypotenuse = sqrt(base² + height²)
    // perimeter = base + height + hypotenuse
    @Override
    public double perimeter() {
        double hypotenuse = Math.sqrt(base * base + height * height);
        return base + height + hypotenuse;
    }

    @Override
    public void describe() {
        System.out.println("Triangle with base " + base + " and height " + height);
    }
}
