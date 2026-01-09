# 07. Access Modifiers - Visibility Control

## What are Access Modifiers?
Keywords that set the accessibility/visibility of classes, methods, constructors, and fields.

**Control WHO can access WHAT.**

---

## Types of Access Modifiers

1. **`public`** - Accessible everywhere
2. **`private`** - Accessible only within class
3. **`protected`** - Accessible within package + subclasses
4. **`default`** (no keyword) - Accessible within package only

---

## Access Levels

| Modifier    | Class | Package | Subclass | World |
|-------------|-------|---------|----------|-------|
| `public`    | ✅    | ✅      | ✅       | ✅    |
| `protected` | ✅    | ✅      | ✅       | ❌    |
| `default`   | ✅    | ✅      | ❌       | ❌    |
| `private`   | ✅    | ❌      | ❌       | ❌    |

---

## 1. Public

**Accessible from anywhere.**

```java
public class Person {
    public String name;

    public void display() {
        System.out.println("Name: " + name);
    }
}

// Accessible from any package
Person person = new Person();
person.name = "John";  // OK
person.display();      // OK
```

**Use for:**
- Classes you want to expose
- Public API methods
- Constants meant to be accessed globally

---

## 2. Private

**Accessible only within the same class.**

```java
public class BankAccount {
    private double balance;  // Hidden from outside

    private void calculateInterest() {
        // Helper method - only used internally
    }

    public void deposit(double amount) {
        balance += amount;  // Can access private member
    }

    public double getBalance() {
        return balance;  // Controlled access
    }
}

// Usage
BankAccount account = new BankAccount();
// account.balance = 1000;  // ERROR: balance is private
account.deposit(1000);      // OK: public method
```

**Use for:**
- Internal implementation details
- Fields (encapsulation)
- Helper methods

---

## 3. Protected

**Accessible within same package + all subclasses (even in different packages).**

```java
// package1/Parent.java
package package1;

public class Parent {
    protected int value = 10;

    protected void display() {
        System.out.println("Protected method");
    }
}

// package2/Child.java
package package2;
import package1.Parent;

public class Child extends Parent {
    void show() {
        System.out.println(value);  // OK: inherited protected member
        display();                  // OK: inherited protected method
    }
}

// package2/Other.java
package package2;
import package1.Parent;

public class Other {
    void test() {
        Parent p = new Parent();
        // System.out.println(p.value);  // ERROR: not a subclass
    }
}
```

**Use for:**
- Members you want subclasses to access
- Inheritance hierarchies

---

## 4. Default (Package-Private)

**Accessible only within the same package. No keyword needed.**

```java
// package1/Class1.java
package package1;

class DefaultClass {  // default access
    int value = 10;   // default access

    void display() {  // default access
        System.out.println("Default method");
    }
}

// package1/Class2.java
package package1;

public class Class2 {
    void test() {
        DefaultClass obj = new DefaultClass();  // OK: same package
        System.out.println(obj.value);          // OK
        obj.display();                          // OK
    }
}

// package2/Class3.java
package package2;
import package1.DefaultClass;  // ERROR: not accessible

public class Class3 {
    // Cannot use DefaultClass here
}
```

**Use for:**
- Package-level internal classes
- Helper classes within package

---

## Files in This Folder

1. **PublicExample.java** - public access
2. **PrivateExample.java** - private access
3. **ProtectedExample.java** - protected access
4. **DefaultExample.java** - default access
5. **AccessComparison.java** - All modifiers in one example

---

## Complete Example

```java
public class AccessExample {
    // Public - accessible everywhere
    public String publicField = "Public";

    // Private - only within this class
    private String privateField = "Private";

    // Protected - same package + subclasses
    protected String protectedField = "Protected";

    // Default - only same package
    String defaultField = "Default";

    // Public method
    public void publicMethod() {
        System.out.println("Public method");
        privateMethod();  // Can call private method within class
    }

    // Private method
    private void privateMethod() {
        System.out.println("Private method");
    }

    // Protected method
    protected void protectedMethod() {
        System.out.println("Protected method");
    }

    // Default method
    void defaultMethod() {
        System.out.println("Default method");
    }
}

// Same package class
class SamePackageClass {
    void test() {
        AccessExample obj = new AccessExample();
        System.out.println(obj.publicField);     // OK
        // System.out.println(obj.privateField); // ERROR
        System.out.println(obj.protectedField);  // OK
        System.out.println(obj.defaultField);    // OK
    }
}

// Different package class
// package other;
// import AccessExample;

// class DifferentPackageClass {
//     void test() {
//         AccessExample obj = new AccessExample();
//         System.out.println(obj.publicField);     // OK
//         // System.out.println(obj.privateField);  // ERROR
//         // System.out.println(obj.protectedField); // ERROR
//         // System.out.println(obj.defaultField);   // ERROR
//     }
// }

// Different package subclass
// package other;
// import AccessExample;

// class SubClass extends AccessExample {
//     void test() {
//         System.out.println(publicField);     // OK
//         // System.out.println(privateField);  // ERROR
//         System.out.println(protectedField);  // OK: inherited
//         // System.out.println(defaultField);   // ERROR
//     }
// }
```

---

## Rules for Classes

**Top-level classes** can only be:
- `public` - accessible from anywhere
- `default` - accessible within package

```java
public class PublicClass { }  // OK
class DefaultClass { }        // OK (default)
// private class PrivateClass { }  // ERROR
// protected class ProtectedClass { }  // ERROR
```

**Inner classes** can have any access modifier.

---

## Method Overriding and Access Modifiers

When overriding, access modifier must be **same or less restrictive**.

```java
class Parent {
    protected void method() { }
}

class Child extends Parent {
    // Valid options
    protected void method() { }  // Same
    public void method() { }     // Less restrictive

    // Invalid
    // private void method() { }  // ERROR: More restrictive
}
```

---

## Best Practices

1. **Start with most restrictive** (private), then widen if needed
2. **Fields:** Always `private` (encapsulation)
3. **Methods:** Use `public` for API, `private` for helpers
4. **Classes:** `public` if part of API, default otherwise
5. **Protected:** Only when inheritance is intended

---

## Key Points

✓ `public` = Accessible everywhere
✓ `private` = Only within class
✓ `protected` = Same package + subclasses
✓ `default` = Only same package
✓ Always use most restrictive access level possible
✓ Fields should be `private` (encapsulation)
✓ When overriding, can't be more restrictive

---

## Real-World Analogy

**House:**
- **Public:** Front door (anyone can enter)
- **Protected:** Family room (family + guests)
- **Default:** Kitchen (only family)
- **Private:** Bedroom (only you)

Different rooms, different access levels!
