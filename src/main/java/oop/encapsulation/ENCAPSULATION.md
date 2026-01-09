# Encapsulation - Data Hiding

## What is it?

**Encapsulation** is the practice of wrapping data (fields) and code (methods) together into a single unit (class) and restricting direct access to some components.

**In simple words:** Hide the internal state of an object and require all interaction through methods.

**"Data Hiding" - Keep fields private, provide public methods**

---

## Why is it necessary?

### 1. Data Protection
Prevents invalid or unauthorized data modification.

**Without Encapsulation:**
```java
class BankAccount {
    public double balance;  // Anyone can access!
}

BankAccount account = new BankAccount();
account.balance = -1000;  // Invalid! But no way to prevent
```

**With Encapsulation:**
```java
class BankAccount {
    private double balance;  // Hidden!

    public void deposit(double amount) {
        if (amount > 0) {  // Validation!
            balance += amount;
        }
    }
}
```

---

### 2. Flexibility
Change implementation without affecting external code.

**Example:**
```java
class Temperature {
    private double celsius;

    public double getFahrenheit() {
        return (celsius * 9/5) + 32;
    }

    // Later, you can change to store Fahrenheit internally
    // without breaking external code!
}
```

---

### 3. Control
Decide what can be read, written, or neither.

**Example:**
```java
class Person {
    private final String id;  // Read-only
    private String name;      // Read & Write
    private int age;          // Read & Write with validation

    public String getId() {
        return id;  // Only getter, no setter
    }

    public void setAge(int age) {
        if (age > 0 && age < 150) {  // Validation
            this.age = age;
        }
    }
}
```

---

### 4. Maintainability
Easier to modify and test internal implementation.

---

### 5. Security
Hide sensitive data from unauthorized access.

**Example:**
```java
class User {
    private String password;  // Hidden!

    public boolean verifyPassword(String input) {
        return password.equals(hashPassword(input));  // Secure check
    }
}
```

---

## How to Achieve Encapsulation?

### Step 1: Make fields private
```java
private String name;
private int age;
```

### Step 2: Provide public getter methods
```java
public String getName() {
    return name;
}

public int getAge() {
    return age;
}
```

### Step 3: Provide public setter methods (with validation)
```java
public void setName(String name) {
    if (name != null && !name.trim().isEmpty()) {
        this.name = name;
    }
}

public void setAge(int age) {
    if (age > 0 && age < 150) {
        this.age = age;
    } else {
        throw new IllegalArgumentException("Invalid age");
    }
}
```

---

## Complete Example

```java
public class Student {
    // Private fields - cannot access directly from outside
    private String rollNumber;
    private String name;
    private int age;
    private double cgpa;

    // Constructor
    public Student(String rollNumber, String name, int age) {
        this.rollNumber = rollNumber;
        setName(name);  // Use setter for validation
        setAge(age);
        this.cgpa = 0.0;
    }

    // Getters - Read access

    public String getRollNumber() {
        return rollNumber;  // Read-only (no setter)
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getCgpa() {
        return cgpa;
    }

    // Setters - Write access with validation

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    public void setAge(int age) {
        if (age >= 18 && age <= 100) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age must be between 18 and 100");
        }
    }

    public void setCgpa(double cgpa) {
        if (cgpa >= 0.0 && cgpa <= 10.0) {
            this.cgpa = cgpa;
        } else {
            throw new IllegalArgumentException("CGPA must be between 0 and 10");
        }
    }

    // Business methods
    public boolean isEligibleForPlacement() {
        return cgpa >= 6.0;
    }
}

// Usage
Student student = new Student("CS101", "Alice", 20);
// student.cgpa = 15.0;  // Error! Cannot access private field
student.setCgpa(8.5);  // OK! Goes through validation
System.out.println(student.getCgpa());  // 8.5
```

---

## Encapsulation Levels

### 1. Full Encapsulation (Best Practice)
All fields private with getters/setters.

```java
class Person {
    private String name;
    private int age;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
```

---

### 2. Read-Only Encapsulation
Fields private with only getters (no setters).

```java
class Product {
    private final String id;
    private final String name;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    // No setters - immutable!
}
```

---

### 3. Write-Only Encapsulation
Fields private with only setters (no getters).

```java
class Logger {
    private String logLevel;

    public void setLogLevel(String level) {
        this.logLevel = level;
    }
    // No getter - internal use only
}
```

---

## Benefits of Encapsulation

✅ **Data Protection** - Prevent invalid data
✅ **Flexibility** - Change implementation anytime
✅ **Control** - Decide what to expose
✅ **Maintainability** - Easy to modify
✅ **Security** - Hide sensitive information
✅ **Testing** - Easy to test each method
✅ **Debugging** - Easy to find issues

---

## Encapsulation vs Abstraction

| Encapsulation                    | Abstraction                      |
|----------------------------------|----------------------------------|
| Data hiding                      | Implementation hiding            |
| How to achieve data hiding       | What to hide                     |
| Private fields + public methods  | Abstract classes/interfaces      |
| Implementation level             | Design level                     |
| Achieved using access modifiers  | Achieved using abstract/interface|

---

## Common Mistakes

### ❌ Mistake 1: Public fields
```java
class Person {
    public String name;  // Bad! Anyone can modify
    public int age;      // Bad! No validation
}
```

### ❌ Mistake 2: Setter without validation
```java
public void setAge(int age) {
    this.age = age;  // Bad! No validation
}

// Allows:
person.setAge(-50);  // Invalid age!
```

### ❌ Mistake 3: Returning mutable objects
```java
class Student {
    private List<String> courses;

    public List<String> getCourses() {
        return courses;  // Bad! Caller can modify!
    }
}

// Better:
public List<String> getCourses() {
    return new ArrayList<>(courses);  // Return copy
}
```

---

## Real-World Examples

### Example 1: Bank Account
```java
public class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    // No setter for balance - only through methods
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
```

### Example 2: User Authentication
```java
public class User {
    private String username;
    private String passwordHash;  // Hashed, not plain text

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    public boolean authenticate(String password) {
        return passwordHash.equals(hashPassword(password));
    }

    // Password is never exposed
    private String hashPassword(String password) {
        // Hashing logic
        return password;  // Simplified
    }
}
```

---

## When to Use?

### Always use encapsulation when:
- ✅ You have class fields
- ✅ You need data validation
- ✅ You want to hide implementation details
- ✅ You need to control access
- ✅ You're writing production code

### Don't skip encapsulation for:
- ❌ "It's just a simple class" - Bad habits start here
- ❌ "I'll add getters/setters later" - Do it now
- ❌ "Only I will use this code" - You won't remember after 6 months

---

## Best Practices

1. **Always start with private fields**
2. **Provide getters/setters only when needed**
3. **Always validate in setters**
4. **Return copies of mutable objects**
5. **Use final for read-only fields**
6. **Follow naming conventions:** `getName()`, `setName()`
7. **Don't create getters/setters automatically** - Think about what should be exposed

---

## Key Takeaways

1. **Encapsulation** = Data hiding + Controlled access
2. **Private fields** + **Public methods** = Encapsulation
3. **Why?** Protection, Flexibility, Control, Security
4. **How?** private fields + getters/setters with validation
5. **Always validate** in setters
6. **Think before exposing** - Not everything needs getter/setter

---

## Real-World Analogy

**ATM Machine:**
- You can't directly access the cash inside (private fields)
- You interact through buttons/screen (public methods)
- ATM validates your requests (validation in setters)
- ATM shows your balance (getter method)
- You can't set your balance directly (no direct access)

This is encapsulation in real life!

---

**Encapsulation is the first pillar of OOP. Master it to write secure, maintainable code!** 🔒
