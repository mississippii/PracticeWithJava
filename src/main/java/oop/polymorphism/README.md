# Polymorphism — One Interface, Many Forms

## What is Polymorphism?

Polymorphism means the same method name behaves differently depending on context.

**"Poly" = Many, "Morph" = Forms**

There are two types:

| Type | Also called | Decided when? | How? |
|---|---|---|---|
| Compile-time | Method Overloading | At compile time | Same name, different parameters |
| Runtime | Method Overriding | At runtime | Parent reference, child object |

---

## 1. Compile-Time Polymorphism — Method Overloading

Same method name, different parameters **in the same class**.  
Java decides which version to call at **compile time** based on the arguments you pass.

```java
public class Calculator {
    public int add(int a, int b)          { return a + b; }        // version 1
    public int add(int a, int b, int c)   { return a + b + c; }    // version 2
    public double add(double a, double b) { return a + b; }        // version 3
    public String add(String a, String b) { return a + " " + b; }  // version 4
}

Calculator calc = new Calculator();
calc.add(5, 10);          // → version 1 (int, int)
calc.add(5, 10, 15);      // → version 2 (int, int, int)
calc.add(2.5, 3.5);       // → version 3 (double, double)
calc.add("Hi", "there");  // → version 4 (String, String)
```

### Rules for overloading
1. Parameters must differ — by count, type, or order
2. Return type alone is NOT enough to overload
   ```java
   int method(int a) { }
   double method(int a) { }  // COMPILE ERROR — same parameters
   ```

---

## 2. Runtime Polymorphism — Method Overriding

Child class provides its own version of a parent method.  
A **parent reference holds a child object** — Java decides which version to call at **runtime**.

```java
Payment p1 = new CashPayment("Alice");
Payment p2 = new CardPayment("Bob", "1234...");
Payment p3 = new MobilePayment("Carol", "017...");

p1.pay(500);    // Alice paid $500.0 in cash
p2.pay(500);    // Bob paid $500.0 via card ending in ....
p3.pay(500);    // Carol paid $500.0 via mobile 017...
```

All three references are type `Payment`. Java looks at the actual object at runtime and calls the right `pay()`.

### The real power — polymorphic array

```java
Payment[] payments = {
    new CashPayment("Alice"),
    new CardPayment("Bob", "1234..."),
    new MobilePayment("Carol", "017...")
};

for (Payment payment : payments) {
    payment.pay(800);   // correct pay() called for each — no if/else needed
}
```

If you add a new `BkashPayment` class, this loop works without any change.

---

## Upcasting and Downcasting

### Upcasting — child stored in parent reference (automatic, safe)

```java
Payment payment = new CashPayment("Alice");  // upcasting
payment.pay(500);           // works — pay() is defined in Payment
// payment.printReceipt();  // COMPILE ERROR — printReceipt() is not in Payment
```

The reference type (`Payment`) decides what you can **call**.  
The object type (`CashPayment`) decides what actually **runs**.

### Downcasting — go back to child type (manual, needs check)

```java
if (payment instanceof CashPayment cash) {  // check + cast in one step (Java 16+)
    cash.printReceipt();   // now can access CashPayment-specific method
}
```

Always check with `instanceof` before downcasting — otherwise you risk a `ClassCastException`.

---

## `instanceof` operator

Used to check the actual type of an object before downcasting.

```java
for (Payment p : payments) {
    if (p instanceof CashPayment cash) {
        cash.printReceipt();
    } else if (p instanceof CardPayment card) {
        card.checkCardLimit();
    } else if (p instanceof MobilePayment mobile) {
        mobile.sendSmsConfirmation();
    }
}
```

The `instanceof X name` syntax (Java 16+) checks and casts in one line — no separate cast needed.

---

## Compile-time vs Runtime — side by side

```java
// Compile-time: Java picks add() version when compiling
calc.add(5, 10);      // decided before program runs
calc.add(2.5, 3.5);   // decided before program runs

// Runtime: Java picks pay() version while program is running
Payment p = new CashPayment("Alice");
p.pay(500);   // decided WHILE running — depends on actual object
```

---

## What cannot be overridden (same as inheritance)

| Method type | Can override? |
|---|---|
| `public` / `protected` | Yes |
| `final` | No — compile error |
| `static` | No — method hiding instead |
| `private` | No — not inherited |

---

## Files in this folder

| File | What it shows |
|---|---|
| `Calculator.java` | Compile-time polymorphism — method overloading |
| `Payment.java` | Parent class — defines `pay()` contract |
| `CashPayment.java` | Child — overrides `pay()`, adds `printReceipt()` |
| `CardPayment.java` | Child — overrides `pay()`, adds `checkCardLimit()` |
| `MobilePayment.java` | Child — overrides `pay()`, adds `sendSmsConfirmation()` |
| `PolymorphismDemo.java` | Full demo — overloading, overriding, upcasting, downcasting, instanceof |
