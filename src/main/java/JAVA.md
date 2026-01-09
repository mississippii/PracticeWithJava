# Java - Complete Reference Guide

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

## Object-Oriented Programming (OOP)

**Definition:** Object-Oriented Programming (OOP) is a programming approach that organizes code using objects which combine data and behavior. It helps build secure, reusable, and maintainable software through encapsulation, inheritance, polymorphism, and abstraction.

### Four Pillars of OOP:

1. **Encapsulation** - Data hiding using private fields and public methods
2. **Inheritance** - Code reusability through parent-child relationships
3. **Polymorphism** - Many forms (method overloading and overriding)
4. **Abstraction** - Hiding implementation details

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

### Key Concepts:
- **Bounded type parameters** - `<T extends Number>`
- **Wildcards** - `<?>`, `<? extends T>`, `<? super T>`
- **PECS principle** - Producer Extends, Consumer Super
- **Type erasure** - Generics removed at runtime
- **Generic methods & classes**
- **Recursive type bounds** - `<T extends Comparable<T>>`

---

## Reflection & Metaprogramming

### Capabilities:
- **Class introspection** - Examine classes at runtime
- **Dynamic proxy** - Create proxy instances
- **Method handles** - Direct method references
- **Annotation processing** - Process annotations at compile-time
- **Bytecode manipulation** - ASM, ByteBuddy, Javassist

---

## Modern Java Features (Java 9-21+)

### Java 9+:
- **Modules (JPMS)** - Java Platform Module System
- **JShell** - Interactive REPL

### Java 10+:
- **Local variable type inference (var)** - Type inference for local variables

### Java 14+:
- **Records** - Immutable data carriers
- **Text blocks** - Multi-line strings
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

### I/O:
- **NIO & NIO.2** - New I/O API
- **Channels** - Bidirectional communication
- **Buffers** - Data containers
- **Selectors** - Multiplexed I/O
- **Async I/O** - Non-blocking I/O
- **Memory-mapped files** - File as memory

### Networking:
- **Socket programming** - TCP/UDP communication
- **HttpClient** - Modern HTTP client (Java 11+)

---

## Reactive Programming

### Frameworks:
- **Project Reactor** - Reactive streams implementation
- **RxJava** - Reactive extensions for JVM
- **Reactive Streams spec** - Standard for async stream processing
- **Backpressure handling** - Flow control

---

## Design Patterns

### Creational:
- **Builder** - Complex object construction
- **Factory** - Object creation without exposing logic
- **Singleton** - Single instance

### Structural:
- **Adapter** - Interface compatibility
- **Decorator** - Add behavior dynamically
- **Proxy** - Control access

### Behavioral:
- **Strategy** - Interchangeable algorithms
- **Observer** - Event notification
- **Command** - Encapsulate requests

### Concurrency Patterns:
- **Producer-Consumer**
- **Thread Pool**
- **Future Pattern**

---

## Security

### Key Topics:
- **Java Security Manager** - Security policy enforcement
- **Cryptography (JCA/JCE)** - Encryption/decryption
- **SSL/TLS** - Secure communication
- **Secure coding practices** - Prevent vulnerabilities

---

## Testing

### Frameworks & Tools:
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework
- **Integration testing** - Test system integration
- **Property-based testing** - QuickCheck-style testing
- **Mutation testing** - Test quality measurement
- **JMH benchmarking** - Performance benchmarking

---

## Build & Tooling

### Build Tools:
- **Maven** - Dependency management & build
- **Gradle** - Modern build tool
- **Custom plugins**
- **Dependency management**
- **CI/CD integration**

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

This comprehensive Java guide now covers EVERYTHING from basics to advanced topics with 36+ detailed interview questions for mid-level Software Engineer preparation! 🚀
