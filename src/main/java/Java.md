# Java - Complete Reference Guide

## 📚 Table of Contents

### Fundamentals
1. [What is Java?](#what-is-java)
2. [Core Java Components](#core-java-components) - JDK, JRE, JVM, JIT, Class Loader, Garbage Collector
3. [JVM Architecture](#jvm-architecture)
4. [Data Types](#data-types) - Primitives & Wrapper Classes
5. [Java Types (Reference Types)](#java-types-reference-types) - Class, Interface, Enum, Record
6. [Access Modifiers](#access-modifiers)
7. [Keywords in Java](#keywords-in-java) - final, static, this, super, abstract, synchronized, volatile, transient

### Object-Oriented Programming
8. [Constructors](#constructors) - Types, Chaining
9. [Object-Oriented Programming (OOP)](#object-oriented-programming-oop) - Encapsulation, Inheritance, Polymorphism, Abstraction
10. [Inner Classes](#inner-classes) - Member, Static, Local, Anonymous
11. [Immutable Class](#immutable-class)

### Core Concepts
12. [Annotations](#annotations) - Built-in & Custom
13. [Generics & Type System](#generics--type-system) - Wildcards, PECS, Type Erasure
14. [Collections Framework](#collections-framework-detailed) - List, Set, Map, Queue
15. [Exception Handling](#exception-handling-detailed) - try-catch, Custom Exceptions
16. [Concurrency & Multithreading](#concurrency--multithreading) - Threads, Synchronization, Executors

### Java 8+ Features
17. [Lambda Expressions](#lambda-expressions-detailed)
18. [Stream API](#stream-api-detailed) - Intermediate & Terminal Operations
19. [Functional Programming](#functional-programming)

### I/O & Data
20. [String Handling](#string-handling-detailed) - String, StringBuilder, StringBuffer
21. [I/O & Networking](#io--networking) - File I/O, NIO, Sockets, HttpClient
22. [Serialization](#serialization)
23. [JDBC (Database Connectivity)](#jdbc-database-connectivity)

### Advanced Topics
24. [Reflection & Metaprogramming](#reflection--metaprogramming)
25. [Modern Java Features (Java 9-21+)](#modern-java-features-java-9-21) - Records, Virtual Threads, Pattern Matching
26. [JVM Internals](#jvm-internals)

### Software Engineering
27. [Design Patterns](#design-patterns) - Creational, Structural, Behavioral
28. [Testing](#testing) - JUnit 5, Mockito
29. [Security](#security) - Encryption, SQL Injection Prevention
30. [Build & Tooling](#build--tooling) - Maven, Gradle
31. [Frameworks & Libraries](#frameworks--libraries) - Spring, Hibernate

### Interview Preparation
32. [Interview Questions](#interview-questions-with-answers) - 36+ Questions with Detailed Answers

---

## What is Java?

**Definition:** Java is a high-level, object-oriented, platform-independent programming language used to build reliable and scalable applications. It runs on the Java Virtual Machine (JVM), supports strong OOP concepts, multithreading, and has a rich ecosystem of libraries and frameworks.

---

## Core Java Components

### JDK (Java Development Kit)
**Stand for:** Java Development Kit

**What is it?** Used to develop Java applications. It includes the JRE, compiler, debugger, and other development tools.

**Components:**
- JRE (Java Runtime Environment)
- Java compiler (javac)
- Debugger (jdb)
- JavaDoc
- Java archiver (jar)
- Other development tools

---

### JRE (Java Runtime Environment)
**Stand for:** Java Runtime Environment

**What is it?** It is the physical implementation of Java Virtual Machine. It provides libraries, JVM, and other components to run Java applications.

**Components:**
- JVM
- Core libraries (java.lang, java.util, etc.)
- Supporting files

---

### JVM (Java Virtual Machine)
**Stand for:** Java Virtual Machine

**What is it?** It is an abstract machine that allows programmers to execute Java bytecode, manage memory, perform garbage collection, and handle runtime execution.

**Key Features:**
- Platform independence (Write Once, Run Anywhere)
- Memory management
- Garbage collection
- Security
- Bytecode execution

---

### JIT (Just-In-Time Compiler)
**Stand for:** Just-In-Time Compiler

**What is it?** A part of the JVM that improves performance by compiling frequently executed bytecode into native machine code at runtime.

**How it works:**
- Identifies "hot spots" (frequently executed code)
- Compiles bytecode to native machine code
- Caches compiled code for reuse
- Significant performance improvement

---

### Class Loader
**What is it?** A part of JVM that loads `.class` files into memory when your program runs. It is responsible for finding and loading class files so your program can use them.

**Key Points:**
- Classes are not loaded all at once
- Classes load on demand (when needed)
- Lazy loading mechanism

**Types of Class Loaders:**
1. **Bootstrap Class Loader** - Loads core Java classes (java.lang, java.util)
2. **Extension Class Loader** - Loads classes from extension directories
3. **Application Class Loader** - Loads application classes from classpath

---

### Garbage Collector
**What is it?** Automatic memory management system that reclaims memory occupied by objects that are no longer in use.

**Benefits:**
- Automatic memory management
- Prevents memory leaks
- No manual memory deallocation needed

**Types:**
- Serial GC
- Parallel GC
- CMS (Concurrent Mark Sweep)
- G1 GC (Garbage First)
- ZGC (Z Garbage Collector - Java 11+)
- Shenandoah GC

---

## JVM Architecture

```
┌─────────────────────────────────────────────────────┐
│                    JVM                              │
├─────────────────────────────────────────────────────┤
│  Class Loader Subsystem                             │
│  ┌─────────┐  ┌─────────┐  ┌─────────────┐        │
│  │Bootstrap│→ │Extension│→ │Application  │        │
│  │ Loader  │  │ Loader  │  │   Loader    │        │
│  └─────────┘  └─────────┘  └─────────────┘        │
├─────────────────────────────────────────────────────┤
│  Runtime Data Areas                                 │
│  ┌────────┐ ┌────────┐ ┌──────────┐ ┌───────────┐ │
│  │ Method │ │  Heap  │ │  Stack   │ │    PC     │ │
│  │  Area  │ │        │ │(per thrd)│ │ Register  │ │
│  └────────┘ └────────┘ └──────────┘ └───────────┘ │
│                        ┌──────────────────┐        │
│                        │ Native Method    │        │
│                        │     Stack        │        │
│                        └──────────────────┘        │
├─────────────────────────────────────────────────────┤
│  Execution Engine                                   │
│  ┌───────────┐  ┌─────┐  ┌────────────────┐       │
│  │Interpreter│  │ JIT │  │Garbage Collector│      │
│  └───────────┘  └─────┘  └────────────────┘       │
└─────────────────────────────────────────────────────┘
```

---

## Data Types

### Primitive Data Types
Java has 8 primitive data types:

| Type    | Size    | Range                                   | Default |
|---------|---------|------------------------------------------|---------|
| byte    | 8 bit   | -128 to 127                              | 0       |
| short   | 16 bit  | -32,768 to 32,767                        | 0       |
| int     | 32 bit  | -2³¹ to 2³¹-1                            | 0       |
| long    | 64 bit  | -2⁶³ to 2⁶³-1                            | 0L      |
| float   | 32 bit  | ~±3.4e-38 to ±3.4e+38                    | 0.0f    |
| double  | 64 bit  | ~±1.7e-308 to ±1.7e+308                  | 0.0d    |
| char    | 16 bit  | 0 to 65,535 (Unicode)                    | '\u0000'|
| boolean | 1 bit   | true or false                            | false   |

---

### Wrapper Classes
Java has corresponding wrapper classes for each primitive type:

| Primitive | Wrapper Class |
|-----------|---------------|
| byte      | Byte          |
| short     | Short         |
| int       | Integer       |
| long      | Long          |
| float     | Float         |
| double    | Double        |
| char      | Character     |
| boolean   | Boolean       |

**Autoboxing:** Automatic conversion from primitive to wrapper
```java
int x = 10;
Integer obj = x;  // Autoboxing
```

**Unboxing:** Automatic conversion from wrapper to primitive
```java
Integer obj = 100;
int x = obj;  // Unboxing
```

---

## Java Types (Reference Types)

### 1. Class
**What is it?** Blueprint of an object. Defines properties (fields) and behaviors (methods).

```java
class Person {
    String name;
    int age;

    void display() {
        System.out.println(name + " - " + age);
    }
}
```

---

### 2. Interface
**What is it?** Contract/Abstract specification. Defines what a class should do, not how.

```java
interface Flyable {
    void fly();  // Abstract method
}
```

---

### 3. Enum
**What is it?** Fixed set of constants. Special class for representing group of constants.

```java
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
```

---

### 4. Record (Java 14+)
**What is it?** Immutable data carrier. Compact syntax for data classes.

```java
record Person(String name, int age) { }
```

---

## Access Modifiers

Controls visibility and accessibility of classes, methods, and fields.

| Modifier  | Class | Package | Subclass | World |
|-----------|-------|---------|----------|-------|
| public    | ✅    | ✅      | ✅       | ✅    |
| protected | ✅    | ✅      | ✅       | ❌    |
| default   | ✅    | ✅      | ❌       | ❌    |
| private   | ✅    | ❌      | ❌       | ❌    |

### 1. Public
Can be accessed from anywhere within same package, subclass, even from different packages.

### 2. Private
Only can be accessed from inside the class.

### 3. Protected
Can be accessed within the same package and by subclasses (even if they're in different packages).

### 4. Default (Package-Private)
Members with default access are only accessible within the same package.

---

## Keywords in Java

### final Keyword
**Purpose:** Restricts modification of variables, methods, and classes.

```java
// Final variable - cannot be reassigned
final int MAX_VALUE = 100;

// Final method - cannot be overridden
class Parent {
    final void display() {
        System.out.println("Cannot override this");
    }
}

// Final class - cannot be inherited
final class ImmutableClass {
    // No subclasses allowed
}
```

### static Keyword
**Purpose:** Belongs to class rather than instance. Shared across all objects.

```java
class Counter {
    static int count = 0;  // Shared by all instances
    
    static void increment() {  // Called without object
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

### this Keyword
**Purpose:** Refers to current object instance.

```java
class Person {
    String name;
    
    Person(String name) {
        this.name = name;  // Distinguish field from parameter
    }
    
    void display() {
        System.out.println(this.name);  // Current object's name
    }
    
    Person getSelf() {
        return this;  // Return current object
    }
}
```

### super Keyword
**Purpose:** Refers to parent class. Access parent's members and constructors.

```java
class Animal {
    String name = "Animal";
    
    void sound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    String name = "Dog";
    
    void display() {
        System.out.println(super.name);  // Prints "Animal"
        System.out.println(this.name);   // Prints "Dog"
        super.sound();  // Calls parent's method
    }
}
```

### abstract Keyword
**Purpose:** Declares incomplete classes/methods that must be implemented by subclasses.

```java
abstract class Shape {
    abstract double area();  // No body - must be overridden
    
    void display() {  // Can have concrete methods too
        System.out.println("I am a shape");
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

### synchronized Keyword
**Purpose:** Thread safety. Only one thread can access at a time.

```java
class Counter {
    private int count = 0;
    
    synchronized void increment() {  // Thread-safe
        count++;
    }
    
    void process() {
        synchronized(this) {  // Synchronized block
            // Critical section
        }
    }
}
```

### volatile Keyword
**Purpose:** Ensures visibility of changes across threads. No caching.

```java
class SharedData {
    volatile boolean flag = false;  // Always read from main memory
    
    void writer() {
        flag = true;  // Visible to all threads immediately
    }
    
    void reader() {
        while (!flag) {  // Will see updated value
            // Wait
        }
    }
}
```

### transient Keyword
**Purpose:** Exclude field from serialization.

```java
class User implements Serializable {
    String username;
    transient String password;  // Won't be serialized
}
```

### instanceof Keyword
**Purpose:** Check object type at runtime.

```java
Object obj = "Hello";

if (obj instanceof String) {
    String str = (String) obj;
    System.out.println(str.length());
}

// Pattern matching (Java 16+)
if (obj instanceof String str) {
    System.out.println(str.length());
}
```

---

## Constructors

### What is a Constructor?
A special method that initializes objects. Called automatically when object is created.

**Rules:**
- Same name as class
- No return type (not even void)
- Called only once per object creation
- Can be overloaded

### Types of Constructors

**1. Default Constructor (No-arg)**
```java
class Person {
    String name;
    
    // Default constructor
    Person() {
        name = "Unknown";
    }
}

Person p = new Person();  // Calls default constructor
```

**2. Parameterized Constructor**
```java
class Person {
    String name;
    int age;
    
    // Parameterized constructor
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

Person p = new Person("John", 25);
```

**3. Copy Constructor**
```java
class Person {
    String name;
    int age;
    
    // Copy constructor
    Person(Person other) {
        this.name = other.name;
        this.age = other.age;
    }
}

Person p1 = new Person("John", 25);
Person p2 = new Person(p1);  // Copy of p1
```

### Constructor Chaining
Calling one constructor from another using `this()` or `super()`.

```java
class Person {
    String name;
    int age;
    String city;
    
    Person() {
        this("Unknown");  // Calls Person(String)
    }
    
    Person(String name) {
        this(name, 0);  // Calls Person(String, int)
    }
    
    Person(String name, int age) {
        this(name, age, "Unknown");  // Calls Person(String, int, String)
    }
    
    Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }
}
```

### Constructor vs Method

| Constructor | Method |
|-------------|--------|
| Same name as class | Any name |
| No return type | Has return type |
| Called automatically | Called explicitly |
| Used to initialize | Used for operations |
| Cannot be inherited | Can be inherited |

---

## Object-Oriented Programming (OOP)

**Definition:** Object-Oriented Programming (OOP) is a programming approach that organizes code using objects which combine data and behavior. It helps build secure, reusable, and maintainable software through encapsulation, inheritance, polymorphism, and abstraction.

### Four Pillars of OOP

---

### 1. Encapsulation
**Definition:** Wrapping data (fields) and code (methods) together as a single unit. Hiding internal details and providing controlled access.

**How to achieve:**
- Make fields `private`
- Provide `public` getter/setter methods

```java
class BankAccount {
    private double balance;  // Hidden from outside
    
    // Controlled access through methods
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {  // Validation
            balance += amount;
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {  // Validation
            balance -= amount;
        }
    }
}

// Usage
BankAccount account = new BankAccount();
account.deposit(1000);
// account.balance = -500;  // ERROR! Cannot access private field
```

**Benefits:**
- Data protection
- Controlled modification
- Flexibility to change implementation
- Better maintainability

---

### 2. Inheritance
**Definition:** Mechanism where one class acquires properties and behaviors of another class. Promotes code reusability.

**Types of Inheritance:**

```java
// Single Inheritance
class Animal {
    void eat() { System.out.println("Eating..."); }
}

class Dog extends Animal {
    void bark() { System.out.println("Barking..."); }
}

// Multilevel Inheritance
class Animal { }
class Mammal extends Animal { }
class Dog extends Mammal { }

// Hierarchical Inheritance
class Animal { }
class Dog extends Animal { }
class Cat extends Animal { }
```

**Complete Example:**
```java
class Vehicle {
    String brand;
    int speed;
    
    void start() {
        System.out.println("Vehicle starting");
    }
    
    void stop() {
        System.out.println("Vehicle stopping");
    }
}

class Car extends Vehicle {
    int doors;
    
    void openSunroof() {
        System.out.println("Sunroof opened");
    }
    
    @Override
    void start() {
        System.out.println("Car engine starting with key");
    }
}

// Usage
Car myCar = new Car();
myCar.brand = "Toyota";  // Inherited from Vehicle
myCar.doors = 4;         // Own field
myCar.start();           // Overridden method
myCar.stop();            // Inherited method
```

**Note:** Java doesn't support multiple inheritance with classes (to avoid diamond problem). Use interfaces instead.

---

### 3. Polymorphism
**Definition:** Ability to take many forms. Same method behaves differently based on object type.

**Two Types:**

**a) Compile-time Polymorphism (Method Overloading)**
Same method name, different parameters. Decided at compile time.

```java
class Calculator {
    // Same name, different parameters
    int add(int a, int b) {
        return a + b;
    }
    
    int add(int a, int b, int c) {
        return a + b + c;
    }
    
    double add(double a, double b) {
        return a + b;
    }
}

Calculator calc = new Calculator();
calc.add(5, 10);        // Calls first method
calc.add(5, 10, 15);    // Calls second method
calc.add(5.5, 10.5);    // Calls third method
```

**b) Runtime Polymorphism (Method Overriding)**
Subclass provides specific implementation. Decided at runtime.

```java
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat meows");
    }
}

// Runtime polymorphism in action
Animal animal1 = new Dog();  // Parent reference, child object
Animal animal2 = new Cat();

animal1.sound();  // Output: Dog barks
animal2.sound();  // Output: Cat meows
```

**Rules for Overriding:**
- Method name must be same
- Parameters must be same
- Return type must be same (or covariant)
- Access modifier cannot be more restrictive
- Cannot override `final`, `static`, or `private` methods

---

### 4. Abstraction
**Definition:** Hiding implementation details and showing only functionality. Focus on "what" not "how".

**Two ways to achieve:**
1. **Abstract Class** (0-100% abstraction)
2. **Interface** (100% abstraction)

**Using Abstract Class:**
```java
abstract class Payment {
    // Abstract method - no implementation
    abstract void pay(double amount);
    
    // Concrete method - has implementation
    void showBalance() {
        System.out.println("Checking balance...");
    }
}

class CreditCardPayment extends Payment {
    @Override
    void pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card");
    }
}

class PayPalPayment extends Payment {
    @Override
    void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal");
    }
}

// Usage
Payment payment = new CreditCardPayment();
payment.pay(100);  // User doesn't know HOW it's implemented
```

**Using Interface:**
```java
interface Drawable {
    void draw();  // Abstract by default
    
    default void info() {  // Default method (Java 8+)
        System.out.println("Drawing shape");
    }
}

class Circle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing Rectangle");
    }
}
```

**Abstract Class vs Interface:**

| Abstract Class | Interface |
|----------------|-----------|
| `extends` keyword | `implements` keyword |
| Can have constructors | No constructors |
| Can have instance variables | Only constants (public static final) |
| Single inheritance | Multiple inheritance |
| 0-100% abstraction | 100% abstraction (before Java 8) |

---

## Inner Classes

### What are Inner Classes?
Classes defined inside another class. Provides logical grouping and encapsulation.

### Types of Inner Classes

**1. Member Inner Class (Non-static)**
```java
class Outer {
    private int x = 10;
    
    class Inner {
        void display() {
            System.out.println("x = " + x);  // Can access outer's private members
        }
    }
}

// Usage
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
inner.display();
```

**2. Static Nested Class**
```java
class Outer {
    static int x = 10;
    
    static class Nested {
        void display() {
            System.out.println("x = " + x);  // Can only access static members
        }
    }
}

// Usage - no outer instance needed
Outer.Nested nested = new Outer.Nested();
nested.display();
```

**3. Local Inner Class**
Defined inside a method.

```java
class Outer {
    void method() {
        final int localVar = 100;
        
        class LocalInner {
            void display() {
                System.out.println("Local var: " + localVar);
            }
        }
        
        LocalInner inner = new LocalInner();
        inner.display();
    }
}
```

**4. Anonymous Inner Class**
Class without name. Used for one-time use.

```java
interface Greeting {
    void greet();
}

class Main {
    public static void main(String[] args) {
        // Anonymous class implementing interface
        Greeting greeting = new Greeting() {
            @Override
            public void greet() {
                System.out.println("Hello!");
            }
        };
        greeting.greet();
        
        // Can also be written as lambda (Java 8+)
        Greeting greeting2 = () -> System.out.println("Hello!");
    }
}
```

**When to Use Inner Classes:**
- Logically grouping classes used in one place only
- Increasing encapsulation
- More readable and maintainable code
- Implementing callbacks and event handlers

---

## Immutable Class

**Definition:** An immutable class in Java is a class whose object state cannot be changed after creation.

**Requirements:**
- Class must be `final`
- All fields must be `private final`
- No setter methods
- Deep copy for mutable fields
- Initialize in constructor only

**Example:**
```java
public final class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}
```

**Benefits:**
- Thread-safe
- Secure
- Good for caching
- Can be used as HashMap keys

---

## Annotations

### What are Annotations?
Annotations provide metadata about the program. They don't directly affect code execution but can be processed by compiler or runtime.

### Built-in Annotations

**1. @Override**
```java
class Animal {
    void sound() { }
}

class Dog extends Animal {
    @Override  // Compiler checks if method actually overrides parent
    void sound() {
        System.out.println("Bark!");
    }
}
```

**2. @Deprecated**
```java
class OldApi {
    @Deprecated
    void oldMethod() { }  // Compiler warning if used
    
    @Deprecated(since = "2.0", forRemoval = true)
    void legacyMethod() { }  // Will be removed in future
}
```

**3. @SuppressWarnings**
```java
@SuppressWarnings("unchecked")
void method() {
    List list = new ArrayList();  // No warning
}

@SuppressWarnings({"unused", "deprecation"})
void anotherMethod() { }
```

**4. @FunctionalInterface**
```java
@FunctionalInterface  // Ensures only one abstract method
interface Calculator {
    int calculate(int a, int b);
}
```

**5. @SafeVarargs**
```java
@SafeVarargs  // Suppresses heap pollution warning
final void process(List<String>... lists) {
    for (List<String> list : lists) {
        System.out.println(list);
    }
}
```

### Creating Custom Annotations

```java
// Define annotation
@Retention(RetentionPolicy.RUNTIME)  // Available at runtime
@Target(ElementType.METHOD)          // Can only be applied to methods
public @interface Test {
    String value() default "";
    int priority() default 0;
}

// Using annotation
class MyTests {
    @Test(value = "test addition", priority = 1)
    void testAddition() {
        assert 2 + 2 == 4;
    }
    
    @Test("test subtraction")
    void testSubtraction() {
        assert 5 - 3 == 2;
    }
}
```

### Meta-Annotations

```java
@Retention(RetentionPolicy.RUNTIME)  // When annotation is available
// SOURCE - Discarded by compiler
// CLASS - In .class file, not runtime (default)
// RUNTIME - Available via reflection

@Target({ElementType.METHOD, ElementType.TYPE})  // Where can be applied
// TYPE, METHOD, FIELD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, etc.

@Documented  // Include in Javadoc

@Inherited   // Subclasses inherit annotation

@Repeatable(Tests.class)  // Can be applied multiple times
```

### Processing Annotations at Runtime

```java
class TestRunner {
    public static void runTests(Class<?> clazz) throws Exception {
        Object instance = clazz.getDeclaredConstructor().newInstance();
        
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                Test test = method.getAnnotation(Test.class);
                System.out.println("Running: " + test.value());
                method.invoke(instance);
            }
        }
    }
}
```

### Common Framework Annotations

```java
// Spring
@Component, @Service, @Repository, @Controller
@Autowired, @Value, @Bean
@RequestMapping, @GetMapping, @PostMapping

// JPA/Hibernate
@Entity, @Table, @Column, @Id
@OneToMany, @ManyToOne, @ManyToMany

// Lombok
@Data, @Getter, @Setter, @Builder
@NoArgsConstructor, @AllArgsConstructor

// Validation
@NotNull, @Size, @Min, @Max, @Email
```

---

## Concurrency & Multithreading

### 🎯 Why Multithreading? The Restaurant Kitchen Story

**The Problem: Single-Threaded (One Chef)**

Imagine a restaurant with ONE chef doing everything:

```java
// Single-threaded restaurant (SLOW!)
public class Restaurant {
    public void serveCustomers() {
        takeOrder();        // 2 minutes
        cookFood();         // 10 minutes
        serveDish();        // 1 minute
        cleanTable();       // 2 minutes
        // Total: 15 minutes per customer!

        // Next customer waits 15 minutes!
        // 10 customers = 150 minutes (2.5 hours!)
        // Restaurant bankruptcy! 💸
    }
}
```

**What goes wrong?**
```
Customer 1: Order → Cook → Serve → Clean (15 min)
Customer 2:              Wait...wait...wait... (starts at 15 min)
Customer 3:              Wait...wait...wait... (starts at 30 min)
...
Customer 10:             Wait...wait...wait... (starts at 135 min!)

Customers leave! Restaurant fails!
```

**The Solution: Multithreading (Multiple Chefs)**

```java
// Multi-threaded restaurant (FAST!)
public class Restaurant {
    public void serveCustomers() {
        // Chef 1: Takes orders (Thread 1)
        Thread orderThread = new Thread(() -> takeOrder());

        // Chef 2: Cooks food (Thread 2)
        Thread cookThread = new Thread(() -> cookFood());

        // Chef 3: Serves dishes (Thread 3)
        Thread serveThread = new Thread(() -> serveDish());

        // Chef 4: Cleans tables (Thread 4)
        Thread cleanThread = new Thread(() -> cleanTable());

        // All chefs work simultaneously!
        orderThread.start();
        cookThread.start();
        serveThread.start();
        cleanThread.start();

        // Multiple customers served at once!
    }
}
```

**Result:**
```
┌────────────────────────────────────────────────────┐
│             SINGLE-THREADED (1 Chef)                │
├────────────────────────────────────────────────────┤
│                                                     │
│  Customer 1: [Order][Cook    ][Serve][Clean]       │
│  Customer 2:                 [Order][Cook    ]...  │
│  Customer 3:                               [Order]..│
│                                                     │
│  Time for 10 customers: 150 minutes                │
│                                                     │
└────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────┐
│            MULTI-THREADED (4 Chefs)                 │
├────────────────────────────────────────────────────┤
│                                                     │
│  Chef 1: [Order1][Order2][Order3][Order4]...       │
│  Chef 2: [Cook1     ][Cook2     ][Cook3    ]...    │
│  Chef 3: [Serve1][Serve2][Serve3][Serve4]...       │
│  Chef 4: [Clean1][Clean2][Clean3][Clean4]...       │
│                                                     │
│  Time for 10 customers: 30 minutes                 │
│  5x FASTER! ✓                                      │
│                                                     │
└────────────────────────────────────────────────────┘
```

### Real-World Scenarios: When to Use Threads

✅ **Use multithreading for:**

**1. Web Server (Handling Multiple Users)**
```java
// Without threads: Server handles 1 user at a time
User 1: Request → Process (5s) → Response
User 2:           Wait 5s...     Request → Process (5s)
User 3:           Wait 10s...              Request → Process

// With threads: Handle 1000s of users simultaneously
Thread 1: User 1 → Process (5s) → Response ✓
Thread 2: User 2 → Process (5s) → Response ✓
Thread 3: User 3 → Process (5s) → Response ✓
// All at same time!
```

**2. File Downloads (Download Multiple Files)**
```java
// Without threads: Download one file at a time
File 1: Download (60s)
File 2:          Wait 60s... Download (60s)
File 3:                     Wait 120s... Download (60s)
Total: 180 seconds

// With threads: Download all simultaneously
Thread 1: File 1 Download (60s) ✓
Thread 2: File 2 Download (60s) ✓
Thread 3: File 3 Download (60s) ✓
Total: 60 seconds (3x faster!)
```

**3. Video Processing**
```java
// Without threads: Process frames one by one
Frame 1 → Process (100ms)
Frame 2 → Process (100ms)
... (1000 frames)
Total: 100 seconds for 1000 frames

// With threads: Process multiple frames simultaneously
Thread 1-8: Process 8 frames at once
Total: 12.5 seconds (8x faster on 8-core CPU!)
```

### The Problem: Race Condition (Thread Safety)

**The Bank Account Disaster:**

```java
// UNSAFE CODE - Race condition!
class BankAccount {
    private int balance = 1000;

    public void withdraw(int amount) {
        if (balance >= amount) {        // Thread 1 & 2 both check
            Thread.sleep(10);            // Simulate processing
            balance = balance - amount;  // Thread 1 & 2 both withdraw!
        }
    }
}

// Two threads withdraw simultaneously:
Thread 1: withdraw(600)  // Checks balance: 1000 ✓
Thread 2: withdraw(600)  // Checks balance: 1000 ✓ (before Thread 1 updates!)

// Thread 1: balance = 1000 - 600 = 400
// Thread 2: balance = 1000 - 600 = 400
// Final balance: 400 (WRONG! Should be -200 or reject one withdrawal!)

// Bank loses money! 💸
```

**What happens?**
```
Time  │ Thread 1             │ Thread 2             │ Balance
──────┼──────────────────────┼──────────────────────┼─────────
  T1  │ Check: balance ≥ 600 │                      │ 1000
  T2  │ ✓ (1000 ≥ 600)       │                      │ 1000
  T3  │                      │ Check: balance ≥ 600 │ 1000
  T4  │                      │ ✓ (1000 ≥ 600)       │ 1000
  T5  │ balance -= 600       │                      │ 400
  T6  │                      │ balance -= 600       │ 400 ❌
      │                      │                      │
Result: Both withdrawals succeed, but balance is wrong!
        Should either be: -200 (both withdraw) OR
                         400 (one rejected)
```

### The Solution: synchronized

```java
// SAFE CODE - Thread-safe with synchronized
class BankAccount {
    private int balance = 1000;

    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance = balance - amount;  // Only one thread at a time!
        }
    }
}

// Now:
Thread 1: withdraw(600)  → Locks method → Checks & withdraws → Unlocks
Thread 2: withdraw(600)  → Waits for lock → Checks (balance=400) → Rejects ✓

// Balance: 400 (CORRECT!)
```

### Real-World Analogy

**Multithreading = Restaurant with Multiple Chefs**

```
Single Thread = 1 Chef:
┌──────────┐
│  Chef 1  │ → Order → Cook → Serve → Clean → Repeat
└──────────┘   (Does everything alone, SLOW!)

Multiple Threads = Multiple Chefs:
┌──────────┐
│  Chef 1  │ → Takes orders
└──────────┘
┌──────────┐
│  Chef 2  │ → Cooks food
└──────────┘
┌──────────┐
│  Chef 3  │ → Serves dishes
└──────────┘
┌──────────┐
│  Chef 4  │ → Cleans tables
└──────────┘
(Each chef specializes, work in parallel, FAST!)

Synchronized = Only one chef can use the oven at a time
```

### When to Use Multithreading?

✅ **Use threads when:**
- CPU-intensive tasks (video processing, data analysis)
- I/O operations (file read/write, network calls, database queries)
- Background tasks (sending emails, generating reports)
- Real-time applications (games, stock trading)
- Web servers handling multiple users

❌ **Don't use threads when:**
- Simple sequential tasks (reading a single file)
- Shared state with complex synchronization (hard to maintain)
- Short-lived programs (thread overhead not worth it)

---

### Key Concepts:
- **Thread lifecycle** - New, Runnable, Running, Blocked, Waiting, Terminated
- **Synchronization** - Preventing thread interference
- **Locks** - ReentrantLock, ReadWriteLock, StampedLock
- **ExecutorService** - Thread pool management
- **ThreadPoolExecutor** - Custom thread pools
- **Fork/Join framework** - Parallel task decomposition
- **CompletableFuture** - Asynchronous programming
- **Future** - Result of asynchronous computation

### Concurrent Collections:
- **ConcurrentHashMap** - Thread-safe HashMap
- **CopyOnWriteArrayList** - Thread-safe ArrayList
- **BlockingQueue** - Producer-consumer pattern

### Synchronization Tools:
- **Atomic classes** - AtomicInteger, AtomicLong, AtomicReference
- **volatile keyword** - Ensures visibility across threads
- **ReadWriteLock** - Multiple readers, single writer
- **StampedLock** - Optimistic read locks

### Modern Features:
- **Virtual threads (Java 21+)** - Lightweight threads
- **Structured concurrency** - Simplified thread management

---

## JVM Internals

### JVM Architecture Components:

1. **Stack** - Method calls, local variables (per thread)
2. **Heap** - Object storage (shared across threads)
3. **Metaspace** - Class metadata (replaces PermGen in Java 8+)
4. **Method Area** - Class structure, method data
5. **PC Register** - Current instruction pointer

### Class Loading Mechanism:
1. **Loading** - Read .class file
2. **Linking** - Verify, Prepare, Resolve
3. **Initialization** - Execute static initializers

### Garbage Collectors:
- **G1GC** - Default in Java 9+ (low latency, high throughput)
- **ZGC** - Ultra-low latency (< 10ms pauses)
- **Shenandoah** - Low pause times
- **Serial GC** - Single-threaded (small apps)
- **Parallel GC** - Multi-threaded (throughput)

### JIT Compilation:
- **C1 (Client)** - Fast compilation, less optimization
- **C2 (Server)** - Slower compilation, more optimization
- **Tiered Compilation** - Combines C1 and C2

### Memory Model:
- **Happens-before** relationship
- **Visibility guarantees**
- **Atomicity**

### JVM Tuning Flags:
- `-Xms` - Initial heap size
- `-Xmx` - Maximum heap size
- `-XX:+UseG1GC` - Use G1 garbage collector
- `-XX:MaxGCPauseMillis` - GC pause time goal
- `-XX:+PrintGCDetails` - Print GC logs

---

## Generics & Type System

### What are Generics?
Generics enable types (classes and interfaces) to be parameters when defining classes, interfaces, and methods. They provide compile-time type safety.

### Why Use Generics?

**Without Generics (Before Java 5):**
```java
List list = new ArrayList();
list.add("Hello");
list.add(123);  // No compile error, but dangerous!

String str = (String) list.get(1);  // Runtime ClassCastException!
```

**With Generics:**
```java
List<String> list = new ArrayList<>();
list.add("Hello");
// list.add(123);  // Compile error! Type safety

String str = list.get(0);  // No casting needed
```

### Generic Class

```java
// Generic class with type parameter T
class Box<T> {
    private T item;
    
    public void setItem(T item) {
        this.item = item;
    }
    
    public T getItem() {
        return item;
    }
}

// Usage
Box<String> stringBox = new Box<>();
stringBox.setItem("Hello");
String s = stringBox.getItem();

Box<Integer> intBox = new Box<>();
intBox.setItem(100);
Integer i = intBox.getItem();
```

### Generic Method

```java
class Utility {
    // Generic method
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
    
    // Generic method with return type
    public static <T> T getFirst(List<T> list) {
        return list.isEmpty() ? null : list.get(0);
    }
}

// Usage
String[] names = {"Alice", "Bob"};
Utility.printArray(names);

Integer[] numbers = {1, 2, 3};
Utility.printArray(numbers);
```

### Bounded Type Parameters

**Upper Bound (extends):**
```java
// T must be Number or its subclass
class Calculator<T extends Number> {
    private T value;
    
    public double square() {
        return value.doubleValue() * value.doubleValue();
    }
}

Calculator<Integer> intCalc = new Calculator<>();
Calculator<Double> doubleCalc = new Calculator<>();
// Calculator<String> strCalc;  // Compile error!
```

**Multiple Bounds:**
```java
// T must extend Number AND implement Comparable
class SortableNumber<T extends Number & Comparable<T>> {
    private T value;
    
    public int compareTo(T other) {
        return value.compareTo(other);
    }
}
```

### Wildcards

**1. Unbounded Wildcard (`?`):**
```java
// Accepts any type
public void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}

printList(Arrays.asList("a", "b"));
printList(Arrays.asList(1, 2, 3));
```

**2. Upper Bounded Wildcard (`? extends T`):**
```java
// Accepts Number or any subclass (Integer, Double, etc.)
public double sum(List<? extends Number> list) {
    double total = 0;
    for (Number num : list) {
        total += num.doubleValue();
    }
    return total;
}

sum(Arrays.asList(1, 2, 3));         // List<Integer>
sum(Arrays.asList(1.5, 2.5, 3.5));   // List<Double>
```

**3. Lower Bounded Wildcard (`? super T`):**
```java
// Accepts Integer or any superclass (Number, Object)
public void addNumbers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
    list.add(3);
}

List<Integer> intList = new ArrayList<>();
List<Number> numList = new ArrayList<>();
List<Object> objList = new ArrayList<>();

addNumbers(intList);
addNumbers(numList);
addNumbers(objList);
```

### PECS Principle
**Producer Extends, Consumer Super**

```java
// Producer (read from) - use extends
public void copy(List<? extends Number> source, List<? super Number> dest) {
    for (Number num : source) {  // Reading from source (Producer)
        dest.add(num);           // Writing to dest (Consumer)
    }
}
```

- Use `extends` when you only GET values (Producer)
- Use `super` when you only PUT values (Consumer)

### Type Erasure
Generics exist only at compile time. At runtime, type information is removed.

```java
// At compile time
List<String> list1 = new ArrayList<>();
List<Integer> list2 = new ArrayList<>();

// At runtime (after type erasure)
// Both become: List list = new ArrayList();

// This is why you cannot:
// - Create generic arrays: new T[]
// - Use instanceof with generics: obj instanceof List<String>
// - Create instances of type parameters: new T()
```

### Recursive Type Bound

```java
// T must be comparable to itself
class ComparableBox<T extends Comparable<T>> {
    private T value;
    
    public boolean isGreaterThan(T other) {
        return value.compareTo(other) > 0;
    }
}
```

---

## Reflection & Metaprogramming

### What is Reflection?
Reflection allows examining and modifying class behavior at runtime. You can inspect classes, interfaces, fields, and methods without knowing their names at compile time.

### Getting Class Information

```java
// Three ways to get Class object
Class<?> clazz1 = String.class;
Class<?> clazz2 = "Hello".getClass();
Class<?> clazz3 = Class.forName("java.lang.String");

// Get class information
System.out.println("Name: " + clazz1.getName());
System.out.println("Simple Name: " + clazz1.getSimpleName());
System.out.println("Package: " + clazz1.getPackage());
System.out.println("Superclass: " + clazz1.getSuperclass());
System.out.println("Interfaces: " + Arrays.toString(clazz1.getInterfaces()));
```

### Accessing Fields

```java
class Person {
    private String name = "John";
    public int age = 25;
}

// Access fields using reflection
Person person = new Person();
Class<?> clazz = person.getClass();

// Get all fields (including private)
Field[] fields = clazz.getDeclaredFields();
for (Field field : fields) {
    field.setAccessible(true);  // Access private fields
    System.out.println(field.getName() + " = " + field.get(person));
}

// Modify private field
Field nameField = clazz.getDeclaredField("name");
nameField.setAccessible(true);
nameField.set(person, "Jane");
```

### Invoking Methods

```java
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    private void secretMethod() {
        System.out.println("Secret!");
    }
}

Calculator calc = new Calculator();
Class<?> clazz = calc.getClass();

// Invoke public method
Method addMethod = clazz.getMethod("add", int.class, int.class);
int result = (int) addMethod.invoke(calc, 5, 3);  // Returns 8

// Invoke private method
Method secretMethod = clazz.getDeclaredMethod("secretMethod");
secretMethod.setAccessible(true);
secretMethod.invoke(calc);
```

### Creating Objects Dynamically

```java
// Using Class.newInstance() - deprecated in Java 9
Class<?> clazz = Class.forName("java.lang.String");
Object obj = clazz.getDeclaredConstructor().newInstance();

// Using Constructor
Constructor<?> constructor = clazz.getConstructor(String.class);
String str = (String) constructor.newInstance("Hello");
```

### Checking Annotations

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyAnnotation {
    String value();
}

class MyClass {
    @MyAnnotation("test")
    public void myMethod() { }
}

// Check annotations
Method method = MyClass.class.getMethod("myMethod");
if (method.isAnnotationPresent(MyAnnotation.class)) {
    MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
    System.out.println(annotation.value());  // Prints "test"
}
```

### Use Cases for Reflection
- Framework development (Spring, Hibernate)
- Testing frameworks (JUnit)
- Serialization/Deserialization
- Dynamic proxy creation
- IDE features (code completion)

### Caution
- **Performance:** Reflection is slower than direct code
- **Security:** Can bypass access modifiers
- **Maintenance:** Breaks at runtime if class structure changes

---

## Modern Java Features (Java 9-21+)

### Java 9+ Features

**1. Modules (JPMS):**
```java
// module-info.java
module com.myapp {
    requires java.base;
    requires java.sql;
    exports com.myapp.api;
}
```

**2. JShell (Interactive REPL):**
```bash
$ jshell
jshell> int x = 10
x ==> 10
jshell> System.out.println(x * 2)
20
```

**3. Factory Methods for Collections:**
```java
List<String> list = List.of("a", "b", "c");        // Immutable
Set<Integer> set = Set.of(1, 2, 3);                // Immutable
Map<String, Integer> map = Map.of("a", 1, "b", 2); // Immutable
```

**4. Private Methods in Interfaces:**
```java
interface MyInterface {
    default void publicMethod() {
        privateHelper();
    }
    
    private void privateHelper() {
        System.out.println("Helper");
    }
}
```

### Java 10+ Features

**1. Local Variable Type Inference (var):**
```java
var list = new ArrayList<String>();  // Compiler infers type
var stream = list.stream();
var map = new HashMap<String, Integer>();

// Works only for local variables with initializers
// var x;  // Error! Cannot infer type
```

### Java 11+ Features

**1. String Methods:**
```java
"  hello  ".strip();          // "hello" (Unicode-aware trim)
"  hello  ".stripLeading();   // "hello  "
"  hello  ".stripTrailing();  // "  hello"
"".isBlank();                 // true
"hello\nworld".lines();       // Stream of lines
"ab".repeat(3);               // "ababab"
```

**2. HttpClient (Standard):**
```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com"))
    .GET()
    .build();
    
HttpResponse<String> response = client.send(request, 
    HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());
```

### Java 14+ Features

**1. Records (Immutable Data Classes):**
```java
// Before records
class PersonOld {
    private final String name;
    private final int age;
    // Constructor, getters, equals, hashCode, toString...
}

// With records
record Person(String name, int age) { }

// Usage
Person p = new Person("John", 25);
System.out.println(p.name());  // "John"
System.out.println(p.age());   // 25
System.out.println(p);         // Person[name=John, age=25]
```

**2. Text Blocks (Multi-line Strings):**
```java
String json = """
    {
        "name": "John",
        "age": 25
    }
    """;

String sql = """
    SELECT *
    FROM users
    WHERE status = 'active'
    """;
```

**3. Pattern Matching for instanceof:**
```java
// Before
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}

// After (Java 14+)
if (obj instanceof String s) {
    System.out.println(s.length());  // s is already cast
}
```

### Java 17+ Features

**1. Sealed Classes:**
```java
// Restrict which classes can extend
sealed class Shape permits Circle, Rectangle, Triangle { }

final class Circle extends Shape { }
final class Rectangle extends Shape { }
non-sealed class Triangle extends Shape { }  // Open for extension
```

**2. Pattern Matching for switch (Preview):**
```java
Object obj = "Hello";
String result = switch (obj) {
    case Integer i -> "Integer: " + i;
    case String s -> "String: " + s;
    case null -> "Null value";
    default -> "Unknown";
};
```

### Java 21+ Features

**1. Virtual Threads (Project Loom):**
```java
// Traditional threads (expensive)
Thread thread = new Thread(() -> doWork());

// Virtual threads (lightweight)
Thread.startVirtualThread(() -> doWork());

// Using ExecutorService
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10_000; i++) {
        executor.submit(() -> doWork());  // 10,000 virtual threads!
    }
}
```

**2. Sequenced Collections:**
```java
SequencedCollection<String> list = new ArrayList<>();
list.addFirst("a");  // Add at beginning
list.addLast("z");   // Add at end
list.getFirst();     // Get first element
list.getLast();      // Get last element
list.reversed();     // Reversed view
```

**3. Record Patterns:**
```java
record Point(int x, int y) { }

void printPoint(Object obj) {
    if (obj instanceof Point(int x, int y)) {
        System.out.println("x=" + x + ", y=" + y);
    }
}
```

---
- **Pattern matching for instanceof**

### Java 17+:
- **Sealed classes** - Restricted inheritance

### Java 21+:
- **Virtual threads** - Lightweight threads
- **Sequenced collections** - Collections with defined order
- **Pattern matching for switch** - Enhanced switch expressions

---

## Functional Programming

### Key Concepts:
- **Lambda internals** - How lambdas work under the hood
- **Stream API advanced** - Custom collectors, parallel streams
- **Optional best practices** - Avoiding null pointer exceptions
- **Functional interfaces** - Single abstract method interfaces

---

## I/O & Networking

### Traditional I/O (java.io)

**Reading Files:**
```java
// Using BufferedReader
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}

// Using Scanner
try (Scanner scanner = new Scanner(new File("file.txt"))) {
    while (scanner.hasNextLine()) {
        System.out.println(scanner.nextLine());
    }
}
```

**Writing Files:**
```java
// Using BufferedWriter
try (BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt"))) {
    writer.write("Hello World");
    writer.newLine();
    writer.write("Second line");
}

// Using PrintWriter
try (PrintWriter writer = new PrintWriter("file.txt")) {
    writer.println("Hello World");
    writer.printf("Number: %d%n", 42);
}
```

### NIO (java.nio) - New I/O

**Files Class (Java 7+):**
```java
// Read entire file
String content = Files.readString(Path.of("file.txt"));
List<String> lines = Files.readAllLines(Path.of("file.txt"));
byte[] bytes = Files.readAllBytes(Path.of("file.txt"));

// Write to file
Files.writeString(Path.of("file.txt"), "Hello World");
Files.write(Path.of("file.txt"), lines);

// Copy, move, delete
Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
Files.move(source, target);
Files.delete(Path.of("file.txt"));

// Check file properties
Files.exists(path);
Files.isDirectory(path);
Files.size(path);
Files.getLastModifiedTime(path);
```

**Walking Directory Tree:**
```java
// List files in directory
try (Stream<Path> files = Files.list(Path.of("."))) {
    files.forEach(System.out::println);
}

// Walk entire tree
try (Stream<Path> paths = Files.walk(Path.of("."))) {
    paths.filter(Files::isRegularFile)
         .filter(p -> p.toString().endsWith(".java"))
         .forEach(System.out::println);
}

// Find files matching pattern
try (Stream<Path> paths = Files.find(Path.of("."), 10,
        (path, attr) -> path.toString().endsWith(".java"))) {
    paths.forEach(System.out::println);
}
```

**Channels and Buffers:**
```java
// Reading with Channel
try (FileChannel channel = FileChannel.open(Path.of("file.txt"))) {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    while (channel.read(buffer) > 0) {
        buffer.flip();  // Switch to read mode
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
        buffer.clear();  // Clear for next read
    }
}

// Writing with Channel
try (FileChannel channel = FileChannel.open(Path.of("file.txt"),
        StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
    ByteBuffer buffer = ByteBuffer.wrap("Hello".getBytes());
    channel.write(buffer);
}
```

### Networking

**Socket Programming (TCP):**
```java
// Server
try (ServerSocket server = new ServerSocket(8080)) {
    System.out.println("Server started on port 8080");
    Socket client = server.accept();  // Wait for client
    
    BufferedReader in = new BufferedReader(
        new InputStreamReader(client.getInputStream()));
    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
    
    String message = in.readLine();
    System.out.println("Received: " + message);
    out.println("Echo: " + message);
}

// Client
try (Socket socket = new Socket("localhost", 8080)) {
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));
    
    out.println("Hello Server!");
    String response = in.readLine();
    System.out.println("Server: " + response);
}
```

**HttpClient (Java 11+):**
```java
HttpClient client = HttpClient.newHttpClient();

// GET request
HttpRequest getRequest = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com/users"))
    .GET()
    .build();

HttpResponse<String> response = client.send(getRequest,
    HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());

// POST request
HttpRequest postRequest = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com/users"))
    .header("Content-Type", "application/json")
    .POST(HttpRequest.BodyPublishers.ofString(
        "{\"name\":\"John\",\"age\":25}"))
    .build();

// Async request
client.sendAsync(getRequest, HttpResponse.BodyHandlers.ofString())
    .thenApply(HttpResponse::body)
    .thenAccept(System.out::println);
```

---

## Serialization

### What is Serialization?
Converting object state to byte stream (for storage or transmission) and reconstructing it back.

### Basic Serialization

```java
// Class must implement Serializable
class Person implements Serializable {
    private static final long serialVersionUID = 1L;  // Version control
    
    private String name;
    private int age;
    private transient String password;  // Won't be serialized
    
    // Constructor, getters, setters...
}

// Serialization (Object to bytes)
Person person = new Person("John", 25);
try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("person.ser"))) {
    oos.writeObject(person);
}

// Deserialization (Bytes to object)
try (ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream("person.ser"))) {
    Person loaded = (Person) ois.readObject();
    System.out.println(loaded.getName());  // "John"
}
```

### serialVersionUID
Unique identifier for class version. If not specified, JVM generates one based on class structure.

```java
// Always define explicitly!
private static final long serialVersionUID = 1L;

// If class structure changes without updating serialVersionUID:
// InvalidClassException during deserialization
```

### Custom Serialization

```java
class CustomPerson implements Serializable {
    private String name;
    private int age;
    
    // Custom serialization
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();  // Default serialization
        oos.writeObject(encrypt(name));  // Custom logic
    }
    
    // Custom deserialization
    private void readObject(ObjectInputStream ois) 
            throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.name = decrypt((String) ois.readObject());
    }
}
```

### Externalizable Interface
Full control over serialization.

```java
class ExternalizablePerson implements Externalizable {
    private String name;
    private int age;
    
    public ExternalizablePerson() { }  // Required no-arg constructor
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException {
        name = in.readUTF();
        age = in.readInt();
    }
}
```

### Serialization Best Practices
1. Always define `serialVersionUID`
2. Use `transient` for sensitive/non-serializable fields
3. Consider using JSON/XML instead for interoperability
4. Be careful with inheritance (parent must be serializable or have no-arg constructor)

---

## JDBC (Database Connectivity)

### What is JDBC?
Java Database Connectivity - API for connecting Java applications to databases.

### Basic JDBC Steps

```java
// 1. Load driver (optional in modern JDBC)
Class.forName("com.mysql.cj.jdbc.Driver");

// 2. Establish connection
String url = "jdbc:mysql://localhost:3306/mydb";
String user = "root";
String password = "password";

try (Connection conn = DriverManager.getConnection(url, user, password)) {
    // 3. Create statement
    // 4. Execute query
    // 5. Process results
}
```

### Statement vs PreparedStatement

**Statement (Simple queries):**
```java
try (Connection conn = DriverManager.getConnection(url, user, password);
     Statement stmt = conn.createStatement()) {
    
    // SELECT
    ResultSet rs = stmt.executeQuery("SELECT * FROM users");
    while (rs.next()) {
        System.out.println(rs.getInt("id") + ": " + rs.getString("name"));
    }
    
    // INSERT, UPDATE, DELETE
    int rows = stmt.executeUpdate("INSERT INTO users (name) VALUES ('John')");
    System.out.println(rows + " row(s) affected");
}
```

**PreparedStatement (Parameterized - prevents SQL injection):**
```java
String sql = "SELECT * FROM users WHERE name = ? AND age > ?";

try (Connection conn = DriverManager.getConnection(url, user, password);
     PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
    pstmt.setString(1, "John");  // Set first parameter
    pstmt.setInt(2, 18);         // Set second parameter
    
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
        System.out.println(rs.getString("name"));
    }
}

// INSERT with PreparedStatement
String insertSql = "INSERT INTO users (name, age) VALUES (?, ?)";
try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
    pstmt.setString(1, "Jane");
    pstmt.setInt(2, 25);
    pstmt.executeUpdate();
}
```

### Batch Processing

```java
try (Connection conn = DriverManager.getConnection(url, user, password);
     PreparedStatement pstmt = conn.prepareStatement(
         "INSERT INTO users (name, age) VALUES (?, ?)")) {
    
    conn.setAutoCommit(false);  // Disable auto-commit
    
    for (User user : users) {
        pstmt.setString(1, user.getName());
        pstmt.setInt(2, user.getAge());
        pstmt.addBatch();
    }
    
    int[] results = pstmt.executeBatch();
    conn.commit();  // Commit all at once
}
```

### Transaction Management

```java
Connection conn = null;
try {
    conn = DriverManager.getConnection(url, user, password);
    conn.setAutoCommit(false);  // Start transaction
    
    // Multiple operations
    stmt1.executeUpdate("UPDATE accounts SET balance = balance - 100 WHERE id = 1");
    stmt2.executeUpdate("UPDATE accounts SET balance = balance + 100 WHERE id = 2");
    
    conn.commit();  // Success - commit all changes
    
} catch (SQLException e) {
    if (conn != null) {
        conn.rollback();  // Error - rollback all changes
    }
} finally {
    if (conn != null) {
        conn.setAutoCommit(true);
        conn.close();
    }
}
```

### Connection Pooling (HikariCP)

```java
// Using HikariCP (fastest connection pool)
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
config.setUsername("root");
config.setPassword("password");
config.setMaximumPoolSize(10);

HikariDataSource dataSource = new HikariDataSource(config);

// Get connection from pool
try (Connection conn = dataSource.getConnection()) {
    // Use connection
}  // Connection returned to pool, not closed
```

### JDBC Best Practices
1. Always use `PreparedStatement` to prevent SQL injection
2. Use try-with-resources for auto-closing
3. Use connection pooling in production
4. Handle transactions properly
5. Don't store credentials in code (use environment variables)

---

## Reactive Programming

### Frameworks:
- **Project Reactor** - Reactive streams implementation
- **RxJava** - Reactive extensions for JVM
- **Reactive Streams spec** - Standard for async stream processing
- **Backpressure handling** - Flow control

---

## Design Patterns

Design patterns are proven solutions to common software design problems.

### Creational Patterns

**1. Singleton - Single Instance**
Ensures only one instance exists throughout the application.

```java
// Thread-safe Singleton using enum (recommended)
enum DatabaseConnection {
    INSTANCE;
    
    public void connect() {
        System.out.println("Connected to database");
    }
}

// Usage
DatabaseConnection.INSTANCE.connect();

// Double-checked locking Singleton
class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() { }
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**2. Factory - Object Creation**
Creates objects without exposing creation logic.

```java
interface Animal {
    void speak();
}

class Dog implements Animal {
    public void speak() { System.out.println("Woof!"); }
}

class Cat implements Animal {
    public void speak() { System.out.println("Meow!"); }
}

// Factory
class AnimalFactory {
    public static Animal createAnimal(String type) {
        return switch (type.toLowerCase()) {
            case "dog" -> new Dog();
            case "cat" -> new Cat();
            default -> throw new IllegalArgumentException("Unknown animal");
        };
    }
}

// Usage
Animal animal = AnimalFactory.createAnimal("dog");
animal.speak();  // Woof!
```

**3. Builder - Complex Object Construction**
Constructs complex objects step by step.

```java
class Pizza {
    private final String size;
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean mushrooms;
    
    private Pizza(Builder builder) {
        this.size = builder.size;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        this.mushrooms = builder.mushrooms;
    }
    
    public static class Builder {
        private final String size;  // Required
        private boolean cheese = false;
        private boolean pepperoni = false;
        private boolean mushrooms = false;
        
        public Builder(String size) {
            this.size = size;
        }
        
        public Builder cheese() { cheese = true; return this; }
        public Builder pepperoni() { pepperoni = true; return this; }
        public Builder mushrooms() { mushrooms = true; return this; }
        
        public Pizza build() { return new Pizza(this); }
    }
}

// Usage - fluent API
Pizza pizza = new Pizza.Builder("Large")
    .cheese()
    .pepperoni()
    .build();
```

### Structural Patterns

**1. Adapter - Interface Compatibility**
Allows incompatible interfaces to work together.

```java
// Old interface
interface OldPrinter {
    void printOld(String text);
}

// New interface we want to use
interface NewPrinter {
    void print(String text);
}

// Adapter
class PrinterAdapter implements NewPrinter {
    private final OldPrinter oldPrinter;
    
    public PrinterAdapter(OldPrinter oldPrinter) {
        this.oldPrinter = oldPrinter;
    }
    
    @Override
    public void print(String text) {
        oldPrinter.printOld(text);  // Adapt old method to new
    }
}
```

**2. Decorator - Add Behavior Dynamically**
Adds functionality to objects without modifying their structure.

```java
interface Coffee {
    double getCost();
    String getDescription();
}

class SimpleCoffee implements Coffee {
    public double getCost() { return 2.0; }
    public String getDescription() { return "Simple coffee"; }
}

// Decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }
    
    public double getCost() { return coffee.getCost() + 0.5; }
    public String getDescription() { return coffee.getDescription() + ", milk"; }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) { super(coffee); }
    
    public double getCost() { return coffee.getCost() + 0.2; }
    public String getDescription() { return coffee.getDescription() + ", sugar"; }
}

// Usage - stack decorators
Coffee coffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
System.out.println(coffee.getDescription());  // Simple coffee, milk, sugar
System.out.println(coffee.getCost());         // 2.7
```

**3. Proxy - Control Access**
Provides a placeholder for another object.

```java
interface Image {
    void display();
}

class RealImage implements Image {
    private String filename;
    
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();  // Expensive operation
    }
    
    private void loadFromDisk() {
        System.out.println("Loading " + filename);
    }
    
    public void display() {
        System.out.println("Displaying " + filename);
    }
}

// Proxy - lazy loading
class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;
    
    public ProxyImage(String filename) {
        this.filename = filename;
    }
    
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);  // Load only when needed
        }
        realImage.display();
    }
}
```

### Behavioral Patterns

**1. Strategy - Interchangeable Algorithms**
Defines a family of algorithms and makes them interchangeable.

```java
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via PayPal");
    }
}

class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    
    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}

// Usage - change strategy at runtime
ShoppingCart cart = new ShoppingCart();
cart.setPaymentStrategy(new CreditCardPayment());
cart.checkout(100.0);

cart.setPaymentStrategy(new PayPalPayment());
cart.checkout(50.0);
```

**2. Observer - Event Notification**
Defines one-to-many dependency where subjects notify observers of state changes.

```java
interface Observer {
    void update(String message);
}

class NewsAgency {
    private List<Observer> observers = new ArrayList<>();
    private String news;
    
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }
}

class NewsChannel implements Observer {
    private String name;
    
    public NewsChannel(String name) { this.name = name; }
    
    public void update(String news) {
        System.out.println(name + " received: " + news);
    }
}

// Usage
NewsAgency agency = new NewsAgency();
agency.addObserver(new NewsChannel("CNN"));
agency.addObserver(new NewsChannel("BBC"));
agency.setNews("Breaking news!");  // Both channels notified
```

**3. Command - Encapsulate Requests**
Encapsulates a request as an object.

```java
interface Command {
    void execute();
    void undo();
}

class Light {
    public void on() { System.out.println("Light on"); }
    public void off() { System.out.println("Light off"); }
}

class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) { this.light = light; }
    
    public void execute() { light.on(); }
    public void undo() { light.off(); }
}

class RemoteControl {
    private Command command;
    
    public void setCommand(Command command) { this.command = command; }
    public void pressButton() { command.execute(); }
    public void pressUndo() { command.undo(); }
}

// Usage
Light light = new Light();
RemoteControl remote = new RemoteControl();
remote.setCommand(new LightOnCommand(light));
remote.pressButton();  // Light on
remote.pressUndo();    // Light off
```

---

## Security

### Input Validation

```java
// Always validate user input
public void processInput(String input) {
    if (input == null || input.isEmpty()) {
        throw new IllegalArgumentException("Input cannot be empty");
    }
    
    // Sanitize input
    String sanitized = input.replaceAll("[^a-zA-Z0-9]", "");
    
    // Use whitelist approach
    if (!input.matches("^[a-zA-Z0-9_]+$")) {
        throw new IllegalArgumentException("Invalid characters");
    }
}
```

### SQL Injection Prevention

```java
// NEVER do this (vulnerable)
String query = "SELECT * FROM users WHERE name = '" + userInput + "'";

// ALWAYS use PreparedStatement
PreparedStatement pstmt = conn.prepareStatement(
    "SELECT * FROM users WHERE name = ?");
pstmt.setString(1, userInput);  // Automatically escaped
```

### Encryption/Decryption

```java
import javax.crypto.*;
import javax.crypto.spec.*;

// AES Encryption
public class AESUtil {
    private static final String ALGORITHM = "AES";
    
    public static String encrypt(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public static String decrypt(String encryptedData, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decoded));
    }
}
```

### Password Hashing

```java
import java.security.MessageDigest;
import java.security.SecureRandom;

// Never store plain text passwords!
public class PasswordUtil {
    
    // Generate salt
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    
    // Hash password with salt
    public static String hashPassword(String password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}

// Better: Use BCrypt library
// String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
// boolean matches = BCrypt.checkpw(password, hashed);
```

### Secure File Operations

```java
// Validate file paths to prevent directory traversal
public void readFile(String filename) {
    Path basePath = Paths.get("/safe/directory").toAbsolutePath().normalize();
    Path filePath = basePath.resolve(filename).normalize();
    
    // Ensure file is within allowed directory
    if (!filePath.startsWith(basePath)) {
        throw new SecurityException("Invalid file path");
    }
    
    // Read file safely
    Files.readString(filePath);
}
```

### Security Best Practices
1. Never trust user input - validate and sanitize
2. Use parameterized queries for database operations
3. Hash passwords with salt (use BCrypt)
4. Use HTTPS for data transmission
5. Keep dependencies updated
6. Principle of least privilege
7. Don't expose sensitive info in error messages

---

## Testing

### JUnit 5 Basics

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    @DisplayName("Addition of two numbers")
    void testAddition() {
        assertEquals(4, calculator.add(2, 2));
    }
    
    @Test
    void testDivision() {
        assertEquals(2, calculator.divide(10, 5));
    }
    
    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, 
            () -> calculator.divide(10, 0));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testIsPositive(int number) {
        assertTrue(calculator.isPositive(number));
    }
    
    @ParameterizedTest
    @CsvSource({"1, 1, 2", "2, 3, 5", "10, 20, 30"})
    void testAddWithCsv(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }
    
    @Disabled("Not implemented yet")
    @Test
    void testFeatureX() { }
    
    @AfterEach
    void tearDown() {
        calculator = null;
    }
}
```

### Assertions

```java
// Basic assertions
assertEquals(expected, actual);
assertNotEquals(unexpected, actual);
assertTrue(condition);
assertFalse(condition);
assertNull(object);
assertNotNull(object);
assertSame(expected, actual);      // Same reference
assertNotSame(unexpected, actual);

// Array assertions
assertArrayEquals(expectedArray, actualArray);

// Exception assertions
assertThrows(IllegalArgumentException.class, () -> doSomething());

// Timeout assertions
assertTimeout(Duration.ofSeconds(2), () -> longRunningMethod());

// Grouped assertions (all run even if one fails)
assertAll(
    () -> assertEquals("John", person.getName()),
    () -> assertEquals(25, person.getAge()),
    () -> assertNotNull(person.getAddress())
);
```

### Mockito - Mocking Framework

```java
import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;

class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testFindUser() {
        // Arrange - setup mock behavior
        User mockUser = new User("John", 25);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        
        // Act
        User result = userService.findUser(1L);
        
        // Assert
        assertEquals("John", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }
    
    @Test
    void testSaveUser() {
        User user = new User("Jane", 30);
        
        userService.saveUser(user);
        
        verify(userRepository).save(user);  // Verify method was called
    }
    
    @Test
    void testThrowException() {
        when(userRepository.findById(anyLong()))
            .thenThrow(new RuntimeException("Database error"));
        
        assertThrows(RuntimeException.class, 
            () -> userService.findUser(1L));
    }
}
```

### Test Lifecycle

```java
class LifecycleTest {
    
    @BeforeAll
    static void setupAll() {
        // Run once before all tests (static)
    }
    
    @BeforeEach
    void setup() {
        // Run before each test
    }
    
    @Test
    void test1() { }
    
    @Test
    void test2() { }
    
    @AfterEach
    void teardown() {
        // Run after each test
    }
    
    @AfterAll
    static void teardownAll() {
        // Run once after all tests (static)
    }
}
```

### Testing Best Practices
1. **AAA Pattern**: Arrange, Act, Assert
2. **One assertion per test** (when possible)
3. **Test edge cases** and error conditions
4. **Use meaningful test names**
5. **Keep tests independent** - no shared state
6. **Mock external dependencies**
7. **Aim for high coverage** but focus on critical paths

---

## Build & Tooling

### Maven

**pom.xml Structure:**
```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>myapp</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>3.0.0</version>
        </dependency>
        
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.10.1</version>
            </plugin>
        </plugins>
    </build>
</project>
```

**Common Maven Commands:**
```bash
mvn clean                  # Delete target directory
mvn compile               # Compile source code
mvn test                  # Run tests
mvn package               # Create JAR/WAR
mvn install               # Install to local repository
mvn clean install         # Clean and install
mvn dependency:tree       # Show dependency tree
```

### Gradle

**build.gradle Structure:**
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.0.0'
}

group = 'com.example'
version = '1.0.0'

java {
    sourceCompatibility = '17'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.0'
}

test {
    useJUnitPlatform()
}
```

**Common Gradle Commands:**
```bash
./gradlew clean           # Delete build directory
./gradlew build           # Compile, test, package
./gradlew test            # Run tests
./gradlew bootRun         # Run Spring Boot app
./gradlew dependencies    # Show dependency tree
```

---

## Frameworks & Libraries

### Spring:
- **Spring Core & Boot** - DI, AOP, auto-configuration
- **Spring WebFlux** - Reactive web framework

### Persistence:
- **Hibernate/JPA** - ORM framework

### Messaging:
- **Kafka** - Distributed event streaming
- **RabbitMQ** - Message broker

### APIs:
- **gRPC** - High-performance RPC
- **GraphQL** - Query language for APIs

---

## Performance & Optimization

### Key Areas:
- **Profiling** - JFR, async-profiler
- **Memory leak detection** - Finding leaks
- **CPU optimization** - Reducing CPU usage
- **Database connection pooling** - HikariCP
- **Caching strategies** - Redis, Caffeine

---

## Architecture

### Architectural Patterns:
- **Clean architecture** - Separation of concerns
- **Domain-Driven Design (DDD)** - Business logic focus
- **Microservices patterns** - Distributed systems
- **Event-driven architecture** - Event sourcing
- **CQRS** - Command Query Responsibility Segregation
- **Event Sourcing** - Event-based state

---

## Native & Interop

### Key Technologies:
- **JNI (Java Native Interface)** - Call native code
- **Panama (Foreign Function & Memory API)** - Modern native interop
- **GraalVM native image** - Ahead-of-time compilation
- **Polyglot programming** - Multiple languages on JVM

---

## Quick Reference Summary

**Java Fundamentals:** JDK, JRE, JVM, JIT, Class Loader, GC

**Data Types:** 8 primitives, 8 wrapper classes, autoboxing/unboxing

**Reference Types:** Class, Interface, Enum, Record

**OOP:** Encapsulation, Inheritance, Polymorphism, Abstraction

**Concurrency:** Threads, locks, concurrent collections, virtual threads

**Modern Java:** Records, sealed classes, pattern matching, virtual threads

**Performance:** JVM tuning, profiling, caching, optimization

---

This is your complete Java reference guide for coding interviews and development! 🚀

## Exception Handling (Detailed)

### What are Exceptions?

**Definition:** An exception is an event that disrupts the normal flow of program execution. It's an object that wraps an error event.

### Exception Hierarchy

```
Throwable
├── Error (Unchecked) - JVM errors
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── VirtualMachineError
└── Exception
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── ArithmeticException
    │   ├── ClassCastException
    │   └── IllegalArgumentException
    └── Other Exceptions (Checked)
        ├── IOException
        ├── SQLException
        ├── FileNotFoundException
        └── ClassNotFoundException
```

---

### Checked vs Unchecked Exceptions

| Checked Exceptions                | Unchecked Exceptions              |
|-----------------------------------|-----------------------------------|
| Must be handled or declared       | Not required to handle            |
| Checked at compile-time           | Checked at runtime                |
| Extends Exception class           | Extends RuntimeException          |
| Example: IOException, SQLException| Example: NullPointerException     |

**Checked Exception Example:**
```java
// Must handle or declare
public void readFile(String path) throws IOException {
    FileReader fr = new FileReader(path);  // Checked exception
    // ...
}

// Or handle it
public void readFile(String path) {
    try {
        FileReader fr = new FileReader(path);
    } catch (IOException e) {
        System.out.println("File not found");
    }
}
```

**Unchecked Exception Example:**
```java
// No need to handle (but should)
public void divide(int a, int b) {
    int result = a / b;  // Can throw ArithmeticException
}

// Better to handle
public void divide(int a, int b) {
    if (b == 0) {
        throw new IllegalArgumentException("Cannot divide by zero");
    }
    int result = a / b;
}
```

---

### try-catch-finally

**Basic syntax:**
```java
try {
    // Code that might throw exception
    int result = 10 / 0;
} catch (ArithmeticException e) {
    // Handle exception
    System.out.println("Cannot divide by zero");
} finally {
    // Always executes (cleanup code)
    System.out.println("Finally block");
}
```

**Multiple catch blocks:**
```java
try {
    String str = null;
    System.out.println(str.length());
} catch (NullPointerException e) {
    System.out.println("Null pointer");
} catch (Exception e) {
    System.out.println("Other exception");
}
```

**Multi-catch (Java 7+):**
```java
try {
    // code
} catch (IOException | SQLException e) {
    System.out.println("IO or SQL exception");
}
```

---

### try-with-resources (Java 7+)

**Automatically closes resources.**

```java
// Old way
BufferedReader br = null;
try {
    br = new BufferedReader(new FileReader("file.txt"));
    String line = br.readLine();
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (br != null) {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// New way (try-with-resources)
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    String line = br.readLine();
} catch (IOException e) {
    e.printStackTrace();
}
// br.close() called automatically!
```

---

### Custom Exceptions

```java
// Custom checked exception
public class InsufficientFundsException extends Exception {
    private double amount;

    public InsufficientFundsException(double amount) {
        super("Insufficient funds: " + amount);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

// Usage
public void withdraw(double amount) throws InsufficientFundsException {
    if (amount > balance) {
        throw new InsufficientFundsException(amount - balance);
    }
    balance -= amount;
}
```

---

### Exception Best Practices

✅ **DO:**
- Catch specific exceptions
- Log exceptions properly
- Use try-with-resources for resources
- Create meaningful custom exceptions
- Clean up resources in finally block

❌ **DON'T:**
- Catch Exception or Throwable (too broad)
- Swallow exceptions (empty catch blocks)
- Use exceptions for flow control
- Ignore exceptions

---

## Collections Framework (Detailed)

### Collections Hierarchy

```
Collection (Interface)
├── List (Interface) - Ordered, allows duplicates
│   ├── ArrayList - Dynamic array
│   ├── LinkedList - Doubly linked list
│   └── Vector - Synchronized ArrayList
│       └── Stack - LIFO
├── Set (Interface) - No duplicates
│   ├── HashSet - Hash table
│   ├── LinkedHashSet - Hash table + linked list
│   └── TreeSet - Red-Black tree (sorted)
└── Queue (Interface) - FIFO
    ├── PriorityQueue - Heap
    ├── ArrayDeque - Resizable array
    └── LinkedList - Also implements Queue

Map (Interface) - Key-value pairs
├── HashMap - Hash table
├── LinkedHashMap - Hash table + linked list
├── TreeMap - Red-Black tree (sorted)
└── Hashtable - Synchronized HashMap (legacy)
```

---

### ArrayList vs LinkedList

| ArrayList                         | LinkedList                        |
|-----------------------------------|-----------------------------------|
| Dynamic array                     | Doubly linked list                |
| Fast random access O(1)           | Slow random access O(n)           |
| Slow insertion/deletion O(n)      | Fast insertion/deletion O(1)      |
| Less memory overhead              | More memory (node objects)        |
| Good for read-heavy operations    | Good for write-heavy operations   |

**Example:**
```java
// ArrayList
List<String> arrayList = new ArrayList<>();
arrayList.add("Apple");
arrayList.add("Banana");
arrayList.add("Cherry");
arrayList.get(1);  // Fast: O(1)
arrayList.remove(1);  // Slow: O(n) - shifts elements

// LinkedList
List<String> linkedList = new LinkedList<>();
linkedList.add("Apple");
linkedList.add("Banana");
linkedList.add("Cherry");
linkedList.get(1);  // Slow: O(n) - traverse nodes
linkedList.remove(1);  // Fast: O(1) - just update links
```

---

### HashSet vs TreeSet vs LinkedHashSet

| HashSet                  | TreeSet                   | LinkedHashSet            |
|--------------------------|---------------------------|--------------------------|
| No order                 | Sorted order              | Insertion order          |
| O(1) add, remove, contains| O(log n) add, remove     | O(1) add, remove         |
| Allows one null          | No null allowed           | Allows one null          |
| Fastest                  | Slowest                   | Middle                   |

**Example:**
```java
// HashSet - No order
Set<String> hashSet = new HashSet<>();
hashSet.add("Banana");
hashSet.add("Apple");
hashSet.add("Cherry");
System.out.println(hashSet);  // [Banana, Apple, Cherry] - random order

// TreeSet - Sorted order
Set<String> treeSet = new TreeSet<>();
treeSet.add("Banana");
treeSet.add("Apple");
treeSet.add("Cherry");
System.out.println(treeSet);  // [Apple, Banana, Cherry] - sorted

// LinkedHashSet - Insertion order
Set<String> linkedHashSet = new LinkedHashSet<>();
linkedHashSet.add("Banana");
linkedHashSet.add("Apple");
linkedHashSet.add("Cherry");
System.out.println(linkedHashSet);  // [Banana, Apple, Cherry] - insertion order
```

---

### HashMap Internals

**How HashMap works:**
1. **Hash function** - Converts key to hash code
2. **Bucket** - Array of linked lists (or trees in Java 8+)
3. **Collision handling** - Multiple keys can have same hash

```java
Map<String, Integer> map = new HashMap<>();
map.put("Alice", 25);
map.put("Bob", 30);
map.put("Charlie", 35);

// Get value
int age = map.get("Alice");  // 25

// Check if key exists
if (map.containsKey("Bob")) {
    System.out.println("Bob exists");
}

// Iterate
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// Java 8+ forEach
map.forEach((key, value) -> System.out.println(key + ": " + value));
```

**HashMap Parameters:**
- **Initial Capacity** - Default 16
- **Load Factor** - Default 0.75 (resize when 75% full)

---

### Comparable vs Comparator

**Comparable:**
- Natural ordering
- Implements in the class itself
- `compareTo()` method
- Single sorting sequence

```java
public class Student implements Comparable<Student> {
    String name;
    int age;

    @Override
    public int compareTo(Student other) {
        return this.age - other.age;  // Sort by age
    }
}

List<Student> students = new ArrayList<>();
Collections.sort(students);  // Uses compareTo()
```

**Comparator:**
- Custom ordering
- Separate comparator class
- `compare()` method
- Multiple sorting sequences

```java
// Sort by name
Comparator<Student> byName = (s1, s2) -> s1.name.compareTo(s2.name);

// Sort by age
Comparator<Student> byAge = (s1, s2) -> s1.age - s2.age;

List<Student> students = new ArrayList<>();
Collections.sort(students, byName);  // Sort by name
Collections.sort(students, byAge);   // Sort by age
```

---

## Lambda Expressions (Detailed)

### What are Lambda Expressions?

**Definition:** Anonymous functions (functions without name) that can be passed as arguments.

**Syntax:**
```
(parameters) -> expression
(parameters) -> { statements; }
```

### Functional Interface

**Definition:** Interface with exactly ONE abstract method.

```java
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);
}
```

---

### Lambda Examples

**Example 1: No parameters**
```java
// Old way
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello");
    }
};

// Lambda
Runnable r2 = () -> System.out.println("Hello");
```

**Example 2: One parameter**
```java
// Old way
Consumer<String> c1 = new Consumer<String>() {
    @Override
    public void accept(String s) {
        System.out.println(s);
    }
};

// Lambda
Consumer<String> c2 = s -> System.out.println(s);
// or
Consumer<String> c3 = System.out::println;  // Method reference
```

**Example 3: Two parameters**
```java
// Old way
Comparator<String> comp1 = new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
};

// Lambda
Comparator<String> comp2 = (s1, s2) -> s1.compareTo(s2);
// or
Comparator<String> comp3 = String::compareTo;  // Method reference
```

**Example 4: Multiple statements**
```java
Calculator calc = (a, b) -> {
    int sum = a + b;
    int product = a * b;
    return sum + product;
};
```

---

### Built-in Functional Interfaces

**1. Predicate\<T\> - Test condition**
```java
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4));  // true
System.out.println(isEven.test(5));  // false
```

**2. Consumer\<T\> - Accept input, no return**
```java
Consumer<String> print = s -> System.out.println(s);
print.accept("Hello");  // Hello
```

**3. Supplier\<T\> - No input, return value**
```java
Supplier<Double> random = () -> Math.random();
System.out.println(random.get());  // 0.123456
```

**4. Function\<T, R\> - Accept input, return value**
```java
Function<String, Integer> length = s -> s.length();
System.out.println(length.apply("Hello"));  // 5
```

**5. BiFunction\<T, U, R\> - Two inputs, one output**
```java
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
System.out.println(add.apply(5, 3));  // 8
```

---

## Stream API (Detailed)

### 🎯 Why Stream API? The Data Processing Evolution Story

**The Old Way: Loops Everywhere (Java 7)**

Imagine you're building an e-commerce report. Find products over $50, get their names in uppercase, sort them, and get top 5:

```java
// OLD WAY - Imperative (Java 7) - VERBOSE!
List<Product> products = getProducts();

// Step 1: Filter products > $50
List<Product> expensiveProducts = new ArrayList<>();
for (Product product : products) {
    if (product.getPrice() > 50) {
        expensiveProducts.add(product);
    }
}

// Step 2: Extract names
List<String> names = new ArrayList<>();
for (Product product : expensiveProducts) {
    names.add(product.getName());
}

// Step 3: Convert to uppercase
List<String> upperNames = new ArrayList<>();
for (String name : names) {
    upperNames.add(name.toUpperCase());
}

// Step 4: Sort
Collections.sort(upperNames);

// Step 5: Get top 5
List<String> top5 = new ArrayList<>();
for (int i = 0; i < Math.min(5, upperNames.size()); i++) {
    top5.add(upperNames.get(i));
}

// Result: 5 separate loops, 5 temporary lists!
// Hard to read, lots of boilerplate, error-prone
```

**Problems with Old Way:**
1. **Too verbose** - 30+ lines for simple task
2. **Multiple loops** - Iterate same data multiple times
3. **Temporary variables** - Many intermediate lists
4. **Hard to read** - Imperative (HOW instead of WHAT)
5. **Not parallel** - Can't easily parallelize
6. **Mutation** - Modifying collections everywhere

**The New Way: Stream API (Java 8+)**

```java
// NEW WAY - Declarative (Java 8+) - CONCISE!
List<String> top5 = products.stream()
    .filter(p -> p.getPrice() > 50)    // Filter expensive
    .map(Product::getName)             // Get names
    .map(String::toUpperCase)          // Uppercase
    .sorted()                          // Sort
    .limit(5)                          // Top 5
    .collect(Collectors.toList());     // Collect result

// Result: 1 line (pipeline), no temporary variables!
// Easy to read, declarative (WHAT not HOW), can parallelize!
```

**Comparison:**

```
┌─────────────────────────────────────────────────────────┐
│                  OLD WAY (Loops)                         │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Products [1000 items]                                   │
│      │                                                   │
│      ├─→ Loop 1: Filter → Temp List 1 [300 items]      │
│      ├─→ Loop 2: Extract names → Temp List 2 [300]     │
│      ├─→ Loop 3: Uppercase → Temp List 3 [300]         │
│      ├─→ Loop 4: Sort → Temp List 4 [300]              │
│      └─→ Loop 5: Limit → Final List [5]                │
│                                                          │
│  5 loops, 4 temporary lists, lots of memory!            │
│                                                          │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│              NEW WAY (Stream API)                        │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Products [1000 items]                                   │
│      │                                                   │
│      └─→ Stream Pipeline                                │
│          (filter → map → map → sorted → limit)          │
│          │                                               │
│          └─→ Process each item ONCE                     │
│              No temp lists!                              │
│              Lazy evaluation (efficient!)               │
│              Final List [5]                              │
│                                                          │
│  1 pass, no temp lists, memory efficient! ✓             │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Real-World Example: Employee Data Processing

**Task:** Find employees in IT department, earning > $80k, get their names, sort alphabetically

```java
// OLD WAY (Java 7)
List<Employee> employees = getEmployees();
List<Employee> itEmployees = new ArrayList<>();
for (Employee emp : employees) {
    if (emp.getDepartment().equals("IT")) {
        itEmployees.add(emp);
    }
}

List<Employee> highEarners = new ArrayList<>();
for (Employee emp : itEmployees) {
    if (emp.getSalary() > 80000) {
        highEarners.add(emp);
    }
}

List<String> names = new ArrayList<>();
for (Employee emp : highEarners) {
    names.add(emp.getName());
}

Collections.sort(names);
System.out.println(names);

// NEW WAY (Java 8+)
List<String> names = employees.stream()
    .filter(e -> e.getDepartment().equals("IT"))
    .filter(e -> e.getSalary() > 80000)
    .map(Employee::getName)
    .sorted()
    .collect(Collectors.toList());

// Same result, 1 readable pipeline! ✓
```

### Lazy Evaluation = Smart & Efficient

**Problem:** Process 1 million numbers, find first 5 even numbers

```java
// Stream API is LAZY (efficient!)
List<Integer> result = IntStream.range(1, 1_000_000)
    .filter(n -> n % 2 == 0)  // Not executed yet!
    .limit(5)                 // Not executed yet!
    .boxed()
    .collect(Collectors.toList());

// What actually happens:
// 1. Checks number 1 → odd → skip
// 2. Checks number 2 → even → collect (1/5)
// 3. Checks number 3 → odd → skip
// 4. Checks number 4 → even → collect (2/5)
// ... continues until 5 even numbers found
// STOPS at number 10! (doesn't check remaining 999,990 numbers!)

// Result: [2, 4, 6, 8, 10]
// Only checked 10 numbers instead of 1 million! ✓
```

### Parallel Processing Made Easy

**Old Way:** Multi-threading is complex

```java
// OLD WAY: Manual thread management (complex!)
ExecutorService executor = Executors.newFixedThreadPool(10);
List<Future<Double>> futures = new ArrayList<>();

for (Order order : orders) {
    Future<Double> future = executor.submit(() -> calculateTotal(order));
    futures.add(future);
}

double total = 0;
for (Future<Double> future : futures) {
    total += future.get();  // Wait for all threads
}
executor.shutdown();
// 20+ lines, complex, error-prone!
```

**New Way:** Parallel streams (simple!)

```java
// NEW WAY: Parallel stream (1 line!)
double total = orders.parallelStream()
    .mapToDouble(this::calculateTotal)
    .sum();

// Automatically uses all CPU cores!
// Simple, clean, efficient! ✓
```

### When to Use Stream API?

✅ **Use Stream API when:**
- Processing collections (filter, map, reduce)
- Need to chain operations
- Want readable, declarative code
- Need parallel processing easily
- Working with large datasets

❌ **Don't use Stream API when:**
- Simple iteration (traditional for loop is fine)
- Need to modify collection during iteration
- Breaking/continuing loops mid-way (use traditional loop)
- Performance critical tight loops (streams have overhead)

### Real-World Analogy

**Stream API = Assembly Line vs Manual Workshop**

```
Manual Workshop (OLD WAY - Loops):
┌─────────────────────────────────────────┐
│ Worker 1: Filter all products           │
│ → Creates pile 1                        │
└─────────────────────────────────────────┘
         ↓ (wait for pile 1 complete)
┌─────────────────────────────────────────┐
│ Worker 2: Process pile 1                │
│ → Creates pile 2                        │
└─────────────────────────────────────────┘
         ↓ (wait for pile 2 complete)
┌─────────────────────────────────────────┐
│ Worker 3: Sort pile 2                   │
│ → Creates final pile                    │
└─────────────────────────────────────────┘

Multiple steps, each waits for previous, storage needed!

Assembly Line (NEW WAY - Stream):
┌────────────────────────────────────────────┐
│ Product → [Filter] → [Process] → [Sort]   │
│         → [Filter] → [Process] → [Sort]   │
│         → [Filter] → [Process] → [Sort]   │
│ Each item flows through pipeline           │
│ No waiting, no intermediate storage!       │
└────────────────────────────────────────────┘

Efficient, continuous flow! ✓
```

---

### What is Stream API?

**Definition:** Sequence of elements that supports sequential and parallel aggregate operations.

**Benefits:**
- Declarative code (what, not how)
- Supports parallel processing
- Lazy evaluation
- No storage (operates on source data)

---

### Creating Streams

```java
// From collection
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream1 = list.stream();

// From array
String[] array = {"a", "b", "c"};
Stream<String> stream2 = Arrays.stream(array);

// Using Stream.of()
Stream<String> stream3 = Stream.of("a", "b", "c");

// Infinite stream
Stream<Integer> stream4 = Stream.iterate(0, n -> n + 1);  // 0, 1, 2, 3...
Stream<Double> stream5 = Stream.generate(Math::random);
```

---

### Intermediate Operations (Lazy)

**1. filter() - Filter elements**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
numbers.stream()
       .filter(n -> n % 2 == 0)  // Even numbers
       .forEach(System.out::println);  // 2, 4, 6, 8, 10
```

**2. map() - Transform elements**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.stream()
     .map(String::toUpperCase)  // Convert to uppercase
     .forEach(System.out::println);  // ALICE, BOB, CHARLIE
```

**3. flatMap() - Flatten nested structures**
```java
List<List<String>> nested = Arrays.asList(
    Arrays.asList("a", "b"),
    Arrays.asList("c", "d")
);
nested.stream()
      .flatMap(List::stream)  // Flatten
      .forEach(System.out::println);  // a, b, c, d
```

**4. distinct() - Remove duplicates**
```java
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 4);
numbers.stream()
       .distinct()
       .forEach(System.out::println);  // 1, 2, 3, 4
```

**5. sorted() - Sort elements**
```java
List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9);
numbers.stream()
       .sorted()
       .forEach(System.out::println);  // 1, 3, 5, 8, 9

// Custom sorting
numbers.stream()
       .sorted(Comparator.reverseOrder())
       .forEach(System.out::println);  // 9, 8, 5, 3, 1
```

**6. limit() - Limit elements**
```java
Stream.iterate(0, n -> n + 1)
      .limit(5)
      .forEach(System.out::println);  // 0, 1, 2, 3, 4
```

**7. skip() - Skip elements**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
numbers.stream()
       .skip(2)
       .forEach(System.out::println);  // 3, 4, 5
```

---

### Terminal Operations (Eager)

**1. forEach() - Iterate**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.stream().forEach(System.out::println);
```

**2. collect() - Collect to collection**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// To List
List<String> list = names.stream()
                         .filter(n -> n.startsWith("A"))
                         .collect(Collectors.toList());

// To Set
Set<String> set = names.stream()
                       .collect(Collectors.toSet());

// To Map
Map<String, Integer> map = names.stream()
                                .collect(Collectors.toMap(
                                    name -> name,
                                    String::length
                                ));
```

**3. reduce() - Reduce to single value**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Sum
int sum = numbers.stream()
                 .reduce(0, (a, b) -> a + b);  // 15

// or using Integer::sum
int sum2 = numbers.stream()
                  .reduce(0, Integer::sum);  // 15

// Product
int product = numbers.stream()
                     .reduce(1, (a, b) -> a * b);  // 120
```

**4. count() - Count elements**
```java
long count = numbers.stream()
                    .filter(n -> n > 3)
                    .count();  // 2
```

**5. anyMatch(), allMatch(), noneMatch()**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

boolean anyEven = numbers.stream().anyMatch(n -> n % 2 == 0);  // true
boolean allEven = numbers.stream().allMatch(n -> n % 2 == 0);  // false
boolean noneNegative = numbers.stream().noneMatch(n -> n < 0); // true
```

**6. findFirst(), findAny()**
```java
Optional<Integer> first = numbers.stream()
                                 .filter(n -> n > 3)
                                 .findFirst();  // Optional[4]

Optional<Integer> any = numbers.stream()
                               .filter(n -> n > 3)
                               .findAny();  // Optional[4] or Optional[5]
```

---

### Collectors (Advanced)

**groupingBy() - Group elements**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Anna", "Ben");

Map<Integer, List<String>> byLength = names.stream()
    .collect(Collectors.groupingBy(String::length));
// {3=[Bob, Ben], 5=[Alice, Anna], 7=[Charlie]}
```

**partitioningBy() - Partition elements**
```java
Map<Boolean, List<Integer>> partitioned = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
// {false=[1, 3, 5], true=[2, 4]}
```

**joining() - Join strings**
```java
String result = names.stream()
    .collect(Collectors.joining(", "));  // Alice, Bob, Charlie
```

---

### Parallel Streams

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Sequential
long sum1 = numbers.stream()
                   .mapToInt(Integer::intValue)
                   .sum();

// Parallel (uses multiple threads)
long sum2 = numbers.parallelStream()
                   .mapToInt(Integer::intValue)
                   .sum();
```

**When to use parallel streams:**
- Large datasets
- CPU-intensive operations
- Independent operations
- No shared state

---

## String Handling (Detailed)

### String vs StringBuilder vs StringBuffer

| Feature           | String        | StringBuilder | StringBuffer  |
|-------------------|---------------|---------------|---------------|
| Mutability        | Immutable     | Mutable       | Mutable       |
| Thread-safe       | Yes           | No            | Yes           |
| Performance       | Slow          | Fast          | Medium        |
| Memory            | More          | Less          | Less          |
| Usage             | Few operations| Many operations| Concurrent    |

---

### String Immutability

**Why immutable?**
- Thread-safe
- Security (passwords, network connections)
- String pool optimization
- HashMap keys

**Example:**
```java
String str = "Hello";
str.concat(" World");  // Creates new string, doesn't modify original
System.out.println(str);  // Hello (unchanged)

str = str.concat(" World");  // Now str points to new string
System.out.println(str);  // Hello World
```

---

### String Pool

**Definition:** JVM maintains a pool of unique string literals to save memory.

```java
String s1 = "Hello";  // Created in string pool
String s2 = "Hello";  // Points to same object in pool
String s3 = new String("Hello");  // Created in heap

System.out.println(s1 == s2);  // true (same reference)
System.out.println(s1 == s3);  // false (different reference)
System.out.println(s1.equals(s3));  // true (same content)

// intern() - Add to string pool
String s4 = s3.intern();
System.out.println(s1 == s4);  // true (now same reference)
```

---

### StringBuilder Example

```java
// String - Creates multiple objects
String str = "Hello";
for (int i = 0; i < 1000; i++) {
    str = str + i;  // Creates new string each time - SLOW!
}

// StringBuilder - Modifies same object
StringBuilder sb = new StringBuilder("Hello");
for (int i = 0; i < 1000; i++) {
    sb.append(i);  // Modifies same object - FAST!
}
String result = sb.toString();
```

**StringBuilder Methods:**
```java
StringBuilder sb = new StringBuilder("Hello");

sb.append(" World");  // Hello World
sb.insert(5, ",");    // Hello, World
sb.delete(5, 6);      // Hello World
sb.reverse();         // dlroW olleH
sb.replace(0, 5, "Hi");  // Hi olleH
sb.substring(0, 2);   // Hi

String str = sb.toString();  // Convert to String
```

---

### Common String Methods

```java
String str = "Hello World";

// Length
int len = str.length();  // 11

// Character at index
char ch = str.charAt(0);  // 'H'

// Substring
String sub1 = str.substring(0, 5);  // Hello
String sub2 = str.substring(6);     // World

// Index
int index1 = str.indexOf("World");  // 6
int index2 = str.lastIndexOf("o");  // 7

// Replace
String replaced = str.replace("World", "Java");  // Hello Java

// Split
String[] parts = str.split(" ");  // ["Hello", "World"]

// Case
String upper = str.toUpperCase();  // HELLO WORLD
String lower = str.toLowerCase();  // hello world

// Trim
String trimmed = "  Hello  ".trim();  // "Hello"

// Check
boolean starts = str.startsWith("Hello");  // true
boolean ends = str.endsWith("World");      // true
boolean contains = str.contains("World");  // true
boolean empty = str.isEmpty();             // false
```

---

## Interview Questions with Answers

### Q1: What is the difference between == and equals()?

**Answer:**
- `==` compares references (memory addresses)
- `equals()` compares content (values)

```java
String s1 = new String("Hello");
String s2 = new String("Hello");

System.out.println(s1 == s2);       // false (different objects)
System.out.println(s1.equals(s2));  // true (same content)
```

---

### Q2: What is the difference between final, finally, and finalize()?

**Answer:**
- **final** - Keyword for constants, prevent override/inheritance
- **finally** - Block that always executes in try-catch
- **finalize()** - Method called by GC before object destruction (deprecated)

```java
// final
final int MAX = 100;

// finally
try {
    // code
} finally {
    // always executes
}

// finalize (deprecated)
@Override
protected void finalize() throws Throwable {
    // cleanup code
}
```

---

### Q3: What is the difference between ArrayList and LinkedList?

**Answer:**
- **ArrayList** - Dynamic array, fast random access O(1), slow insertion/deletion O(n)
- **LinkedList** - Doubly linked list, slow random access O(n), fast insertion/deletion O(1)

**Use ArrayList when:** Frequent read operations
**Use LinkedList when:** Frequent add/remove operations

---

### Q4: What is the difference between HashMap and ConcurrentHashMap?

**Answer:**
- **HashMap** - Not thread-safe, faster, allows one null key
- **ConcurrentHashMap** - Thread-safe, slower, doesn't allow null keys

```java
// HashMap - Not thread-safe
Map<String, Integer> hashMap = new HashMap<>();

// ConcurrentHashMap - Thread-safe
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
```

---

### Q5: What is the difference between Comparable and Comparator?

**Answer:**
- **Comparable** - Natural ordering, compareTo() method, modifies class
- **Comparator** - Custom ordering, compare() method, separate class

```java
// Comparable
class Student implements Comparable<Student> {
    public int compareTo(Student other) {
        return this.age - other.age;
    }
}

// Comparator
Comparator<Student> byName = (s1, s2) -> s1.name.compareTo(s2.name);
```

---

---

### Q6: What is the difference between abstract class and interface?

**Answer:**

| Feature | Abstract Class | Interface |
|---------|----------------|-----------|
| Methods | Can have abstract and concrete | All abstract (Java 8+: default/static) |
| Variables | Any type | public static final only |
| Constructor | Yes | No |
| Multiple inheritance | No | Yes |
| Access modifiers | Any | public only |

```java
// Abstract class
abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void makeSound();

    public void sleep() {  // Concrete method
        System.out.println("Sleeping...");
    }
}

// Interface
interface Flyable {
    void fly();

    default void land() {  // Default method (Java 8+)
        System.out.println("Landing...");
    }
}
```

---

### Q7: What is method overloading vs method overriding?

**Answer:**

| Feature | Overloading | Overriding |
|---------|-------------|------------|
| Definition | Same method name, different parameters | Redefine parent method in child |
| Class | Same class | Different classes (inheritance) |
| Parameters | Must differ | Must be same |
| Return type | Can differ | Must be same (or covariant) |
| Compile/Runtime | Compile-time (static polymorphism) | Runtime (dynamic polymorphism) |

```java
// Overloading
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }
}

// Overriding
class Animal {
    public void makeSound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark!");
    }
}
```

---

### Q8: What is the difference between String, StringBuilder, and StringBuffer?

**Answer:**

| Feature | String | StringBuilder | StringBuffer |
|---------|--------|---------------|--------------|
| Mutability | Immutable | Mutable | Mutable |
| Thread-safe | Yes (immutable) | No | Yes (synchronized) |
| Performance | Slow (creates new object) | Fast | Slower than StringBuilder |
| Use case | Few modifications | Single-threaded | Multi-threaded |

```java
// String - Immutable
String s = "Hello";
s = s + " World";  // Creates new object

// StringBuilder - Mutable, not thread-safe
StringBuilder sb = new StringBuilder("Hello");
sb.append(" World");  // Modifies same object

// StringBuffer - Mutable, thread-safe
StringBuffer sbf = new StringBuffer("Hello");
sbf.append(" World");  // Synchronized methods
```

---

### Q9: Explain Java Memory Model (Heap vs Stack).

**Answer:**

**Stack Memory:**
- Stores local variables and method calls
- LIFO (Last In First Out)
- Fast access
- Limited size
- Thread-specific (each thread has its own stack)
- Automatically cleaned when method returns

**Heap Memory:**
- Stores objects and instance variables
- Shared across all threads
- Larger size
- Garbage collected
- Slower access than stack

```java
public void method() {
    int x = 10;           // Stack
    String s = "Hello";   // "Hello" in String pool (Heap), s reference in Stack

    Person p = new Person("John");  // Person object in Heap, p reference in Stack
}
```

**Memory Diagram:**
```
Stack                    Heap
┌──────────┐            ┌─────────────────┐
│ x = 10   │            │ String "Hello"  │
│ s → ─────┼───────────→│                 │
│ p → ─────┼───────────→│ Person object   │
└──────────┘            │  - name: "John" │
                        └─────────────────┘
```

---

### Q10: What is the difference between fail-fast and fail-safe iterators?

**Answer:**

**Fail-Fast:**
- Throws ConcurrentModificationException if collection is modified during iteration
- Works on original collection
- Examples: ArrayList, HashMap, HashSet

**Fail-Safe:**
- Doesn't throw exception if collection is modified
- Works on clone/snapshot of collection
- Examples: ConcurrentHashMap, CopyOnWriteArrayList

```java
// Fail-Fast
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
for (String item : list) {
    if (item.equals("B")) {
        list.remove(item);  // ConcurrentModificationException!
    }
}

// Fail-Safe
List<String> list = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));
for (String item : list) {
    if (item.equals("B")) {
        list.remove(item);  // OK! Works fine
    }
}

// Solution for fail-fast: Use Iterator.remove()
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String item = iterator.next();
    if (item.equals("B")) {
        iterator.remove();  // Safe removal
    }
}
```

---

## Multithreading Interview Questions

### Q11: What is the difference between Thread and Runnable?

**Answer:**

| Feature | Thread | Runnable |
|---------|--------|----------|
| Type | Class | Interface |
| Inheritance | Extends Thread | Implements Runnable |
| Flexibility | Can't extend other class | Can extend other class |
| Resource sharing | Each thread has separate resources | Threads share same object |
| Use case | Simple threading | Preferred approach |

```java
// Using Thread class
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running");
    }
}

MyThread thread = new MyThread();
thread.start();

// Using Runnable interface (Preferred)
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable running");
    }
}

Thread thread = new Thread(new MyRunnable());
thread.start();

// Using Lambda (Java 8+)
Thread thread = new Thread(() -> System.out.println("Lambda running"));
thread.start();
```

---

### Q12: What is synchronized keyword and how does it work?

**Answer:**
`synchronized` ensures that only one thread can access a method/block at a time.

**Types:**

**1. Synchronized Method:**
```java
class Counter {
    private int count = 0;

    // Synchronized instance method
    public synchronized void increment() {
        count++;  // Only one thread can execute at a time
    }

    // Synchronized static method
    public static synchronized void staticMethod() {
        // Class-level lock
    }
}
```

**2. Synchronized Block:**
```java
class Counter {
    private int count = 0;
    private Object lock = new Object();

    public void increment() {
        synchronized(this) {  // Lock on current object
            count++;
        }
    }

    public void anotherMethod() {
        synchronized(lock) {  // Lock on specific object
            // Code
        }
    }
}
```

**How it works:**
- Each object has a monitor (intrinsic lock)
- Thread acquires lock before entering synchronized block
- Other threads wait until lock is released
- Thread releases lock when exiting synchronized block

---

### Q13: What is the difference between wait(), notify(), and notifyAll()?

**Answer:**
These methods are used for inter-thread communication.

| Method | Purpose |
|--------|---------|
| wait() | Releases lock and waits |
| notify() | Wakes up one waiting thread |
| notifyAll() | Wakes up all waiting threads |

**Example: Producer-Consumer:**
```java
class SharedResource {
    private int data;
    private boolean available = false;

    // Producer
    public synchronized void produce(int value) throws InterruptedException {
        while (available) {
            wait();  // Wait if data is available
        }

        data = value;
        available = true;
        System.out.println("Produced: " + value);
        notify();  // Notify consumer
    }

    // Consumer
    public synchronized int consume() throws InterruptedException {
        while (!available) {
            wait();  // Wait if no data available
        }

        available = false;
        System.out.println("Consumed: " + data);
        notify();  // Notify producer
        return data;
    }
}

// Usage
SharedResource resource = new SharedResource();

// Producer thread
Thread producer = new Thread(() -> {
    try {
        for (int i = 1; i <= 5; i++) {
            resource.produce(i);
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
});

// Consumer thread
Thread consumer = new Thread(() -> {
    try {
        for (int i = 1; i <= 5; i++) {
            resource.consume();
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
});

producer.start();
consumer.start();
```

---

### Q14: What is volatile keyword?

**Answer:**
`volatile` ensures visibility of changes to variables across threads.

**Without volatile:**
```java
class Task implements Runnable {
    private boolean running = true;

    public void run() {
        while (running) {  // May not see changes from other thread!
            // Work
        }
    }

    public void stop() {
        running = false;
    }
}
```

**With volatile:**
```java
class Task implements Runnable {
    private volatile boolean running = true;  // Changes visible immediately

    public void run() {
        while (running) {  // Will see changes from other thread
            // Work
        }
    }

    public void stop() {
        running = false;
    }
}
```

**Key points:**
- Prevents caching of variable in thread's local memory
- Ensures reads/writes go directly to main memory
- Provides visibility guarantee
- Does NOT provide atomicity (use synchronized or atomic classes for that)

---

### Q15: What is the difference between synchronized and ReentrantLock?

**Answer:**

| Feature | synchronized | ReentrantLock |
|---------|--------------|---------------|
| Type | Keyword | Class |
| Lock acquisition | Implicit | Explicit (lock/unlock) |
| Fairness | No control | Can specify fair lock |
| Try lock | Not possible | tryLock() available |
| Interruptible | No | lockInterruptibly() |
| Multiple conditions | One (wait/notify) | Multiple (Condition) |

```java
// synchronized
public synchronized void method() {
    // Code
}

// ReentrantLock
class Counter {
    private final ReentrantLock lock = new ReentrantLock(true);  // Fair lock
    private int count = 0;

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();  // Must unlock in finally
        }
    }

    public void tryIncrement() {
        if (lock.tryLock()) {  // Try to acquire lock
            try {
                count++;
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Could not acquire lock");
        }
    }
}
```

---

### Q16: What is ThreadLocal?

**Answer:**
ThreadLocal provides thread-local variables - each thread has its own copy.

**Use cases:**
- Per-thread context (user session, database connection)
- Avoiding thread-safety issues

```java
public class UserContext {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();  // Important: prevent memory leaks
    }
}

// Usage
// Thread 1
UserContext.setUser(new User("Alice"));
System.out.println(UserContext.getUser().getName());  // Alice

// Thread 2
UserContext.setUser(new User("Bob"));
System.out.println(UserContext.getUser().getName());  // Bob

// Each thread has its own User object
```

---

### Q17: What are Atomic classes?

**Answer:**
Atomic classes provide lock-free thread-safe operations.

**Common classes:**
- AtomicInteger
- AtomicLong
- AtomicBoolean
- AtomicReference

```java
// Non-atomic (not thread-safe)
class Counter {
    private int count = 0;

    public void increment() {
        count++;  // Not atomic: read, modify, write
    }
}

// Using synchronized
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }
}

// Using AtomicInteger (lock-free, faster)
class Counter {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();  // Atomic operation
    }

    public int getCount() {
        return count.get();
    }
}

// Advanced operations
AtomicInteger atomic = new AtomicInteger(0);

atomic.incrementAndGet();  // ++i
atomic.getAndIncrement();  // i++
atomic.addAndGet(5);       // i += 5
atomic.compareAndSet(0, 10);  // if (value == 0) value = 10

// Custom update
atomic.updateAndGet(current -> current * 2);
```

---

### Q18: What is ExecutorService and thread pools?

**Answer:**
ExecutorService manages thread pool for executing tasks.

**Thread pool types:**
```java
// 1. Fixed thread pool
ExecutorService executor = Executors.newFixedThreadPool(5);

// 2. Cached thread pool (creates threads as needed)
ExecutorService executor = Executors.newCachedThreadPool();

// 3. Single thread executor
ExecutorService executor = Executors.newSingleThreadExecutor();

// 4. Scheduled thread pool
ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
```

**Usage:**
```java
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks
for (int i = 0; i < 10; i++) {
    int taskId = i;
    executor.submit(() -> {
        System.out.println("Task " + taskId + " running on " +
                           Thread.currentThread().getName());
    });
}

// Shutdown
executor.shutdown();  // No new tasks accepted

try {
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow();  // Force shutdown
    }
} catch (InterruptedException e) {
    executor.shutdownNow();
}
```

**Custom ThreadPoolExecutor:**
```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    5,              // Core pool size
    10,             // Maximum pool size
    60L,            // Keep alive time
    TimeUnit.SECONDS,
    new LinkedBlockingQueue<>(100),  // Work queue
    new ThreadPoolExecutor.CallerRunsPolicy()  // Rejection policy
);
```

---

### Q19: What is deadlock and how to avoid it?

**Answer:**
Deadlock occurs when two or more threads wait for each other to release resources.

**Deadlock example:**
```java
class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println("Thread 1: Holding lock1");

            try { Thread.sleep(10); } catch (InterruptedException e) {}

            synchronized (lock2) {
                System.out.println("Thread 1: Holding lock1 and lock2");
            }
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println("Thread 2: Holding lock2");

            try { Thread.sleep(10); } catch (InterruptedException e) {}

            synchronized (lock1) {
                System.out.println("Thread 2: Holding lock2 and lock1");
            }
        }
    }
}

// Thread 1 calls method1() - acquires lock1, waits for lock2
// Thread 2 calls method2() - acquires lock2, waits for lock1
// DEADLOCK!
```

**How to avoid:**

**1. Lock ordering:**
```java
// Always acquire locks in same order
public void method1() {
    synchronized (lock1) {
        synchronized (lock2) {
            // Code
        }
    }
}

public void method2() {
    synchronized (lock1) {  // Same order!
        synchronized (lock2) {
            // Code
        }
    }
}
```

**2. Use tryLock with timeout:**
```java
ReentrantLock lock1 = new ReentrantLock();
ReentrantLock lock2 = new ReentrantLock();

public void method() {
    boolean gotLock1 = false;
    boolean gotLock2 = false;

    try {
        gotLock1 = lock1.tryLock(1, TimeUnit.SECONDS);
        gotLock2 = lock2.tryLock(1, TimeUnit.SECONDS);

        if (gotLock1 && gotLock2) {
            // Perform work
        }
    } catch (InterruptedException e) {
        // Handle
    } finally {
        if (gotLock1) lock1.unlock();
        if (gotLock2) lock2.unlock();
    }
}
```

---

### Q20: What is Future and CompletableFuture?

**Answer:**

**Future:**
- Represents result of asynchronous computation
- Blocking - get() waits for result

```java
ExecutorService executor = Executors.newSingleThreadExecutor();

Future<Integer> future = executor.submit(() -> {
    Thread.sleep(1000);
    return 42;
});

System.out.println("Doing other work...");

Integer result = future.get();  // Blocks until result is available
System.out.println("Result: " + result);

executor.shutdown();
```

**CompletableFuture:**
- Enhanced Future with better composition
- Non-blocking callbacks

```java
// Simple async task
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Long running task
    return "Result";
});

future.thenAccept(result -> System.out.println(result));  // Non-blocking

// Chaining
CompletableFuture.supplyAsync(() -> "Hello")
    .thenApply(s -> s + " World")
    .thenApply(String::toUpperCase)
    .thenAccept(System.out::println);  // HELLO WORLD

// Combining futures
CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);

CompletableFuture<Integer> combined = future1.thenCombine(future2, (a, b) -> a + b);
System.out.println(combined.get());  // 30

// Exception handling
CompletableFuture.supplyAsync(() -> {
    if (true) throw new RuntimeException("Error");
    return "Success";
}).exceptionally(ex -> {
    System.out.println("Caught: " + ex.getMessage());
    return "Default value";
}).thenAccept(System.out::println);  // Default value
```

---

## Java 8+ Features Interview Questions

### Q21: What are the key features of Java 8?

**Answer:**
1. Lambda Expressions
2. Functional Interfaces
3. Stream API
4. Default and Static methods in interfaces
5. Optional class
6. Method references
7. Date and Time API (java.time)
8. Nashorn JavaScript engine

---

### Q22: What is Optional and why use it?

**Answer:**
Optional is a container that may or may not contain a value. It helps avoid NullPointerException.

```java
// Without Optional
public String getName(User user) {
    if (user != null) {
        return user.getName();
    }
    return "Unknown";
}

// With Optional
public Optional<String> getName(User user) {
    return Optional.ofNullable(user)
                   .map(User::getName);
}

// Creating Optional
Optional<String> empty = Optional.empty();
Optional<String> value = Optional.of("Hello");  // Throws NPE if null
Optional<String> nullable = Optional.ofNullable(null);  // OK

// Using Optional
Optional<String> opt = Optional.of("Hello");

// Check if present
if (opt.isPresent()) {
    System.out.println(opt.get());
}

// Better: Use ifPresent
opt.ifPresent(System.out::println);

// Provide default value
String result = opt.orElse("Default");
String result = opt.orElseGet(() -> "Default");  // Lazy evaluation
String result = opt.orElseThrow(() -> new RuntimeException("Not found"));

// Transform value
Optional<Integer> length = opt.map(String::length);

// Filter
opt.filter(s -> s.length() > 3)
   .ifPresent(System.out::println);

// flatMap for nested Optionals
Optional<Optional<String>> nested = Optional.of(Optional.of("Hello"));
Optional<String> flat = nested.flatMap(o -> o);
```

---

### Q23: What are method references?

**Answer:**
Method references are shorthand for lambda expressions that call a specific method.

**Types:**

**1. Static method reference:**
```java
// Lambda
Function<String, Integer> lambda = s -> Integer.parseInt(s);

// Method reference
Function<String, Integer> methodRef = Integer::parseInt;
```

**2. Instance method reference (on object):**
```java
String str = "Hello";

// Lambda
Supplier<Integer> lambda = () -> str.length();

// Method reference
Supplier<Integer> methodRef = str::length;
```

**3. Instance method reference (on parameter):**
```java
// Lambda
Function<String, Integer> lambda = s -> s.length();

// Method reference
Function<String, Integer> methodRef = String::length;
```

**4. Constructor reference:**
```java
// Lambda
Supplier<List<String>> lambda = () -> new ArrayList<>();

// Method reference
Supplier<List<String>> methodRef = ArrayList::new;

// With parameters
Function<Integer, List<String>> methodRef = ArrayList::new;
```

---

### Q24: What is the difference between map() and flatMap()?

**Answer:**

| Feature | map() | flatMap() |
|---------|-------|-----------|
| Purpose | Transform element | Transform and flatten |
| Return | Stream<T> | Stream<T> (flattens Stream<Stream<T>>) |
| Use case | One-to-one transformation | One-to-many transformation |

```java
// map() - One-to-one
List<String> words = Arrays.asList("Hello", "World");
List<Integer> lengths = words.stream()
    .map(String::length)  // Stream<Integer>
    .collect(Collectors.toList());
// [5, 5]

// flatMap() - One-to-many + flatten
List<String> words = Arrays.asList("Hello", "World");
List<String> letters = words.stream()
    .flatMap(word -> Arrays.stream(word.split("")))  // Stream<Stream<String>> → Stream<String>
    .collect(Collectors.toList());
// [H, e, l, l, o, W, o, r, l, d]

// Another example
List<List<Integer>> nested = Arrays.asList(
    Arrays.asList(1, 2, 3),
    Arrays.asList(4, 5),
    Arrays.asList(6, 7, 8)
);

// map() - Returns Stream<Stream<Integer>>
Stream<Stream<Integer>> streamOfStreams = nested.stream()
    .map(list -> list.stream());

// flatMap() - Returns Stream<Integer>
List<Integer> flat = nested.stream()
    .flatMap(list -> list.stream())  // Flattens
    .collect(Collectors.toList());
// [1, 2, 3, 4, 5, 6, 7, 8]
```

---

### Q25: What are Collectors in Stream API?

**Answer:**
Collectors are used to collect stream elements into collections or perform reduction operations.

```java
List<String> list = Arrays.asList("A", "B", "C", "D", "A");

// toList()
List<String> result = list.stream().collect(Collectors.toList());

// toSet()
Set<String> set = list.stream().collect(Collectors.toSet());

// toMap()
Map<String, Integer> map = list.stream()
    .distinct()
    .collect(Collectors.toMap(s -> s, String::length));

// joining()
String joined = list.stream().collect(Collectors.joining(", "));
// "A, B, C, D, A"

// counting()
long count = list.stream().collect(Collectors.counting());

// summingInt()
int sum = list.stream().collect(Collectors.summingInt(String::length));

// averagingInt()
double avg = list.stream().collect(Collectors.averagingInt(String::length));

// groupingBy()
Map<Integer, List<String>> grouped = list.stream()
    .collect(Collectors.groupingBy(String::length));
// {1=[A, B, C, D, A]}

// partitioningBy()
Map<Boolean, List<String>> partitioned = list.stream()
    .collect(Collectors.partitioningBy(s -> s.equals("A")));
// {false=[B, C, D], true=[A, A]}

// Custom collector
Map<String, Long> counted = list.stream()
    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
// {A=2, B=1, C=1, D=1}
```

---

### Q26: What is the difference between intermediate and terminal operations?

**Answer:**

**Intermediate Operations:**
- Return Stream
- Lazy (not executed until terminal operation)
- Can be chained

**Examples:** filter, map, flatMap, distinct, sorted, peek, limit, skip

**Terminal Operations:**
- Return non-Stream result (void, value, collection)
- Trigger stream processing
- Cannot be chained (ends the stream)

**Examples:** forEach, collect, reduce, count, anyMatch, findFirst

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Intermediate operations (lazy)
Stream<Integer> stream = numbers.stream()
    .filter(n -> {
        System.out.println("Filtering: " + n);
        return n % 2 == 0;
    })  // Intermediate
    .map(n -> {
        System.out.println("Mapping: " + n);
        return n * 2;
    });  // Intermediate

// Nothing printed yet!

// Terminal operation (triggers execution)
List<Integer> result = stream.collect(Collectors.toList());  // Terminal

// Now filtering and mapping messages are printed
```

---

### Q27: What is the difference between peek() and forEach()?

**Answer:**

| Feature | peek() | forEach() |
|---------|--------|-----------|
| Type | Intermediate | Terminal |
| Returns | Stream | void |
| Use case | Debugging, intermediate processing | Final consumption |

```java
// peek() - Intermediate (for debugging)
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

numbers.stream()
    .filter(n -> n % 2 == 0)
    .peek(n -> System.out.println("Filtered: " + n))  // Debugging
    .map(n -> n * 2)
    .peek(n -> System.out.println("Mapped: " + n))    // Debugging
    .collect(Collectors.toList());

// forEach() - Terminal (final operation)
numbers.stream()
    .filter(n -> n % 2 == 0)
    .forEach(System.out::println);  // Consumes stream
```

---

### Q28: What is parallel stream and when to use it?

**Answer:**
Parallel stream divides work into multiple threads for parallel processing.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Sequential stream
long sum = numbers.stream()
    .map(n -> n * 2)
    .reduce(0, Integer::sum);

// Parallel stream
long parallelSum = numbers.parallelStream()
    .map(n -> n * 2)
    .reduce(0, Integer::sum);

// Or convert sequential to parallel
long sum = numbers.stream()
    .parallel()  // Convert to parallel
    .map(n -> n * 2)
    .reduce(0, Integer::sum);
```

**When to use:**
- ✅ Large datasets (> 10,000 elements)
- ✅ CPU-intensive operations
- ✅ Independent operations (no shared state)
- ❌ Small datasets (overhead > benefit)
- ❌ I/O operations (already parallelized)
- ❌ Operations with side effects

**Benchmark:**
```java
List<Integer> numbers = IntStream.rangeClosed(1, 10_000_000)
    .boxed()
    .collect(Collectors.toList());

// Sequential
long start = System.currentTimeMillis();
long sum = numbers.stream()
    .map(n -> n * 2)
    .reduce(0, Integer::sum);
System.out.println("Sequential: " + (System.currentTimeMillis() - start) + "ms");

// Parallel
start = System.currentTimeMillis();
sum = numbers.parallelStream()
    .map(n -> n * 2)
    .reduce(0, Integer::sum);
System.out.println("Parallel: " + (System.currentTimeMillis() - start) + "ms");
```

---

### Q29: What are default and static methods in interfaces?

**Answer:**

**Default methods:**
- Provide default implementation in interface
- Can be overridden in implementing class
- Added in Java 8 for backward compatibility

**Static methods:**
- Utility methods in interface
- Cannot be overridden
- Called using interface name

```java
interface Vehicle {
    // Abstract method
    void start();

    // Default method (Java 8+)
    default void stop() {
        System.out.println("Vehicle stopped");
    }

    default void honk() {
        System.out.println("Beep beep!");
    }

    // Static method (Java 8+)
    static void repair() {
        System.out.println("Repairing vehicle");
    }
}

class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car started");
    }

    // Can override default method
    @Override
    public void stop() {
        System.out.println("Car stopped");
    }

    // Cannot override static method
}

// Usage
Car car = new Car();
car.start();  // Car started
car.stop();   // Car stopped
car.honk();   // Beep beep! (default implementation)

Vehicle.repair();  // Repairing vehicle (static method)
```

---

### Q30: What is the diamond problem and how Java 8 solves it?

**Answer:**
Diamond problem occurs when a class implements multiple interfaces with same default method.

```java
interface A {
    default void display() {
        System.out.println("A");
    }
}

interface B {
    default void display() {
        System.out.println("B");
    }
}

// Compilation error: inherits unrelated defaults
class C implements A, B {
    // Must override to resolve conflict
    @Override
    public void display() {
        A.super.display();  // Call A's implementation
        // or
        B.super.display();  // Call B's implementation
        // or
        System.out.println("C");  // Own implementation
    }
}
```

**Resolution rules:**
1. Class wins - Class implementation overrides interface default
2. Subtype wins - More specific interface wins
3. Explicit choice - Must override to resolve conflict

---

## JVM and Memory Management Questions

### Q31: Explain Garbage Collection in Java.

**Answer:**
Garbage Collection automatically reclaims memory occupied by unused objects.

**How it works:**
1. **Mark** - Identify reachable objects from GC roots
2. **Sweep** - Remove unreachable objects
3. **Compact** - Defragment memory (optional)

**GC Roots:**
- Local variables
- Active threads
- Static variables
- JNI references

**Heap Structure:**
```
Young Generation                Old Generation
┌──────────────┬─────────────┐  ┌──────────────┐
│   Eden       │ Survivor 0  │  │              │
│              │ Survivor 1  │  │   Tenured    │
└──────────────┴─────────────┘  └──────────────┘
     Minor GC                       Major GC
```

**GC Types:**
```java
// 1. Serial GC (single thread)
java -XX:+UseSerialGC MyApp

// 2. Parallel GC (multiple threads)
java -XX:+UseParallelGC MyApp

// 3. CMS (Concurrent Mark Sweep)
java -XX:+UseConcMarkSweepGC MyApp

// 4. G1 GC (Garbage First - default Java 9+)
java -XX:+UseG1GC MyApp

// 5. ZGC (low latency - Java 11+)
java -XX:+UseZGC MyApp
```

**Object lifecycle:**
```java
// 1. Created in Eden
Object obj = new Object();

// 2. After minor GC, moved to Survivor
// (if still referenced)

// 3. After several GCs, promoted to Old Gen

// 4. Eventually garbage collected in Major GC
```

---

### Q32: What is OutOfMemoryError and how to handle it?

**Answer:**
OutOfMemoryError occurs when JVM cannot allocate memory.

**Types:**

**1. Heap Space:**
```java
// Cause: Too many objects
List<byte[]> list = new ArrayList<>();
while (true) {
    list.add(new byte[1024 * 1024]);  // 1 MB
}
// java.lang.OutOfMemoryError: Java heap space

// Solution: Increase heap size
java -Xmx2g MyApp  // 2 GB heap
```

**2. PermGen/Metaspace:**
```java
// Cause: Too many classes loaded
while (true) {
    MyClassLoader.loadClass("MyClass");
}
// java.lang.OutOfMemoryError: Metaspace

// Solution: Increase Metaspace
java -XX:MaxMetaspaceSize=512m MyApp
```

**3. Unable to create native thread:**
```java
// Cause: Too many threads
while (true) {
    new Thread(() -> {
        try { Thread.sleep(Long.MAX_VALUE); }
        catch (Exception e) {}
    }).start();
}
// java.lang.OutOfMemoryError: unable to create new native thread

// Solution: Reduce thread count or increase OS limits
```

**Prevention:**
```java
// 1. Use object pooling
ObjectPool<Connection> pool = new ObjectPool<>();

// 2. Release resources
try (FileInputStream fis = new FileInputStream("file.txt")) {
    // Use resource
}  // Automatically closed

// 3. Avoid memory leaks
map.clear();  // Clear collections
cache.invalidateAll();  // Clear caches

// 4. Monitor memory
Runtime runtime = Runtime.getRuntime();
long usedMemory = runtime.totalMemory() - runtime.freeMemory();
System.out.println("Used: " + usedMemory / 1024 / 1024 + " MB");
```

---

### Q33: What are strong, soft, weak, and phantom references?

**Answer:**

| Type | GC Behavior | Use Case |
|------|-------------|----------|
| Strong | Never collected | Normal objects |
| Soft | Collected before OOM | Memory-sensitive caches |
| Weak | Collected in next GC | Canonical mappings |
| Phantom | Collected, cleanup before finalization | Cleanup operations |

```java
// 1. Strong Reference (default)
Object strong = new Object();  // Never GC'd while referenced

// 2. Soft Reference (collected before OOM)
SoftReference<byte[]> soft = new SoftReference<>(new byte[1024]);
byte[] data = soft.get();  // May return null after GC

// 3. Weak Reference (collected in next GC)
WeakReference<Object> weak = new WeakReference<>(new Object());
Object obj = weak.get();  // May return null after GC

// 4. Phantom Reference (for cleanup)
ReferenceQueue<Object> queue = new ReferenceQueue<>();
PhantomReference<Object> phantom = new PhantomReference<>(new Object(), queue);
// get() always returns null
```

**Use case: Cache with SoftReference:**
```java
public class Cache<K, V> {
    private Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public V get(K key) {
        SoftReference<V> ref = cache.get(key);
        return (ref != null) ? ref.get() : null;
    }
}
```

---

## Design Patterns Interview Questions

### Q34: What is Singleton pattern and its implementation?

**Answer:**
Singleton ensures only one instance of a class exists.

**Implementations:**

**1. Eager Initialization:**
```java
public class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {}  // Private constructor

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

**2. Lazy Initialization (not thread-safe):**
```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**3. Thread-Safe (synchronized):**
```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**4. Double-Checked Locking:**
```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {  // First check
            synchronized (Singleton.class) {
                if (instance == null) {  // Second check
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**5. Bill Pugh (Best approach):**
```java
public class Singleton {
    private Singleton() {}

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

**6. Enum (Most robust):**
```java
public enum Singleton {
    INSTANCE;

    public void doSomething() {
        // Method
    }
}

// Usage
Singleton.INSTANCE.doSomething();
```

---

### Q35: What is Factory pattern?

**Answer:**
Factory pattern creates objects without exposing creation logic.

```java
// Product interface
interface Vehicle {
    void drive();
}

// Concrete products
class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving car");
    }
}

class Bike implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Riding bike");
    }
}

// Factory
class VehicleFactory {
    public static Vehicle getVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car();
            case "bike":
                return new Bike();
            default:
                throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}

// Usage
Vehicle vehicle = VehicleFactory.getVehicle("car");
vehicle.drive();  // Driving car
```

---

### Q36: What is Builder pattern?

**Answer:**
Builder pattern constructs complex objects step by step.

```java
public class User {
    // Required parameters
    private final String firstName;
    private final String lastName;

    // Optional parameters
    private final int age;
    private final String phone;
    private final String address;

    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    // Builder class
    public static class UserBuilder {
        // Required
        private final String firstName;
        private final String lastName;

        // Optional
        private int age;
        private String phone;
        private String address;

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

// Usage
User user = new User.UserBuilder("John", "Doe")
    .age(30)
    .phone("1234567890")
    .address("123 Main St")
    .build();
```

---

This comprehensive Java guide now covers EVERYTHING from basics to advanced topics with 36+ detailed interview questions for mid-level Software Engineer preparation!

---

## General Interview Questions

### Personal & Experience Questions

**Q1: Can you introduce yourself and explain your journey?**
> *Personal question - prepare your own answer based on your experience*

**Q2: What did you do during your internship?**
> *Personal question - prepare your own answer based on your experience*

**Q3: What did you learn during your internship?**
> *Personal question - prepare your own answer based on your experience*

**Q4: What modules did you work on?**
> *Personal question - prepare your own answer based on your experience*

---

### Frontend & Technology Stack Questions

**Q5: Why did they use Next.js and Node.js?**

**Answer:**

**Next.js:**
- **Server-Side Rendering (SSR):** Improves SEO and initial page load performance
- **Static Site Generation (SSG):** Pre-renders pages at build time for faster delivery
- **File-based Routing:** Automatic routing based on file structure in `/pages` directory
- **API Routes:** Built-in API endpoints without separate backend setup
- **Image Optimization:** Automatic image optimization and lazy loading
- **Code Splitting:** Automatic code splitting for faster page loads

**Node.js:**
- **JavaScript Everywhere:** Same language for frontend and backend (code reusability)
- **Non-blocking I/O:** Handles multiple concurrent connections efficiently
- **NPM Ecosystem:** Largest package ecosystem with millions of libraries
- **Event-driven Architecture:** Perfect for real-time applications
- **Fast Execution:** V8 engine compiles JavaScript to machine code
- **Scalability:** Easy horizontal scaling with clustering

---

**Q6: Why React?**

**Answer:**
- **Component-Based Architecture:** Reusable UI components reduce code duplication
- **Virtual DOM:** Efficient updates by comparing virtual DOM with actual DOM
- **One-way Data Binding:** Predictable data flow makes debugging easier
- **Large Community:** Extensive libraries, tools, and community support
- **JSX:** Write HTML-like syntax in JavaScript for better readability
- **React Hooks:** Manage state and lifecycle in functional components
- **React Native:** Code sharing between web and mobile applications
- **Performance:** Optimized rendering with reconciliation algorithm

---

**Q7: What is React?**

**Answer:**
React is an **open-source JavaScript library** developed by Facebook (Meta) for building user interfaces, particularly single-page applications.

**Key Characteristics:**
- **Declarative:** Describe what UI should look like, React handles the updates
- **Component-Based:** Build encapsulated components that manage their own state
- **Learn Once, Write Anywhere:** Can render on server (Node), mobile (React Native), VR

```jsx
// Example React Component
import React, { useState } from 'react';

function Counter() {
    const [count, setCount] = useState(0);

    return (
        <div>
            <p>Count: {count}</p>
            <button onClick={() => setCount(count + 1)}>
                Increment
            </button>
        </div>
    );
}

export default Counter;
```

---

**Q8: What do you mean by a "one-page application" (SPA)?**

**Answer:**
A **Single Page Application (SPA)** is a web application that loads a single HTML page and dynamically updates content as the user interacts, without requiring full page reloads.

**How it works:**
1. Initial request loads the entire application (HTML, CSS, JS)
2. Subsequent interactions fetch only data (usually JSON) via AJAX/Fetch
3. JavaScript updates the DOM dynamically
4. URL changes are handled by client-side routing (no server round-trip)

**Advantages:**
- Faster user experience (no full page reloads)
- Reduced server load
- Better user experience (app-like feel)
- Easier to debug with browser dev tools

**Disadvantages:**
- Initial load time can be longer
- SEO challenges (solved by SSR/SSG)
- Browser history management complexity
- Memory leaks if not handled properly

**Examples:** Gmail, Facebook, Twitter, Netflix

```
Traditional Web App:          SPA:
User Click → Server Request   User Click → JS handles
Server Response → Full Page   API call → JSON data
Browser Renders Full Page     JS updates only changed parts
```

---

### System Design & Architecture Questions

**Q9: Have you worked with microservices?**
> *Personal question - prepare your own answer based on your experience*

---

**Q10: What are the pros and cons of microservices?**

**Answer:**

| Pros | Cons |
|------|------|
| **Independent Deployment:** Each service can be deployed independently | **Complexity:** Distributed systems are harder to manage |
| **Technology Flexibility:** Different services can use different tech stacks | **Network Latency:** Inter-service communication adds latency |
| **Scalability:** Scale individual services based on demand | **Data Consistency:** Maintaining consistency across services is challenging |
| **Fault Isolation:** Failure in one service doesn't crash the entire system | **Testing Complexity:** Integration testing becomes more difficult |
| **Team Autonomy:** Small teams can own and manage their services | **Operational Overhead:** More services = more monitoring, logging, deployment |
| **Faster Development:** Parallel development by multiple teams | **Debugging Difficulty:** Tracing issues across services is complex |

**When to use Microservices:**
- Large, complex applications
- Multiple development teams
- Need for independent scaling
- Different parts need different technologies

**When to use Monolith:**
- Small to medium applications
- Small team
- Rapid prototyping
- Simple deployment requirements

---

**Q11: What are horizontal and vertical scaling?**

**Answer:**

**Vertical Scaling (Scale Up):**
- Adding more power to existing machine (CPU, RAM, Storage)
- Like upgrading from a small car to a bigger car

```
Before:  [Server: 4GB RAM, 2 CPU]
After:   [Server: 32GB RAM, 16 CPU]
```

| Pros | Cons |
|------|------|
| Simple to implement | Hardware limits (can't scale infinitely) |
| No code changes needed | Single point of failure |
| No distributed system complexity | Expensive high-end hardware |
| Data consistency is easier | Downtime during upgrades |

**Horizontal Scaling (Scale Out):**
- Adding more machines to the pool
- Like adding more cars to a fleet

```
Before:  [Server 1]
After:   [Server 1] [Server 2] [Server 3] [Load Balancer]
```

| Pros | Cons |
|------|------|
| Theoretically unlimited scaling | Complex architecture |
| No single point of failure | Need load balancer |
| Cost-effective (commodity hardware) | Data consistency challenges |
| Can scale on demand | Requires distributed system design |

**Comparison:**

| Aspect | Vertical | Horizontal |
|--------|----------|------------|
| Cost | Expensive (high-end hardware) | Cost-effective (commodity hardware) |
| Complexity | Simple | Complex |
| Downtime | Required for upgrades | Zero downtime possible |
| Limit | Hardware limits | Virtually unlimited |
| Fault Tolerance | Single point of failure | High availability |

---

### Microservices - Deep Dive

#### 1. What are Microservices?

Microservices is an architectural style where an application is built as a collection of **small, independent, loosely coupled services**. Each service runs its own process, owns its own database, and communicates via lightweight protocols (HTTP/REST, gRPC, messaging).

```
Monolithic Architecture:              Microservices Architecture:
┌────────────────────────┐           ┌─────────┐  ┌─────────┐  ┌──────────┐
│   Entire Application   │           │  User   │  │  Order  │  │ Payment  │
│  ┌──────────────────┐  │           │ Service │  │ Service │  │ Service  │
│  │  User Module     │  │           │  [DB1]  │  │  [DB2]  │  │  [DB3]   │
│  │  Order Module    │  │    →      └────┬────┘  └────┬────┘  └────┬─────┘
│  │  Payment Module  │  │                │            │            │
│  │  Notification    │  │           ┌────┴────┐  ┌────┴────┐  ┌────┴─────┐
│  └──────────────────┘  │           │  Notif  │  │Inventory│  │ Shipping │
│     [Single DB]        │           │ Service │  │ Service │  │ Service  │
└────────────────────────┘           │  [DB4]  │  │  [DB5]  │  │  [DB6]   │
                                     └─────────┘  └─────────┘  └──────────┘
```

---

#### 2. Monolithic vs Microservices

| Aspect | Monolithic | Microservices |
|--------|-----------|---------------|
| **Codebase** | Single codebase | Multiple small codebases |
| **Deployment** | Deploy entire app | Deploy individual services |
| **Scaling** | Scale entire app | Scale specific services |
| **Technology** | One tech stack | Different stacks per service |
| **Database** | Shared single database | Database per service |
| **Team** | Large team on one codebase | Small teams own each service |
| **Failure** | One bug can crash everything | Fault isolation per service |
| **Communication** | In-process function calls | Network calls (HTTP, gRPC, MQ) |
| **Complexity** | Simple initially, grows over time | Complex initially, manageable long-term |

---

#### 3. Communication Between Microservices

**A. Synchronous Communication (Request-Response)**

```
┌──────────┐  HTTP/REST   ┌──────────┐
│  Order   │ ──────────→  │ Payment  │
│ Service  │ ←──────────  │ Service  │
└──────────┘   Response   └──────────┘
```

**REST (Representational State Transfer):**
- Uses standard HTTP methods (GET, POST, PUT, DELETE)
- JSON/XML for data exchange
- Simple and widely adopted
- Stateless communication

```java
// REST API call from Order Service to Payment Service
@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    public PaymentResponse processPayment(PaymentRequest request) {
        return restTemplate.postForObject(
            "http://payment-service/api/payments",
            request,
            PaymentResponse.class
        );
    }
}
```

**gRPC (Google Remote Procedure Call):**
- Uses Protocol Buffers (binary serialization)
- Faster than REST (binary vs JSON)
- Supports streaming (bidirectional)
- Strongly typed contracts

```protobuf
// payment.proto
service PaymentService {
    rpc ProcessPayment(PaymentRequest) returns (PaymentResponse);
}

message PaymentRequest {
    string orderId = 1;
    double amount = 2;
}
```

**B. Asynchronous Communication (Event-Driven)**

```
┌──────────┐  Publish    ┌──────────────┐  Subscribe  ┌──────────┐
│  Order   │ ─────────→  │ Message Queue │ ─────────→ │ Payment  │
│ Service  │              │ (Kafka/RMQ)  │             │ Service  │
└──────────┘              └──────────────┘             └──────────┘
                                │
                                ├─────────→ Notification Service
                                │
                                └─────────→ Inventory Service
```

- **Message Queues:** RabbitMQ, Apache Kafka, Amazon SQS
- Services don't wait for response (fire and forget)
- Better fault tolerance and decoupling
- Supports event-driven architecture

```java
// Producer - Order Service publishes event
@Service
public class OrderService {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void placeOrder(Order order) {
        // Save order
        orderRepository.save(order);
        // Publish event
        kafkaTemplate.send("order-topic", new OrderEvent(order.getId(), "CREATED"));
    }
}

// Consumer - Payment Service listens for events
@Service
public class PaymentConsumer {

    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void handleOrderEvent(OrderEvent event) {
        if ("CREATED".equals(event.getStatus())) {
            paymentService.processPayment(event.getOrderId());
        }
    }
}
```

**Synchronous vs Asynchronous:**

| Aspect | Synchronous (REST/gRPC) | Asynchronous (Message Queue) |
|--------|------------------------|------------------------------|
| Coupling | Tight | Loose |
| Speed | Waits for response | Fire and forget |
| Failure Handling | Cascading failures possible | Fault tolerant |
| Complexity | Simpler to implement | More infrastructure needed |
| Use Case | Real-time queries | Background processing, events |

---

#### 4. API Gateway

A single entry point for all client requests that routes them to appropriate microservices.

```
                        ┌──────────────────┐
    Mobile App ────→    │                  │ ───→ User Service
    Web App    ────→    │   API Gateway    │ ───→ Order Service
    3rd Party  ────→    │  (Spring Cloud   │ ───→ Payment Service
                        │   Gateway/Kong)  │ ───→ Product Service
                        └──────────────────┘
```

**Responsibilities:**
- **Routing:** Directs requests to correct service
- **Authentication:** Validates JWT tokens, API keys
- **Rate Limiting:** Prevents abuse (e.g., 100 requests/minute)
- **Load Balancing:** Distributes traffic across instances
- **Caching:** Caches frequent responses
- **Request/Response Transformation:** Aggregates responses from multiple services
- **Logging & Monitoring:** Centralized request logging

```java
// Spring Cloud Gateway Configuration
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r
                .path("/api/users/**")
                .uri("lb://USER-SERVICE"))
            .route("order-service", r -> r
                .path("/api/orders/**")
                .uri("lb://ORDER-SERVICE"))
            .route("payment-service", r -> r
                .path("/api/payments/**")
                .uri("lb://PAYMENT-SERVICE"))
            .build();
    }
}
```

---

#### 5. Service Discovery

Services need to find each other dynamically (IPs/ports change when scaling).

```
┌─────────────┐  1. Register    ┌───────────────────┐
│ User Service│ ──────────────→ │  Service Registry  │
│ (port:8081) │                 │  (Eureka/Consul)   │
└─────────────┘                 │                    │
                                │  user-service:8081 │
┌─────────────┐  2. Register   │  order-service:8082│
│Order Service│ ──────────────→ │  payment:8083      │
│ (port:8082) │                 └────────┬───────────┘
└─────────────┘                          │
                                         │ 3. Discover
┌──────────────┐                         │
│  API Gateway │ ←───────────────────────┘
│  "Where is   │   Returns: user-service → 192.168.1.5:8081
│ user-service?"│
└──────────────┘
```

**Tools:** Netflix Eureka, Consul, Zookeeper, Kubernetes DNS

```java
// Service Registration with Eureka
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

// application.yml
// eureka:
//   client:
//     serviceUrl:
//       defaultZone: http://localhost:8761/eureka/
//   instance:
//     preferIpAddress: true
```

---

#### Spring Boot Actuator - Deep Dive

##### What is Actuator?

Spring Boot Actuator is a **built-in module** that provides **production-ready features** to monitor and manage your application. Think of it as a **health dashboard** for your microservice — it exposes HTTP endpoints that tell you everything about your running application.

```
Without Actuator:                    With Actuator:
┌─────────────────┐                 ┌─────────────────────────────┐
│  Your Service   │                 │  Your Service               │
│                 │                 │                             │
│  Is it alive?   │  → No idea     │  /actuator/health  → UP     │
│  Memory usage?  │  → No idea     │  /actuator/metrics → 512MB  │
│  Active threads?│  → No idea     │  /actuator/info    → v2.1   │
│  DB connected?  │  → No idea     │  /actuator/env     → configs│
│                 │                 │  /actuator/beans   → 230    │
└─────────────────┘                 └─────────────────────────────┘
```

##### Why do we need Actuator?

**1. Health Monitoring:**
- Eureka/Kubernetes checks `/actuator/health` to know if service is alive
- If health check fails → service is removed from the registry → no traffic sent to it

```
Eureka Server                         Microservice
    │                                      │
    │── GET /actuator/health ─────────────→│
    │                                      │
    │←── { "status": "UP" } ──────────────│   ✅ Keep in registry
    │                                      │
    │── GET /actuator/health ─────────────→│
    │                                      │
    │←── { "status": "DOWN" } ────────────│   ❌ Remove from registry
    │                                      │
```

**2. Production Debugging:**
- Check memory/CPU without SSH into the server
- See all active beans, configurations, environment variables
- View HTTP request metrics (how many requests, response times)

**3. Kubernetes Integration:**
- Kubernetes uses **liveness probe** and **readiness probe** from Actuator
- **Liveness:** Is the app running? (restart if not)
- **Readiness:** Is the app ready to accept traffic? (stop sending requests if not)

##### Setting Up Actuator

**Step 1: Add Dependency**
```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**Step 2: Configure Endpoints**
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics, env, beans, loggers
  endpoint:
    health:
      show-details: always    # Show detailed health info
```

##### Key Actuator Endpoints

| Endpoint | URL | Purpose |
|----------|-----|---------|
| **health** | `/actuator/health` | Is the service alive? DB connected? Disk space OK? |
| **info** | `/actuator/info` | App version, description, custom info |
| **metrics** | `/actuator/metrics` | JVM memory, CPU, HTTP request stats |
| **env** | `/actuator/env` | All environment variables and configs |
| **beans** | `/actuator/beans` | All Spring beans loaded in the app |
| **loggers** | `/actuator/loggers` | View/change log levels at runtime |
| **mappings** | `/actuator/mappings` | All HTTP endpoint mappings |
| **threaddump** | `/actuator/threaddump` | Current thread states |
| **heapdump** | `/actuator/heapdump` | Download JVM heap dump |
| **shutdown** | `/actuator/shutdown` | Gracefully shutdown the app (disabled by default) |

##### Endpoint Examples

**1. /actuator/health**
```json
GET http://localhost:8080/actuator/health

{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "MySQL",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 500000000000,
        "free": 250000000000,
        "threshold": 10485760
      }
    },
    "redis": {
      "status": "UP"
    }
  }
}
```

**2. /actuator/metrics**
```json
GET http://localhost:8080/actuator/metrics

{
  "names": [
    "jvm.memory.used",
    "jvm.memory.max",
    "system.cpu.usage",
    "http.server.requests",
    "process.uptime",
    "jvm.threads.live"
  ]
}

// Drill into specific metric
GET http://localhost:8080/actuator/metrics/jvm.memory.used

{
  "name": "jvm.memory.used",
  "measurements": [
    { "statistic": "VALUE", "value": 256901120 }   // ~256MB
  ]
}
```

**3. /actuator/loggers (Change log level at RUNTIME)**
```json
// View current log level
GET http://localhost:8080/actuator/loggers/com.myapp

{
  "configuredLevel": "INFO",
  "effectiveLevel": "INFO"
}

// Change to DEBUG without restarting!
POST http://localhost:8080/actuator/loggers/com.myapp
{
  "configuredLevel": "DEBUG"
}
```

##### Custom Health Indicator

You can write your own health checks:

```java
@Component
public class PaymentGatewayHealthIndicator implements HealthIndicator {

    @Autowired
    private PaymentGatewayClient paymentClient;

    @Override
    public Health health() {
        try {
            boolean isReachable = paymentClient.ping();
            if (isReachable) {
                return Health.up()
                    .withDetail("paymentGateway", "Reachable")
                    .withDetail("responseTime", "45ms")
                    .build();
            }
            return Health.down()
                .withDetail("paymentGateway", "Not Reachable")
                .build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}
```

```json
// Now /actuator/health shows:
{
  "status": "UP",
  "components": {
    "paymentGateway": {
      "status": "UP",
      "details": {
        "paymentGateway": "Reachable",
        "responseTime": "45ms"
      }
    },
    "db": { "status": "UP" },
    "diskSpace": { "status": "UP" }
  }
}
```

##### How Eureka + Actuator Work Together

```
┌─────────────────────────────────────────────────────────┐
│                    Flow:                                 │
│                                                         │
│  1. Service starts → Registers with Eureka              │
│  2. Eureka calls /actuator/health every 30 seconds      │
│  3. Actuator checks: DB? Disk? Custom checks?           │
│  4. Returns UP or DOWN                                  │
│  5. Eureka updates registry accordingly                 │
│  6. API Gateway only routes to UP services              │
│                                                         │
│  ┌──────────┐  heartbeat  ┌────────┐  health  ┌──────┐ │
│  │  Eureka  │ ──────────→ │Actuator│ ───────→ │  DB  │ │
│  │  Server  │ ←────────── │/health │ ←─────── │Redis │ │
│  └──────────┘  UP / DOWN  └────────┘  status  │Disk  │ │
│       │                                       └──────┘ │
│       ↓                                                 │
│  Route traffic only to healthy services                 │
└─────────────────────────────────────────────────────────┘
```

##### How Kubernetes + Actuator Work Together

```yaml
# Kubernetes Deployment using Actuator probes
apiVersion: apps/v1
kind: Deployment
spec:
  template:
    spec:
      containers:
        - name: order-service
          image: order-service:latest
          ports:
            - containerPort: 8080

          # Is the app running? If fails → restart container
          livenessProbe:
            httpGet:
              path: /actuator/health/liveness
              port: 8080
            initialDelaySeconds: 30
            periodSeconds: 10

          # Is the app ready for traffic? If fails → stop sending requests
          readinessProbe:
            httpGet:
              path: /actuator/health/readiness
              port: 8080
            initialDelaySeconds: 20
            periodSeconds: 5
```

##### Security - Protect Actuator Endpoints

Actuator exposes sensitive information, so **always secure it in production:**

```java
@Configuration
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeRequests()
                .requestMatchers(EndpointRequest.to("health", "info"))
                    .permitAll()        // Public: health + info
                .anyRequest()
                    .hasRole("ADMIN")   // Protected: everything else
            .and()
            .httpBasic();
    }
}
```

##### Quick Summary

```
Actuator = Your Service's Health Dashboard

Without Actuator:
  "Is the service alive?"     → 🤷 No idea, SSH into server and check

With Actuator:
  "Is the service alive?"     → GET /actuator/health    → {"status":"UP"}
  "How much memory?"          → GET /actuator/metrics    → 256MB used
  "What went wrong?"          → GET /actuator/loggers    → Change to DEBUG
  "What configs are loaded?"  → GET /actuator/env        → All properties
  "What endpoints exist?"     → GET /actuator/mappings   → All routes
```

| Question | Answer |
|----------|--------|
| What is it? | Built-in monitoring module for Spring Boot |
| Why need it? | Monitor health, metrics, configs of running service |
| Who uses it? | Eureka (service discovery), Kubernetes (probes), DevOps teams, Developers |
| Key endpoint? | `/actuator/health` — tells if service is UP or DOWN |
| Security? | Always protect sensitive endpoints in production |

---

#### 6. Load Balancing

Distributes incoming traffic across multiple instances of a service.

```
                    ┌───────────────────┐
                    │  Order Service #1 │
                ┌──→│  (Instance 1)     │
                │   └───────────────────┘
┌────────────┐  │   ┌───────────────────┐
│    Load    │──┼──→│  Order Service #2 │
│  Balancer  │  │   │  (Instance 2)     │
└────────────┘  │   └───────────────────┘
                │   ┌───────────────────┐
                └──→│  Order Service #3 │
                    │  (Instance 3)     │
                    └───────────────────┘
```

**Algorithms:**
- **Round Robin:** Requests go to each server in turn
- **Weighted Round Robin:** More powerful servers get more requests
- **Least Connections:** Route to server with fewest active connections
- **IP Hash:** Same client always goes to same server

**Types:**
- **Server-side:** Nginx, HAProxy, AWS ELB
- **Client-side:** Spring Cloud LoadBalancer, Netflix Ribbon

---

#### 7. Circuit Breaker Pattern

Prevents cascading failures when a service is down. Like an electrical circuit breaker.

```
States:

CLOSED (Normal)          OPEN (Failure)           HALF-OPEN (Testing)
┌─────────────┐         ┌─────────────┐          ┌─────────────┐
│  Requests   │         │  Requests   │          │ Limited     │
│  pass       │──fail──→│  blocked    │──timer──→│ requests    │
│  through    │  count  │  (fast fail)│  expires │ allowed     │
└─────────────┘         └─────────────┘          └──────┬──────┘
                                                   │         │
                                              success      fail
                                                   │         │
                                                   ↓         ↓
                                                CLOSED     OPEN
```

```java
// Using Resilience4j Circuit Breaker
@Service
public class OrderService {

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public PaymentResponse processPayment(String orderId) {
        // Call payment service
        return restTemplate.getForObject(
            "http://payment-service/api/pay/" + orderId,
            PaymentResponse.class
        );
    }

    // Fallback when circuit is OPEN
    public PaymentResponse paymentFallback(String orderId, Exception ex) {
        return new PaymentResponse("PENDING",
            "Payment service unavailable. Will retry later.");
    }
}
```

**Configuration:**
```yaml
resilience4j:
  circuitbreaker:
    instances:
      paymentService:
        failureRateThreshold: 50       # Open if 50% fail
        waitDurationInOpenState: 30s    # Wait before HALF-OPEN
        slidingWindowSize: 10           # Check last 10 calls
        minimumNumberOfCalls: 5         # Min calls before evaluating
```

---

#### 8. Database per Service (Data Management)

Each microservice owns its own database. No direct database sharing.

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ User Service│    │Order Service│    │Payment Svc  │
└──────┬──────┘    └──────┬──────┘    └──────┬──────┘
       │                  │                  │
  ┌────┴─────┐      ┌────┴─────┐      ┌────┴─────┐
  │  MySQL   │      │ MongoDB  │      │PostgreSQL│
  │ (Users)  │      │ (Orders) │      │(Payments)│
  └──────────┘      └──────────┘      └──────────┘
```

**Patterns for cross-service data:**

**Saga Pattern** (Managing distributed transactions):
```
Order Created → Payment Processed → Inventory Updated → Shipping Started
     │                 │                   │                  │
     │            (if fails)          (compensate)       (compensate)
     │                 │                   │                  │
     └─────── Cancel Order ←── Refund ←── Restock ←── Cancel Ship
```

**Types of Saga:**
- **Choreography:** Each service listens for events and acts
- **Orchestration:** A central coordinator controls the flow

```java
// Saga Orchestrator Example
@Service
public class OrderSagaOrchestrator {

    public void createOrder(OrderRequest request) {
        try {
            // Step 1: Create Order
            orderService.create(request);

            // Step 2: Process Payment
            paymentService.charge(request.getPaymentInfo());

            // Step 3: Update Inventory
            inventoryService.reserve(request.getItems());

            // Step 4: Arrange Shipping
            shippingService.schedule(request.getShippingInfo());

        } catch (PaymentException e) {
            orderService.cancel(request.getOrderId());  // Compensate

        } catch (InventoryException e) {
            paymentService.refund(request.getPaymentId());  // Compensate
            orderService.cancel(request.getOrderId());

        } catch (ShippingException e) {
            inventoryService.release(request.getItems());   // Compensate
            paymentService.refund(request.getPaymentId());
            orderService.cancel(request.getOrderId());
        }
    }
}
```

---

#### 9. Centralized Logging & Monitoring

When you have 20+ services, you need centralized tools to track everything.

```
┌────────────┐
│ Service A  │──┐
└────────────┘  │     ┌─────────────┐    ┌──────────────┐
┌────────────┐  ├────→│  Log        │───→│  Dashboard   │
│ Service B  │──┤     │  Aggregator │    │ (Grafana/    │
└────────────┘  │     │  (ELK/Loki) │    │  Kibana)     │
┌────────────┐  │     └─────────────┘    └──────────────┘
│ Service C  │──┘
└────────────┘
```

**ELK Stack:**
- **Elasticsearch:** Stores and indexes logs
- **Logstash:** Collects and processes logs
- **Kibana:** Visualizes logs in dashboards

**Distributed Tracing (track a request across services):**
- **Tools:** Zipkin, Jaeger, Spring Cloud Sleuth
- Each request gets a unique **Trace ID** that follows it across all services

```
Request: GET /api/orders/123
Trace ID: abc-123

→ API Gateway        [abc-123] 0ms
  → Order Service    [abc-123] 5ms
    → User Service   [abc-123] 10ms
    → Payment Service[abc-123] 15ms
    → Inventory Svc  [abc-123] 20ms
  ← Order Service    [abc-123] 25ms (total)
← API Gateway        [abc-123] 30ms (total)
```

---

#### 10. Containerization & Orchestration

**Docker** - Package each service into a container:
```dockerfile
# Dockerfile for Order Service
FROM openjdk:17-jdk-slim
COPY target/order-service.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Kubernetes** - Orchestrate and manage containers:
```
┌───────────────── Kubernetes Cluster ─────────────────┐
│                                                       │
│  ┌─── Node 1 ────┐  ┌─── Node 2 ────┐  ┌── Node 3 ┐│
│  │ [User Svc x2] │  │ [Order Svc x3]│  │[Pay x2]  ││
│  │ [Order Svc x1]│  │ [Payment x1]  │  │[Notif x1]││
│  └────────────────┘  └───────────────┘  └──────────┘│
│                                                       │
│  Load Balancer ←→ Ingress Controller                 │
│  Auto-scaling  ←→ Horizontal Pod Autoscaler          │
│  Self-healing  ←→ Restart failed containers          │
└───────────────────────────────────────────────────────┘
```

**Key Kubernetes Concepts:**
- **Pod:** Smallest deployable unit (contains one or more containers)
- **Service:** Stable network endpoint for a set of Pods
- **Deployment:** Manages replica sets and rolling updates
- **Ingress:** Routes external traffic to services
- **ConfigMap/Secret:** External configuration and secrets

---

#### 11. Microservices Complete Architecture Diagram

```
┌──────────────────────────────────────────────────────────────────┐
│                         CLIENTS                                  │
│              Mobile App    Web App    3rd Party                   │
└──────────────────────┬───────────────────────────────────────────┘
                       │
                ┌──────┴──────┐
                │ API Gateway │  ← Authentication, Rate Limiting,
                │ (Kong/Zuul) │    Routing, Load Balancing
                └──────┬──────┘
                       │
        ┌──────────────┼──────────────────┐
        │              │                  │
   ┌────┴────┐   ┌────┴────┐   ┌────────┴───────┐
   │  User   │   │  Order  │   │   Payment      │
   │ Service │   │ Service │   │   Service       │
   └────┬────┘   └────┬────┘   └────────┬───────┘
        │              │                 │
   ┌────┴────┐   ┌────┴────┐   ┌───────┴────────┐
   │  MySQL  │   │ MongoDB │   │   PostgreSQL   │
   └─────────┘   └─────────┘   └────────────────┘
        │              │                 │
        └──────────────┼─────────────────┘
                       │
              ┌────────┴────────┐
              │  Message Queue  │  ← Kafka / RabbitMQ
              │  (Event Bus)    │
              └────────┬────────┘
                       │
        ┌──────────────┼──────────────┐
        │              │              │
  ┌─────┴─────┐ ┌─────┴─────┐ ┌─────┴─────┐
  │Notification│ │ Inventory │ │  Shipping │
  │  Service   │ │  Service  │ │  Service  │
  └────────────┘ └───────────┘ └───────────┘

  ┌──────────────────────────────────────────┐
  │          Supporting Infrastructure        │
  │  Service Discovery (Eureka/Consul)       │
  │  Config Server (Spring Cloud Config)     │
  │  Circuit Breaker (Resilience4j)          │
  │  Distributed Tracing (Zipkin/Jaeger)     │
  │  Centralized Logging (ELK Stack)         │
  │  Containerization (Docker + Kubernetes)  │
  └──────────────────────────────────────────┘
```

---

#### 12. Key Microservices Interview Summary

| Component | Purpose | Tools |
|-----------|---------|-------|
| API Gateway | Single entry point, routing, auth | Kong, Spring Cloud Gateway, Zuul |
| Service Discovery | Find services dynamically | Eureka, Consul, Zookeeper |
| Load Balancer | Distribute traffic | Nginx, HAProxy, Ribbon |
| Circuit Breaker | Prevent cascading failures | Resilience4j, Hystrix |
| Message Queue | Async communication | Kafka, RabbitMQ, SQS |
| Config Server | Centralized configuration | Spring Cloud Config, Consul |
| Distributed Tracing | Track requests across services | Zipkin, Jaeger, Sleuth |
| Logging | Centralized log management | ELK Stack, Loki, Splunk |
| Containerization | Package services | Docker |
| Orchestration | Manage containers at scale | Kubernetes, Docker Swarm |
| Database per Service | Data isolation | MySQL, MongoDB, PostgreSQL |
| Saga Pattern | Distributed transactions | Orchestration / Choreography |

---

### Data Structures & Algorithms Questions

**Q12: Explain the given code snippet step by step. What is it trying to solve?**
> *Context-dependent - analyze the specific code provided in the interview*

**Tips for answering:**
1. Read the code carefully, identify input/output
2. Trace through with a simple example
3. Identify the algorithm/pattern used
4. Explain time and space complexity
5. Discuss edge cases

---

**Q13: How can you optimize it?**
> *Context-dependent - based on the specific code*

**Common Optimization Techniques:**
- **Time Complexity:** Use better data structures (HashMap, Set, Heap)
- **Space Complexity:** Use in-place algorithms, sliding window
- **Avoid Redundant Calculations:** Memoization, Dynamic Programming
- **Early Termination:** Break loops when answer is found
- **Two Pointers:** For sorted arrays or linked lists
- **Binary Search:** For sorted data, O(log n) vs O(n)

---

**Q14: How would you find the maximum valid parentheses substring? What is your approach and how would you optimize it?**

**Answer:**

**Problem:** Given a string containing just '(' and ')', find the length of the longest valid parentheses substring.

**Approach 1: Brute Force - O(n^3)**
```java
// Check every possible substring - NOT RECOMMENDED
public int longestValidParentheses(String s) {
    int maxLen = 0;
    for (int i = 0; i < s.length(); i++) {
        for (int j = i + 2; j <= s.length(); j += 2) {
            if (isValid(s.substring(i, j))) {
                maxLen = Math.max(maxLen, j - i);
            }
        }
    }
    return maxLen;
}
```

**Approach 2: Using Stack - O(n) Time, O(n) Space**
```java
public int longestValidParentheses(String s) {
    int maxLen = 0;
    Stack<Integer> stack = new Stack<>();
    stack.push(-1);  // Base for calculating length

    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);  // Push index of '('
        } else {
            stack.pop();    // Pop for ')'
            if (stack.isEmpty()) {
                stack.push(i);  // New base index
            } else {
                maxLen = Math.max(maxLen, i - stack.peek());
            }
        }
    }
    return maxLen;
}
```

**Approach 3: Dynamic Programming - O(n) Time, O(n) Space**
```java
public int longestValidParentheses(String s) {
    int maxLen = 0;
    int[] dp = new int[s.length()];

    for (int i = 1; i < s.length(); i++) {
        if (s.charAt(i) == ')') {
            if (s.charAt(i - 1) == '(') {
                // Case: ...()
                dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
            } else if (i - dp[i - 1] > 0 &&
                       s.charAt(i - dp[i - 1] - 1) == '(') {
                // Case: ...))
                dp[i] = dp[i - 1] + 2 +
                        (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2] : 0);
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
    }
    return maxLen;
}
```

**Approach 4: Two Pointers (Most Optimized) - O(n) Time, O(1) Space**
```java
public int longestValidParentheses(String s) {
    int left = 0, right = 0, maxLen = 0;

    // Left to right scan
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') left++;
        else right++;

        if (left == right) {
            maxLen = Math.max(maxLen, 2 * right);
        } else if (right > left) {
            left = right = 0;
        }
    }

    left = right = 0;

    // Right to left scan
    for (int i = s.length() - 1; i >= 0; i--) {
        if (s.charAt(i) == '(') left++;
        else right++;

        if (left == right) {
            maxLen = Math.max(maxLen, 2 * left);
        } else if (left > right) {
            left = right = 0;
        }
    }

    return maxLen;
}
```

**Complexity Comparison:**

| Approach | Time | Space |
|----------|------|-------|
| Brute Force | O(n^3) | O(n) |
| Stack | O(n) | O(n) |
| Dynamic Programming | O(n) | O(n) |
| Two Pointers | O(n) | O(1) |

---

### Team & Role Questions

**Q15: What was your role in the competition team?**
> *Personal question - prepare your own answer based on your experience*

**Q16: Did you work only on the backend?**
> *Personal question - prepare your own answer based on your experience*

---

### OOP & Java Concepts Questions

**Q17: In your Java project, explain the OOP principles you used.**

**Answer:**

The four main OOP principles are:

**1. Encapsulation:**
- Bundling data (fields) and methods that operate on data within a class
- Hiding internal state using access modifiers (private, protected)
```java
public class BankAccount {
    private double balance;  // Hidden from outside

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

**2. Inheritance:**
- Creating new classes based on existing classes
- Promotes code reuse and establishes IS-A relationship
```java
public class Animal {
    public void eat() { System.out.println("Eating"); }
}

public class Dog extends Animal {
    public void bark() { System.out.println("Barking"); }
}
```

**3. Polymorphism:**
- Objects can take many forms
- Method overloading (compile-time) and overriding (runtime)
```java
Animal animal = new Dog();  // Dog IS-A Animal
animal.eat();  // Runtime polymorphism
```

**4. Abstraction:**
- Hiding complex implementation details
- Showing only essential features
```java
public abstract class Shape {
    abstract double area();  // What to do, not how
}
```

---

**Q18: What is abstraction?**

**Answer:**
Abstraction is the process of **hiding implementation details** and showing only the **essential features** to the user. It focuses on "what" an object does rather than "how" it does it.

**Key Points:**
- Reduces complexity by hiding unnecessary details
- Achieved through **abstract classes** and **interfaces**
- Helps in managing complexity of large systems
- Provides a contract/blueprint for subclasses

**Example:**
```java
// Abstraction - User only knows WHAT methods do, not HOW
public abstract class Vehicle {
    abstract void start();    // What to do
    abstract void stop();     // Implementation hidden
}

public class Car extends Vehicle {
    @Override
    void start() {
        // Complex implementation hidden
        checkBattery();
        initializeEngine();
        injectFuel();
        igniteSpark();
        System.out.println("Car started");
    }

    @Override
    void stop() {
        cutFuelSupply();
        System.out.println("Car stopped");
    }

    // Implementation details hidden from user
    private void checkBattery() { /* ... */ }
    private void initializeEngine() { /* ... */ }
    private void injectFuel() { /* ... */ }
    private void igniteSpark() { /* ... */ }
    private void cutFuelSupply() { /* ... */ }
}
```

**Real-world analogy:** When you drive a car, you use the steering wheel, accelerator, and brakes. You don't need to know how the engine combustion works internally.

---

**Q19: Can you give a real-life example of an interface?**

**Answer:**

**1. Power Socket (Electrical Interface):**
```java
interface PowerSocket {
    void plugIn();
    void unplug();
    int getVoltage();
}

// Different devices implement the same interface
class Laptop implements PowerSocket { /* ... */ }
class Phone implements PowerSocket { /* ... */ }
class Refrigerator implements PowerSocket { /* ... */ }
```
Any device with a compatible plug can use the socket - the socket doesn't care about the device's internal workings.

**2. USB Port:**
```java
interface USB {
    void connect();
    void transferData();
    void disconnect();
}

class Mouse implements USB { /* ... */ }
class Keyboard implements USB { /* ... */ }
class PenDrive implements USB { /* ... */ }
```

**3. Payment Methods:**
```java
interface Payment {
    boolean pay(double amount);
    void refund(double amount);
}

class CreditCard implements Payment { /* ... */ }
class PayPal implements Payment { /* ... */ }
class UPI implements Payment { /* ... */ }
class Cash implements Payment { /* ... */ }
```

**4. Remote Control:**
```java
interface RemoteControl {
    void turnOn();
    void turnOff();
    void changeChannel(int channel);
}

class TVRemote implements RemoteControl { /* ... */ }
class ACRemote implements RemoteControl { /* ... */ }
```

---

**Q20: How did you use abstraction and interfaces in your project?**
> *Personal question - prepare your own answer based on your project*

---

**Q21: Can the interface be replaced with an abstract class? If yes, how?**

**Answer:**

**Yes, in many cases an interface can be replaced with an abstract class, but there are trade-offs:**

**Converting Interface to Abstract Class:**

```java
// Original Interface
interface Payment {
    void pay(double amount);
    void refund(double amount);
}

// Converted to Abstract Class
abstract class Payment {
    abstract void pay(double amount);
    abstract void refund(double amount);
}
```

**Key Differences:**

| Feature | Interface | Abstract Class |
|---------|-----------|----------------|
| Multiple Inheritance | Yes (class can implement multiple interfaces) | No (class can extend only one) |
| Variables | Only public static final (constants) | Can have instance variables |
| Constructors | No | Yes |
| Methods | All abstract (before Java 8) | Can have both abstract and concrete |
| Access Modifiers | Methods are public by default | Can have any access modifier |
| When to use | Define a contract/capability | Share code among related classes |

**When Interface CANNOT be replaced:**
```java
// Multiple interfaces - CANNOT be done with abstract classes
interface Flyable { void fly(); }
interface Swimmable { void swim(); }

class Duck implements Flyable, Swimmable {  // Works!
    public void fly() { /* ... */ }
    public void swim() { /* ... */ }
}

// This is NOT possible with abstract classes:
// class Duck extends FlyableClass, SwimmableClass { }  // ERROR!
```

**When Abstract Class is better:**
```java
abstract class Animal {
    protected String name;  // Shared state

    public Animal(String name) {  // Constructor
        this.name = name;
    }

    public void sleep() {  // Shared implementation
        System.out.println(name + " is sleeping");
    }

    abstract void makeSound();  // Must be implemented
}

class Dog extends Animal {
    public Dog(String name) { super(name); }

    @Override
    void makeSound() { System.out.println("Bark!"); }
}
```

**Best Practice:**
- Use **Interface** when you want to define a contract that unrelated classes can implement
- Use **Abstract Class** when you want to share code among closely related classes

---

### Project Management & Collaboration Questions

**Q22: How did collaboration work in your project?**
> *Personal question - prepare your own answer based on your experience*

**Q23: Was it done in an Agile way?**
> *Personal question - prepare your own answer based on your experience*

---

**Q24: What is the opposite of the Agile model?**

**Answer:** The **Waterfall Model**

The Waterfall model is a linear, sequential approach where each phase must be completed before the next begins. There's no going back to previous phases.

```
Waterfall Model Phases:
┌─────────────────┐
│  Requirements   │
└────────┬────────┘
         ↓
┌─────────────────┐
│     Design      │
└────────┬────────┘
         ↓
┌─────────────────┐
│ Implementation  │
└────────┬────────┘
         ↓
┌─────────────────┐
│    Testing      │
└────────┬────────┘
         ↓
┌─────────────────┐
│   Deployment    │
└────────┬────────┘
         ↓
┌─────────────────┐
│  Maintenance    │
└─────────────────┘
```

---

**Q25: What is the difference between Agile and Waterfall model?**

**Answer:**

| Aspect | Agile | Waterfall |
|--------|-------|-----------|
| **Approach** | Iterative & Incremental | Linear & Sequential |
| **Flexibility** | Highly flexible, welcomes changes | Rigid, changes are difficult |
| **Customer Involvement** | Continuous throughout | Only at beginning and end |
| **Delivery** | Frequent small releases (sprints) | One final delivery at the end |
| **Testing** | Continuous testing | Testing phase after development |
| **Documentation** | Minimal, working software preferred | Extensive documentation |
| **Risk** | Lower (issues caught early) | Higher (issues found late) |
| **Team Structure** | Cross-functional, self-organizing | Specialized teams for each phase |
| **Best For** | Dynamic requirements, innovation | Fixed requirements, compliance |

**Agile:**
```
Sprint 1 → Sprint 2 → Sprint 3 → Sprint 4
[Plan→Build→Test→Review] → [Plan→Build→Test→Review] → ...
     ↑____________Feedback____________↓
```

**Waterfall:**
```
Requirements → Design → Build → Test → Deploy
     ↓           ↓        ↓       ↓       ↓
   (done)     (done)   (done)  (done)  (done)
   No going back to previous phases
```

---

### AI/ML Questions

**Q26: What is the difference between AI and ML?**

**Answer:**

**Artificial Intelligence (AI):**
- Broad field of making machines that can perform tasks requiring human intelligence
- Goal: Create systems that can reason, learn, and act autonomously
- Includes: ML, NLP, Computer Vision, Robotics, Expert Systems

**Machine Learning (ML):**
- Subset of AI that enables machines to learn from data without explicit programming
- Goal: Algorithms that improve through experience
- Types: Supervised, Unsupervised, Reinforcement Learning

```
┌─────────────────────────────────────────┐
│         Artificial Intelligence         │
│  ┌───────────────────────────────────┐  │
│  │       Machine Learning            │  │
│  │  ┌─────────────────────────────┐  │  │
│  │  │      Deep Learning          │  │  │
│  │  └─────────────────────────────┘  │  │
│  └───────────────────────────────────┘  │
└─────────────────────────────────────────┘
```

| Aspect | AI | ML |
|--------|----|----|
| **Definition** | Machines mimicking human intelligence | Machines learning from data |
| **Scope** | Broad (includes ML) | Subset of AI |
| **Goal** | Simulate human thinking | Learn patterns from data |
| **Approach** | Rule-based or learning-based | Data-driven learning |
| **Examples** | Siri, Chess engines, Self-driving cars | Spam filters, Recommendations, Fraud detection |
| **Data Dependency** | May or may not need data | Requires large amounts of data |

**Simple Analogy:**
- **AI** = The entire concept of a smart robot
- **ML** = Teaching the robot by showing examples instead of programming every rule

---

### Testing Questions

**Q27: What testing methods do you know?**

**Answer:**

**By Level:**
1. **Unit Testing** - Testing individual components/methods
2. **Integration Testing** - Testing combined modules
3. **System Testing** - Testing complete system
4. **Acceptance Testing** - Testing against user requirements

**By Approach:**
1. **Black-box Testing** - Test without knowing internal code
2. **White-box Testing** - Test with knowledge of internal code
3. **Gray-box Testing** - Partial knowledge of internals

**By Type:**
1. **Functional Testing** - Does it work correctly?
2. **Performance Testing** - Is it fast enough?
3. **Security Testing** - Is it secure?
4. **Usability Testing** - Is it user-friendly?
5. **Regression Testing** - Do old features still work?
6. **Smoke Testing** - Basic sanity check
7. **Load Testing** - Performance under load
8. **Stress Testing** - Performance at breaking point

```
Testing Pyramid:
        /\
       /  \     E2E Tests (Few)
      /────\
     /      \   Integration Tests (Some)
    /────────\
   /          \ Unit Tests (Many)
  /────────────\
```

---

**Q28: What is black-box and white-box testing?**

**Answer:**

**Black-box Testing:**
- Tester has **NO knowledge** of internal code structure
- Focus on **input/output** and functionality
- Tests **what** the system does, not **how**

```
┌─────────────────────┐
│    BLACK BOX        │
│  ┌───────────────┐  │
│  │   ?????????   │  │  ← Internal code unknown
│  │   ?????????   │  │
│  └───────────────┘  │
└─────────────────────┘
Input → [BOX] → Output (verify output matches expected)
```

**Techniques:**
- Equivalence Partitioning
- Boundary Value Analysis
- Decision Table Testing
- State Transition Testing

**White-box Testing:**
- Tester has **FULL knowledge** of internal code
- Focus on **code paths, logic, and structure**
- Tests **how** the system works internally

```
┌─────────────────────┐
│    WHITE BOX        │
│  ┌───────────────┐  │
│  │ if (x > 0)    │  │  ← Internal code visible
│  │   return y;   │  │
│  └───────────────┘  │
└─────────────────────┘
Test all code paths, branches, conditions
```

**Techniques:**
- Statement Coverage
- Branch Coverage
- Path Coverage
- Condition Coverage

| Aspect | Black-box | White-box |
|--------|-----------|-----------|
| Knowledge | No code knowledge | Full code knowledge |
| Focus | Functionality | Code structure |
| Performed by | Testers | Developers |
| Also called | Behavioral testing | Structural testing |
| Finds | Missing features, UI bugs | Logic errors, security issues |

---

**Q29: What is unit testing, and who performs it - developers or testers?**

**Answer:**

**Unit Testing:**
Testing the **smallest testable parts** (units) of an application in isolation. A unit is typically a single method or function.

**Key Characteristics:**
- Tests one piece of functionality at a time
- Fast to execute (milliseconds)
- Independent of external systems (database, network)
- Uses mocks/stubs for dependencies

```java
// Example: JUnit Test
public class CalculatorTest {

    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    public void testDivide() {
        Calculator calc = new Calculator();
        assertEquals(2, calc.divide(10, 5));
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        Calculator calc = new Calculator();
        calc.divide(10, 0);
    }
}
```

**Who Performs It?**

**Developers** primarily perform unit testing because:
1. They write the code and understand it best
2. Unit tests are written alongside or before code (TDD)
3. Tests are in the same codebase
4. Immediate feedback during development
5. Part of the development workflow

**Testing Responsibilities:**

| Test Type | Primary Responsibility |
|-----------|----------------------|
| Unit Testing | **Developers** |
| Integration Testing | Developers + QA |
| System Testing | QA/Testers |
| Acceptance Testing | QA + Business Users |

---

### Database Questions

**Q30: Which databases have you used?**
> *Personal question - prepare your own answer based on your experience*

---

**Q31: Why did you choose MongoDB or MySQL in your project?**

**Answer:**

**Choose MySQL (Relational/SQL) when:**
- Data has clear relationships (foreign keys)
- Need ACID transactions (banking, e-commerce)
- Complex queries with JOINs
- Data structure is fixed and well-defined
- Need strong consistency

**Choose MongoDB (NoSQL/Document) when:**
- Flexible/evolving schema
- Hierarchical or nested data
- High write throughput needed
- Horizontal scaling required
- Rapid prototyping

| Aspect | MySQL | MongoDB |
|--------|-------|---------|
| Data Model | Tables with rows | Collections with documents |
| Schema | Fixed schema | Flexible schema |
| Query Language | SQL | MongoDB Query Language |
| Scaling | Vertical (primarily) | Horizontal (sharding) |
| Transactions | Strong ACID | Multi-document ACID (v4.0+) |
| Relationships | JOINs | Embedded documents or references |
| Best For | Complex relationships, transactions | Flexible data, high scalability |

```
MySQL:                          MongoDB:
┌──────────────────┐            ┌──────────────────────────┐
│ users            │            │ users collection         │
├──────────────────┤            │ {                        │
│ id | name | age  │            │   _id: ObjectId,         │
├──────────────────┤            │   name: "John",          │
│ 1  | John | 25   │            │   age: 25,               │
│ 2  | Jane | 30   │            │   address: {             │
└──────────────────┘            │     city: "NYC",         │
┌──────────────────┐            │     zip: "10001"         │
│ addresses        │            │   }                      │
├──────────────────┤            │ }                        │
│ id|user_id|city  │            └──────────────────────────┘
└──────────────────┘
```

---

**Q32: What did you use Firebase for?**

**Answer:**

**Firebase Services commonly used:**

1. **Authentication:** User sign-up/login (Google, Facebook, Email)
2. **Realtime Database:** Live data synchronization
3. **Cloud Firestore:** Scalable NoSQL database
4. **Cloud Storage:** File uploads (images, videos)
5. **Cloud Messaging (FCM):** Push notifications
6. **Hosting:** Static website hosting
7. **Analytics:** User behavior tracking

```
┌─────────────────────────────────────────┐
│              Firebase                    │
├─────────────┬─────────────┬─────────────┤
│    Auth     │  Database   │   Storage   │
├─────────────┼─────────────┼─────────────┤
│   FCM       │  Analytics  │   Hosting   │
└─────────────┴─────────────┴─────────────┘
```

> *Add your specific use case from your project*

---

**Q33: Where did you use real-time databases?**

**Answer:**

**Real-time Database Use Cases:**
- **Chat Applications:** Messages appear instantly
- **Live Notifications:** Real-time alerts and updates
- **Collaborative Tools:** Google Docs-like editing
- **Live Dashboards:** Stock prices, sports scores
- **Gaming:** Multiplayer game state synchronization
- **Location Tracking:** Live GPS updates

**How it works:**
```
Traditional Database:           Real-time Database:
Client → Request → Server       Client ←→ WebSocket ←→ Server
Client ← Response ← Server            Persistent Connection
(Poll for updates)              (Push updates instantly)
```

> *Add your specific use case from your project*

---

### Project-Specific Questions

**Q34: Can you explain your projects? (Cart-O, WealthBridge, etc.)**
> *Personal question - prepare your own answer based on your projects*

**Tips for answering:**
1. Brief overview (1-2 sentences)
2. Tech stack used
3. Your role and responsibilities
4. Key features you implemented
5. Challenges faced and how you solved them

---

**Q35: What functionalities did you implement in each project?**
> *Personal question - prepare your own answer based on your projects*

**Structure your answer:**
- Feature name
- Why it was needed
- How you implemented it
- Technologies/tools used
- Impact/result

---

## Java 8 Interfaces — Default and Static Methods

**Q36: What are default and static methods in Java 8 interfaces?**

**Answer:**

Before Java 8, interfaces could only have **abstract methods**. Java 8 introduced **default** and **static** methods to allow adding behavior to interfaces without breaking existing implementations.

**Default Methods:**

```java
public interface Vehicle {

    // abstract method (must be implemented)
    void start();

    // default method (has a body, optional to override)
    default void honk() {
        System.out.println("Beep beep!");
    }
}

public class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car starting...");
    }
    // honk() is inherited — no need to override
}

// Usage
Car car = new Car();
car.start();  // "Car starting..."
car.honk();   // "Beep beep!"
```

**Static Methods:**

```java
public interface MathUtils {

    // static method — belongs to the interface, not instances
    static int add(int a, int b) {
        return a + b;
    }

    static int multiply(int a, int b) {
        return a * b;
    }
}

// Usage — called on the interface directly
int sum = MathUtils.add(5, 3);       // 8
int product = MathUtils.multiply(5, 3); // 15
```

| Feature | Default Method | Static Method |
|---------|---------------|---------------|
| Keyword | `default` | `static` |
| Can be overridden | Yes | No |
| Accessed via | Instance of implementing class | Interface name directly |
| Inherited by subinterfaces | Yes | No |
| Purpose | Add new behavior to existing interfaces | Utility/helper methods |

---

**Q37: What are the advantages of default methods in interfaces?**

**Answer:**

**1. Backward Compatibility (Primary Reason)**
```java
// Before Java 8: Adding a new method broke ALL implementations
public interface Collection<E> {
    // If Java added stream() as abstract, every existing
    // class implementing Collection would break!

    // Solution: add as default method
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
```

**2. Multiple Inheritance of Behavior**
```java
public interface Loggable {
    default void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

public interface Auditable {
    default void audit(String action) {
        System.out.println("[AUDIT] " + action);
    }
}

// A class can get behavior from MULTIPLE interfaces
public class OrderService implements Loggable, Auditable {
    public void placeOrder() {
        log("Placing order...");
        audit("ORDER_PLACED");
    }
}
```

**3. Optional Methods**
```java
public interface EventListener {
    void onClick();

    // Implementing classes can choose to override or not
    default void onHover() { }
    default void onScroll() { }
}
```

**4. Interface Evolution**
- Libraries can add new methods to interfaces in newer versions without breaking client code

---

**Q38: What happens when a class implements two interfaces with the same default method? (Diamond Problem)**

**Answer:**

The compiler raises an error, and the class **must override** the conflicting method to resolve the ambiguity.

```java
public interface InterfaceA {
    default void greet() {
        System.out.println("Hello from A");
    }
}

public interface InterfaceB {
    default void greet() {
        System.out.println("Hello from B");
    }
}

// Compiler Error: class MyClass inherits unrelated defaults for greet()
// MUST override to resolve
public class MyClass implements InterfaceA, InterfaceB {

    @Override
    public void greet() {
        // Option 1: Provide your own implementation
        System.out.println("Hello from MyClass");

        // Option 2: Explicitly call one interface's default
        // InterfaceA.super.greet();

        // Option 3: Call both
        // InterfaceA.super.greet();
        // InterfaceB.super.greet();
    }
}
```

**Resolution Rules:**
1. **Class always wins** — A method in the class or superclass takes priority over any default method
2. **Sub-interface wins** — If `InterfaceB extends InterfaceA`, then `InterfaceB`'s default wins
3. **Must override** — If no rule applies (unrelated interfaces), the class must explicitly override

```java
// Rule 1: Class wins
public class Parent {
    public void greet() {
        System.out.println("Hello from Parent");
    }
}

public class Child extends Parent implements InterfaceA {
    // No conflict — Parent.greet() wins over InterfaceA.greet()
}

// Rule 2: Sub-interface wins
public interface InterfaceC extends InterfaceA {
    default void greet() {
        System.out.println("Hello from C");
    }
}

public class MyClass implements InterfaceA, InterfaceC {
    // No conflict — InterfaceC.greet() wins (more specific)
}
```

---

## Java Version Features

**Q39: What are the key features introduced in Java 8?**

**Answer:**

Java 8 was a **landmark release** that brought functional programming capabilities to Java.

**1. Lambda Expressions**
```java
// Before Java 8
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello");
    }
};

// Java 8 Lambda
Runnable r = () -> System.out.println("Hello");
```

**2. Stream API**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

List<String> result = names.stream()
    .filter(name -> name.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());
// [ALICE, CHARLIE, DAVID]
```

**3. Optional**
```java
Optional<User> user = userRepository.findById(1L);

String name = user
    .map(User::getName)
    .orElse("Unknown");
```

**4. Functional Interfaces**
```java
@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}

Converter<String, Integer> converter = Integer::valueOf;
Integer result = converter.convert("123");
```

**Built-in Functional Interfaces:**

| Interface | Method | Input → Output |
|-----------|--------|----------------|
| `Predicate<T>` | `test(T)` | `T → boolean` |
| `Function<T,R>` | `apply(T)` | `T → R` |
| `Consumer<T>` | `accept(T)` | `T → void` |
| `Supplier<T>` | `get()` | `() → T` |

**5. Method References**
```java
List<String> names = Arrays.asList("Alice", "Bob");
names.forEach(System.out::println);
```

**6. Default and Static Methods in Interfaces** (covered in Q36)

**7. New Date/Time API (java.time)**
```java
LocalDate today = LocalDate.now();
LocalDateTime now = LocalDateTime.now();
ZonedDateTime zoned = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
Duration duration = Duration.between(startTime, endTime);
```

**8. CompletableFuture**
```java
CompletableFuture.supplyAsync(() -> fetchDataFromAPI())
    .thenApply(data -> processData(data))
    .thenAccept(result -> saveResult(result));
```

---

**Q40: What are the key features introduced in Java 17?**

**Answer:**

Java 17 is an **LTS (Long-Term Support)** release with important language enhancements.

**1. Sealed Classes**
```java
// Restrict which classes can extend/implement
public sealed class Shape permits Circle, Rectangle, Triangle {
    abstract double area();
}

public final class Circle extends Shape {
    private double radius;
    double area() { return Math.PI * radius * radius; }
}

public final class Rectangle extends Shape {
    private double width, height;
    double area() { return width * height; }
}

// non-permitted class — COMPILE ERROR
// public class Pentagon extends Shape { }
```

**2. Records**
```java
// Immutable data carrier — replaces boilerplate POJOs
public record UserDTO(String name, String email, int age) { }

// Automatically generates:
// - constructor
// - getters (name(), email(), age())
// - equals(), hashCode(), toString()

UserDTO user = new UserDTO("John", "john@email.com", 30);
System.out.println(user.name());  // "John"
```

**3. Pattern Matching for instanceof**
```java
// Before Java 17
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}

// Java 17
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

**4. Text Blocks**
```java
// Before
String json = "{\n" +
    "  \"name\": \"John\",\n" +
    "  \"age\": 30\n" +
    "}";

// Java 17
String json = """
    {
      "name": "John",
      "age": 30
    }
    """;
```

**5. Switch Expressions**
```java
// Classic switch (statement)
String result;
switch (day) {
    case MONDAY: result = "Start"; break;
    case FRIDAY: result = "End"; break;
    default: result = "Mid";
}

// Java 17 switch (expression)
String result = switch (day) {
    case MONDAY -> "Start";
    case FRIDAY -> "End";
    default -> "Mid";
};
```

**6. Helpful NullPointerExceptions**
```java
// Before: NullPointerException (no details)
// Java 17: "Cannot invoke String.length() because the return
//           value of User.getName() is null"
```

**7. Strong Encapsulation of JDK Internals**
- `sun.misc.Unsafe` and other internal APIs are no longer accessible by default

---

**Q41: Java 8 vs Java 17 — Comparison**

**Answer:**

| Feature | Java 8 (2014) | Java 17 (2021) |
|---------|---------------|----------------|
| **LTS** | Yes | Yes |
| **Lambda & Streams** | Introduced | Enhanced |
| **Records** | Not available | `record Point(int x, int y) {}` |
| **Sealed Classes** | Not available | `sealed class Shape permits ...` |
| **Text Blocks** | Not available | `"""multi-line"""` |
| **Pattern Matching** | Not available | `if (obj instanceof String s)` |
| **Switch** | Statement only | Expression with `->` syntax |
| **var keyword** | Not available | Local variable type inference |
| **NullPointerException** | Generic message | Detailed message with context |
| **Date/Time API** | `java.time` introduced | Same, mature |
| **Optional** | Introduced | Enhanced with more methods |
| **HTTP Client** | Not built-in | `java.net.http.HttpClient` |
| **GC** | Parallel GC default | G1 GC default, ZGC available |
| **Modules (JPMS)** | Not available | Full module system |
| **JDK Internals** | Accessible | Strongly encapsulated |

**Migration Considerations (Java 8 → 17):**
1. **Removed APIs** — `javax.xml.bind` (JAXB), `javax.activation` moved to Jakarta
2. **Module System** — May need `--add-opens` flags for reflection-heavy libraries
3. **GC Changes** — G1 is default; test performance with your workload
4. **Strong Encapsulation** — Libraries using `sun.misc.*` may break
5. **Spring Boot** — Spring Boot 3.x requires Java 17+

```java
// Quick example showing Java 17 improvements together
public sealed interface Shape permits Circle, Rectangle {}

public record Circle(double radius) implements Shape {
    public double area() { return Math.PI * radius * radius; }
}

public record Rectangle(double width, double height) implements Shape {
    public double area() { return width * height; }
}

// Pattern matching + switch expression
public static String describe(Shape shape) {
    return switch (shape) {
        case Circle c    -> "Circle with radius " + c.radius();
        case Rectangle r -> "Rectangle " + r.width() + "x" + r.height();
    };
}
```

---

## OOP Fundamentals

**Q42: What is a Class and what is an Object in Java?**

**Answer:**

**Class** — A **blueprint/template** that defines the structure (fields) and behavior (methods) of objects. It does not occupy memory by itself.

**Object** — A **real instance** created from a class. It occupies memory and holds actual values.

```
┌─────────────────────────────┐
│        Class: Car           │  ← Blueprint (no memory)
│  ─────────────────────────  │
│  Fields:  brand, speed      │
│  Methods: start(), stop()   │
└─────────────────────────────┘
        │ new Car()
        ▼
┌──────────────────┐   ┌──────────────────┐
│  Object: myCar   │   │  Object: yourCar │  ← Instances (in memory)
│  brand = "Toyota" │   │  brand = "Honda" │
│  speed = 120     │   │  speed = 100     │
└──────────────────┘   └──────────────────┘
```

```java
// Class — the blueprint
class Car {
    // Fields (state)
    String brand;
    int speed;

    // Constructor
    Car(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    // Methods (behavior)
    void start() {
        System.out.println(brand + " is starting...");
    }

    void stop() {
        System.out.println(brand + " has stopped.");
    }
}

// Objects — real instances
public class Main {
    public static void main(String[] args) {
        Car myCar = new Car("Toyota", 120);    // Object 1
        Car yourCar = new Car("Honda", 100);   // Object 2

        myCar.start();    // "Toyota is starting..."
        yourCar.start();  // "Honda is starting..."

        System.out.println(myCar == yourCar);  // false — different objects
    }
}
```

| Aspect | Class | Object |
|--------|-------|--------|
| **What** | Blueprint / Template | Instance of a class |
| **Memory** | No memory allocated | Memory allocated on heap |
| **Creation** | Defined using `class` keyword | Created using `new` keyword |
| **Count** | Defined once | Many objects from one class |
| **Contains** | Field declarations, method definitions | Actual field values |
| **Example** | `class Car { }` | `Car myCar = new Car();` |

**What happens when you write `Car myCar = new Car();`?**

```
Stack Memory          Heap Memory
┌──────────┐         ┌───────────────────┐
│  myCar ──┼────────>│   Car Object      │
│ (reference)│        │  brand = "Toyota" │
└──────────┘         │  speed = 120      │
                     └───────────────────┘
```

1. `Car myCar` — Creates a reference variable `myCar` on the **stack**
2. `new Car()` — Creates the actual object on the **heap** and calls the constructor
3. `=` — Links the reference to the object

---

**Q43: What is Constructor Overloading? How is it different from Method Overloading?**

**Answer:**

**Constructor Overloading** — Defining **multiple constructors** in the same class with different parameter lists. Allows creating objects in different ways.

```java
class Employee {
    String name;
    int age;
    String department;

    // Constructor 1: No arguments (default values)
    Employee() {
        this.name = "Unknown";
        this.age = 0;
        this.department = "Unassigned";
    }

    // Constructor 2: Name only
    Employee(String name) {
        this.name = name;
        this.age = 0;
        this.department = "Unassigned";
    }

    // Constructor 3: Name and age
    Employee(String name, int age) {
        this.name = name;
        this.age = age;
        this.department = "Unassigned";
    }

    // Constructor 4: All fields
    Employee(String name, int age, String department) {
        this.name = name;
        this.age = age;
        this.department = department;
    }

    void display() {
        System.out.println(name + " | " + age + " | " + department);
    }
}

// Usage — creating objects in different ways
Employee e1 = new Employee();                          // Unknown | 0 | Unassigned
Employee e2 = new Employee("Alice");                   // Alice | 0 | Unassigned
Employee e3 = new Employee("Bob", 25);                 // Bob | 25 | Unassigned
Employee e4 = new Employee("Charlie", 30, "IT");       // Charlie | 30 | IT
```

**Constructor Chaining with `this()`:**
```java
class Employee {
    String name;
    int age;
    String department;

    Employee() {
        this("Unknown", 0, "Unassigned");  // calls Constructor 3
    }

    Employee(String name) {
        this(name, 0, "Unassigned");       // calls Constructor 3
    }

    Employee(String name, int age, String department) {
        this.name = name;
        this.age = age;
        this.department = department;
    }
}
```

**Difference: Constructor Overloading vs Method Overloading**

| Aspect | Constructor Overloading | Method Overloading |
|--------|------------------------|--------------------|
| **What** | Multiple constructors, different params | Multiple methods (same name), different params |
| **Purpose** | Different ways to create an object | Different ways to perform an action |
| **Name** | Must be same as class name | Must be same method name |
| **Return type** | No return type (not even void) | Can have different return types |
| **Called when** | Object creation (`new`) | Explicitly called on object |
| **`this()` chaining** | Yes — one constructor calls another | No — methods don't chain with `this()` |
| **Inherited** | No — constructors are NOT inherited | Yes — methods are inherited |

```java
class Calculator {
    // CONSTRUCTOR Overloading — different ways to create
    Calculator() { }
    Calculator(int initialValue) { }

    // METHOD Overloading — different ways to add
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; }
    int add(int a, int b, int c) { return a + b + c; }
}
```

---

**Q44: What is Association, Aggregation, and Composition in Java?**

**Answer:**

These describe **relationships between classes** — how objects are connected to each other.

```
Association (general relationship)
├── Aggregation  (HAS-A, weak — parts can exist independently)
└── Composition  (HAS-A, strong — parts cannot exist without the whole)
```

---

**1. Association — "uses" or "knows about"**

A general relationship where two classes are connected. Neither owns the other.

```java
// Teacher and Student — both exist independently
class Teacher {
    String name;
    Teacher(String name) { this.name = name; }
}

class Student {
    String name;
    Teacher teacher;  // Association — Student knows about a Teacher

    Student(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }
}

// Both exist independently
Teacher t = new Teacher("Mr. Smith");
Student s = new Student("Alice", t);
// If we delete student, teacher still exists
```

---

**2. Aggregation — "HAS-A" (Weak relationship)**

A special form of association where one class **has** another, but the contained object can **exist independently**.

```java
// Department HAS Employees, but Employees can exist without Department
class Employee {
    String name;
    Employee(String name) { this.name = name; }
}

class Department {
    String deptName;
    List<Employee> employees;  // Aggregation — Department HAS Employees

    Department(String deptName, List<Employee> employees) {
        this.employees = employees;  // Employees passed from outside
    }
}

// Usage
Employee e1 = new Employee("Alice");
Employee e2 = new Employee("Bob");

List<Employee> team = Arrays.asList(e1, e2);
Department dept = new Department("Engineering", team);

// If Department is destroyed, Alice and Bob still exist!
dept = null;
System.out.println(e1.name);  // "Alice" — still alive
```

---

**3. Composition — "HAS-A" (Strong relationship)**

A stronger form of aggregation where the contained object **cannot exist without** the parent. If the parent is destroyed, the child is also destroyed.

```java
// House HAS Rooms — Rooms cannot exist without a House
class Room {
    String name;
    int area;

    Room(String name, int area) {
        this.name = name;
        this.area = area;
    }
}

class House {
    String address;
    List<Room> rooms;  // Composition — House OWNS Rooms

    House(String address) {
        this.address = address;
        // Rooms are created INSIDE the House — House controls their lifecycle
        this.rooms = new ArrayList<>();
        this.rooms.add(new Room("Living Room", 300));
        this.rooms.add(new Room("Bedroom", 200));
        this.rooms.add(new Room("Kitchen", 150));
    }
}

// Usage
House house = new House("123 Main St");
// If House is destroyed, all Rooms are destroyed too
house = null;  // Rooms are also garbage collected
```

---

**Comparison Table:**

| Aspect | Association | Aggregation | Composition |
|--------|-------------|-------------|-------------|
| **Relationship** | Uses / knows about | HAS-A (weak) | HAS-A (strong) |
| **Ownership** | No ownership | Weak ownership | Strong ownership |
| **Lifecycle** | Independent | Independent | Dependent on parent |
| **Child exists alone?** | Yes | Yes | No |
| **Child created by** | Either side | Outside, passed in | Parent internally |
| **Example** | Teacher ↔ Student | Department → Employee | House → Room |
| **Real-world** | Doctor visits Patient | Car has Passengers | Human has Heart |

```
Association:    Teacher ──── Student     (both independent)

Aggregation:    Department ◇─── Employee   (hollow diamond — weak)
                Car ◇─── Passenger

Composition:    House ◆─── Room           (filled diamond — strong)
                Human ◆─── Heart
```

**Key Rule to Remember:**
- **Aggregation** — Object is passed **from outside** (constructor injection)
- **Composition** — Object is created **inside** the parent class

---
