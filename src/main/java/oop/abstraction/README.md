# 05. Abstraction - Hiding Implementation

## What is Abstraction?
Hiding implementation details and showing only essential features/functionality.

**Focus on "WHAT" an object does, not "HOW" it does it.**

---

## Why Abstraction?

1. **Simplification** - Hide complexity
2. **Flexibility** - Change implementation without affecting users
3. **Security** - Hide internal logic
4. **Reduce Duplication** - Common interface for different implementations

---

## How to Achieve Abstraction?

1. **Abstract Classes** (0-100% abstraction)
2. **Interfaces** (100% abstraction)

---

## Abstract Class

### Definition
A class declared with `abstract` keyword that cannot be instantiated.

### Features
- Cannot create objects
- Can have abstract methods (no body)
- Can have concrete methods (with body)
- Can have constructors
- Can have static methods
- Can have fields (instance variables)
- Can have any access modifiers

### Syntax

```java
abstract class ClassName {
    // Abstract method (no body)
    abstract void methodName();

    // Concrete method (with body)
    void concreteMethod() {
        // implementation
    }
}
```

---

## Abstract Method

### Definition
Method declared without implementation (no body).

### Rules
1. Must be declared with `abstract` keyword
2. Has no body (ends with semicolon)
3. Must be in abstract class
4. Child class must override (unless child is also abstract)

```java
abstract void display();  // Abstract method
```

---

## Complete Example

```java
// Abstract class
abstract class Animal {
    String name;

    // Constructor (allowed in abstract class)
    Animal(String name) {
        this.name = name;
    }

    // Abstract method - no implementation
    abstract void sound();

    // Abstract method
    abstract void move();

    // Concrete method - with implementation
    void sleep() {
        System.out.println(name + " is sleeping");
    }

    // Concrete method
    void displayInfo() {
        System.out.println("Animal: " + name);
    }
}

// Concrete class - implements all abstract methods
class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println(name + " barks: Woof!");
    }

    @Override
    void move() {
        System.out.println(name + " runs on four legs");
    }
}

class Bird extends Animal {
    Bird(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println(name + " chirps");
    }

    @Override
    void move() {
        System.out.println(name + " flies in the sky");
    }
}

// Usage
// Animal animal = new Animal("Generic");  // ERROR: Cannot instantiate
Animal dog = new Dog("Buddy");
Animal bird = new Bird("Sparrow");

dog.sound();         // Buddy barks: Woof!
dog.move();          // Buddy runs on four legs
dog.sleep();         // Buddy is sleeping (inherited)

bird.sound();        // Sparrow chirps
bird.move();         // Sparrow flies in the sky
```

---

## When to Use Abstract Class?

1. Want to share code among related classes
2. Have common fields and methods
3. Need constructors
4. Want to provide default implementation
5. Have a "IS-A" relationship

**Example:** Animal → Dog, Cat (share common behavior)

---

## Files in This Folder

1. **AbstractClassExample.java** - Basic abstract class
2. **ShapeExample.java** - Shapes with abstract area calculation
3. **VehicleExample.java** - Vehicle hierarchy
4. **AbstractVsInterface.java** - Comparison with interface
5. **PayCalculator.java** *(existing)* - Payment calculation abstraction
6. **HrManager.java** *(existing)* - HR management example

---

## Real-World Example: Shape

```java
abstract class Shape {
    String color;

    Shape(String color) {
        this.color = color;
    }

    // Abstract methods - each shape calculates differently
    abstract double area();
    abstract double perimeter();

    // Concrete method - same for all shapes
    void displayColor() {
        System.out.println("Color: " + color);
    }
}

class Circle extends Shape {
    double radius;

    Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    double perimeter() {
        return 2 * Math.PI * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }

    @Override
    double perimeter() {
        return 2 * (length + width);
    }
}

// Usage
Shape circle = new Circle("Red", 5);
System.out.println("Circle Area: " + circle.area());
circle.displayColor();

Shape rectangle = new Rectangle("Blue", 4, 6);
System.out.println("Rectangle Area: " + rectangle.area());
```

---

## Rules for Abstract Class

✓ Cannot instantiate (no objects)
✓ Can have 0 or more abstract methods
✓ Can have concrete methods
✓ Can have constructors
✓ Can have static methods
✓ Can have final methods
✓ Can have instance variables
✓ Child must implement all abstract methods (or be abstract)
✓ Use `extends` keyword to inherit

---

## Abstract Class vs Concrete Class

| Feature              | Abstract Class        | Concrete Class        |
|----------------------|-----------------------|-----------------------|
| Instantiation        | ❌ Cannot             | ✅ Can                |
| Abstract methods     | ✅ Can have           | ❌ Cannot have        |
| Concrete methods     | ✅ Can have           | ✅ Can have           |
| Constructor          | ✅ Can have           | ✅ Can have           |
| Purpose              | Base class            | Create objects        |

---

## Key Points

✓ Abstraction hides implementation details
✓ Abstract class cannot be instantiated
✓ Abstract method has no body
✓ Child class must override all abstract methods
✓ Can have both abstract and concrete methods
✓ Use abstract class when classes are related and share code
✓ Abstraction = Security + Flexibility

---

## Real-World Analogy

**Car:**
- You know WHAT car does (start, stop, accelerate)
- You don't know HOW engine works internally
- Different cars (Honda, Toyota) implement differently
- Same interface, different implementation

This is abstraction!
