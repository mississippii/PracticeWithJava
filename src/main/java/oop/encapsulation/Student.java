package oop.encapsulation;

public class Student {
    // Private fields - cannot access directly from outside
    private String rollNumber;
    private String name;
    private int age;
    private double cgpa;

    // Constructor
    public Student(String rollNumber, String name, int age) {
        this.rollNumber = rollNumber;
        setName(name);  // Use setter for validation
        setAge(age);
        this.cgpa = 0.0;
    }

    // Getters - Read access

    public String getRollNumber() {
        return rollNumber;  // Read-only (no setter)
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getCgpa() {
        return cgpa;
    }

    // Setters - Write access with validation

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    public void setAge(int age) {
        if (age >= 18 && age <= 100) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age must be between 18 and 100");
        }
    }

    public void setCgpa(double cgpa) {
        if (cgpa >= 0.0 && cgpa <= 10.0) {
            this.cgpa = cgpa;
        } else {
            throw new IllegalArgumentException("CGPA must be between 0 and 10");
        }
    }

    // Business methods
    public boolean isEligibleForPlacement() {
        return cgpa >= 6.0;
    }

    @Override
    public String toString() {
        return "Student{roll='" + rollNumber + "', name='" + name
                + "', age=" + age + ", cgpa=" + cgpa + "}";
    }
}
