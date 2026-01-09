# 02. Encapsulation - Data Hiding

## What is Encapsulation?
Wrapping data (fields) and code (methods) together into a single unit and restricting direct access to some components.

**"Data Hiding" - Hide internal state and require all interaction through methods.**

---

## Why Encapsulation?

1. **Data Protection** - Prevent invalid data
2. **Flexibility** - Change implementation without affecting code
3. **Maintainability** - Easy to modify and test
4. **Control** - Decide what to expose and what to hide

---

## How to Achieve Encapsulation?

1. Declare fields as `private`
2. Provide public `getter` methods to read
3. Provide public `setter` methods to write (with validation)

---

## Key Concepts

### 1. Private Fields
```java
private String name;  // Cannot access directly
```

### 2. Getter Method
```java
public String getName() {
    return name;
}
```

### 3. Setter Method with Validation
```java
public void setAge(int age) {
    if (age > 0 && age < 150) {
        this.age = age;
    } else {
        throw new IllegalArgumentException("Invalid age");
    }
}
```

---

## Files in This Folder

1. **BankAccount.java** - Classic encapsulation example
2. **Student.java** - Data validation in setters
3. **Person.java** - Read-only fields (only getter)
4. **CharStack.java** *(existing)* - Data structure encapsulation
5. **ShoppingCart.java** *(existing)* - Business logic encapsulation

---

## Example

```java
public class BankAccount {
    // Private fields - Cannot access directly
    private String accountNumber;
    private double balance;
    private String accountHolder;

    // Constructor
    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
    }

    // Getter for balance (read-only, no setter)
    public double getBalance() {
        return balance;
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // No setter for account number (immutable)

    // Controlled access to modify balance
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid amount");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
            return true;
        } else {
            System.out.println("Insufficient balance or invalid amount");
            return false;
        }
    }

    // Display account info
    public void displayInfo() {
        System.out.println("Account: " + accountNumber);
        System.out.println("Holder: " + accountHolder);
        System.out.println("Balance: $" + balance);
    }
}

// Usage
BankAccount account = new BankAccount("123456", "John Doe");
// account.balance = 1000;  // ERROR: Cannot access private field
account.deposit(1000);      // OK: Controlled access
account.withdraw(500);      // OK: With validation
System.out.println(account.getBalance());  // OK: Read access
```

---

## Benefits Example

### Without Encapsulation (Bad)
```java
class Student {
    public int age;  // Public field
}

Student s = new Student();
s.age = -5;  // No validation! Invalid data!
```

### With Encapsulation (Good)
```java
class Student {
    private int age;  // Private field

    public void setAge(int age) {
        if (age > 0 && age < 100) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Invalid age");
        }
    }

    public int getAge() {
        return age;
    }
}

Student s = new Student();
s.setAge(-5);  // Exception thrown! Data protected!
```

---

## Key Points

✓ Keep fields `private`
✓ Provide `public` getters/setters
✓ Validate data in setters
✓ Make fields read-only if needed (getter only)
✓ Never expose internal implementation
✓ Encapsulation = Data hiding + Controlled access

---

## Real-World Analogy

**ATM Machine:**
- You can't directly access the cash (private fields)
- You interact through buttons/screen (public methods)
- ATM validates your requests (setters with validation)
- ATM shows your balance (getter method)

This is encapsulation!
