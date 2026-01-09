package oop._04_polymorphism;

/**
 * Demonstrates Runtime Polymorphism (Method Overriding)
 * - Dynamic method dispatch
 * - Upcasting
 * - Polymorphic behavior
 * - instanceof operator
 */
public class RuntimePolymorphism {
    public static void main(String[] args) {
        System.out.println("=== Runtime Polymorphism Demo ===\n");

        // Polymorphism - Parent reference, Child objects
        Animal animal1 = new Dog("Buddy");      // Upcasting
        Animal animal2 = new Cat("Whiskers");   // Upcasting
        Animal animal3 = new Bird("Tweety");    // Upcasting

        // Same method call, different behavior (polymorphism)
        System.out.println("--- Calling sound() method ---");
        animal1.sound();  // Dog's sound()
        animal2.sound();  // Cat's sound()
        animal3.sound();  // Bird's sound()

        System.out.println("\n--- Calling eat() method ---");
        animal1.eat();    // Dog's eat()
        animal2.eat();    // Cat's eat()
        animal3.eat();    // Bird's eat()

        // Polymorphic array
        System.out.println("\n--- Polymorphic Array ---");
        Animal[] animals = {
            new Dog("Max"),
            new Cat("Luna"),
            new Bird("Rio"),
            new Dog("Charlie")
        };

        for (Animal animal : animals) {
            animal.sound();
            animal.eat();
            System.out.println();
        }

        // instanceof operator
        System.out.println("--- Using instanceof ---");
        checkAnimal(new Dog("Rocky"));
        checkAnimal(new Cat("Mittens"));
        checkAnimal(new Bird("Polly"));

        // Downcasting example
        System.out.println("\n--- Downcasting ---");
        Animal myPet = new Dog("Fido");
        myPet.sound();

        // Downcast to access Dog-specific methods
        if (myPet instanceof Dog) {
            Dog dog = (Dog) myPet;  // Downcasting
            dog.fetch();  // Now can call Dog-specific method
        }
    }

    // Method demonstrating polymorphism
    static void checkAnimal(Animal animal) {
        System.out.println("\nChecking animal type:");
        if (animal instanceof Dog) {
            System.out.println("This is a Dog");
            ((Dog) animal).fetch();  // Downcast and call Dog method
        } else if (animal instanceof Cat) {
            System.out.println("This is a Cat");
            ((Cat) animal).scratch();  // Downcast and call Cat method
        } else if (animal instanceof Bird) {
            System.out.println("This is a Bird");
            ((Bird) animal).fly();  // Downcast and call Bird method
        }
    }
}

// Parent class
class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    // Method to be overridden
    void sound() {
        System.out.println(name + " makes a sound");
    }

    // Method to be overridden
    void eat() {
        System.out.println(name + " is eating");
    }

    void sleep() {
        System.out.println(name + " is sleeping");
    }
}

// Child class 1
class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println(name + " barks: Woof Woof!");
    }

    @Override
    void eat() {
        System.out.println(name + " is eating dog food");
    }

    // Dog-specific method
    void fetch() {
        System.out.println(name + " is fetching the ball");
    }
}

// Child class 2
class Cat extends Animal {
    Cat(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println(name + " meows: Meow Meow!");
    }

    @Override
    void eat() {
        System.out.println(name + " is eating cat food");
    }

    // Cat-specific method
    void scratch() {
        System.out.println(name + " is scratching the post");
    }
}

// Child class 3
class Bird extends Animal {
    Bird(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println(name + " chirps: Tweet Tweet!");
    }

    @Override
    void eat() {
        System.out.println(name + " is eating bird seeds");
    }

    // Bird-specific method
    void fly() {
        System.out.println(name + " is flying");
    }
}
