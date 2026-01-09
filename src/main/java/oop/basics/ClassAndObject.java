package oop._01_basics;

/**
 * Demonstrates:
 * - Class definition
 * - Object creation
 * - Instance variables (fields)
 * - Instance methods
 * - Multiple objects
 */
public class ClassAndObject {
    public static void main(String[] args) {
        // Creating objects
        Person person1 = new Person();
        person1.name = "Alice";
        person1.age = 25;
        person1.city = "New York";

        Person person2 = new Person();
        person2.name = "Bob";
        person2.age = 30;
        person2.city = "Los Angeles";

        // Calling methods
        person1.displayInfo();
        person2.displayInfo();

        person1.greet();
        person2.greet();

        // Each object has its own copy of instance variables
        System.out.println("\n--- Verifying separate instances ---");
        System.out.println("person1 name: " + person1.name);
        System.out.println("person2 name: " + person2.name);
    }
}

/**
 * Person class - Blueprint for creating Person objects
 */
class Person {
    // Instance variables (fields) - each object has its own copy
    String name;
    int age;
    String city;

    // Instance method - behavior
    void displayInfo() {
        System.out.println("\n=== Person Information ===");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("City: " + city);
    }

    // Instance method
    void greet() {
        System.out.println("Hello! My name is " + name);
    }

    // Instance method with logic
    boolean isAdult() {
        return age >= 18;
    }

    // Instance method with parameter
    void celebrateBirthday() {
        age++;
        System.out.println("Happy Birthday " + name + "! Now you are " + age);
    }
}
