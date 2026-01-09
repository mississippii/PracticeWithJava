package oop._11_advanced;

/**
 * Demonstrates Java Records (Java 14+)
 * - Immutable data carriers
 * - Automatically generates constructor, getters, equals, hashCode, toString
 * - Compact syntax for data classes
 */
public record Human(String name, int age, String city) {
    // Compact constructor for validation
    public Human {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    // Can add custom methods
    public boolean isAdult() {
        return age >= 18;
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("City: " + city);
        System.out.println("Adult: " + (isAdult() ? "Yes" : "No"));
    }

    // Static factory method
    public static Human createUnknown() {
        return new Human("Unknown", 0, "Unknown");
    }

    public static void main(String[] args) {
        System.out.println("=== Record Demo ===\n");

        // Creating records
        Human person1 = new Human("Alice", 25, "New York");
        Human person2 = new Human("Bob", 17, "Los Angeles");

        // Automatically generated toString()
        System.out.println("person1: " + person1);
        System.out.println("person2: " + person2);

        // Automatically generated getters (no get prefix)
        System.out.println("\n--- Using getters ---");
        System.out.println("Name: " + person1.name());
        System.out.println("Age: " + person1.age());
        System.out.println("City: " + person1.city());

        // Custom method
        System.out.println("\n--- Custom method ---");
        person1.displayInfo();

        // Automatically generated equals()
        System.out.println("\n--- Equality check ---");
        Human person3 = new Human("Alice", 25, "New York");
        System.out.println("person1 equals person3? " + person1.equals(person3));  // true
        System.out.println("person1 == person3? " + (person1 == person3));  // false

        // Immutability (cannot modify)
        // person1.name = "Charlie";  // ERROR: Cannot assign, records are immutable

        // Static factory
        System.out.println("\n--- Static factory ---");
        Human unknown = Human.createUnknown();
        System.out.println(unknown);
    }
}
