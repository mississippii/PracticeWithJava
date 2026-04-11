package oop.polymorphism;

// Compile-Time Polymorphism — Method Overloading
// Same method name, different parameters.
// Java picks the right version at COMPILE TIME based on what arguments you pass.

public class Calculator {

    // 1. Different number of parameters
    public int add(int a, int b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    // 2. Different type of parameters
    public double add(double a, double b) {
        return a + b;
    }

    // 3. Different order of parameters
    public String add(String a, String b) {
        return a + " " + b;
    }
}
