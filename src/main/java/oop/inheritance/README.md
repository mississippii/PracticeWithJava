# 03. Inheritance - Code Reusability

## What is Inheritance?
Mechanism where a new class (child/derived/subclass) acquires properties and behaviors from an existing class (parent/base/superclass).

**"IS-A" relationship**

---

## Why Inheritance?

1. **Code Reusability** - Don't repeat code
2. **Extensibility** - Add new features to existing code
3. **Method Overriding** - Change behavior in child class
4. **Polymorphism** - Achieve runtime polymorphism

---

## Syntax

```java
class Parent {
    // parent members
}

class Child extends Parent {
    // child members + inherited members
}
```

---

## Types of Inheritance in Java

### 1. Single Inheritance
One child extends one parent.

```java
class Animal { }
class Dog extends Animal { }
```

### 2. Multi-level Inheritance
Chain of inheritance.

```java
class Animal { }
class Mammal extends Animal { }
class Dog extends Mammal { }
```

### 3. Hierarchical Inheritance
Multiple children extend same parent.

```java
class Animal { }
class Dog extends Animal { }
class Cat extends Animal { }
```

### ❌ Multiple Inheritance (Not Supported)
One class cannot extend multiple classes (to avoid diamond problem).

```java
// Not allowed in Java
class C extends A, B { }  // ERROR
```

*Note: Java supports multiple inheritance through interfaces.*

---

## Key Concepts

### 1. `super` Keyword
Reference to parent class. Used to:
- Call parent constructor
- Access parent methods
- Access parent fields

```java
class Parent {
    int value = 10;

    Parent() {
        System.out.println("Parent constructor");
    }

    void display() {
        System.out.println("Parent method");
    }
}

class Child extends Parent {
    int value = 20;

    Child() {
        super();  // Call parent constructor
        System.out.println("Child constructor");
    }

    void show() {
        System.out.println(value);         // 20 (child)
        System.out.println(super.value);   // 10 (parent)
        super.display();                   // Call parent method
    }
}
```

### 2. Method Overriding
Child class provides specific implementation of parent's method.

```java
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {
    @Override  // Annotation (optional but recommended)
    void sound() {
        System.out.println("Dog barks");
    }
}
```

### 3. Constructor Chaining
Child constructor automatically calls parent's no-arg constructor.

```java
class Parent {
    Parent() {
        System.out.println("Parent constructor");
    }
}

class Child extends Parent {
    Child() {
        // super(); is called automatically
        System.out.println("Child constructor");
    }
}

// Output:
// Parent constructor
// Child constructor
```

---

## Files in This Folder

1. **SingleInheritance.java** - Basic inheritance example
2. **MultiLevelInheritance.java** - Chain of inheritance
3. **HierarchicalInheritance.java** - Multiple children
4. **SuperKeyword.java** - Usage of super
5. **MethodOverriding.java** - Override parent methods
6. **ConstructorChaining.java** - Constructor execution order
7. **Animal.java, Dog.java** *(existing)* - Animal hierarchy
8. **Account.java, SavingsAccount.java** *(existing)* - Banking example
9. **Product.java, DigitalProduct.java** *(existing)* - Product hierarchy

---

## Complete Example

```java
// Parent class
class Vehicle {
    String brand;
    int speed;

    Vehicle(String brand) {
        this.brand = brand;
        System.out.println("Vehicle constructor");
    }

    void start() {
        System.out.println("Vehicle starting...");
    }

    void displayInfo() {
        System.out.println("Brand: " + brand + ", Speed: " + speed);
    }
}

// Child class
class Car extends Vehicle {
    int doors;

    Car(String brand, int doors) {
        super(brand);  // Call parent constructor
        this.doors = doors;
        System.out.println("Car constructor");
    }

    @Override
    void start() {
        super.start();  // Call parent method
        System.out.println("Car engine starting...");
    }

    @Override
    void displayInfo() {
        super.displayInfo();  // Call parent method
        System.out.println("Doors: " + doors);
    }
}

// Usage
Car car = new Car("Toyota", 4);
car.speed = 120;
car.start();
car.displayInfo();

/* Output:
Vehicle constructor
Car constructor
Vehicle starting...
Car engine starting...
Brand: Toyota, Speed: 120
Doors: 4
*/
```

---

## Rules for Overriding

1. Method signature must be same (name + parameters)
2. Return type must be same or covariant
3. Access modifier: Same or less restrictive
   ```java
   // Parent
   protected void method() { }

   // Child - Valid options
   protected void method() { }  // Same
   public void method() { }     // Less restrictive

   // Child - Invalid
   private void method() { }    // More restrictive - ERROR
   ```
4. Cannot override:
   - `static` methods (it's hiding, not overriding)
   - `final` methods
   - `private` methods (not inherited)
5. Exception: Can throw same, subclass, or no exception (for checked)

---

## Inheritance vs Composition

### When to use Inheritance?
- True "IS-A" relationship
- Example: Dog IS-A Animal ✓

### When to use Composition?
- "HAS-A" relationship
- Example: Car HAS-A Engine ✓
- Prefer composition over inheritance for flexibility

---

## Key Points

✓ Use `extends` keyword for inheritance
✓ Java supports single inheritance only (for classes)
✓ Child class inherits all non-private members
✓ Use `super` to access parent members
✓ Use `@Override` annotation for clarity
✓ Constructor chaining happens automatically
✓ Inheritance is for "IS-A" relationship

---

## Real-World Analogy

**Family Tree:**
- Parent has certain traits (fields) and behaviors (methods)
- Child inherits those traits
- Child can have additional traits
- Child can override behaviors (do things differently)

This is inheritance!
