# Complete OOP (Object-Oriented Programming) Guide

## 📚 Table of Contents

1. [Basics](#01-basics) - Classes, Objects, Constructors
2. [Encapsulation](#02-encapsulation) - Data Hiding
3. [Inheritance](#03-inheritance) - Code Reusability
4. [Polymorphism](#04-polymorphism) - Many Forms
5. [Abstraction](#05-abstraction) - Abstract Classes
6. [Interface](#06-interface) - Contract
7. [Access Modifiers](#07-access-modifiers) - Visibility Control
8. [Static](#08-static) - Class Level Members
9. [Final](#09-final) - Constants & Restrictions
10. [Relationships](#10-relationships) - Association, Aggregation, Composition
11. [Advanced](#11-advanced) - Constructor Chaining, Enums

---

## 01. Basics

### What is a Class?
A blueprint or template for creating objects. It defines properties (fields) and behaviors (methods).

### What is an Object?
An instance of a class. It represents a real-world entity with state and behavior.

**Folder:** `01_basics/`

**Key Concepts:**
- Class definition
- Object creation
- Constructors (default, parameterized, copy)
- `this` keyword
- Object class methods (toString, equals, hashCode)

**Example:**
```java
class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}
```

---

## 02. Encapsulation

### Definition
Wrapping data (fields) and code (methods) together into a single unit (class) and hiding internal details.

**Folder:** `02_encapsulation/`

**Key Concepts:**
- Private fields
- Public getter/setter methods
- Data validation
- Information hiding

**Benefits:**
- Data protection
- Flexibility to change implementation
- Better maintainability

**Example:**
```java
class BankAccount {
    private double balance;  // Hidden

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

---

## 03. Inheritance

### Definition
Mechanism where a new class (child/derived) inherits properties and methods from an existing class (parent/base).

**Folder:** `03_inheritance/`

**Key Concepts:**
- `extends` keyword
- Single inheritance
- Multi-level inheritance
- Method overriding
- `super` keyword
- Constructor chaining

**Types:**
- **Single:** Class B extends Class A
- **Multi-level:** Class C extends B extends A
- **Hierarchical:** Multiple classes extend same parent

**Example:**
```java
class Animal {
    void eat() {
        System.out.println("Eating...");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Barking...");
    }
}
```

---

## 04. Polymorphism

### Definition
"Many forms" - ability of an object to take many forms. Same method behaves differently.

**Folder:** `04_polymorphism/`

**Types:**

### 1. Compile-time (Static) Polymorphism
- **Method Overloading:** Same method name, different parameters

```java
class Calculator {
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
}
```

### 2. Runtime (Dynamic) Polymorphism
- **Method Overriding:** Child class provides specific implementation

```java
class Animal {
    void sound() { System.out.println("Animal sound"); }
}

class Cat extends Animal {
    @Override
    void sound() { System.out.println("Meow"); }
}

// Usage
Animal animal = new Cat();  // Upcasting
animal.sound();  // Output: Meow (runtime decision)
```

**Key Concepts:**
- Upcasting vs Downcasting
- `instanceof` operator
- Dynamic method dispatch

---

## 05. Abstraction

### Definition
Hiding implementation details and showing only essential features. Focus on "what" not "how".

**Folder:** `05_abstraction/`

**Abstract Class:**
- Cannot be instantiated
- Can have abstract and concrete methods
- Can have constructors, fields, static methods

```java
abstract class Shape {
    String color;

    abstract double area();  // No body

    void display() {  // Concrete method
        System.out.println("Color: " + color);
    }
}

class Circle extends Shape {
    double radius;

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}
```

**Key Rules:**
- Abstract method must be overridden
- 0-100% abstraction possible
- Use `abstract` keyword

---

## 06. Interface

### Definition
A contract that defines "what" a class should do, not "how". 100% abstraction (before Java 8).

**Folder:** `06_interface/`

**Key Concepts:**
- All methods are public abstract (before Java 8)
- All fields are public static final
- `implements` keyword
- Multiple inheritance support
- Default methods (Java 8+)
- Static methods (Java 8+)
- Private methods (Java 9+)

```java
interface Flyable {
    void fly();  // public abstract by default

    default void land() {  // Java 8+
        System.out.println("Landing...");
    }
}

class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Bird flying");
    }
}
```

**Multiple Inheritance:**
```java
interface A { void methodA(); }
interface B { void methodB(); }

class C implements A, B {
    public void methodA() { }
    public void methodB() { }
}
```

---

## 07. Access Modifiers

### Definition
Keywords that set the accessibility/visibility of classes, methods, and fields.

**Folder:** `07_access_modifiers/`

| Modifier    | Class | Package | Subclass | World |
|-------------|-------|---------|----------|-------|
| `public`    | ✓     | ✓       | ✓        | ✓     |
| `protected` | ✓     | ✓       | ✓        | ✗     |
| `default`   | ✓     | ✓       | ✗        | ✗     |
| `private`   | ✓     | ✗       | ✗        | ✗     |

**Examples:**
```java
public class Example {
    public int a;       // Accessible everywhere
    protected int b;    // Same package + subclasses
    int c;              // Same package (default)
    private int d;      // Only within class
}
```

---

## 08. Static

### Definition
Members that belong to the class rather than instances. Shared across all objects.

**Folder:** `08_static/`

**Key Concepts:**
- Static variables (class variables)
- Static methods (class methods)
- Static blocks (initialization)
- Static nested classes

```java
class Counter {
    static int count = 0;  // Shared by all objects

    static void increment() {  // Can't access instance members
        count++;
    }

    static {  // Static block - runs once when class loads
        System.out.println("Class loaded");
    }
}

// Usage
Counter.increment();  // No object needed
System.out.println(Counter.count);
```

**Rules:**
- Static methods can't access instance members directly
- Can't use `this` in static context
- Called using class name

---

## 09. Final

### Definition
Keyword to create constants, prevent inheritance, and prevent method overriding.

**Folder:** `09_final/`

**Uses:**

### 1. Final Variable (Constant)
```java
final int MAX_VALUE = 100;  // Cannot be changed
```

### 2. Final Method (Cannot override)
```java
class Parent {
    final void display() {
        System.out.println("Cannot override");
    }
}
```

### 3. Final Class (Cannot inherit)
```java
final class Utility {
    // Cannot be extended
}
```

**Blank Final:**
```java
class Example {
    final int value;  // Blank final

    Example(int value) {
        this.value = value;  // Must initialize in constructor
    }
}
```

---

## 10. Relationships

### Definition
How classes are related to each other.

**Folder:** `10_relationships/`

### 1. Association
General relationship. "Has-a" or "uses-a".

```java
class Teacher {
    Student student;  // Teacher has Student
}
```

### 2. Aggregation
Weak "has-a" relationship. Parts can exist independently.

```java
class Department {
    List<Professor> professors;  // Department has Professors
    // Professors can exist without Department
}
```

### 3. Composition
Strong "has-a" relationship. Parts cannot exist independently.

```java
class House {
    Room room;  // House has Room
    // Room cannot exist without House

    House() {
        room = new Room();  // Room created with House
    }
}
```

**Comparison:**
- **Association:** Loose coupling (uses)
- **Aggregation:** Has-a (weak ownership)
- **Composition:** Part-of (strong ownership)

---

## 11. Advanced

### Definition
Advanced OOP concepts and features.

**Folder:** `11_advanced/`

### Constructor Chaining
Calling one constructor from another.

```java
class Person {
    String name;
    int age;

    Person() {
        this("Unknown", 0);  // Calls parameterized constructor
    }

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### Enums
Special class representing group of constants.

```java
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

// Usage
Day today = Day.MONDAY;
```

### Inner Classes
Class within a class.

```java
class Outer {
    class Inner {
        void display() {
            System.out.println("Inner class");
        }
    }
}
```

---

## 📊 OOP Concepts Comparison

### Abstract Class vs Interface

| Feature              | Abstract Class          | Interface               |
|---------------------|-------------------------|-------------------------|
| Methods             | Abstract + Concrete     | Abstract (before Java 8)|
| Fields              | Any type               | public static final     |
| Inheritance         | Single                 | Multiple                |
| Constructor         | Yes                    | No                      |
| Access Modifiers    | Any                    | public (methods)        |
| When to use         | IS-A relationship      | CAN-DO capability       |

### Method Overloading vs Overriding

| Feature              | Overloading            | Overriding              |
|---------------------|------------------------|-------------------------|
| Definition          | Same name, diff params | Same signature          |
| Where               | Same class             | Parent-child            |
| Polymorphism        | Compile-time           | Runtime                 |
| Return type         | Can be different       | Must be same/covariant  |
| Access modifier     | Can be different       | Same or less restrictive|

---

## 🎯 Folder Structure

```
oop/
├── OOP_GUIDE.md (This file)
├── 01_basics/          ✓ Classes, Objects, Constructors
├── 02_encapsulation/   ✓ Data hiding
├── 03_inheritance/     ✓ Code reusability
├── 04_polymorphism/    ✓ Method overloading/overriding
├── 05_abstraction/     ✓ Abstract classes
├── 06_interface/       ✓ Interfaces
├── 07_access_modifiers/ ✓ public, private, protected, default
├── 08_static/          ✓ Static members
├── 09_final/           ✓ final keyword
├── 10_relationships/   ✓ Association, Aggregation, Composition
└── 11_advanced/        ✓ Constructor chaining, Enums, Inner classes
```

Each folder contains:
- `README.md` - Concept explanation
- Example Java files with clear, runnable code
- Comments explaining each part

---

## 🚀 Learning Path

1. **Start:** 01_basics (Understand classes and objects)
2. **Core Pillars:** 02-05 (Encapsulation, Inheritance, Polymorphism, Abstraction)
3. **Interfaces:** 06_interface
4. **Modifiers:** 07_access_modifiers, 08_static, 09_final
5. **Relationships:** 10_relationships
6. **Advanced:** 11_advanced

---

## 💡 Best Practices

1. **Encapsulation:** Always use private fields with public getters/setters
2. **Inheritance:** Favor composition over inheritance when possible
3. **Polymorphism:** Use interfaces for flexibility
4. **Abstraction:** Abstract what varies
5. **Naming:** Classes (PascalCase), methods (camelCase)
6. **Single Responsibility:** One class, one purpose

---

## 📝 Quick Reference

**Create Object:**
```java
ClassName obj = new ClassName();
```

**Inheritance:**
```java
class Child extends Parent { }
```

**Interface:**
```java
class MyClass implements MyInterface { }
```

**Abstract Class:**
```java
abstract class MyClass {
    abstract void method();
}
```

**Access Modifiers:**
```java
public class Example {
    public int a;
    protected int b;
    int c;              // default
    private int d;
}
```

---

**Happy Learning! 🎓**
