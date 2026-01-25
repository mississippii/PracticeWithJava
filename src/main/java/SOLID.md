# SOLID Principles - Clean Code Design Guide

> **SOLID** = 5 principles for writing maintainable, scalable, and flexible code.

---

## Quick Overview

| Letter | Principle | One-Liner |
|--------|-----------|-----------|
| **S** | Single Responsibility | One class = One job |
| **O** | Open/Closed | Open for extension, closed for modification |
| **L** | Liskov Substitution | Child classes should be substitutable for parent |
| **I** | Interface Segregation | Many small interfaces > One fat interface |
| **D** | Dependency Inversion | Depend on abstractions, not concretions |

---

## Real-World Analogy: Restaurant Kitchen

Think of a restaurant kitchen to understand SOLID:

| Principle | Restaurant Analogy |
|-----------|-------------------|
| **S** - Single Responsibility | Chef cooks, waiter serves, cashier bills - everyone has ONE job |
| **O** - Open/Closed | Add new dishes to menu without changing the kitchen |
| **L** - Liskov Substitution | Any chef can replace another chef for the same task |
| **I** - Interface Segregation | Waiter doesn't need to know cooking, chef doesn't need billing |
| **D** - Dependency Inversion | Kitchen depends on "ingredients" concept, not specific vendors |

---

# S - Single Responsibility Principle (SRP)

## Definition
> **"A class should have only one reason to change."**

A class should do ONE thing and do it well.

---

## Real-World Analogy: Swiss Army Knife vs Dedicated Tools

**Swiss Army Knife (Bad SRP)**:
- Does many things: cutting, screwing, opening bottles
- If blade breaks, entire tool is affected
- Hard to improve one function without affecting others

**Dedicated Tools (Good SRP)**:
- Knife just cuts
- Screwdriver just screws
- Each tool does one thing perfectly
- Easy to replace or upgrade individual tools

---

## Code Example

### ❌ BAD - Violates SRP

```java
public class Employee {
    private String name;
    private double salary;

    // Responsibility 1: Employee data
    public String getName() { return name; }
    public double getSalary() { return salary; }

    // Responsibility 2: Salary calculation (should be separate)
    public double calculateTax() {
        return salary * 0.3;
    }

    // Responsibility 3: Database operations (should be separate)
    public void saveToDatabase() {
        // Connect to DB
        // Execute INSERT query
        System.out.println("Saving to database...");
    }

    // Responsibility 4: Report generation (should be separate)
    public void generatePayslip() {
        // Create PDF
        // Format salary details
        System.out.println("Generating payslip...");
    }
}
```

**Problems**:
- Change in tax calculation? Modify Employee class
- Change database? Modify Employee class
- Change payslip format? Modify Employee class
- Class has 4 reasons to change!

---

### ✅ GOOD - Follows SRP

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

    public Employee findById(int id) {
        // Fetch from DB
        return null;
    }
}

// Responsibility 4: Report generation
public class PayslipGenerator {
    public void generate(Employee employee) {
        System.out.println("Generating payslip for " + employee.getName());
    }
}
```

**Benefits**:
- Each class has ONE reason to change
- Easy to test individually
- Easy to reuse (TaxCalculator can be used elsewhere)
- Easy to maintain

---

## How to Identify SRP Violation?

Ask yourself:
1. **"What does this class do?"** - If you use "AND" in the answer, it's doing too much
2. **"Who would request changes?"** - If different stakeholders (accountant, DBA, HR), split the class
3. **"Can I describe the class in one sentence without 'and'?"**

---

## When to Apply SRP?

✅ Apply when:
- Class is getting too large (>200-300 lines)
- Multiple reasons to change
- Hard to name the class (ends up with generic names like "Manager", "Handler")
- Difficult to test

⚠️ Don't over-apply:
- Don't create classes with single methods
- Related functionality can stay together
- Balance between too many classes and too few

---

# O - Open/Closed Principle (OCP)

## Definition
> **"Software entities should be open for extension but closed for modification."**

You should be able to add new functionality WITHOUT changing existing code.

---

## Real-World Analogy: USB Ports

**Your Laptop (Closed for Modification)**:
- You don't open your laptop to add new device support
- Motherboard stays unchanged

**USB Port (Open for Extension)**:
- Plug in mouse, keyboard, camera, phone - all work!
- New devices can be added without modifying laptop

**How?** → Common interface (USB standard)

---

## Code Example

### ❌ BAD - Violates OCP

```java
public class PaymentProcessor {

    public void processPayment(String paymentType, double amount) {
        // Every new payment method requires modifying this class!

        if (paymentType.equals("CREDIT_CARD")) {
            System.out.println("Processing credit card payment: $" + amount);
            // Credit card logic
        }
        else if (paymentType.equals("PAYPAL")) {
            System.out.println("Processing PayPal payment: $" + amount);
            // PayPal logic
        }
        else if (paymentType.equals("BKASH")) {
            System.out.println("Processing bKash payment: $" + amount);
            // bKash logic
        }
        // Adding new payment? Must modify this class!
        // else if (paymentType.equals("CRYPTO")) { ... }
    }
}
```

**Problems**:
- Adding bKash? Modify existing code
- Adding Crypto? Modify again
- Risk of breaking existing functionality
- Long if-else chain
- Violates SRP too!

---

### ✅ GOOD - Follows OCP

```java
// Step 1: Create abstraction (interface)
public interface PaymentMethod {
    void pay(double amount);
}

// Step 2: Implement for each payment type
public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing credit card payment: $" + amount);
        // Credit card specific logic
    }
}

public class PayPalPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment: $" + amount);
        // PayPal specific logic
    }
}

public class BkashPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing bKash payment: $" + amount);
        // bKash specific logic
    }
}

// Step 3: Processor works with abstraction
public class PaymentProcessor {

    public void processPayment(PaymentMethod paymentMethod, double amount) {
        paymentMethod.pay(amount);
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        // Use different payment methods
        processor.processPayment(new CreditCardPayment(), 100);
        processor.processPayment(new PayPalPayment(), 200);
        processor.processPayment(new BkashPayment(), 300);

        // Adding Crypto? Just create new class - NO modification!
        // processor.processPayment(new CryptoPayment(), 400);
    }
}
```

**Benefits**:
- Add new payment method? Create new class (NO modification to existing)
- Existing code stays untouched and tested
- Each payment method is isolated
- Easy to test individually

---

## Adding New Feature (Crypto Payment)

```java
// Just add a new class - NO changes to existing code!
public class CryptoPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing crypto payment: $" + amount);
        // Crypto specific logic
    }
}

// Use it
processor.processPayment(new CryptoPayment(), 500);
```

**No existing code was modified!** This is OCP in action.

---

## Techniques to Achieve OCP

1. **Interfaces/Abstract Classes** - Define contracts
2. **Strategy Pattern** - Swap algorithms at runtime
3. **Template Method Pattern** - Define skeleton, override steps
4. **Decorator Pattern** - Add behavior without modification

---

# L - Liskov Substitution Principle (LSP)

## Definition
> **"Objects of a superclass should be replaceable with objects of its subclasses without affecting program correctness."**

If B is a subclass of A, then wherever A is used, B should work without problems.

---

## Real-World Analogy: Electric Cars

**Car (Parent Class)**:
- Has method: `refuel()`

**Electric Car (Subclass)**:
- Doesn't use fuel! Uses `charge()` instead
- If code calls `refuel()` on ElectricCar → Problem!

**Better Design**:
- Parent should be "Vehicle" with `addEnergy()`
- Each child implements its own way

---

## Code Example

### ❌ BAD - Violates LSP

```java
public class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

public class Sparrow extends Bird {
    @Override
    public void fly() {
        System.out.println("Sparrow flying high!");
    }
}

public class Penguin extends Bird {
    @Override
    public void fly() {
        // Penguins CAN'T fly!
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}

// Problem: Code that expects Bird to fly will break with Penguin
public class BirdHandler {
    public void makeBirdFly(Bird bird) {
        bird.fly();  // CRASHES if bird is Penguin!
    }
}
```

**Problems**:
- Penguin IS-A Bird, but can't substitute Bird (breaks fly)
- Calling code must check type (defeats polymorphism)
- Unexpected exceptions

---

### ✅ GOOD - Follows LSP

```java
// Separate flying capability
public interface Flyable {
    void fly();
}

// Base bird class without fly assumption
public abstract class Bird {
    public abstract void eat();
    public abstract void makeSound();
}

// Flying birds
public class Sparrow extends Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Sparrow flying!");
    }

    @Override
    public void eat() {
        System.out.println("Sparrow eating seeds");
    }

    @Override
    public void makeSound() {
        System.out.println("Chirp chirp!");
    }
}

public class Eagle extends Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("Eagle soaring high!");
    }

    @Override
    public void eat() {
        System.out.println("Eagle hunting");
    }

    @Override
    public void makeSound() {
        System.out.println("Screech!");
    }
}

// Non-flying birds
public class Penguin extends Bird {
    // No fly method - Penguin doesn't claim to fly

    @Override
    public void eat() {
        System.out.println("Penguin eating fish");
    }

    @Override
    public void makeSound() {
        System.out.println("Honk!");
    }

    public void swim() {
        System.out.println("Penguin swimming!");
    }
}

// Now code is type-safe
public class BirdHandler {
    public void makeFly(Flyable flyable) {
        flyable.fly();  // Only accepts things that CAN fly
    }

    public void feedBird(Bird bird) {
        bird.eat();  // All birds can eat - safe!
    }
}
```

**Benefits**:
- Penguin doesn't need to implement fly
- Type system enforces correctness
- No runtime exceptions
- All birds can substitute in `feedBird()` safely

---

## Classic LSP Example: Rectangle & Square

### ❌ BAD Design

```java
public class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

public class Square extends Rectangle {
    // Square width == height always
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // Must keep equal!
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
        this.width = height;  // Must keep equal!
    }
}

// Problem!
public void testRectangle(Rectangle rect) {
    rect.setWidth(5);
    rect.setHeight(10);
    // Expected area: 50
    // If rect is Square: width=10, height=10, area=100!
    assert rect.getArea() == 50;  // FAILS for Square!
}
```

### ✅ Better Design

```java
public interface Shape {
    int getArea();
}

public class Rectangle implements Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getArea() { return width * height; }
}

public class Square implements Shape {
    private final int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public int getArea() { return side * side; }
}
```

---

## LSP Rules

1. **Method signatures**: Subclass must accept same or broader input types
2. **Return types**: Subclass must return same or narrower return types
3. **Exceptions**: Subclass must not throw new exceptions
4. **Preconditions**: Subclass cannot strengthen preconditions
5. **Postconditions**: Subclass cannot weaken postconditions
6. **Invariants**: Subclass must maintain parent's invariants

---

# I - Interface Segregation Principle (ISP)

## Definition
> **"Clients should not be forced to depend on interfaces they don't use."**

Many specific interfaces are better than one general-purpose interface.

---

## Real-World Analogy: Restaurant Roles

**BAD: One interface for all staff**
```
interface RestaurantStaff {
    void cook();
    void serve();
    void clean();
    void manageBills();
    void orderInventory();
}
```
- Waiter must implement `cook()` - doesn't make sense!
- Chef must implement `manageBills()` - waste!

**GOOD: Specific interfaces**
```
interface Cook { void cook(); }
interface Server { void serve(); }
interface Cleaner { void clean(); }
interface Cashier { void manageBills(); }
```
- Each role implements only what they need

---

## Code Example

### ❌ BAD - Fat Interface

```java
// One big interface
public interface Worker {
    void work();
    void eat();
    void sleep();
    void attendMeeting();
    void writeCode();
    void reviewCode();
    void manageTeam();
}

// Developer
public class Developer implements Worker {
    @Override public void work() { System.out.println("Working..."); }
    @Override public void eat() { System.out.println("Eating..."); }
    @Override public void sleep() { System.out.println("Sleeping..."); }
    @Override public void attendMeeting() { System.out.println("In meeting..."); }
    @Override public void writeCode() { System.out.println("Coding..."); }
    @Override public void reviewCode() { System.out.println("Reviewing..."); }

    @Override
    public void manageTeam() {
        // Developer doesn't manage team!
        throw new UnsupportedOperationException();
    }
}

// Manager
public class Manager implements Worker {
    @Override public void work() { System.out.println("Working..."); }
    @Override public void eat() { System.out.println("Eating..."); }
    @Override public void sleep() { System.out.println("Sleeping..."); }
    @Override public void attendMeeting() { System.out.println("In meeting..."); }
    @Override public void manageTeam() { System.out.println("Managing..."); }

    @Override
    public void writeCode() {
        // Manager doesn't code!
        throw new UnsupportedOperationException();
    }

    @Override
    public void reviewCode() {
        // Manager doesn't review code!
        throw new UnsupportedOperationException();
    }
}

// Robot Worker
public class Robot implements Worker {
    @Override public void work() { System.out.println("Working 24/7..."); }

    @Override public void eat() { throw new UnsupportedOperationException(); }
    @Override public void sleep() { throw new UnsupportedOperationException(); }
    // ... many more unsupported methods
}
```

**Problems**:
- Classes forced to implement methods they don't need
- Empty implementations or exceptions everywhere
- Hard to understand what each class actually does
- Any change to interface affects ALL implementers

---

### ✅ GOOD - Segregated Interfaces

```java
// Small, focused interfaces
public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}

public interface Sleepable {
    void sleep();
}

public interface Codeable {
    void writeCode();
    void reviewCode();
}

public interface Manageable {
    void manageTeam();
    void attendMeeting();
}

// Developer implements only what's needed
public class Developer implements Workable, Eatable, Sleepable, Codeable {
    @Override public void work() { System.out.println("Working..."); }
    @Override public void eat() { System.out.println("Eating..."); }
    @Override public void sleep() { System.out.println("Sleeping..."); }
    @Override public void writeCode() { System.out.println("Coding..."); }
    @Override public void reviewCode() { System.out.println("Reviewing..."); }
}

// Manager implements only what's needed
public class Manager implements Workable, Eatable, Sleepable, Manageable {
    @Override public void work() { System.out.println("Working..."); }
    @Override public void eat() { System.out.println("Eating..."); }
    @Override public void sleep() { System.out.println("Sleeping..."); }
    @Override public void manageTeam() { System.out.println("Managing..."); }
    @Override public void attendMeeting() { System.out.println("Meeting..."); }
}

// Robot - just work, no human needs!
public class Robot implements Workable {
    @Override public void work() { System.out.println("Working 24/7..."); }
}
```

**Benefits**:
- Each class implements only relevant methods
- No empty implementations
- Clear separation of concerns
- Easy to understand each class's capabilities
- Adding new interface doesn't affect unrelated classes

---

## ISP Guidelines

1. **Break fat interfaces** into smaller, specific ones
2. **Group by cohesion** - related methods together
3. **Think from client's perspective** - what do they actually need?
4. **Prefer many small interfaces** over one large interface
5. **Interfaces should represent roles**, not entire entities

---

# D - Dependency Inversion Principle (DIP)

## Definition
> **"High-level modules should not depend on low-level modules. Both should depend on abstractions."**
> **"Abstractions should not depend on details. Details should depend on abstractions."**

Depend on interfaces/abstractions, NOT concrete implementations.

---

## Real-World Analogy: Power Outlets

**Without DIP (Bad)**:
- Each appliance has hardwired electricity
- Change power source? Rewire everything!

**With DIP (Good)**:
- Appliances have plugs (abstraction)
- Wall has outlets (abstraction)
- Power source can change without affecting appliances
- Appliances can change without affecting power source

---

## Code Example

### ❌ BAD - Violates DIP

```java
// Low-level module
public class MySQLDatabase {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

// High-level module depends directly on low-level module
public class UserService {
    private MySQLDatabase database;  // Direct dependency on concrete class!

    public UserService() {
        this.database = new MySQLDatabase();  // Tight coupling!
    }

    public void createUser(String name) {
        database.save(name);
    }
}

// Problem: What if we want to use PostgreSQL or MongoDB?
// Must modify UserService!

// Another low-level module
public class EmailService {
    public void sendEmail(String to, String message) {
        System.out.println("Sending email to " + to);
    }
}

// Another high-level module with direct dependency
public class NotificationService {
    private EmailService emailService;  // Tight coupling!

    public NotificationService() {
        this.emailService = new EmailService();
    }

    public void notifyUser(String userId, String message) {
        emailService.sendEmail(userId, message);
    }
}

// Problem: What if we want to use SMS or Push notifications?
// Must modify NotificationService!
```

**Problems**:
- Tight coupling between high-level and low-level modules
- Changing database requires changing UserService
- Hard to test (can't mock MySQL easily)
- Violates OCP too!

---

### ✅ GOOD - Follows DIP

```java
// Step 1: Create abstraction (interface)
public interface Database {
    void save(String data);
    String read(String id);
}

// Step 2: Low-level modules implement abstraction
public class MySQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }

    @Override
    public String read(String id) {
        return "Data from MySQL";
    }
}

public class PostgreSQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to PostgreSQL: " + data);
    }

    @Override
    public String read(String id) {
        return "Data from PostgreSQL";
    }
}

public class MongoDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MongoDB: " + data);
    }

    @Override
    public String read(String id) {
        return "Data from MongoDB";
    }
}

// Step 3: High-level module depends on abstraction
public class UserService {
    private Database database;  // Depends on interface, not concrete!

    // Dependency Injection via constructor
    public UserService(Database database) {
        this.database = database;
    }

    public void createUser(String name) {
        database.save(name);
    }

    public String getUser(String id) {
        return database.read(id);
    }
}

// Usage - easily swap implementations!
public class Main {
    public static void main(String[] args) {
        // Use MySQL
        Database mysql = new MySQLDatabase();
        UserService userService1 = new UserService(mysql);
        userService1.createUser("John");

        // Use PostgreSQL - NO changes to UserService!
        Database postgres = new PostgreSQLDatabase();
        UserService userService2 = new UserService(postgres);
        userService2.createUser("Jane");

        // Use MongoDB - NO changes to UserService!
        Database mongo = new MongoDatabase();
        UserService userService3 = new UserService(mongo);
        userService3.createUser("Bob");
    }
}
```

---

### Notification Example with DIP

```java
// Abstraction
public interface NotificationSender {
    void send(String to, String message);
}

// Implementations
public class EmailNotification implements NotificationSender {
    @Override
    public void send(String to, String message) {
        System.out.println("Email to " + to + ": " + message);
    }
}

public class SMSNotification implements NotificationSender {
    @Override
    public void send(String to, String message) {
        System.out.println("SMS to " + to + ": " + message);
    }
}

public class PushNotification implements NotificationSender {
    @Override
    public void send(String to, String message) {
        System.out.println("Push to " + to + ": " + message);
    }
}

// High-level service
public class NotificationService {
    private NotificationSender sender;  // Depends on abstraction

    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String userId, String message) {
        sender.send(userId, message);
    }
}

// Multiple senders
public class MultiNotificationService {
    private List<NotificationSender> senders;

    public MultiNotificationService(List<NotificationSender> senders) {
        this.senders = senders;
    }

    public void notifyUser(String userId, String message) {
        for (NotificationSender sender : senders) {
            sender.send(userId, message);
        }
    }
}

// Usage
List<NotificationSender> allSenders = Arrays.asList(
    new EmailNotification(),
    new SMSNotification(),
    new PushNotification()
);
MultiNotificationService service = new MultiNotificationService(allSenders);
service.notifyUser("user123", "Hello!");
// Sends via Email, SMS, and Push - all at once!
```

**Benefits**:
- Easy to swap implementations
- Easy to test (inject mocks)
- High-level modules are stable
- Low-level modules are interchangeable
- Follows OCP naturally

---

## Dependency Injection Types

### 1. Constructor Injection (Recommended)
```java
public class UserService {
    private final Database database;

    public UserService(Database database) {
        this.database = database;
    }
}
```

### 2. Setter Injection
```java
public class UserService {
    private Database database;

    public void setDatabase(Database database) {
        this.database = database;
    }
}
```

### 3. Interface Injection
```java
public interface DatabaseInjector {
    void inject(Database database);
}

public class UserService implements DatabaseInjector {
    private Database database;

    @Override
    public void inject(Database database) {
        this.database = database;
    }
}
```

---

## DIP in Spring Boot

```java
// Spring handles dependency injection automatically!

@Repository
public class MySQLUserRepository implements UserRepository {
    // Implementation
}

@Service
public class UserService {
    private final UserRepository repository;  // Interface!

    @Autowired  // Spring injects MySQLUserRepository automatically
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

---

# SOLID Summary

## All 5 Principles Together

```java
// ✅ Single Responsibility: Each class does ONE thing
public class User { }                    // Data only
public class UserRepository { }          // DB operations only
public class UserValidator { }           // Validation only
public class UserNotifier { }            // Notifications only

// ✅ Open/Closed: Extend via interfaces, not modification
public interface PaymentMethod { void pay(double amount); }
public class CreditCard implements PaymentMethod { }
public class PayPal implements PaymentMethod { }
// Add new payment? Create new class, don't modify existing!

// ✅ Liskov Substitution: Subtypes replaceable
public void processPayment(PaymentMethod method) {
    method.pay(100);  // Works with ANY PaymentMethod implementation
}

// ✅ Interface Segregation: Small, focused interfaces
public interface Readable { String read(); }
public interface Writable { void write(String data); }
public interface Deletable { void delete(); }
// Classes implement only what they need

// ✅ Dependency Inversion: Depend on abstractions
public class OrderService {
    private final PaymentMethod payment;      // Interface!
    private final NotificationSender notifier; // Interface!

    public OrderService(PaymentMethod payment, NotificationSender notifier) {
        this.payment = payment;
        this.notifier = notifier;
    }
}
```

---

## When to Apply SOLID?

| Principle | Apply When |
|-----------|------------|
| **SRP** | Class has multiple reasons to change |
| **OCP** | Adding features requires modifying existing code |
| **LSP** | Subclass can't substitute parent properly |
| **ISP** | Classes implement methods they don't need |
| **DIP** | High-level modules depend on low-level implementations |

---

## Common Violations & Fixes

| Violation | Symptom | Fix |
|-----------|---------|-----|
| SRP | God classes, >500 lines | Split into smaller classes |
| OCP | Long if-else/switch | Use polymorphism + interfaces |
| LSP | Subclass throws exceptions | Rethink inheritance hierarchy |
| ISP | Empty method implementations | Break into smaller interfaces |
| DIP | `new` keyword everywhere | Use dependency injection |

---

## Benefits of Following SOLID

| Benefit | How SOLID Helps |
|---------|-----------------|
| **Maintainable** | Small, focused classes easy to understand |
| **Testable** | Dependencies can be mocked |
| **Flexible** | Easy to swap implementations |
| **Scalable** | Add features without breaking existing code |
| **Reusable** | Small interfaces = more reuse opportunities |
| **Less Bugs** | Changes isolated, less ripple effects |

---

## Remember

> "SOLID principles are not rules, they are guidelines."

- Don't apply blindly
- Balance with pragmatism
- Over-engineering is also a problem
- Context matters - small scripts don't need full SOLID
- Start simple, refactor when needed

**Golden Rule**: If your code is hard to change, hard to test, or hard to understand → Apply SOLID principles!
