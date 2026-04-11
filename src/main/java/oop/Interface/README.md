# Interface — Contract for Behavior

## What is an Interface?

An interface defines **what** a class must do, without saying **how** to do it.

It is a contract — any class that signs it (`implements`) must fulfill every method in it.

```java
interface Payable {
    void pay(double amount);        // must be implemented
    String getPaymentMethod();      // must be implemented
}

class CashPayment implements Payable {
    @Override
    public void pay(double amount) { ... }     // fulfills the contract

    @Override
    public String getPaymentMethod() { ... }   // fulfills the contract
}
```

---

## Interface vs Abstract Class

| | Interface | Abstract Class |
|---|---|---|
| Keyword | `interface` / `implements` | `abstract` / `extends` |
| Multiple inheritance | Yes — implement many | No — extend one only |
| Constructor | No | Yes |
| Fields | Only `public static final` constants | Any type |
| Methods | abstract, default, static, private | abstract + concrete |
| When to use | Unrelated classes share a capability | Related classes share code |

**Rule of thumb:**
- Use **interface** when you define a capability: `Payable`, `Flyable`, `Comparable`
- Use **abstract class** when you define a family: `Shape → Circle, Rectangle`

---

## Method Types in an Interface

### 1. Abstract method — `public abstract` by default

Every implementing class **must** provide its own version.

```java
interface Payable {
    void pay(double amount);          // abstract — no body
    String getPaymentMethod();        // abstract — no body
}
```

### 2. Constant — `public static final` by default

Cannot be changed. Shared by all implementing classes.

```java
interface Payable {
    double MAX_AMOUNT = 100_000.0;   // constant — no modifier needed
}

// Access:
Payable.MAX_AMOUNT   // via interface
card.MAX_AMOUNT      // via implementing object (but prefer interface name)
```

### 3. Default method — Java 8+

Has a body. Implementing class **gets it for free** and can override it if needed.

```java
interface Payable {
    default void printReceipt(double amount) {
        System.out.println("Receipt: " + getPaymentMethod() + " — $" + amount);
    }
}
```

- `CashPayment` does not override it → uses Payable's version
- `BkashPayment` overrides it → sends SMS instead

### 4. Static method — Java 8+

Belongs to the **interface itself**, not to any implementing object.  
Called as `Payable.validate(...)`, never on an instance.

```java
interface Payable {
    static void validate(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount");
    }
}

// Usage:
Payable.validate(500);   // correct
cash.validate(500);      // works but misleading — avoid
```

### 5. Private method — Java 9+

Helper method inside the interface — shared by default methods, hidden from outside.

```java
interface Payable {
    default void printReceipt(double amount) {
        System.out.println("[Receipt] " + formatAmount(amount));  // uses private helper
    }

    default void printSummary(double amount) {
        System.out.println("[Summary] " + formatAmount(amount));  // reuses same helper
    }

    private String formatAmount(double amount) {
        return String.format("$%.2f", amount);   // shared logic, not exposed
    }
}
```

---

## Multiple Interface Implementation

A class can implement **more than one interface** — unlike class inheritance which allows only one.

```java
class CardPayment implements Payable, Taxable {
    // Must implement: pay(), getPaymentMethod()  (from Payable)
    // Must implement: calculateTax()              (from Taxable)
    // Gets for free:  printReceipt(), totalWithTax() (defaults)
}
```

```java
CardPayment card = new CardPayment("Carol", "4321");

// Can be used as either type
Payable  p = card;
Taxable  t = card;

p.pay(1000);
t.calculateTax(1000);
```

---

## Interface Extending Interface

An interface can extend another interface — adding more methods to the contract.

```java
interface Payable {
    void pay(double amount);
    String getPaymentMethod();
}

interface Refundable extends Payable {
    void refund(double amount);   // adds refund on top of Payable
}
```

A class implementing `Refundable` must fulfill **both** contracts:

```java
class OnlinePayment implements Refundable {
    @Override public void pay(double amount) { ... }
    @Override public String getPaymentMethod() { ... }
    @Override public void refund(double amount) { ... }
}
```

---

## Polymorphism with Interface

Interface reference can hold any implementing object — same as parent reference in inheritance.

```java
Payable[] payments = {
    new CashPayment("Alice"),
    new BkashPayment("Bob", "017..."),
    new CardPayment("Carol", "4321"),
    new OnlinePayment("David")
};

for (Payable payment : payments) {
    payment.pay(750);   // correct pay() called for each at runtime
}
```

---

## Functional Interface

An interface with **exactly one abstract method**. Can be used with a lambda expression.

```java
@FunctionalInterface
interface Greeting {
    void greet(String name);   // only one abstract method
}

// Without lambda (old way):
Greeting g = new Greeting() {
    @Override
    public void greet(String name) {
        System.out.println("Hello, " + name);
    }
};

// With lambda (clean way):
Greeting g = name -> System.out.println("Hello, " + name);
g.greet("Alice");   // Hello, Alice
```

`@FunctionalInterface` is optional but recommended — compiler will error if you add a second abstract method by mistake.

**Built-in functional interfaces in Java:** `Runnable`, `Callable`, `Comparator`, `Predicate`, `Function`, `Consumer`, `Supplier`

---

## The Diamond Problem — Why Interface Solves It

### The problem with classes

```
        A
       / \
      B   C     both override greet() from A
       \ /
        D        which greet() does D get? — AMBIGUOUS
```

```java
class A { void greet() { System.out.println("A"); } }
class B extends A { void greet() { System.out.println("B"); } }
class C extends A { void greet() { System.out.println("C"); } }

class D extends B, C { }   // COMPILE ERROR — not allowed in Java
// d.greet() — B's version or C's? Java cannot decide, so it blocks this entirely.
```

Java **forbids** multiple class inheritance because there is no safe way to resolve the conflict.

---

### Why interfaces didn't have this problem (before Java 8)

Before Java 8, interfaces had **no method bodies** — only signatures. So even if two interfaces declared the same method, there was nothing to conflict. The implementing class always provided the only body.

```java
interface Flyable   { void move(); }   // no body
interface Swimmable { void move(); }   // no body

class Duck implements Flyable, Swimmable {
    @Override
    public void move() {               // Duck writes the only body — no conflict
        System.out.println("Duck moves");
    }
}
```

---

### Java 8 default methods — diamond can appear again

When Java 8 added default methods, the diamond became possible with interfaces too:

```java
interface A {
    default void greet() { System.out.println("Hello from A"); }
}
interface B extends A {
    default void greet() { System.out.println("Hello from B"); }
}
interface C extends A {
    default void greet() { System.out.println("Hello from C"); }
}

class D implements B, C { }   // which greet() does D get?
```

Java resolves this with **3 clear rules**.

---

### Rule 1 — Class always wins over interface

If the class itself provides a body, it wins. No conflict.

```java
class D implements B, C {
    @Override
    public void greet() {
        System.out.println("Hello from D");  // D decides — end of story
    }
}
```

---

### Rule 2 — More specific interface wins

If one interface extends another, the child interface's default method wins automatically.

```java
interface A {
    default void greet() { System.out.println("Hello from A"); }
}
interface B extends A {
    @Override
    default void greet() { System.out.println("Hello from B"); }
}

class D implements A, B { }   // B is more specific — B's greet() wins, no override needed

new D().greet();   // Hello from B
```

---

### Rule 3 — Two unrelated interfaces conflict → class MUST override

If two interfaces at the same level both have the same default method, Java forces the implementing class to resolve it. Compile error if it doesn't.

```java
interface B {
    default void greet() { System.out.println("Hello from B"); }
}
interface C {
    default void greet() { System.out.println("Hello from C"); }
}

class D implements B, C {
    @Override
    public void greet() {
        B.super.greet();   // explicitly pick B's version
        // C.super.greet();   — or pick C's version
        // or write your own logic entirely
    }
}
```

`InterfaceName.super.method()` is how you explicitly call a specific interface's default method.

---

### Why interfaces can solve it but classes cannot

| | Class Inheritance | Interface |
|---|---|---|
| Multiple inheritance | Not allowed | Allowed |
| Diamond problem | Unsolvable — Java blocks it | Resolved with 3 rules |
| Conflict resolution | No mechanism | Class overrides + `Interface.super.method()` |
| Why safe? | Blocked entirely | Class always has the final say |

The fundamental difference: with classes Java has no safe way to decide — so it forbids it. With interfaces, the **implementing class always has the final say**. Even when two interfaces conflict, Java forces you to write the resolution explicitly — making it controlled, not ambiguous.

---

## Common Mistakes

### Trying to instantiate an interface
```java
Payable p = new Payable();   // COMPILE ERROR — interface cannot be instantiated
Payable p = new CashPayment("Alice");  // correct
```

### Forgetting to implement all abstract methods
```java
class CashPayment implements Payable {
    @Override
    public void pay(double amount) { ... }
    // forgot getPaymentMethod() — COMPILE ERROR
}
```

### Calling static method on an instance
```java
Payable.validate(500);   // correct
cash.validate(500);      // works but misleading — always use interface name
```

---

## Files in this folder

| File | What it shows |
|---|---|
| `Payable.java` | Main interface — all 5 method types + constant |
| `Taxable.java` | Second interface — for multiple interface demo |
| `Refundable.java` | Interface extending Payable — adds refund contract |
| `CashPayment.java` | Implements Payable, uses default method as-is |
| `BkashPayment.java` | Implements Payable, overrides default method |
| `CardPayment.java` | Implements Payable + Taxable (multiple interfaces) |
| `OnlinePayment.java` | Implements Refundable (interface extending interface) |
| `InterfaceDemo.java` | Full demo — all concepts in one place |
