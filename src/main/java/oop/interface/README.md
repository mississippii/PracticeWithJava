# 06. Interface - Contract

## What is an Interface?
A contract that defines "WHAT" a class should do, not "HOW" to do it. 100% abstraction.

**A reference type that contains only method signatures (and constants).**

---

## Why Interface?

1. **100% Abstraction** - Only method declarations
2. **Multiple Inheritance** - Implement multiple interfaces
3. **Loose Coupling** - Depend on interface, not implementation
4. **Flexibility** - Easy to swap implementations

---

## Syntax

```java
interface InterfaceName {
    // Abstract methods (public abstract by default)
    void method1();
    int method2(String param);

    // Constants (public static final by default)
    int MAX_VALUE = 100;
}

class ClassName implements InterfaceName {
    // Must implement all methods
    @Override
    public void method1() {
        // implementation
    }

    @Override
    public int method2(String param) {
        // implementation
        return 0;
    }
}
```

---

## Features

### Before Java 8
- All methods: `public abstract` (by default)
- All fields: `public static final` (by default)
- No constructors
- No method body

### After Java 8
- **Default methods** - methods with body
- **Static methods** - utility methods

### After Java 9
- **Private methods** - helper methods

---

## Interface Methods

### 1. Abstract Methods (Default)

```java
interface Animal {
    void sound();  // public abstract by default
}
```

### 2. Default Methods (Java 8+)

```java
interface Animal {
    default void sleep() {
        System.out.println("Sleeping...");
    }
}
```

### 3. Static Methods (Java 8+)

```java
interface MathUtils {
    static int add(int a, int b) {
        return a + b;
    }
}

// Usage
int sum = MathUtils.add(5, 10);
```

### 4. Private Methods (Java 9+)

```java
interface Helper {
    default void method1() {
        commonLogic();
    }

    default void method2() {
        commonLogic();
    }

    private void commonLogic() {
        // Reusable code
    }
}
```

---

## Multiple Inheritance

Java doesn't support multiple inheritance for classes but supports it through interfaces.

```java
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// Class implementing multiple interfaces
class Duck implements Flyable, Swimmable {
    @Override
    public void fly() {
        System.out.println("Duck flying");
    }

    @Override
    public void swim() {
        System.out.println("Duck swimming");
    }
}

// Usage
Duck duck = new Duck();
duck.fly();
duck.swim();
```

---

## Interface Extending Interface

```java
interface A {
    void methodA();
}

interface B extends A {
    void methodB();
}

class C implements B {
    public void methodA() { }  // From A
    public void methodB() { }  // From B
}
```

---

## Files in This Folder

1. **BasicInterface.java** - Simple interface example
2. **MultipleInheritance.java** - Implementing multiple interfaces
3. **DefaultMethods.java** - Default methods (Java 8+)
4. **StaticMethods.java** - Static methods in interface
5. **InterfaceExtending.java** - Interface extending interface
6. **FunctionalInterface.java** - Single abstract method
7. **Payment.java** *(existing)* - Payment interface
8. **PaymentGateway.java** *(existing)* - Gateway interface
9. **BkashPayment.java, CashPayment.java** *(existing)* - Implementations

---

## Complete Example

```java
// Interface
interface Payment {
    // Abstract method
    void processPayment(double amount);

    // Default method
    default void printReceipt() {
        System.out.println("Printing receipt...");
    }

    // Static method
    static void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    // Constant
    double TAX_RATE = 0.18;
}

// Implementation 1
class CreditCard implements Payment {
    @Override
    public void processPayment(double amount) {
        Payment.validateAmount(amount);  // Call static method
        System.out.println("Processing credit card payment: $" + amount);
        printReceipt();  // Call default method
    }
}

// Implementation 2
class PayPal implements Payment {
    @Override
    public void processPayment(double amount) {
        Payment.validateAmount(amount);
        System.out.println("Processing PayPal payment: $" + amount);
    }

    // Override default method
    @Override
    public void printReceipt() {
        System.out.println("Sending email receipt...");
    }
}

// Usage
Payment payment1 = new CreditCard();
payment1.processPayment(100);

Payment payment2 = new PayPal();
payment2.processPayment(200);

System.out.println("Tax Rate: " + Payment.TAX_RATE);
```

---

## Marker Interface

Interface with no methods. Used to mark/tag classes.

```java
interface Serializable {
    // No methods
}

class Student implements Serializable {
    // Now Student objects can be serialized
}
```

**Examples in Java:**
- `Serializable`
- `Cloneable`
- `Remote`

---

## Functional Interface

Interface with exactly ONE abstract method. Can be used with lambda expressions.

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);  // Only one abstract method

    // Can have default/static methods
    default void display() {
        System.out.println("Calculator");
    }
}

// Usage with lambda
Calculator add = (a, b) -> a + b;
System.out.println(add.calculate(5, 3));  // 8
```

---

## Abstract Class vs Interface

| Feature              | Abstract Class          | Interface               |
|----------------------|-------------------------|-------------------------|
| **Keyword**          | `abstract`              | `interface`             |
| **Inheritance**      | `extends` (single)      | `implements` (multiple) |
| **Methods**          | Abstract + Concrete     | Abstract + Default/Static|
| **Fields**           | Any type                | `public static final`   |
| **Constructor**      | ✅ Yes                  | ❌ No                   |
| **Access Modifiers** | Any                     | `public` (methods)      |
| **When to use**      | IS-A (shared code)      | CAN-DO (capability)     |
| **Abstraction**      | 0-100%                  | 100%                    |

---

## When to Use Interface?

1. Define a contract/capability
2. Multiple inheritance needed
3. Unrelated classes need same behavior
4. Want to achieve loose coupling

**Examples:**
- `Flyable` - anything that can fly (bird, plane)
- `Comparable` - anything that can be compared
- `Serializable` - anything that can be serialized

---

## Key Points

✓ Interface is a contract (WHAT, not HOW)
✓ Use `implements` keyword
✓ Can implement multiple interfaces
✓ All methods are `public abstract` by default (before Java 8)
✓ All fields are `public static final` by default
✓ Default methods provide default implementation (Java 8+)
✓ Static methods provide utility functions (Java 8+)
✓ Cannot instantiate interface
✓ Class must implement all abstract methods

---

## Real-World Analogy

**Remote Control Interface:**
- Defines buttons: power(), volumeUp(), volumeDown()
- Different devices (TV, AC, Speaker) implement differently
- Same interface, different implementations
- Can control any device through interface

This is an interface!
