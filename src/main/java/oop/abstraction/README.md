# Abstraction — Hiding Implementation Details

## What is Abstraction?

Abstraction means showing **what** an object does, while hiding **how** it does it.

The caller knows the contract (method names and what they return) but has no idea about the internal implementation.

**Real-world analogy:** You press the accelerator in a car — you know *what* it does (speed up). You don't know *how* the engine, fuel injection, and transmission work together. That complexity is hidden from you.

---

## Two ways to achieve abstraction in Java

| | Abstract Class | Interface |
|---|---|---|
| Keyword | `abstract class` | `interface` |
| Abstraction level | Partial (some methods can have body) | Full (before Java 8, no method bodies) |
| Constructor | Yes | No |
| Fields | Yes (any type) | Only `public static final` constants |
| Inheritance | `extends` (single) | `implements` (multiple) |
| When to use | Related classes sharing common code | Unrelated classes sharing a contract |

This folder focuses on **abstract classes**.

---

## Abstract Class

A class declared with `abstract` — it defines the contract but **cannot be instantiated**.

```java
// Cannot do this:
Shape shape = new Shape("Red");  // COMPILE ERROR

// Must use a concrete subclass:
Shape circle = new Circle("Red", 5.0);
```

### It can have two types of methods:

**Abstract method** — no body, just the signature. Every concrete child class MUST implement it.
```java
public abstract double area();       // no body — child must implement
public abstract double perimeter();  // no body — child must implement
```

**Concrete method** — has a body. Shared by all child classes. Can be overridden but doesn't have to be.
```java
public void displayColor() {
    System.out.println("Color: " + color);  // same for all shapes
}
```

---

## How this folder uses it

```
Shape (abstract)
├── area()        — abstract, no body
├── perimeter()   — abstract, no body
├── displayColor()— concrete, shared by all
└── describe()    — concrete, can be overridden

Circle    extends Shape  →  implements area(), perimeter(), overrides describe()
Rectangle extends Shape  →  implements area(), perimeter(), overrides describe()
Triangle  extends Shape  →  implements area(), perimeter(), overrides describe()
```

Each shape calculates `area()` and `perimeter()` differently. The abstract class forces every shape to provide its own implementation — you cannot forget.

---

## Abstract class can have a constructor

Even though you cannot instantiate an abstract class directly, it can have a constructor. Child classes call it using `super()`.

```java
protected Shape(String color) {
    this.color = color;  // sets up shared state for all shapes
}

public Circle(String color, double radius) {
    super(color);   // calls Shape's constructor
    this.radius = radius;
}
```

---

## Runtime polymorphism with abstraction

This is where it comes together. A `Shape` reference can hold any concrete shape. Java picks the right `area()` at runtime.

```java
Shape circle    = new Circle("Red", 5.0);
Shape rectangle = new Rectangle("Blue", 4.0, 6.0);
Shape triangle  = new Triangle("Green", 3.0, 4.0);

Shape[] shapes = { circle, rectangle, triangle };

for (Shape shape : shapes) {
    System.out.println(shape.area());       // each shape's own calculation
    System.out.println(shape.perimeter());  // each shape's own calculation
    shape.displayColor();                   // shared — runs from Shape
    shape.describe();                       // overridden in each shape
}
```

The loop doesn't know or care whether it's dealing with a Circle, Rectangle, or Triangle. It just calls `area()` and Java figures out the rest at runtime.

---

## Access modifiers matter

A common mistake is forgetting `public` on overriding methods:

```java
// Wrong — package-private (no modifier)
double area() { ... }

// Correct — public, accessible from anywhere
@Override
public double area() { ... }
```

The overriding method cannot be MORE restrictive than the parent. Since the abstract method is `public`, the implementation must also be `public`.

---

## Math.PI vs custom PI constant

Avoid defining your own PI:
```java
protected final double PI = 3.14159;  // less accurate, wastes memory on every instance
```

Use Java's built-in constant:
```java
Math.PI  // 3.141592653589793 — more accurate, no memory overhead
```

---

## Abstract Class vs Concrete Class

| | Abstract Class | Concrete Class |
|---|---|---|
| Can instantiate? | No | Yes |
| Abstract methods? | Yes | No |
| Concrete methods? | Yes | Yes |
| Constructor? | Yes (called via super) | Yes |
| Purpose | Define contract + shared code | Create objects |

---

## `final` and `static` methods in abstract classes

Abstract classes can also have `final` and `static` methods. Same rules apply.

### `final` method — inherited but cannot be overridden

```java
abstract class Shape {
    public final void printType() {
        System.out.println("I am a Shape");
    }
}

class Circle extends Shape {
    @Override
    public void printType() { }   // COMPILE ERROR: cannot override final method

    // But Circle can call it — it IS inherited
    // circle.printType();  →  I am a Shape ✓
}
```

Use `final` in an abstract class when you want to share a method with all subclasses but **guarantee no subclass changes it**.

---

### `static` method — inherited but cannot be overridden (method hiding)

```java
abstract class Shape {
    public static void info() {
        System.out.println("Shape static method");
    }
}

class Circle extends Shape {
    public static void info() {   // hides Shape's info(), does NOT override
        System.out.println("Circle static method");
    }
}

Circle.info();    // Circle static method

Shape s = new Circle("Red", 5.0);
s.info();         // Shape static method ← parent's version, decided at compile time
```

Static methods are bound at **compile time** based on the reference type — they do not participate in runtime polymorphism.

---

### `private` method — NOT inherited at all

```java
abstract class Shape {
    private void internalCalc() {
        System.out.println("internal");
    }
}

class Circle extends Shape {
    public void test() {
        internalCalc();   // COMPILE ERROR: private access in Shape
    }
}
```

---

### Summary table

| Method type | Inherited? | Can override? | Polymorphism? |
|---|---|---|---|
| `public` / `protected` | Yes | Yes | Yes — runtime |
| `abstract` | Yes (must implement) | Yes — required | Yes — runtime |
| `final` | Yes (child can call it) | No — compile error | No |
| `static` | Yes (accessible via child) | No — method hiding | No — compile time |
| `private` | No | No | No |

---

## Common Mistakes

### Trying to instantiate an abstract class
```java
Shape shape = new Shape("Red");   // COMPILE ERROR
```

### Forgetting to implement all abstract methods
```java
class Triangle extends Shape {
    @Override
    public double area() { return 0.5 * base * height; }

    // forgot perimeter() — COMPILE ERROR: Triangle must implement perimeter()
}
```

### Assuming `3 * base` is correct for triangle perimeter
```java
// Wrong — only works for equilateral triangle
return 3 * base;

// Correct for right triangle (base + height + hypotenuse)
return base + height + Math.sqrt(base * base + height * height);
```

---

## Files in this folder

| File | What it shows |
|---|---|
| `Shape.java` | Abstract class — defines the contract |
| `Circle.java` | Concrete class — implements area and perimeter for circle |
| `Rectangle.java` | Concrete class — implements area and perimeter for rectangle |
| `Triangle.java` | Concrete class — implements area and perimeter for right triangle |
| `ShapeExample.java` | Demo — shows polymorphism with all shapes |
