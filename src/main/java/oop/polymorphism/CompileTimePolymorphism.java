package oop._04_polymorphism;

/**
 * Demonstrates Compile-Time Polymorphism (Method Overloading)
 * - Same method name, different parameters
 * - Compile-time binding
 * - Different number of parameters
 * - Different types of parameters
 * - Different order of parameters
 */
public class CompileTimePolymorphism {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("=== Method Overloading Demo ===\n");

        // Different number of parameters
        System.out.println("--- Different Number of Parameters ---");
        System.out.println("add(5, 10) = " + calc.add(5, 10));
        System.out.println("add(5, 10, 15) = " + calc.add(5, 10, 15));
        System.out.println("add(1, 2, 3, 4) = " + calc.add(1, 2, 3, 4));

        // Different types of parameters
        System.out.println("\n--- Different Types of Parameters ---");
        System.out.println("add(5, 10) [int] = " + calc.add(5, 10));
        System.out.println("add(5.5, 10.3) [double] = " + calc.add(5.5, 10.3));
        System.out.println("add(\"Hello\", \"World\") [String] = " + calc.add("Hello", "World"));

        // Different order of parameters
        System.out.println("\n--- Different Order of Parameters ---");
        Printer printer = new Printer();
        printer.print(5, "stars");     // int, String
        printer.print("stars", 5);     // String, int

        // Constructor overloading
        System.out.println("\n--- Constructor Overloading ---");
        Product p1 = new Product();
        Product p2 = new Product("Laptop");
        Product p3 = new Product("Phone", 699.99);
        Product p4 = new Product("Tablet", 399.99, 50);

        p1.display();
        p2.display();
        p3.display();
        p4.display();
    }
}

class Calculator {
    // Overloaded add methods - different number of parameters

    int add(int a, int b) {
        System.out.println("add(int, int) called");
        return a + b;
    }

    int add(int a, int b, int c) {
        System.out.println("add(int, int, int) called");
        return a + b + c;
    }

    int add(int a, int b, int c, int d) {
        System.out.println("add(int, int, int, int) called");
        return a + b + c + d;
    }

    // Overloaded add methods - different types of parameters

    double add(double a, double b) {
        System.out.println("add(double, double) called");
        return a + b;
    }

    String add(String a, String b) {
        System.out.println("add(String, String) called");
        return a + " " + b;
    }

    // Overloaded multiply methods

    int multiply(int a, int b) {
        return a * b;
    }

    double multiply(double a, double b) {
        return a * b;
    }

    int multiply(int a, int b, int c) {
        return a * b * c;
    }
}

class Printer {
    // Different order of parameters

    void print(int count, String text) {
        System.out.println("Printing " + text + " " + count + " times:");
        for (int i = 0; i < count; i++) {
            System.out.print("* ");
        }
        System.out.println();
    }

    void print(String text, int count) {
        System.out.println("Text: \"" + text + "\" repeated " + count + " times:");
        for (int i = 0; i < count; i++) {
            System.out.print(text + " ");
        }
        System.out.println();
    }
}

class Product {
    private String name;
    private double price;
    private int quantity;

    // Constructor overloading

    Product() {
        this.name = "Unknown";
        this.price = 0.0;
        this.quantity = 0;
    }

    Product(String name) {
        this.name = name;
        this.price = 0.0;
        this.quantity = 0;
    }

    Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    void display() {
        System.out.println("Product: " + name + ", Price: $" + price + ", Quantity: " + quantity);
    }
}
