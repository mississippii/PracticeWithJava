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
32. [Interview Questions](#interview-questions-with-answers) - Q1–Q36: Core Java Questions
33. [Real-World Production & Performance](#real-world-java-interview-questions--production--performance) - Q37–Q60: Production Debugging, Memory Leaks, GC, Thread Pools, JVM Tuning
34. [Java & Microservices](#java--microservices--real-interview-questions) - Q61–Q67: Distributed Systems, Timeouts, Tracing, Caching, Cascading Failures
35. [More Real-World Java Questions](#more-real-world-java-interview-questions) - Q68–Q72: Parallel Streams, ThreadLocal, HashMap, ExecutorService, Local vs Prod

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

### HashMap Internals — Deep Dive

#### What happens internally when you do `map.put(key, value)`?

**Step-by-step process:**

```
map.put("Alice", 25)  — What happens inside?
═══════════════════════════════════════════════

Step 1: Calculate hashCode()
  "Alice".hashCode() → 63650250

Step 2: Apply internal hash() function (spreads bits to reduce collisions)
  hash = hashCode ^ (hashCode >>> 16)
  This XORs upper 16 bits with lower 16 bits to mix the hash

Step 3: Calculate bucket index
  index = hash & (capacity - 1)     // Same as hash % capacity but faster
  If capacity = 16: index = hash & 15  → gives index 0-15

Step 4: Go to that bucket index in the internal array
  If bucket is EMPTY    → Create new Node, store it
  If bucket is NOT EMPTY → Collision! (see collision handling below)

Step 5: If collision — check existing nodes:
  a) If key already exists (equals() returns true) → REPLACE value
  b) If key is different → APPEND to linked list (or tree)

Step 6: Check if size > threshold (capacity × loadFactor)
  If yes → RESIZE (double the array, rehash all entries)
```

```
Visual: HashMap internal structure
══════════════════════════════════

HashMap (capacity = 16, loadFactor = 0.75)

  Bucket Array (Node[16]):
  ┌────────┐
  │  [0]   │ → null
  ├────────┤
  │  [1]   │ → null
  ├────────┤
  │  [2]   │ → [key="Bob", val=30, hash=123] → null
  ├────────┤
  │  [3]   │ → null
  ├────────┤
  │  [4]   │ → [key="Alice", val=25, hash=456] → [key="Eve", val=28, hash=789] → null
  ├────────┤                                       ↑ COLLISION! (same bucket, different keys)
  │  [5]   │ → null
  ├────────┤
  │  ...  │
  ├────────┤
  │  [10]  │ → [key="Charlie", val=35, hash=321] → null
  ├────────┤
  │  ...  │
  └────────┘

  Each bucket is a LinkedList of Nodes (Java 7)
  or LinkedList → Red-Black Tree (Java 8+ when list > 8 nodes)
```

#### How hashCode() is calculated

```java
// Object's default hashCode() — returns memory address (native method)
Object obj = new Object();
obj.hashCode();  // e.g., 366712642

// String's hashCode() — formula: s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
"Alice".hashCode();  // 63650250
// = 'A'*31^4 + 'l'*31^3 + 'i'*31^2 + 'c'*31^1 + 'e'*31^0
// = 65*923521 + 108*29791 + 105*961 + 99*31 + 101

// Why 31?
// 1. It's an odd prime number (reduces collisions)
// 2. 31 * i = (i << 5) - i  → JVM optimizes to bit shift (fast!)
```

```java
// HashMap's internal hash() — further spreads the bits
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    //                          XOR upper 16 bits with lower 16 bits
    //                          This reduces collisions when capacity is small
}
```

#### How the bucket index is determined

```java
// Index calculation (inside HashMap source code):
int index = (n - 1) & hash;    // n = array capacity (always power of 2)

// Why power of 2?
// Because (n - 1) & hash  is equivalent to  hash % n  but MUCH faster
// Example: capacity = 16
//   (16 - 1) & hash  =  15 & hash  =  0b1111 & hash  → keeps last 4 bits
//   This gives index 0 to 15

// That's why HashMap capacity is ALWAYS a power of 2 (16, 32, 64, ...)
```

#### What happens during hash collisions

```
COLLISION = Two different keys get the SAME bucket index
════════════════════════════════════════════════════════

Two types of collision:

1. Same hashCode, different keys (rare but possible)
   "Aa".hashCode() == "BB".hashCode()  → both return 2112!

2. Different hashCode, same bucket index (more common)
   hash("Alice") = 456  →  456 & 15 = 8  → bucket[8]
   hash("Eve")   = 789  →  789 & 15 = 8  → bucket[8]  ← SAME bucket!
```

```
How collisions are resolved:
═════════════════════════════

JAVA 7 — LinkedList only (Separate Chaining):

  bucket[4] → [Alice:25] → [Eve:28] → [Tom:32] → null

  Problem: If many collisions → long linked list → O(n) lookup!


JAVA 8+ — LinkedList + Red-Black Tree (Treeification):

  If LinkedList length > TREEIFY_THRESHOLD (8):
    LinkedList is CONVERTED to Red-Black Tree

  bucket[4] → LinkedList: [A] → [B] → [C] → ... (length <= 8)
                                    OR
  bucket[4] → Red-Black Tree:       [D]                (length > 8)
                                   /   \
                                 [B]   [F]
                                / \   / \
                              [A] [C][E] [G]

  When tree shrinks below UNTREEIFY_THRESHOLD (6):
    Tree is converted BACK to LinkedList
```

```java
// Key constants in HashMap source code:
static final int DEFAULT_INITIAL_CAPACITY = 16;       // Default bucket array size
static final float DEFAULT_LOAD_FACTOR = 0.75f;       // Resize when 75% full
static final int TREEIFY_THRESHOLD = 8;               // LinkedList → Tree
static final int UNTREEIFY_THRESHOLD = 6;             // Tree → LinkedList
static final int MIN_TREEIFY_CAPACITY = 64;           // Min capacity for treeification
static final int MAXIMUM_CAPACITY = 1 << 30;          // Max capacity (2^30)
```

#### Why Java 8 introduced Treeification (LinkedList → Red-Black Tree)

```
PROBLEM (Java 7):
  Worst case: all keys hash to same bucket → one long LinkedList
  Lookup time: O(n) — defeats the purpose of HashMap!

  Attacker could craft keys with same hashCode (HashDoS attack)
  → Force all entries into one bucket → O(n) for every operation

SOLUTION (Java 8):
  When a bucket's LinkedList grows beyond 8 nodes → convert to Red-Black Tree
  Lookup time: O(log n) instead of O(n)

  Performance comparison for a bucket with 1000 collisions:
  ┌──────────────┬─────────────┬──────────────────┐
  │ Operation    │ LinkedList  │ Red-Black Tree   │
  ├──────────────┼─────────────┼──────────────────┤
  │ Search       │ O(1000)     │ O(~10)           │
  │ Insert       │ O(1000)     │ O(~10)           │
  │ Delete       │ O(1000)     │ O(~10)           │
  └──────────────┴─────────────┴──────────────────┘
```

#### How resizing & rehashing works

```
Resizing is triggered when:
  size > capacity × loadFactor
  Default: size > 16 × 0.75 = 12  → resize!

What happens during resize:
═══════════════════════════

Step 1: Create a NEW array with DOUBLE the capacity
  Old: Node[16]  →  New: Node[32]

Step 2: REHASH every entry (recalculate bucket index)
  Because index = hash & (capacity - 1), changing capacity changes every index!

  Example:
  hash("Alice") = 456
  Old: 456 & 15 (capacity 16) = bucket[8]
  New: 456 & 31 (capacity 32) = bucket[8] or bucket[24]  ← index may change!

Step 3: Move all entries to new positions in the new array

  BEFORE (capacity = 16):
  bucket[2]  → [Bob:30]
  bucket[4]  → [Alice:25] → [Eve:28]
  bucket[10] → [Charlie:35]

  AFTER RESIZE (capacity = 32):
  bucket[2]  → [Bob:30]        ← same index
  bucket[4]  → [Alice:25]      ← Eve moved to different bucket!
  bucket[20] → [Eve:28]        ← new position after rehash
  bucket[26] → [Charlie:35]    ← new position after rehash
```

```java
// Why resizing is EXPENSIVE:
// - Creates new array (memory allocation)
// - Rehashes EVERY entry (CPU intensive)
// - Temporarily doubles memory usage
// - O(n) operation

// Best practice: If you know the size upfront, set initial capacity!
Map<String, Integer> map = new HashMap<>(100);       // expect ~100 entries
// Or more precisely:
Map<String, Integer> map = new HashMap<>(134, 0.75f); // 100/0.75 ≈ 134
// This avoids any resizing!
```

#### What is Load Factor and Capacity?

```
CAPACITY = Size of the internal bucket array
  Default: 16
  Always a power of 2 (16, 32, 64, 128, ...)
  Doubles on each resize

LOAD FACTOR = How full the map can get before resizing
  Default: 0.75 (75%)
  Threshold = capacity × loadFactor = 16 × 0.75 = 12

  ┌──────────────────┬─────────────────────────────────┐
  │  Load Factor     │  Effect                          │
  ├──────────────────┼─────────────────────────────────┤
  │  Low  (0.5)      │  More memory, fewer collisions   │
  │                   │  Resizes sooner, faster lookups  │
  ├──────────────────┼─────────────────────────────────┤
  │  Default (0.75)  │  Good balance of space & time    │
  │                   │  Recommended for most cases      │
  ├──────────────────┼─────────────────────────────────┤
  │  High (1.0)      │  Less memory, more collisions    │
  │                   │  Resizes later, slower lookups   │
  └──────────────────┴─────────────────────────────────┘
```

```java
// Custom capacity and load factor
Map<String, Integer> map1 = new HashMap<>(32);           // capacity 32, LF 0.75
Map<String, Integer> map2 = new HashMap<>(32, 0.5f);     // capacity 32, LF 0.50
Map<String, Integer> map3 = new HashMap<>(1000, 0.75f);  // capacity 1024 (next power of 2)
```

#### Why HashMap is NOT thread-safe

```java
// PROBLEM 1: Race condition on put()
// Two threads put into same bucket simultaneously → data loss

Thread-1: map.put("A", 1)     // both calculate same bucket index
Thread-2: map.put("B", 2)     // Thread-2 overwrites Thread-1's node!
// Result: "A" is LOST

// PROBLEM 2: Infinite loop during resize (Java 7)
// Two threads trigger resize simultaneously
// LinkedList nodes can form a CYCLE → infinite loop → CPU 100%!
// (Fixed in Java 8 by changing resize algorithm, but still not safe)

// PROBLEM 3: Stale reads
// Thread-1 puts a value, Thread-2 may not see it (no memory visibility guarantee)

// PROBLEM 4: ConcurrentModificationException
Map<String, Integer> map = new HashMap<>();
map.put("A", 1);
map.put("B", 2);

// Iterating and modifying at the same time → EXCEPTION
for (String key : map.keySet()) {
    if (key.equals("A")) {
        map.remove(key);  // ConcurrentModificationException!
    }
}
```

#### HashMap vs ConcurrentHashMap — Deep Comparison

```
HashMap (NOT thread-safe):
══════════════════════════
┌──────────────────────────────────┐
│         Single Lock? NO LOCK!    │
│  bucket[0] → [A:1]              │
│  bucket[1] → [B:2] → [C:3]     │   Any thread can read/write
│  bucket[2] → [D:4]              │   at any time → CHAOS
│  bucket[3] → null               │
└──────────────────────────────────┘


ConcurrentHashMap (Java 8+, Segment-level locking):
═══════════════════════════════════════════════════
┌──────────────────────────────────┐
│  bucket[0] → [A:1]   🔒 lock0   │   Each bucket has its OWN lock
│  bucket[1] → [B:2]   🔒 lock1   │   Thread-1 can write to bucket[0]
│  bucket[2] → [D:4]   🔒 lock2   │   Thread-2 can write to bucket[2]
│  bucket[3] → null     🔒 lock3   │   SIMULTANEOUSLY! (fine-grained)
└──────────────────────────────────┘

Java 7 ConcurrentHashMap: Used Segment[] (16 segments, each with own lock)
Java 8 ConcurrentHashMap: Uses CAS + synchronized on individual bucket nodes
                          (even finer granularity, better performance)
```

```java
// ConcurrentHashMap — Thread-safe operations
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Basic operations (thread-safe)
map.put("Alice", 25);
map.get("Alice");
map.remove("Alice");

// Atomic compound operations (these are NOT safe in regular HashMap)
map.putIfAbsent("Alice", 25);                     // Put only if key doesn't exist
map.computeIfAbsent("Alice", k -> expensiveCalc()); // Compute only if absent
map.computeIfPresent("Alice", (k, v) -> v + 1);   // Update only if present
map.merge("Alice", 1, Integer::sum);               // Merge with existing value

// Safe iteration (no ConcurrentModificationException)
map.forEach((key, value) -> System.out.println(key + ": " + value));
```

| Feature | HashMap | ConcurrentHashMap |
|---------|---------|-------------------|
| Thread-safe | No | Yes |
| Null key | 1 null key allowed | **NOT allowed** (throws NPE) |
| Null value | Allowed | **NOT allowed** |
| Performance (single thread) | Faster (no locking overhead) | Slightly slower |
| Performance (multi-thread) | Broken (race conditions) | Excellent (fine-grained locks) |
| Locking | None | Per-bucket (CAS + synchronized) |
| Iterator | Fail-fast (`ConcurrentModificationException`) | Weakly consistent (no exception) |
| `size()` | Exact | Approximate (best-effort) |
| When to use | Single-threaded code | Multi-threaded / concurrent code |

#### Why ConcurrentHashMap doesn't allow null?

```java
// In HashMap:
map.get("key") returns null  → Does the key exist with null value?
                               Or does the key NOT exist?
                               → You can check with containsKey()

// In ConcurrentHashMap:
// Between get() and containsKey(), another thread may have changed the map!
// This makes null values AMBIGUOUS in concurrent context
// So ConcurrentHashMap simply forbids null keys and null values
```

#### HashMap Time Complexity Summary

```
┌────────────┬──────────────────┬──────────────────┐
│ Operation  │ Average Case     │ Worst Case       │
├────────────┼──────────────────┼──────────────────┤
│ put()      │ O(1)             │ O(log n) *       │
│ get()      │ O(1)             │ O(log n) *       │
│ remove()   │ O(1)             │ O(log n) *       │
│ containsKey│ O(1)             │ O(log n) *       │
│ resize()   │ O(n)             │ O(n)             │
└────────────┴──────────────────┴──────────────────┘

* Java 8+: O(log n) because of Red-Black Tree
  Java 7:  O(n) because of LinkedList only
```

#### Complete Internal Flow: put() and get()

```java
// SIMPLIFIED source code of HashMap.put()
public V put(K key, V value) {
    // Step 1: Calculate hash
    int hash = hash(key);                  // hashCode ^ (hashCode >>> 16)

    // Step 2: Find bucket
    int index = (capacity - 1) & hash;     // Bucket index
    Node<K,V> node = table[index];         // Go to that bucket

    // Step 3: Bucket is empty → just insert
    if (node == null) {
        table[index] = new Node<>(hash, key, value, null);
    }
    // Step 4: Bucket has entries → traverse
    else {
        while (node != null) {
            // Step 4a: Key already exists → replace value
            if (node.hash == hash && (node.key == key || key.equals(node.key))) {
                V oldValue = node.value;
                node.value = value;       // REPLACE
                return oldValue;
            }
            // Step 4b: Key doesn't match → go to next node
            node = node.next;
        }
        // Step 4c: Key not found → add new node to list/tree
        addNewNode(hash, key, value, index);
    }

    // Step 5: Check if resize needed
    if (++size > threshold) {             // threshold = capacity * loadFactor
        resize();                          // Double capacity, rehash everything
    }

    // Step 6: Check if treeification needed (Java 8+)
    if (bucketLength > TREEIFY_THRESHOLD) { // > 8
        treeifyBin(table, hash);            // Convert LinkedList → Red-Black Tree
    }

    return null;
}
```

```java
// SIMPLIFIED source code of HashMap.get()
public V get(Object key) {
    // Step 1: Calculate hash
    int hash = hash(key);

    // Step 2: Find bucket
    int index = (capacity - 1) & hash;
    Node<K,V> node = table[index];

    // Step 3: Traverse bucket
    while (node != null) {
        // Compare hash first (fast int comparison), then equals()
        if (node.hash == hash && (node.key == key || key.equals(node.key))) {
            return node.value;    // FOUND!
        }
        node = node.next;         // Check next node in chain
    }
    return null;                   // NOT FOUND
}

// Key insight: This is why you MUST override BOTH hashCode() AND equals()
// - hashCode() → finds the correct BUCKET
// - equals()   → finds the correct KEY within that bucket
```

#### Why you MUST override both hashCode() and equals()

```java
// BAD: Override only equals(), not hashCode()
class Employee {
    String id;

    @Override
    public boolean equals(Object o) {
        return this.id.equals(((Employee) o).id);
    }
    // hashCode() NOT overridden → uses default (memory address)
}

Employee e1 = new Employee("E001");
Employee e2 = new Employee("E001");  // Same id, different object

Map<Employee, String> map = new HashMap<>();
map.put(e1, "Alice");
map.get(e2);  // Returns NULL!!! Even though e1.equals(e2) is true

// WHY? Because:
// e1.hashCode() = 12345 (memory address) → bucket[5]
// e2.hashCode() = 67890 (different memory address) → bucket[10]
// HashMap looks in bucket[10] — Alice is in bucket[5] — NOT FOUND!
```

```java
// CORRECT: Override BOTH hashCode() and equals()
class Employee {
    String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Same id → same hashCode → same bucket!
    }
}

Employee e1 = new Employee("E001");
Employee e2 = new Employee("E001");

Map<Employee, String> map = new HashMap<>();
map.put(e1, "Alice");
map.get(e2);  // Returns "Alice" ✓

// hashCode() contract:
// 1. If a.equals(b) is true  → a.hashCode() MUST == b.hashCode()
// 2. If a.equals(b) is false → hashCode() CAN be same (collision)
// 3. hashCode() must be CONSISTENT (same value for same object state)
```

#### HashMap vs Hashtable vs ConcurrentHashMap

| Feature | HashMap | Hashtable | ConcurrentHashMap |
|---------|---------|-----------|-------------------|
| Thread-safe | No | Yes (synchronized methods) | Yes (fine-grained locks) |
| Null key/value | 1 null key, many null values | **No nulls** | **No nulls** |
| Performance | Fastest (single thread) | Slowest (locks entire table) | Fast (locks per bucket) |
| Introduced | Java 1.2 | Java 1.0 (legacy) | Java 1.5 |
| Iterator | Fail-fast | Fail-fast (Enumerator) | Weakly consistent |
| Extends | AbstractMap | Dictionary (legacy) | AbstractMap |
| Use in 2024? | Yes (single-threaded) | **NO (legacy, use CHM)** | Yes (multi-threaded) |

```java
// NEVER use Hashtable in modern Java
Hashtable<String, Integer> ht = new Hashtable<>();  // ✗ Legacy, slow

// Use HashMap (single-threaded) or ConcurrentHashMap (multi-threaded)
Map<String, Integer> map = new HashMap<>();                // ✓ Single-thread
Map<String, Integer> map = new ConcurrentHashMap<>();      // ✓ Multi-thread
Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());  // ✓ Alternative
```

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

### Q4: What happens internally when you put an element into a HashMap?

**Answer:**

```
map.put("Alice", 25) — Internal flow:
═════════════════════════════════════

1. hashCode()      → "Alice".hashCode() = 63650250
2. hash()          → 63650250 ^ (63650250 >>> 16) = spread bits
3. Bucket index    → hash & (capacity - 1) = e.g., index 10
4. Check bucket[10]:
   ├── Empty?  → Store new Node(hash, "Alice", 25, null)
   └── Not empty? → Traverse chain:
       ├── Key matches (equals())? → Replace value
       └── Key different?          → Append to LinkedList/Tree
5. size > threshold? → Resize (double array, rehash all)
6. Bucket length > 8? → Treeify (LinkedList → Red-Black Tree)
```

**Key constants:**
- Default capacity: **16** (always power of 2)
- Load factor: **0.75** (resize at 75% full, threshold = 16 × 0.75 = 12)
- Treeify threshold: **8** (LinkedList → Red-Black Tree)
- Untreeify threshold: **6** (Tree → LinkedList)

**Why override both hashCode() AND equals()?**
- `hashCode()` → finds the correct **bucket**
- `equals()` → finds the correct **key** within that bucket
- If only equals() is overridden → same logical key may land in different buckets → `get()` returns null!

**HashMap vs ConcurrentHashMap:**

| Feature | HashMap | ConcurrentHashMap |
|---------|---------|-------------------|
| Thread-safe | No | Yes (CAS + per-bucket synchronized) |
| Null key/value | 1 null key, null values OK | **No nulls allowed** |
| Locking | None | Fine-grained (per bucket node) |
| Iterator | Fail-fast (ConcurrentModificationException) | Weakly consistent (no exception) |
| When to use | Single-threaded code | Multi-threaded / concurrent code |

> **See detailed deep-dive with source code:** HashMap Internals section in Collections Framework above

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

## SOLID Principles

> **SOLID** = 5 principles for writing maintainable, scalable, and flexible code.

| Letter | Principle | One-Liner |
|--------|-----------|-----------|
| **S** | Single Responsibility | One class = One job |
| **O** | Open/Closed | Open for extension, closed for modification |
| **L** | Liskov Substitution | Child classes should be substitutable for parent |
| **I** | Interface Segregation | Many small interfaces > One fat interface |
| **D** | Dependency Inversion | Depend on abstractions, not concretions |

**Real-World Analogy: Restaurant Kitchen**

| Principle | Restaurant Analogy |
|-----------|-------------------|
| **S** - Single Responsibility | Chef cooks, waiter serves, cashier bills - everyone has ONE job |
| **O** - Open/Closed | Add new dishes to menu without changing the kitchen |
| **L** - Liskov Substitution | Any chef can replace another chef for the same task |
| **I** - Interface Segregation | Waiter doesn't need to know cooking, chef doesn't need billing |
| **D** - Dependency Inversion | Kitchen depends on "ingredients" concept, not specific vendors |

---

### S - Single Responsibility Principle (SRP)

> **"A class should have only one reason to change."**

**Bad — Violates SRP:**

```java
public class Employee {
    private String name;
    private double salary;

    public String getName() { return name; }
    public double getSalary() { return salary; }

    // Responsibility 2: Salary calculation (should be separate)
    public double calculateTax() {
        return salary * 0.3;
    }

    // Responsibility 3: Database operations (should be separate)
    public void saveToDatabase() {
        System.out.println("Saving to database...");
    }

    // Responsibility 4: Report generation (should be separate)
    public void generatePayslip() {
        System.out.println("Generating payslip...");
    }
}
```

**Good — Follows SRP:**

```java
// Responsibility 1: Employee data only
public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }
}

// Responsibility 2: Tax calculations
public class TaxCalculator {
    public double calculateTax(Employee employee) {
        return employee.getSalary() * 0.3;
    }
}

// Responsibility 3: Database operations
public class EmployeeRepository {
    public void save(Employee employee) {
        System.out.println("Saving " + employee.getName() + " to database");
    }
}

// Responsibility 4: Report generation
public class PayslipGenerator {
    public void generate(Employee employee) {
        System.out.println("Generating payslip for " + employee.getName());
    }
}
```

---

### O - Open/Closed Principle (OCP)

> **"Software entities should be open for extension but closed for modification."**

**Bad — Violates OCP:**

```java
public class PaymentProcessor {
    public void processPayment(String paymentType, double amount) {
        if (paymentType.equals("CREDIT_CARD")) {
            System.out.println("Processing credit card: $" + amount);
        } else if (paymentType.equals("PAYPAL")) {
            System.out.println("Processing PayPal: $" + amount);
        }
        // Adding new payment? Must modify this class!
    }
}
```

**Good — Follows OCP:**

```java
public interface PaymentMethod {
    void pay(double amount);
}

public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing credit card: $" + amount);
    }
}

public class PayPalPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal: $" + amount);
    }
}

// Adding Crypto? Just create new class — NO modification to existing code!
public class CryptoPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing crypto: $" + amount);
    }
}

public class PaymentProcessor {
    public void processPayment(PaymentMethod method, double amount) {
        method.pay(amount);  // Works with ANY payment type
    }
}
```

---

### L - Liskov Substitution Principle (LSP)

> **"Objects of a superclass should be replaceable with objects of its subclasses without breaking the program."**

**Bad — Violates LSP:**

```java
public class Bird {
    public void fly() { System.out.println("Flying..."); }
}

public class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}

// Code that expects Bird to fly will CRASH with Penguin
```

**Good — Follows LSP:**

```java
public interface Flyable {
    void fly();
}

public abstract class Bird {
    public abstract void eat();
}

public class Sparrow extends Bird implements Flyable {
    @Override
    public void fly() { System.out.println("Sparrow flying!"); }
    @Override
    public void eat() { System.out.println("Eating seeds"); }
}

public class Penguin extends Bird {
    // No fly method — Penguin doesn't claim to fly
    @Override
    public void eat() { System.out.println("Eating fish"); }
    public void swim() { System.out.println("Swimming!"); }
}
```

**Classic Example — Rectangle & Square:**

```java
// BAD: Square extends Rectangle breaks area calculation
// GOOD: Both implement Shape interface
public interface Shape {
    int getArea();
}

public class Rectangle implements Shape {
    private final int width, height;
    public Rectangle(int w, int h) { this.width = w; this.height = h; }
    public int getArea() { return width * height; }
}

public class Square implements Shape {
    private final int side;
    public Square(int s) { this.side = s; }
    public int getArea() { return side * side; }
}
```

---

### I - Interface Segregation Principle (ISP)

> **"Clients should not be forced to depend on interfaces they don't use."**

**Bad — Fat Interface:**

```java
public interface Worker {
    void work();
    void eat();
    void sleep();
    void writeCode();
    void manageTeam();
}

// Robot forced to implement eat() and sleep() — makes no sense!
public class Robot implements Worker {
    public void work() { System.out.println("Working 24/7..."); }
    public void eat() { throw new UnsupportedOperationException(); }
    public void sleep() { throw new UnsupportedOperationException(); }
    public void writeCode() { throw new UnsupportedOperationException(); }
    public void manageTeam() { throw new UnsupportedOperationException(); }
}
```

**Good — Segregated Interfaces:**

```java
public interface Workable { void work(); }
public interface Eatable { void eat(); }
public interface Sleepable { void sleep(); }
public interface Codeable { void writeCode(); }
public interface Manageable { void manageTeam(); }

public class Developer implements Workable, Eatable, Sleepable, Codeable {
    public void work() { System.out.println("Working..."); }
    public void eat() { System.out.println("Eating..."); }
    public void sleep() { System.out.println("Sleeping..."); }
    public void writeCode() { System.out.println("Coding..."); }
}

public class Robot implements Workable {
    public void work() { System.out.println("Working 24/7..."); }
}
```

---

### D - Dependency Inversion Principle (DIP)

> **"High-level modules should not depend on low-level modules. Both should depend on abstractions."**

**Bad — Violates DIP:**

```java
public class MySQLDatabase {
    public void save(String data) { System.out.println("Saving to MySQL"); }
}

public class UserService {
    private MySQLDatabase database = new MySQLDatabase();  // Tight coupling!

    public void createUser(String name) {
        database.save(name);
    }
    // Want PostgreSQL? Must modify UserService!
}
```

**Good — Follows DIP:**

```java
public interface Database {
    void save(String data);
}

public class MySQLDatabase implements Database {
    public void save(String data) { System.out.println("Saving to MySQL"); }
}

public class PostgreSQLDatabase implements Database {
    public void save(String data) { System.out.println("Saving to PostgreSQL"); }
}

public class UserService {
    private Database database;  // Depends on abstraction!

    public UserService(Database database) {
        this.database = database;  // Injected from outside
    }

    public void createUser(String name) {
        database.save(name);
    }
}

// Swap easily — NO changes to UserService
UserService service1 = new UserService(new MySQLDatabase());
UserService service2 = new UserService(new PostgreSQLDatabase());
```

**DIP in Spring Boot:**

```java
@Service
public class UserService {
    private final UserRepository repository;  // Interface!

    @Autowired  // Spring injects the implementation automatically
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

---

### SOLID Summary

| Violation | Symptom | Fix |
|-----------|---------|-----|
| SRP | God classes, >500 lines | Split into smaller classes |
| OCP | Long if-else/switch chains | Use polymorphism + interfaces |
| LSP | Subclass throws exceptions | Rethink inheritance hierarchy |
| ISP | Empty method implementations | Break into smaller interfaces |
| DIP | `new` keyword everywhere | Use dependency injection |

---

## Real-World Java Interview Questions — Production & Performance

> These questions test **understanding**, not memorization. Interviewers use these to separate seniors from juniors.

---

### Q37: Why does a Java application slow down over time without throwing errors?

**Answer:**

This is one of the most common production issues. The app starts fast but degrades over days/weeks.

```
Root Causes:
════════════

1. MEMORY LEAKS (Slow GC death)
   ──────────────────────────────
   Objects accumulate in heap but are never garbage collected.
   GC runs more frequently → longer GC pauses → app feels slower.

   Timeline:
   Day 1:  Heap 30% used → GC runs every 5 min  → 10ms pause
   Day 7:  Heap 70% used → GC runs every 30 sec → 200ms pause
   Day 14: Heap 95% used → GC runs constantly   → 2-5 sec pause
   Day 15: OutOfMemoryError (finally crashes)

2. THREAD LEAKS
   ─────────────
   Threads are created but never terminated.
   Each thread = ~1MB stack memory + OS resources.

   Day 1:  50 threads  → normal
   Day 30: 5000 threads → context switching kills CPU

3. CONNECTION LEAKS
   ─────────────────
   DB connections / HTTP connections opened but never closed.
   Pool exhausted → new requests wait → timeout → slow.

4. CACHE WITHOUT EVICTION
   ───────────────────────
   In-memory cache (HashMap) grows unbounded.
   More entries → more memory → more GC pressure.

5. LOG FILE GROWTH
   ────────────────
   Synchronous logging to a file that grows to GBs.
   Disk I/O becomes bottleneck.
```

```java
// Example: Memory leak via cache without eviction
public class UserCache {
    // ✗ BAD: Grows forever, never evicts
    private static Map<Long, User> cache = new HashMap<>();

    public User getUser(Long id) {
        if (!cache.containsKey(id)) {
            cache.put(id, userRepository.findById(id));  // Keeps growing!
        }
        return cache.get(id);
    }
}

// ✓ FIX: Use bounded cache with eviction
public class UserCache {
    private static Map<Long, User> cache = new LinkedHashMap<>(1000, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, User> eldest) {
            return size() > 1000;  // Evict when exceeds 1000 entries
        }
    };
}

// ✓ BETTER: Use Caffeine cache (production-grade)
Cache<Long, User> cache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .expireAfterWrite(Duration.ofMinutes(10))
    .build();
```

**How to diagnose:**
```bash
# Monitor heap usage over time
jstat -gcutil <PID> 5000          # GC stats every 5 seconds

# Take heap dump when app is slow
jmap -dump:live,format=b,file=heap.hprof <PID>

# Analyze with Eclipse MAT or VisualVM
# Look for: "Leak Suspects" report → shows objects holding most memory
```

---

### Q38: How can memory leaks happen even without static variables?

**Answer:**

Most developers think memory leaks = `static` collections. But there are many sneakier causes:

```java
// CAUSE 1: Non-static inner class holds reference to outer class
// ═══════════════════════════════════════════════════════════════
class OrderService {
    private List<byte[]> largeData = new byte[10_000_000];  // 10MB

    public Runnable createTask() {
        // ✗ Anonymous inner class holds implicit reference to OrderService
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Processing...");
                // Even though largeData is not used here,
                // this Runnable keeps OrderService (and its 10MB) alive!
            }
        };
    }
}

// ✓ FIX: Use static inner class or lambda
public static Runnable createTask() {
    return () -> System.out.println("Processing...");  // No outer reference
}
```

```java
// CAUSE 2: Event listeners / callbacks never unregistered
// ═══════════════════════════════════════════════════════
class DashboardController {
    public void init() {
        // ✗ Registers listener but never removes it
        eventBus.register(this);  // EventBus holds reference to this controller
    }
    // If DashboardController is recreated (e.g., scope refresh),
    // old instances are NEVER garbage collected because EventBus still references them
}

// ✓ FIX: Always unregister in destroy/close
class DashboardController {
    public void init() { eventBus.register(this); }

    @PreDestroy
    public void cleanup() { eventBus.unregister(this); }  // Release reference
}
```

```java
// CAUSE 3: Unclosed resources (streams, connections, ResultSets)
// ══════════════════════════════════════════════════════════════
public List<User> getUsers() {
    // ✗ Connection opened, never closed on exception path
    Connection conn = dataSource.getConnection();
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM users");
    // If exception thrown here → conn is LEAKED
    return mapResults(rs);
}

// ✓ FIX: try-with-resources (auto-closes)
public List<User> getUsers() {
    try (Connection conn = dataSource.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
        return mapResults(rs);
    }  // All 3 resources auto-closed, even on exception
}
```

```java
// CAUSE 4: ThreadLocal not cleaned up (in thread pools)
// ═════════════════════════════════════════════════════
// Covered in Q5 (ThreadLocal section) — threads in pool are REUSED,
// so ThreadLocal values persist across requests → memory builds up

// CAUSE 5: String.substring() pre-Java 7u6
// Old JVMs: substring() shared the original char[], keeping entire string in memory
// Fixed in Java 7u6+: substring() creates new char[]

// CAUSE 6: Custom ClassLoader holding references
// Covered in Q49 (ClassLoader leaks)
```

```
Summary of leak causes:
┌────────────────────────────────┬──────────────────────────────────┐
│ Leak Source                    │ Fix                              │
├────────────────────────────────┼──────────────────────────────────┤
│ Non-static inner class         │ Use static inner class or lambda │
│ Event listeners                │ Unregister in @PreDestroy        │
│ Unclosed resources             │ try-with-resources               │
│ ThreadLocal in thread pools    │ Always call .remove() in finally │
│ Unbounded caches               │ Use LRU cache with max size      │
│ Collection growing in a field  │ Set size limit or TTL eviction   │
│ InputStream not closed         │ try-with-resources               │
│ JDBC ResultSet/Statement       │ try-with-resources               │
└────────────────────────────────┴──────────────────────────────────┘
```

---

### Q39: Why does CPU spike when traffic is low?

**Answer:**

Counter-intuitive but very common in production. Low traffic + high CPU usually means:

```
Root Causes:
════════════

1. GC THRASHING (most common!)
   ────────────────────────────
   Heap is almost full → GC runs constantly → CPU 100%
   But GC reclaims almost nothing → runs again immediately

   Clue: Low traffic, high CPU, high GC time in jstat
   jstat -gcutil <PID>  → GC time > 90%

2. THREAD CONTENTION / DEADLOCK
   ─────────────────────────────
   Multiple threads fighting over same lock.
   Even 2 requests can cause 100% CPU if they spin on a lock.

3. INFINITE LOOP / BUSY WAIT
   ──────────────────────────
   while (true) { if (condition) break; }  // Burns CPU waiting

4. REGEX CATASTROPHIC BACKTRACKING
   ────────────────────────────────
   Regex like "(a+)+" on input "aaaaaaaaaaaaaaab"
   → Exponential time → one request pins one core at 100%

5. SCHEDULED TASK GONE WRONG
   ──────────────────────────
   @Scheduled cron job doing heavy computation in background
   Not related to traffic at all

6. SERIALIZATION / DESERIALIZATION LOOP
   ─────────────────────────────────────
   Circular references in JSON serialization → infinite recursion
```

```java
// Example: Regex backtracking — ONE request can pin CPU to 100%
String evilPattern = "(a+)+$";
String evilInput = "aaaaaaaaaaaaaaaaaaaaaaaab";
// This takes MINUTES to evaluate — exponential backtracking!
Pattern.compile(evilPattern).matcher(evilInput).matches();

// ✓ FIX: Use possessive quantifiers or atomic groups
String safePattern = "(a++)$";  // Possessive — no backtracking
```

```java
// Example: Busy-wait burning CPU
// ✗ BAD
while (!isReady()) {
    // Burning CPU doing nothing!
}

// ✓ FIX: Use proper waiting mechanism
while (!isReady()) {
    Thread.sleep(100);  // Or use CountDownLatch, CompletableFuture
}

// ✓ BEST: Use CountDownLatch
CountDownLatch latch = new CountDownLatch(1);
latch.await();  // Blocks thread without CPU burn
// In another thread: latch.countDown();
```

**How to diagnose:**
```bash
# Find which threads are consuming CPU
top -H -p <PID>                   # Show threads sorted by CPU
jstack <PID> > thread_dump.txt    # Thread dump — find what they're doing

# Look for:
# - Threads in RUNNABLE state doing GC → GC thrashing
# - Threads in BLOCKED state on same lock → contention
# - Thread stack showing regex/loop → code issue
```

---

### Q40: What happens if a thread is interrupted but the code ignores it?

**Answer:**

This is a **silent killer** in production. The thread never stops, resources never release, shutdown hangs.

```java
// ✗ BAD: Swallowing InterruptedException — the thread LOSES its interrupted status
public void processMessages() {
    while (true) {
        try {
            Message msg = queue.take();  // Blocking call
            process(msg);
        } catch (InterruptedException e) {
            // Swallowed! Thread continues running forever.
            // Thread.currentThread().isInterrupted() is now FALSE
            // The thread can NEVER be stopped gracefully
        }
    }
}

// What happens:
// 1. ExecutorService.shutdownNow() sends interrupt to all threads
// 2. queue.take() throws InterruptedException
// 3. Code catches it and does NOTHING
// 4. Loop continues → queue.take() blocks again
// 5. Thread never terminates → shutdown hangs → app won't stop
```

```java
// ✓ FIX Option 1: Re-set the interrupt flag and exit
public void processMessages() {
    while (!Thread.currentThread().isInterrupted()) {  // Check flag
        try {
            Message msg = queue.take();
            process(msg);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restore interrupt flag!
            break;                                // Exit the loop
        }
    }
    cleanup();  // Release resources
}

// ✓ FIX Option 2: Propagate the exception (let caller handle)
public void processMessages() throws InterruptedException {
    while (true) {
        Message msg = queue.take();  // InterruptedException propagates up
        process(msg);
    }
}
```

```
Interrupt Mechanism:
═══════════════════

Thread.interrupt()
    │
    ├── If thread is BLOCKED (sleep, wait, take, join):
    │     → Throws InterruptedException
    │     → Interrupt flag is CLEARED (set to false)
    │     → YOU must re-set it: Thread.currentThread().interrupt()
    │
    └── If thread is RUNNING:
          → Sets interrupt flag to TRUE
          → Thread must CHECK: Thread.currentThread().isInterrupted()
          → No exception is thrown automatically

Rule: NEVER swallow InterruptedException silently
      Either re-throw it OR re-set the interrupt flag
```

---

### Q41: How do thread pools become hidden bottlenecks?

**Answer:**

Thread pools seem safe but silently degrade under specific conditions.

```java
// PROBLEM 1: All threads blocked on slow I/O
// ═══════════════════════════════════════════
ExecutorService pool = Executors.newFixedThreadPool(10);

// All 10 threads call a slow external API (5 second timeout)
for (int i = 0; i < 100; i++) {
    pool.submit(() -> {
        httpClient.get("https://slow-api.com/data");  // Takes 5 sec
        // All 10 threads stuck waiting
        // Remaining 90 tasks queued → unbounded queue grows
        // User sees: "app is unresponsive"
    });
}

// ✓ FIX: Separate pools for I/O vs CPU tasks
ExecutorService cpuPool = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors()  // CPU-bound tasks
);
ExecutorService ioPool = Executors.newFixedThreadPool(50);  // I/O-bound tasks
```

```java
// PROBLEM 2: Unbounded queue hides the overload (default in newFixedThreadPool)
// ═══════════════════════════════════════════════════════════════════════════════
ExecutorService pool = Executors.newFixedThreadPool(10);
// Internally: new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
//                                    new LinkedBlockingQueue<>());  ← UNBOUNDED!

// If tasks arrive faster than they're processed:
// Queue grows: 100 → 1000 → 100,000 → OutOfMemoryError!
// No error, no rejection — just silent memory growth until crash

// ✓ FIX: Use bounded queue with rejection policy
ExecutorService pool = new ThreadPoolExecutor(
    10,                                    // core threads
    20,                                    // max threads
    60, TimeUnit.SECONDS,                  // idle thread timeout
    new ArrayBlockingQueue<>(500),         // BOUNDED queue (max 500 waiting)
    new ThreadPoolExecutor.CallerRunsPolicy()  // Rejection: caller thread runs it
);
```

```java
// PROBLEM 3: Task exception silently swallowed
// ═════════════════════════════════════════════
pool.submit(() -> {
    int result = 10 / 0;  // ArithmeticException!
    // With submit() → exception is SWALLOWED silently
    // No log, no error — task just disappears
});

// ✓ FIX Option 1: Use execute() instead of submit()
pool.execute(() -> {
    int result = 10 / 0;  // Exception propagates to UncaughtExceptionHandler
});

// ✓ FIX Option 2: Get the Future and handle exception
Future<?> future = pool.submit(() -> { int result = 10 / 0; });
try {
    future.get();  // Exception surfaces here
} catch (ExecutionException e) {
    log.error("Task failed", e.getCause());
}

// ✓ FIX Option 3: Wrap tasks with try-catch
pool.submit(() -> {
    try {
        int result = 10 / 0;
    } catch (Exception e) {
        log.error("Task failed", e);
    }
});
```

```
Thread Pool Sizing Guidelines:
══════════════════════════════
CPU-bound tasks (computation, sorting, encryption):
  threads = number of CPU cores (Runtime.getRuntime().availableProcessors())

I/O-bound tasks (DB queries, HTTP calls, file I/O):
  threads = CPU cores × (1 + wait_time / compute_time)
  Typical: 2× to 10× CPU cores

Mixed: Use SEPARATE pools for CPU and I/O tasks
```

---

### Q42: Why does JVM warm-up affect performance benchmarks?

**Answer:**

```
JVM Execution Phases:
═════════════════════

Request 1-100         Request 100-10000     Request 10000+
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│  INTERPRETED  │───→│  C1 COMPILED  │───→│  C2 COMPILED  │
│  (slow)       │    │  (faster)     │    │  (fastest)    │
│               │    │               │    │               │
│  Bytecode     │    │  Quick JIT    │    │  Optimized    │
│  runs line    │    │  compilation  │    │  native code  │
│  by line      │    │  basic opts   │    │  inlining,    │
│               │    │               │    │  branch pred  │
│  ~10x slower  │    │  ~3x slower   │    │  Full speed   │
└───────────────┘    └───────────────┘    └───────────────┘

Timeline: 0 sec ──────── ~30 sec ──────── ~2-5 min ────── stable
```

```java
// BAD BENCHMARK: Measures cold JVM, not real performance
long start = System.nanoTime();
for (int i = 0; i < 1000; i++) {
    myMethod();
}
long end = System.nanoTime();
System.out.println("Avg: " + (end - start) / 1000 + " ns");
// WRONG! First 100 iterations ran interpreted (10x slower)
// Average is polluted by cold start

// ✓ CORRECT: Warm up first, then measure
// Warm-up phase (discard results)
for (int i = 0; i < 10_000; i++) {
    myMethod();  // Let JIT compile and optimize
}

// Measurement phase
long start = System.nanoTime();
for (int i = 0; i < 100_000; i++) {
    myMethod();
}
long end = System.nanoTime();
System.out.println("Avg: " + (end - start) / 100_000 + " ns");

// ✓ BEST: Use JMH (Java Microbenchmark Harness) — handles warm-up automatically
@Benchmark
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
public void myBenchmark() {
    myMethod();
}
```

**Real production impact:**
```
Kubernetes / Container deployment:
  1. New pod starts → JVM cold
  2. Load balancer sends traffic → first 100 requests are SLOW (10-50x)
  3. Users see timeout errors
  4. After 2-5 min → JVM warmed up → normal speed

Solutions:
  ✓ Readiness probe with delay (don't send traffic until warmed up)
  ✓ Warm-up script: hit key endpoints on startup before going live
  ✓ GraalVM Native Image: AOT compiled, no warm-up (but no JIT optimization)
  ✓ CDS (Class Data Sharing): Pre-load classes to speed up startup
```

---

### Q43: How can retry logic overload a system silently?

**Answer:**

Retry logic is meant to improve reliability, but it can **amplify failures** and bring down the entire system.

```
Scenario: Service A calls Service B
═══════════════════════════════════

Normal:    A ──→ B (200 OK)           1 request

With retries when B is slow:
  A ──→ B (timeout)    attempt 1
  A ──→ B (timeout)    attempt 2      3 requests instead of 1!
  A ──→ B (timeout)    attempt 3

Now multiply by 1000 concurrent users:
  Normal traffic:  1,000 requests to B
  With retries:    3,000 requests to B  ← 3x amplification!

  B is already struggling → 3x more load → B crashes completely
  This is called a "RETRY STORM"
```

```
The Cascade Effect:
═══════════════════

User → A → B → C (database)

C is slow (DB overloaded)
  → B retries C (3x load on C)
  → B becomes slow (waiting for C retries)
  → A retries B (3x load on B)
  → A becomes slow
  → Users retry from browser (F5)
  → 3 × 3 × 3 = 27x amplification!

  Result: ENTIRE SYSTEM COLLAPSES from one slow database
```

```java
// ✗ BAD: Aggressive retry without backoff
public String callService(String url) {
    for (int i = 0; i < 5; i++) {
        try {
            return httpClient.get(url);  // 5 retries, no delay
        } catch (Exception e) {
            // Retry immediately — hammering the failing service
        }
    }
    throw new RuntimeException("Service unavailable");
}

// ✓ FIX: Exponential backoff + jitter + max retries + circuit breaker
public String callService(String url) {
    int maxRetries = 3;
    for (int i = 0; i < maxRetries; i++) {
        try {
            return httpClient.get(url);
        } catch (Exception e) {
            if (i == maxRetries - 1) throw new ServiceUnavailableException();

            // Exponential backoff: 1s, 2s, 4s + random jitter
            long delay = (long) Math.pow(2, i) * 1000;
            long jitter = ThreadLocalRandom.current().nextLong(0, 500);
            Thread.sleep(delay + jitter);
        }
    }
}

// ✓ BEST: Use Resilience4j Circuit Breaker (stops retries when service is down)
// When failure rate > 50% → circuit OPENS → no retries for 30 seconds
// After 30 sec → circuit HALF-OPEN → allows 1 test request
// If test succeeds → circuit CLOSES → normal operation resumes
```

```
Retry Best Practices:
═════════════════════
✓ Exponential backoff (1s → 2s → 4s → 8s)
✓ Add jitter (random delay to avoid thundering herd)
✓ Set maximum retries (3-5 max)
✓ Use circuit breaker (stop retrying when service is clearly down)
✓ Only retry on transient errors (timeout, 503), NOT on 400/404
✓ Set a total timeout (don't retry forever)
✗ NEVER retry immediately without delay
✗ NEVER retry on all exceptions blindly
```

---

### Q44: Why does increasing heap size sometimes worsen performance?

**Answer:**

```
Intuition: More heap = more room = better performance
Reality:   More heap = LONGER GC pauses = WORSE latency

Why?
════
GC must scan and compact the ENTIRE heap during major/full GC.
Bigger heap = more objects to scan = longer pause.

  ┌─────────────────────────────────────────────────────┐
  │  Heap Size  │  GC Frequency  │  GC Pause Duration   │
  ├─────────────┼────────────────┼──────────────────────┤
  │  256MB      │  Often         │  Short (10-50ms)     │
  │  2GB        │  Less often    │  Medium (100-500ms)  │
  │  8GB        │  Rarely        │  LONG (1-5 seconds!) │
  │  32GB       │  Very rarely   │  VERY LONG (10-30s!) │
  └─────────────┴────────────────┴──────────────────────┘

  With 8GB heap + G1 GC:
    App runs great for 2 hours
    → Full GC triggers → 5 second pause → ALL requests timeout
    → Health check fails → Kubernetes kills the pod
    → Pod restarts → cold JVM → even worse!
```

```java
// Scenario: E-commerce app with 50ms latency requirement

// ✗ BAD: -Xmx16g (huge heap, rare but devastating GC pauses)
// Normal: 5ms latency
// During GC: 8000ms latency → users see errors

// ✓ BETTER: -Xmx4g with G1 GC and pause target
// -XX:+UseG1GC -XX:MaxGCPauseMillis=50
// GC runs more often but pauses stay under 50ms

// ✓ BEST: Use ZGC for large heaps (Java 17+)
// -XX:+UseZGC
// Pause time < 1ms regardless of heap size (even 16TB!)
// Trade-off: ~10-15% throughput overhead
```

```
GC Choice Based on Heap Size:
═════════════════════════════
Heap < 256MB  → Serial GC (-XX:+UseSerialGC)
Heap < 4GB    → G1 GC (-XX:+UseG1GC) — default since Java 9
Heap 4-32GB   → G1 GC with tuned pause target
Heap > 8GB    → ZGC (-XX:+UseZGC) — ultra-low latency
Heap > 8GB    → Shenandoah (-XX:+UseShenandoahGC) — alternative to ZGC

Also consider:
  Compressed OOPs: Works only with heap ≤ 32GB (-XX:+UseCompressedOops)
  If heap > 32GB → object references double from 4 bytes to 8 bytes
  → 32GB might actually perform better than 48GB!
```

---

### Q45: How do blocking calls affect scalable Java systems?

**Answer:**

```
The Problem:
════════════

Traditional Spring Boot (Tomcat): 200 threads by default

  Thread-1: handleRequest() → callDatabase() ──── BLOCKED 50ms ──── response
  Thread-2: handleRequest() → callDatabase() ──── BLOCKED 50ms ──── response
  ...
  Thread-200: handleRequest() → callExternalAPI() ── BLOCKED 5000ms ── response

  If each request blocks for 50ms:
    Max throughput = 200 threads / 0.05s = 4,000 req/sec ✓

  If external API is slow (5 seconds):
    Max throughput = 200 threads / 5s = 40 req/sec ✗ ← System grinds to a halt!
    Thread-201 onwards → queued → timeout → user sees errors
```

```java
// ✗ BAD: Blocking call in a web handler — holds the thread hostage
@GetMapping("/order/{id}")
public Order getOrder(@PathVariable Long id) {
    Order order = orderRepository.findById(id);              // Blocks ~50ms
    User user = restTemplate.getForObject(userServiceUrl);   // Blocks ~2000ms!
    PaymentInfo pay = restTemplate.getForObject(paymentUrl);  // Blocks ~1000ms
    // Total: thread is blocked for ~3 seconds
    // Only 200/3 = 66 concurrent requests possible!
    return enrichOrder(order, user, pay);
}

// ✓ FIX Option 1: Parallel calls with CompletableFuture
@GetMapping("/order/{id}")
public Order getOrder(@PathVariable Long id) {
    CompletableFuture<User> userFuture = CompletableFuture.supplyAsync(
        () -> restTemplate.getForObject(userServiceUrl), ioPool
    );
    CompletableFuture<PaymentInfo> payFuture = CompletableFuture.supplyAsync(
        () -> restTemplate.getForObject(paymentUrl), ioPool
    );
    Order order = orderRepository.findById(id);
    return enrichOrder(order, userFuture.join(), payFuture.join());
    // Total: ~2 seconds (parallel) instead of ~3 seconds (sequential)
}

// ✓ FIX Option 2: Spring WebFlux (non-blocking, reactive)
@GetMapping("/order/{id}")
public Mono<Order> getOrder(@PathVariable Long id) {
    Mono<User> user = webClient.get().uri(userServiceUrl).retrieve().bodyToMono(User.class);
    Mono<PaymentInfo> pay = webClient.get().uri(paymentUrl).retrieve().bodyToMono(PaymentInfo.class);
    return Mono.zip(user, pay)
        .map(tuple -> enrichOrder(order, tuple.getT1(), tuple.getT2()));
    // Non-blocking: thread is NOT held — can handle thousands of concurrent requests
}
```

```
Blocking (Thread-per-request):         Non-Blocking (Reactive):
══════════════════════════════         ════════════════════════
200 threads → 200 concurrent           10 threads → 10,000+ concurrent
Each thread blocked on I/O             Thread released during I/O wait
Simple code, limited scale             Complex code, massive scale

When to use which:
  Internal CRUD app (100 users)    → Blocking (Spring MVC) is fine
  Public API (10K+ concurrent)     → Non-blocking (WebFlux) or Virtual Threads
  Microservice calling 5+ APIs     → CompletableFuture or WebFlux
```

```java
// ✓ FIX Option 3: Virtual Threads (Java 21+) — BEST of both worlds
// Blocking code syntax + non-blocking performance
@GetMapping("/order/{id}")
public Order getOrder(@PathVariable Long id) {
    // Same blocking code, but runs on virtual thread
    // JVM automatically suspends/resumes virtual thread during I/O
    // Millions of virtual threads possible!
    Order order = orderRepository.findById(id);
    User user = restTemplate.getForObject(userServiceUrl);
    return enrichOrder(order, user);
}

// application.yml
// spring.threads.virtual.enabled=true  ← Enable virtual threads in Spring Boot 3.2+
```

---

### Q46: Why does excessive object creation hurt latency-sensitive applications?

**Answer:**

```
Every object created in Java:
═════════════════════════════
1. Allocates memory on heap (12-16 bytes header + fields)
2. Eventually must be garbage collected
3. GC pauses the application to reclaim memory

More objects = more GC = more pauses = higher latency
```

```java
// ✗ BAD: Creating millions of short-lived objects in a hot loop
public double calculateAverage(List<Transaction> transactions) {
    return transactions.stream()
        .map(t -> new BigDecimal(t.getAmount()))   // NEW object per element!
        .map(bd -> bd.multiply(new BigDecimal("1.1"))) // ANOTHER new object!
        .map(bd -> bd.setScale(2, RoundingMode.HALF_UP)) // ANOTHER!
        .mapToDouble(BigDecimal::doubleValue)
        .average()
        .orElse(0.0);
    // 3 new objects per transaction × 1M transactions = 3M objects → GC pressure!
}

// ✓ FIX: Reuse objects, use primitives
public double calculateAverage(List<Transaction> transactions) {
    double sum = 0;
    for (Transaction t : transactions) {
        sum += t.getAmount() * 1.1;  // Primitives, no object creation
    }
    return transactions.isEmpty() ? 0 : sum / transactions.size();
}
```

```java
// ✗ BAD: String concatenation in loop (creates new String each iteration)
String result = "";
for (int i = 0; i < 100_000; i++) {
    result += "item" + i + ",";  // New String + StringBuilder each iteration!
}

// ✓ FIX: Use StringBuilder
StringBuilder sb = new StringBuilder(1_000_000);
for (int i = 0; i < 100_000; i++) {
    sb.append("item").append(i).append(",");
}
String result = sb.toString();

// ✗ BAD: Autoboxing in loop
long sum = 0;
for (Long value : listOfLongs) {
    sum += value;  // Unboxing Long → long on every iteration
}

// ✓ FIX: Use primitive streams
long sum = listOfLongs.stream().mapToLong(Long::longValue).sum();
```

```
Object Creation Reduction Techniques:
═════════════════════════════════════
✓ Use primitives (int, long, double) instead of wrappers (Integer, Long, Double)
✓ StringBuilder instead of String concatenation
✓ Object pooling for expensive objects (DB connections, threads)
✓ Reuse formatters (DateTimeFormatter is thread-safe, create once)
✓ Avoid unnecessary streams in hot paths — use for-loop
✓ Use flyweight pattern for repeated immutable objects
✓ Pre-size collections: new ArrayList<>(expectedSize)
```

---

### Q47: How can poor exception handling crash long-running services?

**Answer:**

```java
// PROBLEM 1: Catching and swallowing exceptions silently
// ══════════════════════════════════════════════════════
@Scheduled(fixedRate = 60000)
public void syncData() {
    try {
        List<Record> records = fetchFromExternalAPI();
        for (Record r : records) {
            try {
                processRecord(r);
            } catch (Exception e) {
                // ✗ Swallowed! If processRecord fails for ALL records,
                // the sync runs every minute, does nothing, and nobody knows
            }
        }
    } catch (Exception e) {
        // ✗ Silently failing — service LOOKS healthy but data is stale
    }
}

// ✓ FIX: Log, count failures, alert
@Scheduled(fixedRate = 60000)
public void syncData() {
    int failures = 0;
    try {
        List<Record> records = fetchFromExternalAPI();
        for (Record r : records) {
            try {
                processRecord(r);
            } catch (Exception e) {
                failures++;
                log.error("Failed to process record {}: {}", r.getId(), e.getMessage());
            }
        }
        if (failures > records.size() * 0.5) {
            alertService.sendAlert("Sync failing for >50% records!");
        }
    } catch (Exception e) {
        log.error("Sync completely failed", e);
        alertService.sendAlert("Data sync DOWN: " + e.getMessage());
    }
}
```

```java
// PROBLEM 2: Catching Throwable/Error — hiding fatal issues
// ══════════════════════════════════════════════════════════
try {
    processRequest();
} catch (Throwable t) {
    // ✗ Catches OutOfMemoryError, StackOverflowError too!
    // App continues in a BROKEN state
    log.error("Error", t);
}

// ✓ FIX: Only catch Exception, let Errors propagate
try {
    processRequest();
} catch (Exception e) {
    log.error("Request failed", e);
    // Errors (OOM, SOF) will propagate up and crash the JVM — which is correct!
}
```

```java
// PROBLEM 3: Exception in one item kills the entire batch
// ═══════════════════════════════════════════════════════
public void processBatch(List<Order> orders) {
    for (Order order : orders) {
        validate(order);     // Throws on bad data
        enrich(order);       // If order 5 fails, orders 6-1000 are SKIPPED
        save(order);
    }
}

// ✓ FIX: Isolate failures per item
public BatchResult processBatch(List<Order> orders) {
    List<Order> succeeded = new ArrayList<>();
    List<FailedOrder> failed = new ArrayList<>();

    for (Order order : orders) {
        try {
            validate(order);
            enrich(order);
            save(order);
            succeeded.add(order);
        } catch (Exception e) {
            failed.add(new FailedOrder(order, e.getMessage()));
            log.warn("Order {} failed: {}", order.getId(), e.getMessage());
        }
    }
    return new BatchResult(succeeded, failed);  // Caller sees what failed
}
```

```java
// PROBLEM 4: Exception in finally block masks original exception
// ══════════════════════════════════════════════════════════════
try {
    connection = getConnection();
    execute(connection);           // Throws SQLException
} finally {
    connection.close();            // ✗ Also throws! Original exception is LOST
}

// ✓ FIX: Use try-with-resources — handles suppressed exceptions properly
try (Connection conn = getConnection()) {
    execute(conn);
    // If both execute() AND close() throw, close() becomes "suppressed"
    // Original exception is preserved
}
```

---

### Q48: Why does logging become a performance killer in production?

**Answer:**

```
Logging Impact:
═══════════════

DEBUG level in production with 10,000 req/sec:
  Each request logs 20 lines → 200,000 log lines/sec
  Each line: ~200 bytes → 40 MB/sec of log data
  Disk I/O becomes bottleneck → requests queue up → latency increases

  Synchronous logging:
    Thread writes log → waits for disk I/O → continues processing
    200,000 × 1ms I/O = thread blocked for significant time

  String formatting overhead:
    log.debug("Processing user " + user.toString() + " with order " + order.toString());
    → toString() and concatenation happen EVEN IF debug level is disabled!
```

```java
// ✗ BAD: String concatenation evaluated even when DEBUG is off
log.debug("User " + user.getName() + " placed order " + order.getId());
// user.getName() + string concat happens BEFORE debug() checks the level
// With 10K req/sec → millions of wasted string operations

// ✓ FIX: Use parameterized logging (SLF4J)
log.debug("User {} placed order {}", user.getName(), order.getId());
// Arguments are only evaluated IF debug level is enabled!

// ✓ Even better for expensive operations:
if (log.isDebugEnabled()) {
    log.debug("Full order details: {}", order.toDetailedString());  // Expensive call
}
```

```java
// ✗ BAD: Logging entire objects / large payloads
log.info("Received request: {}", requestBody);  // 50KB JSON per request!
// 10K req/sec × 50KB = 500 MB/sec of log data!

// ✓ FIX: Log only essential fields
log.info("Received request: userId={}, orderId={}", req.getUserId(), req.getOrderId());
```

```java
// ✗ BAD: Logging inside hot loops
for (Transaction t : transactions) {  // 1 million items
    log.debug("Processing transaction: {}", t.getId());  // 1M log lines!
    process(t);
}

// ✓ FIX: Log summary or at intervals
log.info("Processing {} transactions", transactions.size());
int processed = 0;
for (Transaction t : transactions) {
    process(t);
    if (++processed % 10_000 == 0) {
        log.info("Progress: {}/{}", processed, transactions.size());
    }
}
log.info("Completed processing {} transactions", processed);
```

```
Logging Best Practices for Production:
═══════════════════════════════════════
✓ Use ASYNC logging (Logback AsyncAppender — logs are queued, not blocking)
✓ Set production level to INFO or WARN (never DEBUG in prod)
✓ Use parameterized logging: log.info("msg {}", var)  NOT  "msg " + var
✓ Log size limits: truncate large payloads
✓ Don't log inside loops — log summaries
✓ Don't log sensitive data (passwords, tokens, PII)
✓ Use structured logging (JSON format) for log aggregation
✓ Rotate log files (don't let them grow to GBs)
```

```xml
<!-- Logback async appender configuration -->
<appender name="ASYNC" class="ch.qos.logback.classic.AsyncAppender">
    <queueSize>1024</queueSize>
    <discardingThreshold>0</discardingThreshold>
    <appender-ref ref="FILE" />
</appender>
```

---

### Q49: How do class loaders cause memory leaks?

**Answer:**

This is common in **application servers** (Tomcat, WildFly) and **hot-reload** frameworks where classes are reloaded.

```
How ClassLoader Leak Happens:
═════════════════════════════

Normal: Undeploy WAR → old ClassLoader + all its classes → garbage collected

Leak:   Undeploy WAR → old ClassLoader STILL referenced → NEVER garbage collected
        → All classes loaded by it stay in memory
        → Redeploy 10 times = 10 copies of all classes in memory!
        → Eventually: OutOfMemoryError: Metaspace
```

```java
// CAUSE 1: ThreadLocal holding class from old ClassLoader
// ═══════════════════════════════════════════════════════
// Thread pool thread survives redeploy
// ThreadLocal holds an object → object's class → class's ClassLoader
// Old ClassLoader cannot be GC'd → ALL its classes stay in memory

// CAUSE 2: Static field referencing class from webapp
// ═══════════════════════════════════════════════════
// JVM system class (e.g., JDBC DriverManager) holds reference to
// a JDBC driver loaded by webapp ClassLoader
// System ClassLoader → Driver → Webapp ClassLoader (pinned!)

// CAUSE 3: Shutdown hooks not cleaned up
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    // This thread references classes from webapp ClassLoader
    // On redeploy: hook still registered → ClassLoader pinned
}));

// CAUSE 4: Logging framework holding references
// SLF4J / Log4j static loggers hold references to webapp classes
```

```java
// ✓ Prevention in Spring Boot / Tomcat:
// 1. Always clean ThreadLocals in filter
@Component
public class CleanupFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                     FilterChain chain) throws Exception {
        try {
            chain.doFilter(req, res);
        } finally {
            MyContext.clear();  // Clean all ThreadLocals after every request
        }
    }
}

// 2. Deregister JDBC drivers on shutdown
@PreDestroy
public void cleanup() {
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    while (drivers.hasMoreElements()) {
        Driver driver = drivers.nextElement();
        if (driver.getClass().getClassLoader() == getClass().getClassLoader()) {
            DriverManager.deregisterDriver(driver);
        }
    }
}
```

```
Diagnosis:
══════════
Error: java.lang.OutOfMemoryError: Metaspace

# Increase Metaspace (temporary fix)
-XX:MaxMetaspaceSize=512m

# Find the leak
jmap -clstats <PID>                    # ClassLoader statistics
jcmd <PID> VM.classloader_stats        # Detailed loader info

# Look for: multiple ClassLoaders with the same name
# → Indicates old ClassLoaders not being GC'd
```

---

### Q50: What Java decision caused a real production problem? (Scenario-based)

**Answer:**

Here are real-world production incidents that interviewers expect you to discuss:

```
INCIDENT 1: HashMap in Multi-threaded Code (CPU 100%)
═════════════════════════════════════════════════════

What happened:
  - Shared HashMap used across threads (not ConcurrentHashMap)
  - Under load: two threads resize the HashMap simultaneously
  - Java 7: LinkedList in bucket formed a CYCLE (circular reference)
  - Thread doing get() enters infinite loop → CPU core pinned at 100%
  - App unresponsive, had to be killed

Root cause: HashMap is NOT thread-safe (no synchronization)
Fix: Replaced HashMap with ConcurrentHashMap
Lesson: Always check if a data structure is used by multiple threads
```

```
INCIDENT 2: Connection Pool Exhaustion (App Freezes)
════════════════════════════════════════════════════

What happened:
  - HikariCP pool: 10 connections
  - Code opened connection but didn't close on exception path
  - After 10 leaked connections → pool exhausted
  - Every new request waits for connection → 30 sec timeout → 503 error
  - App LOOKS alive (health check passes) but EVERY request fails

Root cause:
  Connection conn = dataSource.getConnection();
  // ... exception thrown here ...
  conn.close();  // Never reached!

Fix: try-with-resources
  try (Connection conn = dataSource.getConnection()) { ... }

Lesson: ALWAYS use try-with-resources for closeable resources
```

```
INCIDENT 3: Retry Storm Took Down Payment Service
══════════════════════════════════════════════════

What happened:
  - Payment service had 2 second timeout
  - Database was slow (3 sec response) due to maintenance
  - Each failed request retried 5 times immediately (no backoff)
  - 100 req/sec × 5 retries = 500 req/sec to an already overloaded DB
  - DB crashed → payment service crashed → order service crashed → FULL OUTAGE

Fix:
  - Exponential backoff (1s, 2s, 4s)
  - Circuit breaker (stop retrying after 50% failure rate)
  - Bulkhead pattern (isolate payment calls from other functionality)

Lesson: Retries without backoff and circuit breaker are a ticking time bomb
```

```
INCIDENT 4: DEBUG Logging in Production (Latency 10x)
═════════════════════════════════════════════════════

What happened:
  - Developer set log level to DEBUG to troubleshoot an issue
  - Forgot to revert it back to INFO
  - 50,000 req/sec × 30 debug lines each = 1.5M log lines/sec
  - Synchronous file logging → disk I/O saturated
  - P99 latency went from 20ms to 2000ms
  - Discovered after 3 days when customers complained

Fix: Changed back to INFO, switched to async logging
Lesson: Never use DEBUG in production; use dynamic log level change (Actuator)
```

```
INCIDENT 5: OutOfMemoryError from Unbounded Cache
══════════════════════════════════════════════════

What happened:
  - In-memory HashMap cache for user sessions
  - Sessions added but never removed (no TTL, no eviction)
  - Day 1: 10,000 sessions = 100MB → fine
  - Day 30: 3,000,000 sessions = 12GB → OOM
  - GC pauses grew from 10ms to 8 seconds before crash

Fix: Replaced HashMap with Caffeine cache (max 50,000 entries, 30 min TTL)
Lesson: Every cache MUST have a maximum size AND TTL eviction policy
```

**How to answer this question in an interview:**
```
Structure your answer as:
1. CONTEXT — What was the system/service?
2. SYMPTOM — What did users/monitoring see?
3. ROOT CAUSE — What was the actual code/design issue?
4. FIX — What did you change?
5. LESSON — What did you learn / what process changed?

Pick ONE incident you've actually experienced (or can explain deeply).
Interviewers value depth over breadth here.
```

---

### Q51: You see OutOfMemoryError even though heap size looks sufficient. How?

**Answer:**

"Sufficient heap" is misleading. OOM doesn't always mean heap is too small.

```
OOM Types — Not all are about heap!
════════════════════════════════════

1. java.lang.OutOfMemoryError: Java heap space
   → Heap IS full — objects consuming all memory

2. java.lang.OutOfMemoryError: Metaspace
   → Class metadata area full — too many classes loaded
   → NOT related to -Xmx heap size at all!

3. java.lang.OutOfMemoryError: GC overhead limit exceeded
   → GC spending > 98% of time collecting, recovering < 2% memory
   → Heap "has space" but it's all LIVE objects → nothing to collect

4. java.lang.OutOfMemoryError: unable to create new native thread
   → OS limit on threads reached (not a heap issue!)
   → Each thread = ~1MB native stack (OUTSIDE heap)
   → 4000 threads = 4GB native memory + heap = total RAM exceeded

5. java.lang.OutOfMemoryError: Direct buffer memory
   → NIO direct buffers (ByteBuffer.allocateDirect) exhausted
   → Limited by -XX:MaxDirectMemorySize, NOT -Xmx

6. java.lang.OutOfMemoryError: Compressed class space
   → Related to Metaspace, limited by -XX:CompressedClassSpaceSize
```

```java
// SCENARIO: Heap looks "50% free" but OOM happens
// ═══════════════════════════════════════════════

// Heap: 4GB, used: 2GB (50% free!)
// But trying to allocate a 3GB array:
byte[] data = new byte[3_000_000_000];  // OOM!
// 2GB used + 3GB needed = 5GB > 4GB heap

// Even worse: FRAGMENTATION
// 2GB free but in 1000 small chunks (2MB each)
// Need one contiguous 500MB object → OOM
// GC with compaction would help but if it's young gen → no compaction

// SCENARIO: Memory is "free" but objects are in wrong generation
// Old Gen: 3.5GB / 3.5GB (FULL)  → Full GC triggered
// Young Gen: 200MB / 500MB (free) → doesn't help Old Gen
// Result: GC overhead limit exceeded even though Young Gen has space
```

```java
// SCENARIO: Thread OOM — heap is fine, but can't create threads
public void handleRequest() {
    new Thread(() -> {
        processAsync();  // Creating thread per request!
    }).start();
}
// 10,000 concurrent requests = 10,000 threads
// 10,000 × 1MB stack = 10GB native memory → OS says NO
// Error: unable to create new native thread
// Heap is only 2GB and fine!

// ✓ FIX: Use thread pool, not new Thread()
ExecutorService pool = Executors.newFixedThreadPool(50);
pool.submit(() -> processAsync());
```

```bash
# Diagnosis — which type of OOM?
# 1. Enable heap dump on OOM
java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heap.hprof -jar app.jar

# 2. Check what's consuming memory
jmap -histo:live <PID> | head -30    # Top objects by count and size

# 3. Check Metaspace
jcmd <PID> VM.metaspace             # Metaspace usage

# 4. Check native memory
jcmd <PID> VM.native_memory summary  # Requires -XX:NativeMemoryTracking=summary

# 5. Check thread count
jstack <PID> | grep "Thread" | wc -l
```

```
OOM Cheat Sheet:
┌────────────────────────────────┬──────────────────────────────────┐
│ OOM Type                       │ Fix                              │
├────────────────────────────────┼──────────────────────────────────┤
│ Java heap space                │ Increase -Xmx or fix leak       │
│ Metaspace                      │ Increase -XX:MaxMetaspaceSize    │
│ GC overhead limit exceeded     │ Fix memory leak (all objects live)│
│ Unable to create native thread │ Use thread pool, increase ulimit │
│ Direct buffer memory           │ Increase -XX:MaxDirectMemorySize │
│ Compressed class space         │ Increase -XX:CompressedClassSpaceSize│
└────────────────────────────────┴──────────────────────────────────┘
```

---

### Q52: Threads are available but requests are waiting. Why?

**Answer:**

This is one of the trickiest production issues — you look at thread pool metrics and see idle threads, but requests are queued.

```
Root Causes:
════════════

1. LOCK CONTENTION — threads idle but BLOCKED waiting for a lock
   ─────────────────────────────────────────────────────────────
   Thread-1: holds lock on synchronized(cache) → doing slow I/O
   Thread-2 to Thread-200: BLOCKED waiting for cache lock
   Thread pool says: 200 threads available
   Reality: 199 threads can't do anything

2. DATABASE CONNECTION POOL EXHAUSTION
   ──────────────────────────────────
   Tomcat: 200 threads (available!)
   HikariCP: 10 connections (all busy)
   Threads are free but WAITING for a DB connection
   → Requests sit in HikariCP queue, not Tomcat queue

3. RATE LIMITER / SEMAPHORE THROTTLING
   ────────────────────────────────────
   Code uses a Semaphore(5) → only 5 concurrent operations
   195 threads wait on semaphore.acquire()
   Thread pool says "idle" but work is throttled

4. I/O WAIT (not CPU-bound, not thread-bound)
   ───────────────────────────────────────────
   Thread calls external API → thread is RUNNABLE (not blocked)
   But it's waiting for network response
   Thread pool sees it as "active" but it's doing nothing useful

5. BLOCKING QUEUE BETWEEN PRODUCER AND CONSUMER
   ─────────────────────────────────────────────
   Request thread puts work on queue → returns "202 Accepted"
   Consumer threads process queue → queue is full → put() blocks
   Consumer is the bottleneck, not the thread pool
```

```java
// Example: synchronized method creates hidden bottleneck
public class CacheService {
    // ✗ BAD: Only ONE thread can access this at a time!
    public synchronized Object getFromCache(String key) {
        if (!cache.containsKey(key)) {
            Object value = database.query(key);  // 200ms DB call
            cache.put(key, value);               // While this runs,
        }                                        // ALL other threads WAIT
        return cache.get(key);
    }
}

// ✓ FIX: Use ConcurrentHashMap with computeIfAbsent
public class CacheService {
    private ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    public Object getFromCache(String key) {
        return cache.computeIfAbsent(key, k -> database.query(k));
        // Only blocks threads waiting for the SAME key, not all keys
    }
}
```

```bash
# Diagnosis: Find what threads are ACTUALLY doing
jstack <PID> > thread_dump.txt

# Look for:
# "BLOCKED" state → lock contention
grep -c "BLOCKED" thread_dump.txt

# "WAITING" on HikariCP → connection pool issue
grep "HikariPool" thread_dump.txt

# "WAITING" on Semaphore → throttling
grep "Semaphore\|acquire" thread_dump.txt

# "RUNNABLE" but in socket read → I/O wait
grep -A 5 "RUNNABLE" thread_dump.txt | grep "socketRead\|HttpClient"
```

---

### Q53: A deadlock occurs only under high load. How do you debug it?

**Answer:**

Deadlocks under high load are hard to reproduce because they need **specific timing** — two threads must acquire locks in opposite order at the same moment.

```
Why high load triggers deadlocks:
═════════════════════════════════

Low load:  Thread-A gets lock1, then lock2 → done → releases both
           Thread-B arrives later → gets both locks easily → no deadlock

High load: Thread-A gets lock1 (waiting for lock2)
           Thread-B gets lock2 (waiting for lock1)  ← DEADLOCK!
           This timing only happens when threads overlap

  Thread-A:  lock1 ──────────→ needs lock2 (held by B) → STUCK
  Thread-B:  lock2 ──────────→ needs lock1 (held by A) → STUCK
```

```java
// Classic deadlock example that only appears under load
class TransferService {
    public void transfer(Account from, Account to, double amount) {
        synchronized (from) {                   // Lock account A
            synchronized (to) {                 // Lock account B
                from.debit(amount);
                to.credit(amount);
            }
        }
    }
}

// Low load: transfer(A→B) finishes before transfer(B→A) starts → OK
// High load:
//   Thread-1: transfer(A→B) → locks A, waiting for B
//   Thread-2: transfer(B→A) → locks B, waiting for A
//   → DEADLOCK!

// ✓ FIX: Always lock in CONSISTENT ORDER (by ID)
public void transfer(Account from, Account to, double amount) {
    Account first = from.getId() < to.getId() ? from : to;
    Account second = from.getId() < to.getId() ? to : from;

    synchronized (first) {          // Always lock lower ID first
        synchronized (second) {     // Then higher ID
            from.debit(amount);
            to.credit(amount);
        }
    }
}

// ✓ FIX 2: Use tryLock with timeout (no permanent deadlock)
public void transfer(Account from, Account to, double amount) {
    ReentrantLock lockFrom = from.getLock();
    ReentrantLock lockTo = to.getLock();

    boolean gotBoth = false;
    while (!gotBoth) {
        boolean gotFrom = lockFrom.tryLock(100, TimeUnit.MILLISECONDS);
        boolean gotTo = lockTo.tryLock(100, TimeUnit.MILLISECONDS);
        gotBoth = gotFrom && gotTo;

        if (!gotBoth) {
            if (gotFrom) lockFrom.unlock();  // Release and retry
            if (gotTo) lockTo.unlock();
            Thread.sleep(ThreadLocalRandom.current().nextInt(50));  // Random backoff
        }
    }
    try {
        from.debit(amount);
        to.credit(amount);
    } finally {
        lockFrom.unlock();
        lockTo.unlock();
    }
}
```

```bash
# DETECTION: Thread dump reveals deadlocks automatically
jstack <PID>

# JVM automatically detects deadlocks and prints:
# "Found one Java-level deadlock:"
# =============================
# "Thread-1":
#   waiting to lock monitor 0x00007f... (object 0x000000..., a Account)
#   which is held by "Thread-2"
# "Thread-2":
#   waiting to lock monitor 0x00007f... (object 0x000000..., a Account)
#   which is held by "Thread-1"

# Programmatic detection:
ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
long[] deadlockedThreads = tmx.findDeadlockedThreads();
if (deadlockedThreads != null) {
    ThreadInfo[] infos = tmx.getThreadInfo(deadlockedThreads, true, true);
    for (ThreadInfo info : infos) {
        System.out.println(info);
    }
}
```

```
Prevention Rules:
═════════════════
✓ Lock ordering: Always acquire locks in the same global order
✓ Lock timeout: Use tryLock() with timeout instead of synchronized
✓ Minimize lock scope: Don't hold locks during I/O or slow operations
✓ Use concurrent collections instead of manual synchronization
✓ Avoid nested locks when possible
✓ Use java.util.concurrent over synchronized (more tools: tryLock, lockInterruptibly)
```

---

### Q54: GC pauses suddenly increase after a minor code change. What changed?

**Answer:**

A "minor" code change can dramatically change the object allocation pattern, which changes GC behavior.

```
Common Code Changes That Alter GC:
═══════════════════════════════════

1. CHANGED DATA STRUCTURE
   → Before: List<Integer> (1000 integers = ~16KB)
   → After: List<Map<String, Object>> (1000 maps = ~500KB)
   → 30x more objects → much more GC work

2. REMOVED .stream().limit() OR ADDED .collect()
   → Before: stream().limit(10).forEach(...)   → 10 objects
   → After: stream().collect(toList())         → ALL objects materialized
   → If source has 1M items → 1M objects created

3. CHANGED RETURN TYPE TO HOLD REFERENCES LONGER
   → Before: method returns primitive (int) → no GC impact
   → After: method returns new Object() → promotes to old gen if method is slow

4. ADDED LOGGING WITH STRING CONCATENATION
   → log.debug("User " + user + " order " + order)
   → Creates new String objects even when DEBUG is off
   → At 10K req/sec → millions of garbage strings

5. MOVED FROM STREAMS TO FOR-EACH (or vice versa)
   → Streams create intermediate objects (Iterator, Spliterator, pipeline)
   → For-each on simple iterations = zero extra objects

6. ADDED CACHING THAT PROMOTES OBJECTS TO OLD GEN
   → Before: Objects created and GC'd quickly (Young Gen, minor GC)
   → After: Cached objects survive Young Gen → promoted to Old Gen
   → Old Gen fills up → triggers MAJOR GC (much longer pause!)
```

```java
// Example: "Minor" change that doubled GC pressure
// BEFORE:
public void processOrders(List<Order> orders) {
    for (Order order : orders) {
        process(order.getId(), order.getAmount());  // Primitives, no allocation
    }
}

// AFTER: "Improved" with DTO for clarity
public void processOrders(List<Order> orders) {
    List<OrderDTO> dtos = orders.stream()
        .map(o -> new OrderDTO(o.getId(), o.getAmount(), o.getStatus()))  // New object per order!
        .collect(Collectors.toList());  // Another new ArrayList!
    dtos.forEach(dto -> process(dto));
}
// If orders has 100K items → 100K new OrderDTO objects + new ArrayList → GC pressure
```

```bash
# Diagnosis: Compare GC before and after the change
# Before deployment:
jstat -gcutil <PID> 1000 10    # GC stats every 1 sec, 10 samples

# After deployment:
jstat -gcutil <PID> 1000 10

# Compare:
# - YGC (young gen collections): frequency increased?
# - YGCT (young gen time): increased?
# - FGC (full GC count): started happening?
# - FGCT (full GC time): how long?

# Allocation profiling (which code allocates most):
java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints \
     -XX:StartFlightRecording=duration=60s,filename=alloc.jfr -jar app.jar
# Open .jfr in JDK Mission Control → Allocation tab → shows allocation hotspots
```

---

### Q55: JVM doesn't exit even after main() finishes. What's holding it?

**Answer:**

The JVM exits only when **all non-daemon threads** have finished. If any non-daemon thread is still running, JVM stays alive.

```
JVM Shutdown Rule:
══════════════════
  JVM exits when:
    All NON-DAEMON threads have terminated
    OR
    System.exit() is called

  Daemon threads: Background helpers (GC, finalizer)
    → Automatically killed when JVM shuts down
    → Set via: thread.setDaemon(true) BEFORE thread.start()

  Non-daemon threads: Default for new Thread()
    → JVM waits for ALL of them to finish
    → If even ONE is running, JVM stays alive
```

```java
// CAUSE 1: Non-daemon thread still running
public static void main(String[] args) {
    Thread worker = new Thread(() -> {
        while (true) {
            // Infinite loop — non-daemon thread
            // JVM will NEVER exit!
        }
    });
    worker.start();
    System.out.println("main() finished");
    // main() exits but JVM stays alive because 'worker' is non-daemon
}

// ✓ FIX: Make it daemon
worker.setDaemon(true);  // Must call BEFORE start()
worker.start();
// Now JVM exits when main() finishes

// CAUSE 2: ExecutorService not shut down
public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(5);
    pool.submit(() -> System.out.println("Task done"));
    // main() finishes, but pool's threads are non-daemon → JVM hangs!

    // ✓ FIX: Always shut down executors
    pool.shutdown();  // Graceful: finishes running tasks, rejects new ones
    // OR
    pool.shutdownNow();  // Aggressive: interrupts running tasks
}

// CAUSE 3: Timer or ScheduledExecutorService running
Timer timer = new Timer();  // Non-daemon by default!
timer.scheduleAtFixedRate(task, 0, 1000);
// ✓ FIX: new Timer(true) for daemon timer

// CAUSE 4: Open ServerSocket / HTTP server listening
ServerSocket server = new ServerSocket(8080);
server.accept();  // Blocks forever on non-daemon thread

// CAUSE 5: AWT/Swing event dispatch thread
// Creating a JFrame starts the EDT (non-daemon)
// JVM stays alive until all windows are disposed

// CAUSE 6: Shutdown hooks
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    // If this hook takes too long or hangs, JVM doesn't fully exit
    slowCleanup();  // Blocks for 5 minutes!
}));
```

```bash
# Diagnosis: Find what threads are keeping JVM alive
jstack <PID> | grep -v "daemon"  # Show only non-daemon threads

# Or programmatically:
Thread.getAllStackTraces().forEach((thread, stack) -> {
    if (!thread.isDaemon()) {
        System.out.println("Non-daemon: " + thread.getName());
        for (StackTraceElement e : stack) {
            System.out.println("  " + e);
        }
    }
});
```

---

### Q56: App behaves differently on Java 8 vs Java 17. Why?

**Answer:**

```
Major Breaking / Behavioral Changes Between Java 8 → 17:
═════════════════════════════════════════════════════════

1. STRING CONCATENATION CHANGED (Java 9)
   ──────────────────────────────────────
   Java 8:  "a" + b + "c" → new StringBuilder().append("a").append(b).append("c").toString()
   Java 9+: Uses invokedynamic → StringConcatFactory (different bytecode)
   Impact: Usually faster, but code that depends on StringBuilder behavior may break

2. GARBAGE COLLECTOR DEFAULT CHANGED (Java 9)
   ──────────────────────────────────────────
   Java 8:  Parallel GC (throughput optimized)
   Java 9+: G1 GC (latency optimized)
   Impact: Different pause times, different memory patterns
   → App tuned for Parallel GC may perform differently on G1

3. STRONG ENCAPSULATION OF JDK INTERNALS (Java 9+, strict in Java 17)
   ────────────────────────────────────────────────────────────────────
   Java 8:  sun.misc.Unsafe, com.sun.* → freely accessible
   Java 17: IllegalAccessError at runtime!

   Libraries affected: Lombok, Hibernate, Jackson, Mockito (older versions)
   → Crash with: "module java.base does not export sun.misc to unnamed module"
   → Fix: --add-opens java.base/java.lang=ALL-UNNAMED (workaround)
           Or update libraries to Java 17 compatible versions
```

```java
// 4. HASHMAP ITERATION ORDER CHANGED (Java 9)
//    ─────────────────────────────────────────
//    Java 8: HashMap iteration order was "accidentally" consistent
//    Java 9: Intentionally randomized per JVM run
//    Code that depended on insertion order breaks!
//    Fix: Use LinkedHashMap if order matters

// 5. DEPRECATED APIs REMOVED
//    ───────────────────────
//    Java 8:  Thread.stop(), Runtime.runFinalizersOnExit() → deprecated
//    Java 17: REMOVED → compilation error
//    Fix: Use Thread.interrupt() instead of Thread.stop()

// 6. LOCALE DATA CHANGED (Java 9)
//    ─────────────────────────────
//    Java 8:  JRE locale data (Oracle's custom)
//    Java 9+: CLDR locale data (Unicode standard) → DEFAULT
//    Impact: Date/currency formatting may produce different output!
//    "March" → "Mar" in some locales, decimal separator changes

// 7. URL.equals() AND hashCode() BEHAVIOR
//    Java 8: URL.equals() does DNS lookup! (blocking, surprising)
//    Still same in Java 17 but URI is preferred
//    Fix: Use URI instead of URL for comparison
```

```java
// 8. NEW FEATURES THAT CHANGE CODING PATTERNS
//    ──────────────────────────────────────────

// Records (Java 16) — change how DTOs are written
// Java 8:
public class UserDTO {
    private final String name;
    private final int age;
    public UserDTO(String name, int age) { this.name = name; this.age = age; }
    // + getters, equals, hashCode, toString (50+ lines)
}
// Java 17:
public record UserDTO(String name, int age) {}  // Done! 1 line!

// Sealed Classes (Java 17)
public sealed interface Shape permits Circle, Rectangle, Triangle {}
// Only these 3 can implement Shape

// Pattern Matching instanceof (Java 16)
// Java 8:
if (obj instanceof String) {
    String s = (String) obj;  // Explicit cast needed
    System.out.println(s.length());
}
// Java 17:
if (obj instanceof String s) {  // Cast built into instanceof
    System.out.println(s.length());
}

// Text Blocks (Java 15)
// Java 8:
String json = "{\n" +
              "  \"name\": \"Alice\",\n" +
              "  \"age\": 25\n" +
              "}";
// Java 17:
String json = """
    {
        "name": "Alice",
        "age": 25
    }
    """;
```

```
Migration Checklist (Java 8 → 17):
═══════════════════════════════════
✓ Update all dependencies (Lombok, Spring, Jackson, Mockito)
✓ Replace removed APIs (javax.* → jakarta.* for EE)
✓ Test date/locale formatting
✓ Check for reflection on JDK internals
✓ Review GC configuration (Parallel → G1 different tuning)
✓ Check HashMap iteration order dependencies
✓ Run full test suite on Java 17 before production
```

---

### Q57: High latency but low CPU and memory usage. What could be blocking?

**Answer:**

Low CPU + Low memory + High latency = **threads are WAITING for something external**.

```
Root Causes (in order of likelihood):
═════════════════════════════════════

1. DATABASE QUERY WAITING FOR LOCK
   → Another transaction holds a row lock
   → Your query waits → thread blocked → latency increases
   → CPU idle because thread is sleeping, not computing
   → Check: SELECT * FROM information_schema.innodb_lock_waits;

2. DNS RESOLUTION DELAY
   → Every HTTP call resolves hostname first
   → DNS server slow → 2-5 sec added to EVERY request
   → CPU does nothing during DNS wait
   → Check: time nslookup your-service-name

3. EXTERNAL API / NETWORK LATENCY
   → Calling downstream service with 3 sec response time
   → Thread waits on socket read → no CPU used
   → If no timeout set: can wait forever!

4. GARBAGE COLLECTOR PAUSES (intermittent)
   → Most requests fast → some hit GC pause → latency spike
   → Average looks fine → P99 terrible
   → Check: -verbose:gc or -Xlog:gc

5. TCP CONNECTION ESTABLISHMENT
   → No keep-alive → every request opens new TCP connection
   → TCP handshake: 1 RTT (round trip time)
   → TLS handshake: 2 more RTTs
   → If service is in another region: 3 × 50ms = 150ms overhead per request

6. THREAD POOL QUEUE WAIT TIME
   → Thread pool is small → requests queued
   → Queue wait time added to response time
   → CPU idle because threads are busy but work is queued
```

```java
// DIAGNOSIS: Measure each step to find the bottleneck
public Order getOrder(Long id) {
    long start = System.nanoTime();

    long t1 = System.nanoTime();
    Order order = orderRepo.findById(id).orElseThrow();
    log.info("DB query: {}ms", (System.nanoTime() - t1) / 1_000_000);

    long t2 = System.nanoTime();
    User user = userClient.getUser(order.getUserId());
    log.info("User API: {}ms", (System.nanoTime() - t2) / 1_000_000);

    long t3 = System.nanoTime();
    Payment pay = paymentClient.getPayment(order.getPaymentId());
    log.info("Payment API: {}ms", (System.nanoTime() - t3) / 1_000_000);

    log.info("Total: {}ms", (System.nanoTime() - start) / 1_000_000);
    return enrich(order, user, pay);
}

// Output:
// DB query: 15ms
// User API: 2500ms   ← Found it! User service is the bottleneck
// Payment API: 50ms
// Total: 2565ms
```

```bash
# Quick diagnosis from server
# 1. Check if threads are in WAITING state
jstack <PID> | grep -c "WAITING\|TIMED_WAITING"

# 2. Check what they're waiting on
jstack <PID> | grep -A 3 "WAITING" | grep "at "

# 3. Check network (DNS, connections)
time nslookup your-database-host
ss -s                                    # Socket statistics
netstat -an | grep ESTABLISHED | wc -l   # Active connections

# 4. Check DB for lock waits
# MySQL: SHOW PROCESSLIST;
# PostgreSQL: SELECT * FROM pg_stat_activity WHERE wait_event IS NOT NULL;
```

---

### Q58: JVM tuning improved latency but reduced throughput. Explain.

**Answer:**

Latency and throughput are **opposing goals** in GC tuning. Improving one often degrades the other.

```
The Fundamental Trade-off:
══════════════════════════

THROUGHPUT = Total work done per unit time
  → Maximize by: letting GC run less frequently, bigger pauses OK
  → Parallel GC: stops everything, collects fast → high throughput

LATENCY = Response time for individual requests
  → Minimize by: running GC more frequently with smaller pauses
  → G1/ZGC: concurrent collection → low latency but more CPU overhead

┌─────────────────────┬──────────────────┬──────────────────┐
│ GC                  │ Throughput       │ Latency (pause)  │
├─────────────────────┼──────────────────┼──────────────────┤
│ Parallel GC         │ BEST (highest)   │ WORST (longest)  │
│ G1 GC               │ Good             │ Good             │
│ ZGC                 │ Lower (~10-15%)  │ BEST (< 1ms)    │
│ Shenandoah          │ Lower (~10-15%)  │ BEST (< 1ms)    │
└─────────────────────┴──────────────────┴──────────────────┘

Example:
  Parallel GC:  Throughput = 10,000 req/sec, P99 = 500ms
  ZGC:          Throughput = 8,500 req/sec,  P99 = 2ms

  ZGC has 15% less throughput BUT 250x better tail latency
```

```
WHY throughput drops with low-latency GC:
═════════════════════════════════════════

1. CONCURRENT GC USES CPU
   → ZGC runs GC threads ALONGSIDE application threads
   → If 8 cores: 6 for app + 2 for GC = 25% CPU overhead
   → Parallel GC: stops app completely but uses ALL 8 cores for GC → faster collection

2. WRITE BARRIERS
   → Concurrent GC needs "write barriers" (extra code on every reference write)
   → Small overhead per operation → adds up at high throughput

3. MORE FREQUENT COLLECTIONS
   → Small pauses = GC runs more often = more total GC work
   → Each collection has fixed overhead (setup, finalization)
```

```bash
# Tuning for THROUGHPUT (batch processing, background jobs):
java -XX:+UseParallelGC \
     -XX:ParallelGCThreads=8 \
     -Xms4g -Xmx4g \
     -jar batch-app.jar

# Tuning for LATENCY (user-facing APIs):
java -XX:+UseZGC \
     -Xms4g -Xmx4g \
     -jar api-app.jar

# Tuning for BALANCED (most apps):
java -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=50 \
     -Xms4g -Xmx4g \
     -jar app.jar
```

```
Decision Guide:
═══════════════
Batch processing / data pipeline  → Parallel GC (max throughput)
Web APIs (moderate traffic)       → G1 GC (balanced)
Low-latency APIs (P99 matters)    → ZGC or Shenandoah
Trading systems / real-time       → ZGC with large heap
Microservices (small heap < 1GB)  → G1 GC or Serial GC
```

---

### Q59: Background jobs affect API response time. How do you isolate them?

**Answer:**

```
The Problem:
════════════
  Same JVM runs both API requests and background jobs.
  Background job does heavy computation or loads huge data.
  → Steals CPU from API threads
  → Fills heap → triggers GC → API gets paused
  → Saturates DB connection pool → API queries wait
```

```java
// ✗ PROBLEM: Background job shares everything with API
@Scheduled(fixedRate = 60000)
public void generateDailyReport() {
    List<Order> allOrders = orderRepo.findAll();      // 1M rows into heap!
    BigDecimal total = calculateRevenue(allOrders);    // CPU-heavy for 30 sec
    emailService.sendReport(total);                    // Holds DB connection
}
// Impact: GC spike + CPU spike + connection pool competition = slow API
```

```java
// ✓ FIX 1: Separate thread pool for background jobs (resource isolation)
@Bean("backgroundExecutor")
public Executor backgroundExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);       // Only 2 threads for background
    executor.setMaxPoolSize(3);
    executor.setThreadNamePrefix("bg-");
    return executor;
}

@Async("backgroundExecutor")
@Scheduled(fixedRate = 60000)
public void generateDailyReport() { ... }
// Now runs on dedicated pool, doesn't steal Tomcat threads
```

```java
// ✓ FIX 2: Separate DataSource for background jobs
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean("apiDataSource")
    @ConfigurationProperties("spring.datasource.api")
    public DataSource apiDataSource() {
        return DataSourceBuilder.create().build();  // Pool: 20 connections for API
    }

    @Bean("backgroundDataSource")
    @ConfigurationProperties("spring.datasource.background")
    public DataSource backgroundDataSource() {
        return DataSourceBuilder.create().build();  // Pool: 3 connections for jobs
    }
}
// Background jobs can't exhaust API's connection pool
```

```java
// ✓ FIX 3: Process in batches to reduce heap and GC impact
@Scheduled(fixedRate = 60000)
public void generateDailyReport() {
    BigDecimal total = BigDecimal.ZERO;
    int page = 0;
    Page<Order> batch;
    do {
        batch = orderRepo.findAll(PageRequest.of(page++, 1000));  // 1000 at a time
        total = total.add(calculateRevenue(batch.getContent()));
        // Each batch GC'd before next batch loaded → steady memory usage
    } while (batch.hasNext());
    emailService.sendReport(total);
}
```

```java
// ✓ FIX 4: Lower thread priority for background work
Thread.currentThread().setPriority(Thread.MIN_PRIORITY);  // OS gives API threads more CPU

// ✓ FIX 5: Run heavy jobs during off-peak hours
@Scheduled(cron = "0 0 3 * * ?")  // 3 AM daily
public void heavyReport() { ... }
```

```
Best Practices:
═══════════════
✓ Separate thread pool (don't compete with Tomcat)
✓ Separate DB connection pool (don't starve API)
✓ Batch processing (don't load 1M rows at once)
✓ Schedule during off-peak hours
✓ Lower thread priority
✗ Ideally: Run background jobs in a SEPARATE service/pod entirely
```

---

### Q60: Scaling instances made things worse. Why?

**Answer:**

Adding more instances doesn't help (and can hurt) when the bottleneck is **shared resources**.

```
Scenario:
═════════

1 instance:  500 req/sec, 100ms latency  → OK
3 instances: 500 req/sec, 300ms latency  → WORSE!
5 instances: 500 req/sec, 500ms latency  → EVEN WORSE!

WHY?
```

```
Root Causes:
════════════

1. DATABASE IS THE BOTTLENECK
   ──────────────────────────
   1 instance × 20 connections = 20 DB connections
   5 instances × 20 connections = 100 DB connections!

   Database max_connections = 100 → all consumed
   DB overloaded → every query slower → ALL instances slower

   Fix: Scale DB (read replicas), optimize queries, add caching

2. DISTRIBUTED LOCKING / CONTENTION
   ─────────────────────────────────
   Multiple instances fighting for same distributed lock (Redis, DB row lock)
   More instances = more contention = longer wait times

   Fix: Partition data so instances work on different subsets

3. THUNDERING HERD ON CACHE MISS
   ──────────────────────────────
   1 instance: cache miss → 1 DB query → cache populated
   5 instances: cache miss at same time → 5 identical DB queries!
   If cache TTL synced → ALL instances expire cache simultaneously

   Fix: Cache stampede lock (only 1 instance refreshes, others wait)

4. SHARED DOWNSTREAM SERVICE OVERLOADED
   ─────────────────────────────────────
   Each instance calls payment service:
   1 instance:  100 calls/sec → payment service OK
   5 instances: 500 calls/sec → payment service overloaded → slow for everyone!

   Fix: Rate limit per instance, circuit breaker

5. NETWORK SATURATION
   ───────────────────
   More instances = more inter-service calls = more network traffic
   Switch/LB bandwidth saturated → packet drops → retries → slower

6. CONNECTION POOL PER INSTANCE
   ────────────────────────────
   5 instances × 20 idle connections each = 100 idle connections
   DB maintains state for each → memory overhead on DB server
```

```
When Scaling Helps vs Doesn't Help:
════════════════════════════════════

SCALING HELPS when:
  ✓ Bottleneck is CPU on your app (compute-bound)
  ✓ Bottleneck is memory on your app
  ✓ Workload is stateless and parallelizable
  ✓ Downstream resources can handle increased load

SCALING DOESN'T HELP when:
  ✗ Bottleneck is database (shared resource)
  ✗ Bottleneck is external service (fixed capacity)
  ✗ Distributed locking creates contention
  ✗ Cache synchronization overhead increases
  ✗ Network becomes the bottleneck

Before scaling, ALWAYS ask:
  "What is the bottleneck? Will more instances help THAT specific bottleneck?"
```

---

### Java & Microservices – Real Interview Questions

> **Note:** Some microservices topics overlap with earlier questions. Cross-references are provided where applicable.
> Related: Q41 (thread pools), Q43 (retries/backoff), Q44/Q58 (GC tuning), Q45 (blocking calls), Q52 (threads waiting), Q57 (high latency low CPU), Q60 (scaling made things worse).
> Also see SpringBoot.md: Q17 (connection pools), Q18 (circuit breaker), Q22 (logging), Q25 (Docker), Q31 (async issues).

---

**Q61. Two microservices work perfectly in isolation but fail when connected. What do you check?**

**Answer:**

This is one of the most common microservice problems — **integration failures** despite passing unit/integration tests individually.

```
Root Causes (Check in this order):
═══════════════════════════════════

1. CONTRACT MISMATCH
   ─────────────────
   Service A sends:  { "userId": 123, "name": "Veer" }
   Service B expects: { "user_id": 123, "userName": "Veer" }

   → JSON field naming convention differs (camelCase vs snake_case)
   → Missing @JsonProperty annotations
   → Response wrapper differences (one returns data directly, other wraps in { "data": ... })

2. TIMEOUT CONFIGURATION
   ──────────────────────
   Service A timeout: 5 seconds
   Service B response time: 7 seconds under load

   → Works in dev (fast responses) but fails in prod (slow responses)
   → Different timeout defaults between RestTemplate, WebClient, Feign

3. SERIALIZATION/DESERIALIZATION
   ─────────────────────────────
   // Service A (Jackson default)
   ObjectMapper mapper = new ObjectMapper();

   // Service B (with custom config)
   mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

   → Service A adds a new field → Service B rejects it
   → Date format differences: "2024-01-15" vs "2024-01-15T10:30:00Z"
   → Enum handling: "ACTIVE" vs 1

4. NETWORK / SERVICE DISCOVERY
   ───────────────────────────
   → DNS resolution fails in Kubernetes
   → Service registered with wrong port
   → Load balancer health check passes but app isn't fully initialized
   → SSL/TLS certificate mismatch between services

5. AUTHENTICATION / AUTHORIZATION
   ──────────────────────────────
   → JWT token not forwarded between services
   → Token expires during inter-service call chain
   → Service-to-service auth (mTLS) not configured
   → CORS issues when called through API gateway

6. DATA FORMAT / ENCODING
   ──────────────────────
   → Character encoding mismatch (UTF-8 vs ISO-8859-1)
   → Content-Type header missing or wrong
   → Service A sends XML, Service B expects JSON
```

```
Debugging Approach:
══════════════════

Step 1: Check the EXACT request/response
   → Use Postman/curl to manually call Service B with Service A's exact payload
   → Compare headers, body, content-type

Step 2: Check logs on BOTH sides
   → Enable request/response logging in both services

   // Spring Boot — log all RestTemplate calls
   @Bean
   public RestTemplate restTemplate() {
       RestTemplate rt = new RestTemplate();
       rt.setInterceptors(List.of(new LoggingInterceptor()));
       return rt;
   }

Step 3: Check contract compatibility
   → Use Consumer-Driven Contract Testing (Spring Cloud Contract, Pact)

   // Pact test on Consumer side
   @Pact(consumer = "OrderService", provider = "UserService")
   public RequestResponsePact getUserPact(PactDslWithProvider builder) {
       return builder
           .given("user 123 exists")
           .uponReceiving("get user by id")
           .path("/api/users/123")
           .method("GET")
           .willRespondWith()
           .status(200)
           .body(newJsonBody(o -> {
               o.integerType("userId", 123);
               o.stringType("name", "Veer");
           }).build())
           .toPact();
   }

Step 4: Test with realistic data volumes
   → Services might work with small payloads but fail with large ones
   → Pagination differences, max response size limits
```

```
Prevention:
══════════
1. Contract testing in CI/CD pipeline
2. Shared API schemas (OpenAPI/Swagger)
3. Integration test environment that mirrors production topology
4. Canary deployments for inter-service changes
5. Always design for backward compatibility (additive changes only)
```

---

**Q62. How do timeout settings between services affect overall system stability?**

**Answer:**

Timeout misconfiguration is one of the **top 3 causes of microservice outages**. It creates cascading failures.

```
The Timeout Cascade Problem:
════════════════════════════

User → API Gateway → Order Service → Payment Service → Bank API
        (30s)          (30s)           (30s)            (60s!)

What happens:
1. Bank API is slow (takes 45 seconds)
2. Payment Service waits 30 seconds → times out
3. Order Service waits 30 seconds for Payment → times out
4. API Gateway waits 30 seconds for Order → times out
5. User waited 30 seconds → got error
6. BUT: Bank API is STILL processing the payment!
   → Payment might succeed AFTER timeout
   → Now you have an inconsistency: Order failed but payment went through
```

```
THE GOLDEN RULE: Timeouts must DECREASE as you go outward
══════════════════════════════════════════════════════════

                    WRONG                           RIGHT
              ┌──────────────┐               ┌──────────────┐
   Gateway    │  timeout=30s │    Gateway     │  timeout=10s │
              └──────┬───────┘               └──────┬───────┘
              ┌──────┴───────┐               ┌──────┴───────┐
   Order Svc  │  timeout=30s │    Order Svc   │  timeout=8s  │
              └──────┬───────┘               └──────┬───────┘
              ┌──────┴───────┐               ┌──────┴───────┐
   Payment    │  timeout=30s │    Payment     │  timeout=5s  │
              └──────┬───────┘               └──────┬───────┘
              ┌──────┴───────┐               ┌──────┴───────┐
   Bank API   │ response=45s │    Bank API    │ response=45s │
              └──────────────┘               └──────────────┘

   Result: ALL services                Result: Payment fails fast (5s)
   block for 30s each                  Order gets error in 5s
   Thread pools exhausted              Gateway responds in ~6s
   CASCADING FAILURE                   Circuit breaker opens
                                       Fallback response to user
```

```java
// Proper timeout configuration in Spring Boot

// 1. RestTemplate with timeouts
@Bean
public RestTemplate paymentRestTemplate() {
    var factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(2000);  // 2s to establish connection
    factory.setReadTimeout(5000);     // 5s to read response
    return new RestTemplate(factory);
}

// 2. WebClient with timeouts
@Bean
public WebClient paymentWebClient() {
    HttpClient httpClient = HttpClient.create()
        .responseTimeout(Duration.ofSeconds(5))
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000);

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
}

// 3. Feign Client with timeouts
@FeignClient(name = "payment-service",
             configuration = PaymentFeignConfig.class)
public interface PaymentClient {
    @GetMapping("/api/payments/{id}")
    PaymentResponse getPayment(@PathVariable String id);
}

@Configuration
public class PaymentFeignConfig {
    @Bean
    public Request.Options options() {
        return new Request.Options(
            2, TimeUnit.SECONDS,    // connect timeout
            5, TimeUnit.SECONDS,    // read timeout
            true                     // follow redirects
        );
    }
}

// 4. Resilience4j timeout (wraps any call)
@TimeLimiter(name = "paymentService",
             fallbackMethod = "paymentFallback")
public CompletableFuture<PaymentResponse> getPayment(String id) {
    return CompletableFuture.supplyAsync(() ->
        paymentClient.getPayment(id));
}

private CompletableFuture<PaymentResponse> paymentFallback(
        String id, Throwable t) {
    return CompletableFuture.completedFuture(
        PaymentResponse.pending("Payment processing, check back later"));
}
```

```
Types of Timeouts You Must Configure:
══════════════════════════════════════

1. CONNECTION TIMEOUT (2-5 seconds)
   → How long to wait to establish TCP connection
   → If this fails, the service is down/unreachable

2. READ/RESPONSE TIMEOUT (5-30 seconds depending on operation)
   → How long to wait for response after connection established
   → Set based on P99 latency of downstream service + buffer

3. SOCKET TIMEOUT
   → How long to wait for individual socket read operations
   → Guards against connections that hang mid-response

4. CIRCUIT BREAKER TIMEOUT (wraps everything)
   → Overall time budget for the entire operation
   → Should be slightly less than read timeout

5. RETRY TIMEOUT (total time budget for all retries)
   → Don't retry indefinitely
   → Total time = (single timeout × max retries) + backoff delays
   → Must be less than upstream caller's timeout!
```

```
Common Mistakes:
════════════════
1. Using framework defaults (often 0 = infinite timeout!)
2. Same timeout for all services (fast lookup vs slow report generation)
3. Not accounting for retries in timeout budget
4. Connection pool wait timeout not set (threads block waiting for connection)
5. Not setting timeouts on database calls
6. Timeout on Gateway > sum of downstream timeouts (waste of threads)
```

---

**Q63. Your microservices share a database. What problems does this cause and how do you fix it?**

**Answer:**

Shared database is the **#1 anti-pattern in microservices** architecture. It creates hidden coupling that defeats the purpose of microservices.

```
The Shared Database Problem:
════════════════════════════

   ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
   │ Order Service│     │ User Service │     │Inventory Svc │
   └──────┬───────┘     └──────┬───────┘     └──────┬───────┘
          │                    │                     │
          └────────────────────┼─────────────────────┘
                               │
                    ┌──────────┴──────────┐
                    │   SHARED DATABASE   │
                    │                     │
                    │  users table        │
                    │  orders table       │
                    │  inventory table    │
                    │  (all tightly       │
                    │   coupled!)         │
                    └─────────────────────┘
```

```
Problems:
═════════

1. SCHEMA COUPLING
   ───────────────
   User Service renames: users.name → users.full_name
   → Order Service breaks (it was reading users.name directly)
   → Inventory Service breaks (it was JOINing on users.name)
   → Cannot deploy services independently

2. DEPLOYMENT COUPLING
   ────────────────────
   Any schema migration requires coordinating ALL services
   → "We can't deploy Order Service until User Service is updated"
   → Defeats the purpose of microservices

3. PERFORMANCE COUPLING
   ─────────────────────
   Order Service runs heavy report query → locks tables
   → User Service read queries slow down
   → Inventory Service inserts blocked
   → One service's load affects ALL others

4. SCALING IMPOSSIBILITY
   ──────────────────────
   Inventory needs 10x read replicas for Black Friday
   User Service needs different indexing strategy
   Order Service needs different database entirely (time-series)
   → Can't optimize for each service's access patterns

5. HIDDEN DATA DEPENDENCIES
   ─────────────────────────
   Who owns the "users" table? User Service?
   But Order Service adds columns to it...
   And Inventory Service has triggers on it...
   → No clear ownership = no one is responsible

6. TESTING NIGHTMARE
   ──────────────────
   Can't test Order Service without setting up User and Inventory data
   → Integration tests become massive and fragile
   → "Works on my machine" because test data differs
```

```
The Solution: Database per Service
══════════════════════════════════

   ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
   │ Order Service│     │ User Service │     │Inventory Svc │
   └──────┬───────┘     └──────┬───────┘     └──────┬───────┘
          │                    │                     │
   ┌──────┴───────┐    ┌──────┴───────┐    ┌───────┴──────┐
   │ Order DB     │    │  User DB     │    │ Inventory DB │
   │ (PostgreSQL) │    │  (PostgreSQL)│    │   (MongoDB)  │
   └──────────────┘    └──────────────┘    └──────────────┘

   Each service owns its data. Period.
```

```java
// How services communicate WITHOUT shared DB:

// === Pattern 1: API Calls (Synchronous) ===
@Service
public class OrderService {
    private final UserServiceClient userClient;  // Feign/RestTemplate

    public Order createOrder(Long userId, List<Item> items) {
        // Don't JOIN users table — call User Service
        UserDTO user = userClient.getUser(userId);

        // Don't query inventory table — call Inventory Service
        boolean available = inventoryClient.checkStock(items);

        if (!available) throw new OutOfStockException();

        Order order = new Order(user.getId(), user.getName(), items);
        return orderRepository.save(order);
    }
}

// === Pattern 2: Events (Asynchronous — preferred) ===
// User Service publishes events when data changes
@Service
public class UserService {
    private final KafkaTemplate<String, UserEvent> kafka;

    public void updateUser(Long id, UserUpdateRequest req) {
        User user = userRepository.save(/* ... */);

        // Publish event — other services react
        kafka.send("user-events", new UserEvent(
            "USER_UPDATED", user.getId(), user.getName(), user.getEmail()
        ));
    }
}

// Order Service listens and maintains its OWN copy of user data
@KafkaListener(topics = "user-events")
public void handleUserEvent(UserEvent event) {
    if ("USER_UPDATED".equals(event.getType())) {
        // Update local read-only copy
        orderUserCache.save(new OrderUser(
            event.getUserId(),
            event.getName()
        ));
    }
}

// === Pattern 3: CQRS + Event Sourcing (for complex cases) ===
// Each service has its own read model optimized for its queries
// Events are the source of truth
```

```
Migration Strategy (Shared DB → DB per Service):
═════════════════════════════════════════════════

Phase 1: Add API layer
   → Services call each other's APIs instead of direct DB access
   → But still share the same physical database

Phase 2: Split schemas
   → Move tables to separate schemas within same DB
   → Each service only has permissions on its own schema

Phase 3: Separate databases
   → Move each schema to its own database instance
   → Update connection configs

Phase 4: Handle cross-service queries
   → Replace JOINs across services with API calls or events
   → Implement Saga pattern for distributed transactions
   → Accept eventual consistency where appropriate

Rule: If you MUST have ACID transactions across services,
      those services probably shouldn't be separate microservices.
```

---

**Q64. How do you handle caching consistency across multiple microservices?**

**Answer:**

Caching in a monolith is straightforward. In microservices, it becomes a **distributed consistency problem**.

```
The Problem:
════════════

   ┌───────────┐         ┌───────────┐
   │ Service A │         │ Service B │
   │ Cache: $99│         │ Cache: $99│  ← Both cached product price
   └─────┬─────┘         └─────┬─────┘
         │                     │
         │   Price updated to $79 in Product Service
         │                     │
   ┌─────┴─────┐         ┌────┴──────┐
   │ Service A │         │ Service B │
   │ Cache: $99│         │ Cache: $99│  ← STALE! Shows wrong price
   └───────────┘         └───────────┘

   User sees $79 on product page (Service A clears cache)
   But checkout shows $99 (Service B still has stale cache)
   → Customer trust destroyed
```

```java
// === Solution 1: Cache Invalidation via Events (Most Common) ===

// Product Service publishes event on price change
@Service
public class ProductService {
    private final KafkaTemplate<String, ProductEvent> kafka;

    public void updatePrice(Long productId, BigDecimal newPrice) {
        productRepository.updatePrice(productId, newPrice);

        // Broadcast cache invalidation
        kafka.send("product-events", new ProductEvent(
            "PRICE_CHANGED", productId, newPrice
        ));
    }
}

// Every service listening clears its local cache
@Component
public class ProductCacheInvalidator {
    private final CacheManager cacheManager;

    @KafkaListener(topics = "product-events")
    public void onProductEvent(ProductEvent event) {
        if ("PRICE_CHANGED".equals(event.getType())) {
            Cache cache = cacheManager.getCache("products");
            if (cache != null) {
                cache.evict(event.getProductId());  // Evict specific entry
            }
        }
    }
}

// === Solution 2: Shared Redis Cache (Centralized) ===
// All services read/write to the same Redis instance

@Configuration
public class SharedCacheConfig {
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))  // Short TTL = less stale data
            .serializeValuesWith(
                SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(factory)
            .cacheDefaults(config)
            .build();
    }
}

// Product Service updates cache directly
@CachePut(value = "products", key = "#productId")
public Product updatePrice(Long productId, BigDecimal newPrice) {
    // All services reading from same Redis see updated value immediately
    return productRepository.save(/* ... */);
}

// === Solution 3: Cache-Aside with Short TTL ===
// Accept eventual consistency with very short cache lifetime

@Cacheable(value = "products", key = "#id")
public Product getProduct(Long id) {
    return productRepository.findById(id).orElseThrow();
}
// TTL = 30 seconds → maximum staleness is 30 seconds
// Trade-off: more DB hits vs fresher data
```

```
Choosing the Right Strategy:
════════════════════════════

┌─────────────────────┬──────────────┬───────────────┬──────────────┐
│ Strategy            │ Consistency  │ Complexity    │ Best For     │
├─────────────────────┼──────────────┼───────────────┼──────────────┤
│ Event invalidation  │ Near-instant │ Medium        │ Most cases   │
│ Shared Redis        │ Immediate    │ Low           │ Small systems│
│ Short TTL           │ Eventual     │ Very Low      │ Non-critical │
│ Write-through       │ Immediate    │ High          │ Critical data│
│ No cache (call API) │ Always fresh │ Lowest        │ Low traffic  │
└─────────────────────┴──────────────┴───────────────┴──────────────┘

Rules of Thumb:
  → Prices, inventory, auth tokens: Short TTL (30s-2min) + event invalidation
  → User profiles, product descriptions: Longer TTL (5-15min) + event invalidation
  → Static config, country lists: Long TTL (hours) + manual invalidation
  → Financial data: DON'T CACHE or write-through only
```

```
Common Pitfalls:
════════════════
1. Cache stampede: 1000 requests hit when cache expires
   → Fix: Distributed lock, probabilistic early refresh

2. Thundering herd on invalidation: All services re-fetch at once
   → Fix: Stagger TTL with jitter, publish new value WITH event

3. Inconsistent serialization: Service A caches Java object,
   Service B can't deserialize (different class version)
   → Fix: Use JSON serialization, version your cache keys

4. Memory leak: Unbounded local caches grow forever
   → Fix: Always set maxSize + TTL, use Caffeine with eviction policy
```

---

**Q65. How do you implement distributed tracing across Java microservices?**

**Answer:**

When a request flows through 5-10 services, you need to trace it end-to-end to debug issues.

```
The Problem Without Tracing:
════════════════════════════

User reports: "My order took 12 seconds"

You check:
  API Gateway logs: "Request processed in 300ms"  (its part)
  Order Service logs: "Order created in 200ms"    (its part)
  Payment Service logs: "Payment processed in 1s"  (its part)

  Where did the other 10.5 seconds go?!
  → Without tracing, you're BLIND to the full picture
```

```
How Distributed Tracing Works:
══════════════════════════════

                          Trace ID: abc-123
   ┌──────────────────────────────────────────────────────────┐
   │                                                          │
   │  ┌─────────┐   ┌─────────────┐   ┌─────────────────┐   │
   │  │ Gateway  │──→│Order Service│──→│ Payment Service  │   │
   │  │Span: s1  │   │Span: s2     │   │Span: s3          │   │
   │  │200ms     │   │Parent: s1   │   │Parent: s2        │   │
   │  │          │   │500ms        │   │3000ms ← SLOW!    │   │
   │  └─────────┘   │  ┌────────┐ │   └─────────────────┘   │
   │                 │  │DB Query│ │                          │
   │                 │  │Span: s4│ │                          │
   │                 │  │50ms    │ │                          │
   │                 │  └────────┘ │                          │
   │                 └─────────────┘                          │
   └──────────────────────────────────────────────────────────┘

   Trace = entire request journey
   Span  = one operation within the trace
   Each span has: traceId, spanId, parentSpanId, duration, tags
```

```java
// === Spring Boot Implementation with Micrometer Tracing ===
// (Replaces Spring Cloud Sleuth in Spring Boot 3.x)

// Step 1: Add dependencies (pom.xml)
// <dependency>
//     <groupId>io.micrometer</groupId>
//     <artifactId>micrometer-tracing-bridge-otel</artifactId>
// </dependency>
// <dependency>
//     <groupId>io.opentelemetry</groupId>
//     <artifactId>opentelemetry-exporter-zipkin</artifactId>
// </dependency>

// Step 2: Configure application.yml
// management:
//   tracing:
//     sampling:
//       probability: 1.0  # 100% in dev, 10-20% in prod
//   zipkin:
//     tracing:
//       endpoint: http://zipkin:9411/api/v2/spans

// Step 3: Trace ID automatically propagates!
// Spring Boot auto-configures tracing for:
//   ✓ RestTemplate / WebClient calls
//   ✓ Kafka messages
//   ✓ JDBC queries
//   ✓ Spring MVC controllers

@RestController
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    // Trace ID automatically appears in logs:
    // 2024-01-15 10:30:00 [abc-123,span-456] INFO OrderController - Creating order

    @PostMapping("/orders")
    public Order createOrder(@RequestBody OrderRequest req) {
        log.info("Creating order for user {}", req.getUserId());
        // ↑ Log will include [traceId, spanId] automatically
        return orderService.create(req);
    }
}

// Step 4: Custom spans for important operations
@Service
public class OrderService {
    private final ObservationRegistry registry;

    public Order create(OrderRequest req) {
        // Create custom span for business logic
        return Observation.createNotStarted("order.create", registry)
            .lowCardinalityKeyValue("order.type", req.getType())
            .observe(() -> {
                // This block is timed and traced
                Order order = buildOrder(req);
                paymentClient.charge(order);  // Auto-traced (RestTemplate)
                inventoryClient.reserve(order); // Auto-traced
                return orderRepository.save(order); // Auto-traced (JDBC)
            });
    }
}

// Step 5: Propagate trace context in async/Kafka
@KafkaListener(topics = "order-events")
public void handleOrderEvent(
        @Payload OrderEvent event,
        @Header(KafkaHeaders.RECEIVED_KEY) String key) {
    // Trace context automatically extracted from Kafka headers
    // This span is linked to the original HTTP request span
    log.info("Processing order event: {}", event.getOrderId());
    // Appears in same trace in Zipkin/Jaeger
}
```

```
Visualization in Zipkin/Jaeger:
═══════════════════════════════

Search: traceId = abc-123

Timeline:
  Gateway          |████|                                    200ms
  Order Service       |██████████████|                       500ms
    └─ DB: findUser      |██|                                50ms
    └─ Payment Call         |████████████████████████|        3000ms ← BOTTLENECK!
    └─ DB: saveOrder                                 |██|    30ms

Total: 3.7 seconds
Root cause: Payment Service is slow (3 seconds)

Without tracing: "Something is slow somewhere"
With tracing: "Payment Service P99 is 3 seconds, caused by bank API timeout"
```

```
Production Tips:
════════════════
1. Sample rate: 100% in dev, 10-20% in prod (tracing has overhead)
2. Always log traceId — correlate logs with traces
3. Add business context as span tags (orderId, userId, amount)
4. Set up alerts: "notify when any trace exceeds 5 seconds"
5. Use baggage for cross-cutting concerns (tenant ID, region)
6. Trace storage: Zipkin (simple), Jaeger (advanced), Tempo (Grafana stack)
```

---

**Q66. A JVM-level issue in one microservice causes cascading failures across other services. How?**

**Answer:**

A single JVM issue can bring down your entire microservice ecosystem through **cascading failure**.

```
How One JVM Crash Takes Down Everything:
════════════════════════════════════════

Step 1: Service C gets OOM (memory leak)
   ┌───────────┐
   │ Service C │ ← OutOfMemoryError! GC thrashing, then crash
   │ JVM: DEAD │
   └───────────┘

Step 2: Services calling C start blocking
   ┌───────────┐     ┌───────────┐
   │ Service A │────→│ Service C │  ← Connection refused / timeout
   │ Thread: ▓▓│     │   DEAD    │     Service A's threads are WAITING
   │ Thread: ▓▓│     └───────────┘     (blocked on HTTP call)
   │ Thread: ▓▓│                       Thread pool filling up...
   └───────────┘

Step 3: Service A's thread pool exhausted
   ┌───────────┐
   │ Service A │ ← All threads blocked waiting for C
   │ Pool: FULL│    Can't handle ANY requests (even those NOT going to C)
   │ Queue: 999│    Effectively dead even though JVM is running
   └───────────┘

Step 4: Service B calls Service A...
   ┌───────────┐     ┌───────────┐
   │ Service B │────→│ Service A │  ← No response (thread pool full)
   │ Thread: ▓▓│     │ Pool: FULL│
   │ Thread: ▓▓│     └───────────┘
   └───────────┘
   Now Service B is also stuck...

Step 5: ENTIRE SYSTEM DOWN
   All because Service C had a memory leak!
```

```
JVM Issues That Cascade:
═══════════════════════

1. OutOfMemoryError
   → JVM becomes unresponsive → health checks fail → removed from LB
   → But pending requests from other services still timeout

2. Long GC Pauses (Stop-the-World)
   → Service appears alive but doesn't respond for 5-30 seconds
   → Callers timeout → retry → MORE load → LONGER GC → death spiral

3. Thread Starvation
   → JVM is fine but all threads are blocked
   → New requests queue up → memory grows → OOM

4. Class Loading / Metaspace Leak
   → Gradual degradation over days/weeks
   → Redeploy "fixes" it temporarily

5. Native Memory Leak
   → JVM heap is fine but OS kills process (OOM Killer)
   → Sudden death without proper error
```

```java
// === Prevention Strategies ===

// 1. Circuit Breaker — MOST IMPORTANT
@CircuitBreaker(name = "serviceC", fallbackMethod = "fallback")
@TimeLimiter(name = "serviceC")
@Retry(name = "serviceC")
public CompletableFuture<Response> callServiceC(Request req) {
    return CompletableFuture.supplyAsync(() ->
        serviceCClient.process(req));
}

public CompletableFuture<Response> fallback(Request req, Throwable t) {
    log.warn("Service C unavailable, using fallback", t);
    return CompletableFuture.completedFuture(Response.degraded());
}

// 2. Bulkhead — Isolate thread pools per downstream service
// resilience4j config:
// resilience4j.bulkhead.instances.serviceC.maxConcurrentCalls=10
// → Even if Service C is dead, only 10 threads are affected
// → Remaining threads serve other requests normally

// 3. Health checks that detect JVM issues BEFORE they cascade
@Component
public class JvmHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        long heapUsed = memory.getHeapMemoryUsage().getUsed();
        long heapMax = memory.getHeapMemoryUsage().getMax();
        double usagePercent = (double) heapUsed / heapMax * 100;

        if (usagePercent > 90) {
            return Health.down()
                .withDetail("heap_usage", usagePercent + "%")
                .withDetail("action", "SCALE UP or investigate leak")
                .build();
        }

        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        long[] deadlocked = threads.findDeadlockedThreads();
        if (deadlocked != null) {
            return Health.down()
                .withDetail("deadlocked_threads", deadlocked.length)
                .build();
        }

        return Health.up().build();
    }
}

// 4. Kubernetes liveness vs readiness probes
// liveness: /actuator/health/liveness → restart if JVM is broken
// readiness: /actuator/health/readiness → stop sending traffic if overloaded
```

```
Architecture-Level Prevention:
═════════════════════════════
1. Async communication (Kafka) over sync (HTTP) where possible
   → Producer doesn't care if consumer JVM is down
   → Messages queue up and process when consumer recovers

2. Deploy multiple instances of every service (minimum 3)
   → One instance JVM crash = other 2 handle traffic

3. Set resource limits in Kubernetes
   → resources.limits.memory: 512Mi
   → JVM can't consume more → OOM kills ONE pod, not the node

4. JVM flags for graceful degradation:
   -XX:+HeapDumpOnOutOfMemoryError     # Capture evidence
   -XX:+ExitOnOutOfMemoryError         # Die fast, don't thrash
   -XX:MaxMetaspaceSize=256m           # Prevent metaspace leak
```

---

**Q67. What is the single most common design mistake that causes real-world microservice outages?**

**Answer:**

**Synchronous call chains without fallbacks.**

```
The #1 Killer: Synchronous Chain of Doom
═════════════════════════════════════════

   User → A → B → C → D → E → Database

   If ANY link fails → ENTIRE chain fails
   If ANY link is slow → ENTIRE chain is slow

   5 services, each 99.9% uptime:
   0.999 × 0.999 × 0.999 × 0.999 × 0.999 = 99.5% uptime

   99.5% = 43 hours of downtime per year!
   vs 99.9% = 8.7 hours for a single service
```

```
Why This Happens:
════════════════

Developers build microservices like they build monoliths:
  Monolith: orderService.createOrder() calls userService.getUser()
  Microservice: Same thing but now it's an HTTP call

They moved the code boundaries but kept the SAME coupling.
This is a "distributed monolith" — worst of both worlds:
  ✗ Network latency of microservices
  ✗ Coupling of monolith
  ✗ Complexity of distributed system
  ✗ None of the benefits of either
```

```
Real-World Outage Scenarios from this Mistake:
═══════════════════════════════════════════════

Scenario 1: "The Monday Morning Crash"
  → Weekend: Email service goes down for maintenance
  → Monday: Order Service calls Email Service synchronously after order
  → Email call times out (30 seconds)
  → Order Service thread pool fills up in 2 minutes
  → No one can place orders
  → Revenue loss: $50K/hour
  → Root cause: Sending email was synchronous and mandatory

Scenario 2: "The Inventory Check Cascade"
  → Product page calls Inventory Service for stock count
  → Inventory Service under load, responds in 8 seconds
  → Product page timeout = 5 seconds → error
  → User refreshes → double the load
  → Retry storm → Inventory Service OOM → crash
  → Now ALL product pages show errors (even cached ones expired)

Scenario 3: "The Deploy That Broke Everything"
  → Deploy new User Service version
  → Bug: returns XML instead of JSON for edge case
  → Order Service can't parse response → 500 error
  → Recommendation Service can't parse → 500 error
  → Cart Service can't parse → 500 error
  → One bad deploy broke 3 services
```

```java
// === The Fix: Design for Failure ===

// RULE 1: Make downstream calls OPTIONAL where possible
@Service
public class OrderService {

    public Order createOrder(OrderRequest req) {
        // MUST succeed — core business logic
        Order order = orderRepository.save(new Order(req));

        // CAN FAIL — enrich with user details
        try {
            UserDTO user = userClient.getUser(req.getUserId());
            order.setUserName(user.getName());
        } catch (Exception e) {
            log.warn("User service unavailable, using userId only");
            order.setUserName("User#" + req.getUserId());  // Graceful degradation
        }

        // SHOULD BE ASYNC — non-critical side effects
        kafkaTemplate.send("order-events", new OrderCreatedEvent(order));
        // ↑ Email, analytics, recommendations react asynchronously

        return order;
    }
}

// RULE 2: Classify every dependency
// ┌─────────────┬───────────────────────────────────┐
// │ Category    │ How to handle                     │
// ├─────────────┼───────────────────────────────────┤
// │ CRITICAL    │ Fail the request if unavailable   │
// │ (payment)   │ But use circuit breaker + retry   │
// ├─────────────┼───────────────────────────────────┤
// │ IMPORTANT   │ Use cached/default value           │
// │ (user info) │ Degrade gracefully                │
// ├─────────────┼───────────────────────────────────┤
// │ NICE-TO-HAVE│ Make async (fire and forget)      │
// │ (email,logs)│ Process later via message queue   │
// └─────────────┴───────────────────────────────────┘

// RULE 3: Prefer async over sync
// Instead of: Order → HTTP → Email Service
// Do:         Order → Kafka → Email Service (async)
//
// Instead of: API → HTTP → Report Service (slow)
// Do:         API → Kafka → Report Service → Webhook/Poll (async)
```

```
Prevention Checklist for Every Microservice:
════════════════════════════════════════════

□ Every HTTP call has a timeout configured
□ Every HTTP call has a circuit breaker
□ Every HTTP call has a fallback (cached value, default, or error)
□ Non-critical operations are async (Kafka/RabbitMQ)
□ Thread pools are isolated per downstream service (bulkhead)
□ Retries use exponential backoff (not immediate retry)
□ Health checks distinguish liveness from readiness
□ At least 2 instances of every service
□ Graceful shutdown configured (finish in-flight requests)
□ Distributed tracing enabled (can debug cross-service issues)

If you do ALL of these, you've prevented 90% of microservice outages.
```

---

### More Real-World Java Interview Questions

> **Note:** Many practical Java questions overlap with earlier answers. Cross-references:
> App slowing over time → Q37 | Memory leaks without static → Q38 | Low CPU high latency → Q57
> Threads available but queued → Q52 | Deadlock in prod only → Q53 | Heap size made it slower → Q44
> GC pauses after code change → Q54 | JVM won't exit → Q55 | Retry overload → Q43
> Logging impacting performance → Q48 | Java 8 vs 17 → Q56 | Background threads affecting API → Q59
> Scaling made it worse → Q60 | Production stories → Q50
>
> Below are **new questions** covering gaps not addressed above.

---

**Q68. Parallel streams were introduced but throughput dropped. Why?**

**Answer:**

This is one of the most common Java performance traps. Parallel streams look like a free speedup but often make things **worse**.

```
Why Parallel Streams Can REDUCE Throughput:
═══════════════════════════════════════════

CAUSE 1: SHARED ForkJoinPool
─────────────────────────────
ALL parallel streams in your JVM share ONE ForkJoinPool.commonPool()
Default size = Runtime.getRuntime().availableProcessors() - 1

Your web app (200 Tomcat threads) + parallel streams:
  Thread-1: parallelStream() → submits to ForkJoinPool (8 threads)
  Thread-2: parallelStream() → submits to ForkJoinPool (same 8 threads!)
  ...
  Thread-50: parallelStream() → ALL WAITING for those 8 ForkJoin threads

  200 web threads fight over 8 ForkJoin threads
  → WORSE than sequential because of scheduling overhead + contention
```

```java
// ✗ BAD: Parallel stream in a web controller
@GetMapping("/products")
public List<Product> getProducts() {
    return products.parallelStream()         // Uses shared ForkJoinPool!
        .filter(p -> p.isActive())
        .map(this::enrichWithPrice)          // If this calls DB or API → disaster
        .collect(Collectors.toList());
}
// Under 100 concurrent requests, all 100 fight for the same ForkJoinPool
// Sequential would have been FASTER

// ✓ FIX 1: Use custom ForkJoinPool (isolate from other streams)
@GetMapping("/products")
public List<Product> getProducts() throws Exception {
    ForkJoinPool customPool = new ForkJoinPool(4);  // Dedicated pool
    try {
        return customPool.submit(() ->
            products.parallelStream()
                .filter(p -> p.isActive())
                .map(this::enrichWithPrice)
                .collect(Collectors.toList())
        ).get();
    } finally {
        customPool.shutdown();
    }
}

// ✓ FIX 2: Just don't use parallel streams in web apps (best fix)
@GetMapping("/products")
public List<Product> getProducts() {
    return products.stream()                 // Sequential — predictable, fast enough
        .filter(p -> p.isActive())
        .map(this::enrichWithPrice)
        .collect(Collectors.toList());
}
```

```
CAUSE 2: I/O-BOUND OPERATIONS IN PARALLEL STREAM
──────────────────────────────────────────────────
Parallel streams are designed for CPU-bound work.
Using them for I/O is a guaranteed performance killer.

// ✗ TERRIBLE: Network calls inside parallel stream
users.parallelStream()
    .map(user -> restTemplate.getForObject(url + user.getId()))  // HTTP call!
    .collect(Collectors.toList());

ForkJoinPool has 8 threads. Each is now BLOCKED on HTTP call (200ms each).
  → 8 items processed at a time, rest wait
  → ForkJoinPool is designed for SHORT CPU tasks, not blocking I/O
  → Other parallel streams in the JVM are now STARVED

// ✓ FIX: Use CompletableFuture with dedicated I/O pool
ExecutorService ioPool = Executors.newFixedThreadPool(20);

List<CompletableFuture<UserDetail>> futures = users.stream()  // Sequential stream!
    .map(user -> CompletableFuture.supplyAsync(
        () -> restTemplate.getForObject(url + user.getId()), ioPool))
    .collect(Collectors.toList());

List<UserDetail> results = futures.stream()
    .map(CompletableFuture::join)
    .collect(Collectors.toList());
```

```
CAUSE 3: SMALL DATA + HIGH OVERHEAD
────────────────────────────────────
Parallel stream overhead: split → distribute → execute → merge
For small collections, overhead > benefit

Benchmarks (approximate):
┌───────────────┬────────────┬──────────────┐
│ Collection    │ Sequential │ Parallel     │
├───────────────┼────────────┼──────────────┤
│ 100 items     │ 0.05ms     │ 2ms (40x!)   │
│ 1,000 items   │ 0.5ms      │ 1.5ms (3x)   │
│ 100,000 items │ 50ms       │ 15ms ✓       │
│ 10M items     │ 5000ms     │ 800ms ✓✓     │
└───────────────┴────────────┴──────────────┘

CAUSE 4: NON-SPLITTABLE DATA STRUCTURE
──────────────────────────────────────
Not all collections split equally:

  ArrayList   → Splits perfectly (random access) ✓✓
  IntStream   → Splits perfectly ✓✓
  HashSet     → Splits well ✓
  LinkedList  → CANNOT split efficiently ✗ (must traverse to find midpoint)
  TreeMap     → Splits poorly ✗

// ✗ Parallel on LinkedList — splits terribly
LinkedList<Integer> list = new LinkedList<>(data);
list.parallelStream().map(x -> x * 2).collect(Collectors.toList());
// Splitting LinkedList is O(n) for each split → O(n log n) total just to split

CAUSE 5: AUTOBOXING OVERHEAD
─────────────────────────────
// ✗ BAD: Autoboxing kills parallel performance
List<Integer> list = ...;  // boxed Integers
long sum = list.parallelStream()
    .mapToLong(i -> i)     // Unboxes each Integer → long
    .sum();

// ✓ FIX: Use primitive streams
long sum = IntStream.range(0, 10_000_000)
    .parallel()
    .mapToLong(i -> i * 2L)
    .sum();
```

```
When Parallel Streams ACTUALLY Help:
═════════════════════════════════════
✓ Large collections (>100K elements)
✓ CPU-intensive operations (math, sorting, transformation)
✓ Splittable data structures (ArrayList, arrays, IntStream)
✓ Stateless, side-effect-free operations
✓ Batch processing (not web request handling)
✓ No shared mutable state

When to AVOID:
✗ Web request handlers (shared ForkJoinPool contention)
✗ I/O operations (DB calls, HTTP calls, file reads)
✗ Small collections (<10K elements)
✗ LinkedList or other poorly-splittable structures
✗ Operations with side effects or shared state
✗ When you're already using thread pools (double parallelism = worse)
```

---

**Q69. ThreadLocal fixed one issue but introduced another. What went wrong?**

**Answer:**

ThreadLocal is a double-edged sword. It solves thread-safety problems but creates **data leakage** and **memory leak** problems, especially in thread pools.

```
The Setup:
══════════
Problem: Multiple threads sharing a SimpleDateFormat (not thread-safe)
Fix:     ThreadLocal<SimpleDateFormat> — each thread gets its own copy
New Bug: Users see WRONG DATA belonging to OTHER users!
```

```java
// === The Classic ThreadLocal Trap ===

// Step 1: Developer creates ThreadLocal for per-request context
public class RequestContext {
    private static final ThreadLocal<UserSession> session = new ThreadLocal<>();

    public static void set(UserSession s) { session.set(s); }
    public static UserSession get() { return session.get(); }
    public static void clear() { session.remove(); }
}

// Step 2: Filter sets context at request start
@Component
public class AuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res, FilterChain chain) throws Exception {
        UserSession user = authenticate(req);
        RequestContext.set(user);
        chain.doFilter(req, res);
        // ✗ BUG: What if exception is thrown above?
        // clear() never runs → stale data stays in ThreadLocal
    }
}

// Step 3: Controller reads context
@GetMapping("/profile")
public Profile getProfile() {
    UserSession user = RequestContext.get();
    // Returns Alice's data... but the thread previously served Bob
    // If clear() wasn't called, Bob's session is STILL in ThreadLocal
    return profileService.getProfile(user.getUserId());
    // ✗ USER SEES SOMEONE ELSE'S DATA — security vulnerability!
}
```

```
WHY This Happens in Thread Pools:
═════════════════════════════════

Thread pools REUSE threads. ThreadLocal values persist across reuse.

Request 1: Thread-5 handles Alice
  ThreadLocal = { user: "Alice", role: "ADMIN" }
  Response sent. But clear() was never called.

Request 2: Thread-5 is REUSED for Bob
  ThreadLocal STILL = { user: "Alice", role: "ADMIN" }  ← STALE!
  If code reads ThreadLocal before setting new value:
    Bob sees Alice's data, or worse, has Alice's ADMIN role!

Timeline:
  Thread-5: [Alice's request] ─────────── [Bob's request] ────
  ThreadLocal: set(Alice)    (no clear!)    get() → Alice!!  set(Bob)
                                            ↑ BUG: Returns Alice
```

```java
// === The Correct Pattern ===

// ALWAYS use try-finally to guarantee cleanup
@Component
public class AuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res, FilterChain chain) throws Exception {
        try {
            UserSession user = authenticate(req);
            RequestContext.set(user);
            chain.doFilter(req, res);
        } finally {
            RequestContext.clear();  // ✓ ALWAYS runs, even on exception
        }
    }
}

// === Other ThreadLocal Traps ===

// TRAP 1: ThreadLocal + @Async = data not available
@Service
public class OrderService {
    public void createOrder() {
        UserSession user = RequestContext.get();  // ✓ Works (Tomcat thread)

        asyncMethod();  // ✗ Runs on DIFFERENT thread → ThreadLocal is empty!
    }

    @Async
    public void asyncMethod() {
        UserSession user = RequestContext.get();  // NULL! Different thread pool
        // Fix: Pass the value explicitly as a parameter
    }
}

// TRAP 2: ThreadLocal + CompletableFuture = same problem
CompletableFuture.supplyAsync(() -> {
    UserSession user = RequestContext.get();  // NULL — ForkJoinPool thread
    return user.getName();  // NullPointerException!
});

// Fix: Capture before, use inside
UserSession user = RequestContext.get();  // Capture on calling thread
CompletableFuture.supplyAsync(() -> {
    return user.getName();  // ✓ Using captured variable, not ThreadLocal
});

// TRAP 3: ThreadLocal + Virtual Threads (Java 21) = MEMORY EXPLOSION
// Virtual threads are cheap to create (millions possible)
// Each virtual thread with ThreadLocal = millions of ThreadLocal copies
// Solution: Use ScopedValues (Java 21 preview) instead of ThreadLocal
// ScopedValue<UserSession> SESSION = ScopedValue.newInstance();
// ScopedValue.where(SESSION, user).run(() -> { ... });
```

```
ThreadLocal Problem Summary:
════════════════════════════
┌─────────────────────────┬──────────────────────────────────────┐
│ Issue                   │ Prevention                           │
├─────────────────────────┼──────────────────────────────────────┤
│ Data leakage between    │ ALWAYS clear() in finally block      │
│ requests (security bug) │                                      │
├─────────────────────────┼──────────────────────────────────────┤
│ Memory leak in thread   │ clear() + avoid storing large objects│
│ pools                   │                                      │
├─────────────────────────┼──────────────────────────────────────┤
│ Not available in @Async │ Pass value explicitly as parameter   │
│ or CompletableFuture    │ or use TaskDecorator to propagate    │
├─────────────────────────┼──────────────────────────────────────┤
│ ClassLoader leak during │ clear() before undeploy/redeploy     │
│ hot redeploy            │ (see Q49)                            │
├─────────────────────────┼──────────────────────────────────────┤
│ Memory explosion with   │ Use ScopedValues (Java 21+)          │
│ Virtual Threads         │                                      │
└─────────────────────────┴──────────────────────────────────────┘
```

---

**Q70. HashMap performance degrades as data grows. What mistake caused this?**

**Answer:**

HashMap is O(1) average for get/put. If it becomes O(n), you have a **broken hashCode()** implementation.

```
How HashMap Works (Quick Recap — see HashMap Deep Dive section above):
═════════════════════════════════════════════════════════════════════
Bucket array (default 16 slots):
  [0] → null
  [1] → Entry("Alice") → null
  [2] → null
  [3] → Entry("Bob") → Entry("Charlie") → null   ← collision chain
  ...

Good hashCode: entries spread evenly across buckets → O(1) lookup
Bad hashCode:  entries all land in SAME bucket → O(n) linked list traversal!
```

```java
// === The Problem: Bad hashCode() ===

// ✗ TERRIBLE: Constant hashCode
public class Employee {
    private String name;
    private String department;

    @Override
    public int hashCode() {
        return 42;  // Every object goes to the SAME bucket!
    }

    @Override
    public boolean equals(Object o) {
        // proper equals implementation
    }
}

Map<Employee, String> map = new HashMap<>();
// Insert 10,000 employees → ALL in one bucket
// Bucket: Entry → Entry → Entry → ... → Entry (10,000 long chain!)
//
// get() must traverse this entire chain: O(n) instead of O(1)
// With 10,000 entries: 10,000x slower than expected!
//
// Note: Java 8+ converts long chains to Red-Black Tree (O(log n))
// but only if hashCode varies AND Comparable is implemented
// If hashCode is constant, treeification still can't save you from poor distribution

// ✗ BAD: Only using one field with low cardinality
public class Order {
    private String status;      // Only 5 possible values: NEW, PAID, SHIPPED...
    private Long orderId;
    private BigDecimal amount;

    @Override
    public int hashCode() {
        return status.hashCode();  // Only 5 distinct hash values!
    }
    // 100,000 orders distributed across only 5 buckets
    // Average chain length: 20,000 → effectively O(n)
}

// ✗ BAD: Mutable hashCode (the "disappearing entry" bug)
public class Product {
    private String name;
    private double price;  // Mutable!

    @Override
    public int hashCode() {
        return Objects.hash(name, price);  // Includes mutable field
    }
}

Map<Product, String> map = new HashMap<>();
Product p = new Product("Laptop", 999.99);
map.put(p, "electronics");

p.setPrice(899.99);               // hashCode CHANGES!
map.get(p);                        // Returns NULL — can't find it!
// Entry is in bucket for hashCode(999.99) but we're searching in bucket for hashCode(899.99)
// The entry is still there but UNREACHABLE → memory leak
```

```java
// === The Fix: Proper hashCode() ===

public class Employee {
    private String name;
    private String department;
    private Long id;

    // ✓ GOOD: Uses all significant fields, well-distributed
    @Override
    public int hashCode() {
        return Objects.hash(id, name, department);
    }

    // ✓ EVEN BETTER: For performance-critical code
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }
    // Why 31? Prime number, JVM optimizes 31 * x as (x << 5) - x

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee that = (Employee) o;
        return Objects.equals(id, that.id)
            && Objects.equals(name, that.name)
            && Objects.equals(department, that.department);
    }
}

// ✓ BEST: Use immutable keys (records in Java 16+)
public record EmployeeKey(Long id, String name) {}
// hashCode() and equals() auto-generated from ALL fields
// Immutable → hashCode never changes → no "disappearing entry" bug
```

```
Diagnosing HashMap Performance Issues:
═══════════════════════════════════════

Symptom: get()/put() taking milliseconds instead of microseconds

Step 1: Check hashCode distribution
  Map<Integer, Integer> bucketDistribution = new HashMap<>();
  for (Key key : map.keySet()) {
      int bucket = key.hashCode() & (capacity - 1);
      bucketDistribution.merge(bucket, 1, Integer::sum);
  }
  // If one bucket has 90% of entries → bad hashCode

Step 2: Check if keys are mutable
  → If key fields change after put() → entries become unreachable
  → Map grows but get() returns null → looks like a memory leak

Step 3: Check load factor / resizing
  → Default load factor: 0.75 (resize at 75% capacity)
  → If you know the size upfront:
     new HashMap<>(expectedSize / 0.75 + 1)  // Avoid resizing

Performance Table:
┌──────────────────────┬──────────┬────────────┬────────────────┐
│ Scenario             │ get()    │ put()      │ Root Cause     │
├──────────────────────┼──────────┼────────────┼────────────────┤
│ Good hashCode        │ O(1)     │ O(1)       │ —              │
│ Constant hashCode    │ O(n)     │ O(n)       │ All same bucket│
│ Low-cardinality hash │ O(n/k)   │ O(n/k)     │ Few buckets    │
│ Mutable key          │ O(1)*    │ O(1)       │ *Returns null! │
│ No initial capacity  │ O(1)     │ O(1) amort │ Resizing spikes│
│ Tree (Java 8+ >8)    │ O(log n) │ O(log n)   │ Collision chain│
└──────────────────────┴──────────┴────────────┴────────────────┘
```

---

**Q71. ExecutorService tasks fail silently. Why does this happen?**

**Answer:**

This is one of the most dangerous Java concurrency bugs — exceptions thrown in submitted tasks **disappear without a trace**.

```
The Problem:
════════════

ExecutorService pool = Executors.newFixedThreadPool(4);

pool.submit(() -> {
    System.out.println("Processing order...");
    int result = 10 / 0;  // ArithmeticException!
    System.out.println("Done");  // Never prints
});

// Output: "Processing order..."
// No exception logged. No error. No stack trace.
// Task silently died. You'd never know it failed.
// Data is now INCONSISTENT — order was half-processed.
```

```
WHY Exceptions Disappear:
═════════════════════════

submit() vs execute():

pool.execute(runnable)  → Exception propagates to UncaughtExceptionHandler
                          → Default handler prints stack trace to stderr

pool.submit(runnable)   → Exception is CAPTURED inside the Future object
                          → Only thrown when you call Future.get()
                          → If you never call get() → exception LOST FOREVER

Most developers use submit() and never call get():

  pool.submit(() -> processOrder(order));  // Fire and forget
  // Exception? What exception? ¯\_(ツ)_/¯
```

```java
// === Demonstration of the Problem ===

ExecutorService pool = Executors.newFixedThreadPool(2);

// ✗ SILENT FAILURE — exception swallowed
Future<?> future = pool.submit(() -> {
    throw new RuntimeException("Database connection failed!");
});
// future holds the exception, but nobody checks it

// Only shows up if you call get():
try {
    future.get();  // NOW it throws ExecutionException wrapping the original
} catch (ExecutionException e) {
    System.out.println("Caught: " + e.getCause().getMessage());
    // "Caught: Database connection failed!"
}


// === 5 Ways to Fix Silent Failures ===

// FIX 1: Always check Future.get()
Future<?> future = pool.submit(() -> processOrder(order));
try {
    future.get(30, TimeUnit.SECONDS);  // Blocks and rethrows exceptions
} catch (ExecutionException e) {
    log.error("Task failed: {}", e.getCause().getMessage(), e.getCause());
    // Handle the failure
} catch (TimeoutException e) {
    future.cancel(true);
    log.error("Task timed out");
}

// FIX 2: Wrap task with try-catch inside
pool.submit(() -> {
    try {
        processOrder(order);
    } catch (Exception e) {
        log.error("Order processing failed for order {}", order.getId(), e);
        // Send to dead letter queue, alert, etc.
    }
});

// FIX 3: Use execute() instead of submit() for fire-and-forget
pool.execute(() -> {
    processOrder(order);  // Exception goes to UncaughtExceptionHandler
});

// FIX 4: Custom ThreadFactory with UncaughtExceptionHandler
ExecutorService pool = Executors.newFixedThreadPool(4,
    r -> {
        Thread t = new Thread(r);
        t.setUncaughtExceptionHandler((thread, ex) -> {
            log.error("Uncaught exception in thread {}: {}",
                thread.getName(), ex.getMessage(), ex);
        });
        return t;
    });
// Note: This only works with execute(), NOT submit()

// FIX 5: Override afterExecute in ThreadPoolExecutor
ExecutorService pool = new ThreadPoolExecutor(
    4, 4, 0L, TimeUnit.MILLISECONDS,
    new LinkedBlockingQueue<>()
) {
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        // For submit() tasks, exception is inside the Future
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();  // Extract exception if any
                }
            } catch (ExecutionException e) {
                t = e.getCause();
            } catch (Exception e) {
                t = e;
            }
        }
        if (t != null) {
            log.error("Task failed with exception", t);
        }
    }
};
```

```java
// === CompletableFuture Has the Same Trap ===

// ✗ SILENT: Exception lost if no handler chained
CompletableFuture.runAsync(() -> {
    throw new RuntimeException("Failed!");
});
// No one ever sees this exception

// ✓ FIX: Always chain an exception handler
CompletableFuture.runAsync(() -> {
    processOrder(order);
}).exceptionally(ex -> {
    log.error("Async task failed", ex);
    alertOps("Order processing failed: " + ex.getMessage());
    return null;
});

// ✓ OR: Use whenComplete to handle both success and failure
CompletableFuture.supplyAsync(() -> fetchData())
    .thenApply(data -> transform(data))
    .whenComplete((result, error) -> {
        if (error != null) {
            log.error("Pipeline failed", error);
        } else {
            log.info("Pipeline completed: {}", result);
        }
    });
```

```
Summary of submit() vs execute():
══════════════════════════════════
┌──────────────────┬───────────────────────┬───────────────────────┐
│                  │ execute(Runnable)     │ submit(Runnable)      │
├──────────────────┼───────────────────────┼───────────────────────┤
│ Return type      │ void                  │ Future<?>             │
│ Exception        │ Goes to               │ Captured in Future    │
│ handling         │ UncaughtException     │ Only seen via get()   │
│                  │ Handler (logged)      │ (silent if not called)│
│ Use when         │ Fire-and-forget       │ Need result or status │
│ Risk             │ Thread dies, replaced │ Silent failure        │
│                  │ by new thread         │ if get() not called   │
└──────────────────┴───────────────────────┴───────────────────────┘

Golden Rule: If you call submit(), you MUST handle the Future.
            If you truly don't care about the result, use execute().
```

---

**Q72. A Java fix worked locally but failed in production. What went wrong?**

**Answer:**

This is an extremely common scenario. The environments SEEM identical but have critical differences.

```
Top Causes (ordered by frequency):
═══════════════════════════════════

CAUSE 1: DIFFERENT JVM VERSION / FLAGS
───────────────────────────────────────
Local:  Java 17.0.9, default GC (G1), default heap (-Xmx256m)
Prod:   Java 17.0.2, ZGC, -Xmx8g, -XX:+UseCompressedOops

Impact:
  → Minor JVM version differences can change behavior
  → GC differences: G1 finalizes objects differently than ZGC
  → Heap size: Large heap → different GC frequency → timing bugs appear
  → Compressed OOPs: Object layout changes → serialization breaks
```

```
CAUSE 2: TIMING AND CONCURRENCY
────────────────────────────────
Local:  1 user, 1 request at a time
Prod:   1000 concurrent users, connection pools, thread pools

// Code that "works" with 1 thread but breaks with 100:
private Map<String, Object> cache = new HashMap<>();  // NOT thread-safe!

public Object getData(String key) {
    if (!cache.containsKey(key)) {    // Thread A checks: false
        // Thread B also checks: false (race condition!)
        cache.put(key, loadFromDB(key)); // Both threads put → corrupt HashMap
    }
    return cache.get(key);  // → null, wrong value, or infinite loop!
}

// Why it works locally: single thread, race condition never triggers
// Why it fails in prod: 100 threads hit this simultaneously
```

```
CAUSE 3: ENVIRONMENT DIFFERENCES
─────────────────────────────────
              Local               Production
Timezone:     Asia/Kolkata        UTC
Locale:       en_IN               en_US
OS:           Windows/Mac         Linux
File path:    C:\data\file.txt    /opt/data/file.txt
Line ending:  \r\n                \n
DNS:          Instant             50ms (first call)
Disk:         SSD (0.1ms)         Network storage (5ms)
Memory:       16GB free           Shared with 20 containers

// Classic timezone bug
LocalDate today = LocalDate.now();  // Different date at 11 PM IST vs UTC!

// Classic file path bug
File f = new File("data/config.txt");  // Relative path works in IDE
// In production: working directory is /opt/app, file is in /opt/data
// → FileNotFoundException

// Classic line ending bug
String[] lines = content.split("\n");  // Works on Linux
// On Windows file with \r\n: each line has trailing \r → comparisons fail
```

```
CAUSE 4: DEPENDENCY / CLASSPATH DIFFERENCES
──────────────────────────────────────────
Local:  mvn dependency:tree shows jackson 2.15
Prod:   Fat JAR has jackson 2.13 (transitive dependency won)

// Works with 2.15:
objectMapper.readValue(json, new TypeReference<List<Record>>() {});
// Record is a Java keyword since Java 14, handled in jackson 2.15
// Fails with 2.13: "Unrecognized token 'Record'"

// Or: Different library version pulled in by another dependency
// Local build: guava 32.0 (direct dependency)
// Prod: guava 28.0 (transitive from an older library wins in shading)
```

```
CAUSE 5: DATA DIFFERENCES
──────────────────────────
Local:  Clean test database, 100 rows, all valid
Prod:   5 years of data, millions of rows, edge cases everywhere

// Works locally with test data:
String email = user.getEmail().toLowerCase();  // NullPointerException!
// Prod has users from 2019 with null email (field was optional back then)

// Works locally with small dataset:
List<Order> orders = orderRepo.findAll();  // 50 orders → fine
// Prod: findAll() loads 2 MILLION orders → OutOfMemoryError

// Works locally with ASCII:
String name = user.getName().substring(0, 50);  // Fits in DB column
// Prod: "José García" — multibyte UTF-8 → 50 chars = 60 bytes → DB truncation error
```

```
CAUSE 6: NETWORK AND INFRASTRUCTURE
────────────────────────────────────
Local:  All services on localhost, <1ms latency, no firewalls
Prod:   Services in different availability zones, 5-50ms latency, firewalls

// Local: HTTP call takes 2ms → timeout of 5s is never hit
// Prod: 50ms network + 200ms processing + occasional GC pause = 300ms
//       Under load: connection pool exhausted → 5s wait for connection
//       → TimeoutException that NEVER happens locally

// Kubernetes-specific:
// Local: Always same instance, sticky session
// Prod: Load balancer distributes across 5 pods
//       → Session state lost between requests
//       → "User logged in but next request says unauthorized"
```

```
Prevention Checklist:
═══════════════════
□ Pin exact JVM version in Dockerfile (FROM eclipse-temurin:17.0.9_9-jre)
□ Match JVM flags between local and prod (-Xmx, GC type, etc.)
□ Run with prod-like data volume in staging
□ Use docker-compose locally to match prod topology
□ Test with concurrent load (JMeter, Gatling) before deploying
□ Use the same timezone (UTC everywhere)
□ Null-check everything that comes from the database
□ Use explicit dependency versions (no ranges, no LATEST)
□ Diff dependency trees between local build and deployed artifact
□ Log environment info on startup (JVM version, OS, timezone, memory)
```
