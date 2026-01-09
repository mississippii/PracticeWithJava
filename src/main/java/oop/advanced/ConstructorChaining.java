package oop._11_advanced;

/**
 * Demonstrates Constructor Chaining using this()
 * - Calling one constructor from another
 * - Reduces code duplication
 * - this() must be first statement
 */
public class ConstructorChaining {
    private String name;
    private String email;
    private String phoneNumber;

    // Constructor 1 - All parameters
    public ConstructorChaining(String name, String email, String phoneNumber) {
        this(name, email);  // Call constructor 2
        this.phoneNumber = phoneNumber;
    }

    // Constructor 2 - Two parameters
    public ConstructorChaining(String name, String email) {
        this(name);  // Call constructor 3
        this.email = email;
    }

    // Constructor 3 - One parameter (base constructor)
    public ConstructorChaining(String name) {
        this.name = name;
    }

    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
    }

    public static void main(String[] args) {
        System.out.println("=== Constructor Chaining Demo ===\n");

        ConstructorChaining c1 = new ConstructorChaining("Alice");
        c1.display();

        System.out.println();

        ConstructorChaining c2 = new ConstructorChaining("Bob", "bob@email.com");
        c2.display();

        System.out.println();

        ConstructorChaining c3 = new ConstructorChaining("Charlie", "charlie@email.com", "123-456-7890");
        c3.display();
    }
}


