package oop.inheritance;

public class InheritanceDemo {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // 1. Runtime Polymorphism — method overriding
        // -------------------------------------------------------
        // Parent reference holds child object.
        // Java picks the correct eat() at RUNTIME based on actual object.

        Animal a1 = new Dog("Husky", "Black");
        Animal a2 = new Cow("Bessie", "Brown");

        a1.eat();   // Dog is eating   — Dog's eat() runs
        a2.eat();   // Cow is eating   — Cow's eat() runs

        System.out.println();

        // One loop, multiple behaviors — no if/else needed
        Animal[] animals = {
            new Dog("Rex",    "White"),
            new Cow("Bessie", "Brown"),
            new Dog("Max",    "Black"),
        };

        for (Animal animal : animals) {
            animal.eat();   // correct eat() picked at runtime
        }

        System.out.println();

        // -------------------------------------------------------
        // 2. final method — inherited but CANNOT be overridden
        // -------------------------------------------------------
        // Dog inherits breathe() from Animal — it can call it
        // But Dog cannot provide its own version (compile error if tried)

        Dog dog = new Dog("Rex", "White");
        dog.breathe();    // Animal is breathing ✓ — inherited, runs Animal's version

        Animal a = new Dog("Husky", "Black");
        a.breathe();      // Animal is breathing ✓ — same, final locks the behavior

        System.out.println();

        // -------------------------------------------------------
        // 3. static method — method HIDING, not overriding
        // -------------------------------------------------------
        // Static methods are bound at COMPILE TIME based on reference type.
        // They do NOT participate in runtime polymorphism.

        Dog.describe();    // I am a Dog      — called directly on Dog class

        Animal ref = new Dog("Rex", "White");
        ref.describe();    // I am an Animal  ← Animal's version! reference type wins
                           // even though the actual object is a Dog

        // Contrast with instance method (runtime polymorphism):
        ref.eat();         // Dog is eating   ← Dog's version — object type wins

        System.out.println();

        // -------------------------------------------------------
        // Summary
        // -------------------------------------------------------
        // eat()    — overriding  → runtime polymorphism  → object type decides
        // breathe() — final      → cannot override       → always Animal's version
        // describe() — static   → method hiding         → reference type decides
    }
}
