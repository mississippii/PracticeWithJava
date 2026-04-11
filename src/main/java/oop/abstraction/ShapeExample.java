package oop.abstraction;

public class ShapeExample {
    public static void main(String[] args) {

        Shape circle    = new Circle("Red", 5.0);
        Shape rectangle = new Rectangle("Blue", 4.0, 6.0);
        Shape triangle  = new Triangle("Green", 3.0, 4.0);

        Shape[] shapes = { circle, rectangle, triangle };

        // -------------------------------------------------------
        // 1. Abstract methods — runtime polymorphism
        // -------------------------------------------------------
        // area() and perimeter() are abstract — each shape has its own version.
        // Java picks the right one at runtime based on the actual object.

        System.out.println("=== Area & Perimeter (runtime polymorphism) ===");
        for (Shape shape : shapes) {
            System.out.println(shape.getClass().getSimpleName()
                    + " → area: " + String.format("%.2f", shape.area())
                    + ", perimeter: "  + String.format("%.2f", shape.perimeter()));
        }

        System.out.println();

        // -------------------------------------------------------
        // 2. Concrete method — inherited as-is
        // -------------------------------------------------------
        // displayColor() has a body in Shape and none of the subclasses override it.
        // All shapes just use Shape's version.

        System.out.println("=== displayColor (inherited concrete method) ===");
        for (Shape shape : shapes) {
            shape.displayColor();
        }

        System.out.println();

        // -------------------------------------------------------
        // 3. final method — inherited but CANNOT be overridden
        // -------------------------------------------------------
        // printType() is final in Shape. Every shape can call it,
        // but no subclass is allowed to provide its own version.

        System.out.println("=== printType (final — cannot be overridden) ===");
        for (Shape shape : shapes) {
            shape.printType();   // always prints "I am a Shape" — locked by final
        }

        System.out.println();

        // -------------------------------------------------------
        // 4. static method — method hiding, NOT overriding
        // -------------------------------------------------------
        // Static methods are resolved at COMPILE TIME from the reference type.
        // They do NOT participate in runtime polymorphism.

        System.out.println("=== category (static — method hiding) ===");
        Shape.category();     // Category: Geometric Shape — Shape's version

        Shape s = new Circle("Red", 5.0);
        s.category();         // Category: Geometric Shape — reference type is Shape,
                              // so Shape's version runs even though object is Circle

        System.out.println();

        // -------------------------------------------------------
        // 5. describe() — concrete method that subclasses CAN override
        // -------------------------------------------------------

        System.out.println("=== describe (overridden concrete method) ===");
        for (Shape shape : shapes) {
            shape.describe();   // each shape's own describe() runs
        }
    }
}
