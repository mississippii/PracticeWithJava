package oop._01_basics;

/**
 * Demonstrates:
 * - Using 'this' to differentiate instance vs local variables
 * - Using 'this' to call another constructor
 * - Using 'this' to pass current object
 * - Using 'this' to return current object
 */
public class ThisKeyword {
    public static void main(String[] args) {
        System.out.println("=== Using 'this' keyword ===\n");

        // 1. Differentiate instance vs local variables
        Student s1 = new Student("Alice", 20);
        s1.display();

        // 2. Constructor chaining with 'this()'
        Student s2 = new Student("Bob");
        s2.display();

        Student s3 = new Student();
        s3.display();

        // 3. Method chaining with 'this'
        Student s4 = new Student();
        s4.setName("Charlie").setAge(22).setGrade("A");
        s4.display();
    }
}

class Student {
    // Instance variables
    private String name;
    private int age;
    private String grade;

    // Constructor 1 - All parameters
    Student(String name, int age) {
        // 'this' differentiates instance variable from parameter
        this.name = name;  // this.name = instance variable
        this.age = age;    // name = parameter
        this.grade = "Not assigned";
    }

    // Constructor 2 - One parameter, calls Constructor 1
    Student(String name) {
        this(name, 0);  // Calling another constructor with 'this()'
        System.out.println("Constructor with name only called");
    }

    // Constructor 3 - No parameter, calls Constructor 1
    Student() {
        this("Unknown", 0);  // Constructor chaining
        System.out.println("Default constructor called");
    }

    // Method returning 'this' for method chaining
    Student setName(String name) {
        this.name = name;
        return this;  // Return current object
    }

    Student setAge(int age) {
        this.age = age;
        return this;  // Return current object
    }

    Student setGrade(String grade) {
        this.grade = grade;
        return this;  // Return current object
    }

    void display() {
        System.out.println("Student: " + this.name + ", Age: " + this.age + ", Grade: " + this.grade);
    }

    // Method that passes current object to another method
    void compare(Student other) {
        System.out.println("Comparing " + this.name + " with " + other.name);
        if (this.age > other.age) {
            System.out.println(this.name + " is older");
        } else if (this.age < other.age) {
            System.out.println(other.name + " is older");
        } else {
            System.out.println("Same age");
        }
    }
}
