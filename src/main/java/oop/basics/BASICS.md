# Basics - Classes & Objects

## What is it?

**Basics** refers to the fundamental building blocks of Object-Oriented Programming in Java. It covers:
- **Classes** - Blueprints or templates for creating objects
- **Objects** - Instances of classes with actual values
- **Constructors** - Special methods to initialize objects
- **this keyword** - Reference to the current object

---

## Why is it necessary?

### 1. Foundation of OOP
Without understanding classes and objects, you cannot write object-oriented code. Everything in Java revolves around objects.

### 2. Code Organization
Classes help organize code into logical units, making it easier to understand and maintain.

### 3. Reusability
Once you create a class, you can create multiple objects from it without rewriting code.

### 4. Real-World Modeling
Classes allow you to model real-world entities (Person, Car, Account) in your code.

---

## Core Concepts

### What is a Class?
A class is a blueprint or template that defines:
- **Properties (fields)** - What an object "has"
- **Behaviors (methods)** - What an object "does"

**Analogy:** A class is like a blueprint for a house. The blueprint defines rooms, doors, windows, but isn't a house itself.

**Example:**
```java
class Person {
    // Properties
    String name;
    int age;

    // Behavior
    void introduce() {
        System.out.println("Hi, I'm " + name);
    }
}
```

---

### What is an Object?
An object is an instance of a class with actual values.

**Analogy:** If class is a blueprint, object is the actual house built from that blueprint.

**Example:**
```java
Person person1 = new Person();
person1.name = "Alice";
person1.age = 25;

Person person2 = new Person();
person2.name = "Bob";
person2.age = 30;
```

---

### What is a Constructor?
A constructor is a special method that initializes an object when it's created.

**Why necessary?**
- Ensures object is in valid state from the start
- Provides convenient way to set initial values
- Can perform setup logic

**Types:**
1. **Default Constructor** - No parameters
2. **Parameterized Constructor** - Takes parameters
3. **Copy Constructor** - Creates object from another object

**Example:**
```java
class Person {
    String name;
    int age;

    // Default constructor
    Person() {
        name = "Unknown";
        age = 0;
    }

    // Parameterized constructor
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Copy constructor
    Person(Person other) {
        this.name = other.name;
        this.age = other.age;
    }
}
```

---

### What is 'this' keyword?
`this` is a reference to the current object.

**Why necessary?**

1. **Differentiate between instance and local variables**
```java
class Person {
    String name;

    Person(String name) {
        this.name = name;  // this.name = instance variable
                           // name = parameter
    }
}
```

2. **Call other constructors**
```java
class Person {
    String name;
    int age;

    Person() {
        this("Unknown", 0);  // Call parameterized constructor
    }

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

3. **Return current object (method chaining)**
```java
class Person {
    String name;

    Person setName(String name) {
        this.name = name;
        return this;  // Return current object
    }
}

// Usage
person.setName("Alice").setAge(25).display();
```

---

## When to Use?

### Classes:
- When you need to model real-world entities
- When you need to organize related data and behavior
- When you want code reusability

### Objects:
- Every time you need an instance of a class
- To represent actual entities with specific values

### Constructors:
- Always! Every class should have at least one constructor
- Use parameterized constructor for mandatory fields
- Use default constructor for default values

### 'this' keyword:
- When parameter name matches field name
- For constructor chaining
- For method chaining (fluent API)

---

## Benefits

✅ **Modularity** - Code organized into logical units
✅ **Reusability** - Create multiple objects from one class
✅ **Maintainability** - Changes in one place affect all objects
✅ **Abstraction** - Hide complex implementation
✅ **Real-world modeling** - Represent entities naturally

---

## Common Mistakes

❌ **Forgetting `this` keyword**
```java
class Person {
    String name;

    Person(String name) {
        name = name;  // Wrong! Both refer to parameter
    }
}
```

❌ **Not initializing objects**
```java
Person person;  // Only declared, not initialized
person.name = "Alice";  // NullPointerException!

// Correct:
Person person = new Person();
```

❌ **Confusing class with object**
```java
Person.name = "Alice";  // Wrong! name is not static
```

---

## Real-World Example

```java
class BankAccount {
    // Properties
    private String accountNumber;
    private String holderName;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, String holderName) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = 0.0;
    }

    // Behaviors
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}

// Usage
BankAccount account = new BankAccount("123456", "John Doe");
account.deposit(1000);
account.withdraw(200);
System.out.println("Balance: " + account.getBalance());
```

---

## Key Takeaways

1. **Class** = Blueprint (defines structure)
2. **Object** = Instance (actual entity with values)
3. **Constructor** = Initializer (sets up object)
4. **this** = Current object reference

**Remember:** Without classes and objects, there is no OOP!

---

## Next Steps

After mastering basics:
1. Learn **Encapsulation** - Hide implementation details
2. Learn **Inheritance** - Reuse code through parent-child relationship
3. Learn **Polymorphism** - Same interface, different implementations
4. Learn **Abstraction** - Focus on "what" not "how"

---

**Basics are the foundation of everything in Java. Master them first!** 🎯
