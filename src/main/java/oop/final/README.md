# 09. Final - Constants & Restrictions

## What is Final?
A keyword that creates restrictions. Once assigned, cannot be changed or overridden.

**Creates immutability and prevents modifications.**

---

## Types of Final

1. **Final Variable** - Constant
2. **Final Method** - Cannot override
3. **Final Class** - Cannot inherit
4. **Final Parameter** - Cannot modify in method

---

## 1. Final Variable (Constant)

### Definition
Variable whose value cannot be changed once assigned.

```java
final int MAX_VALUE = 100;
// MAX_VALUE = 200;  // ERROR: Cannot assign to final variable
```

### Types

#### a) Final Instance Variable
```java
class Person {
    final String name;  // Must be initialized

    Person(String name) {
        this.name = name;  // Initialization in constructor
    }

    void changeName(String newName) {
        // this.name = newName;  // ERROR: Cannot change final variable
    }
}
```

#### b) Final Static Variable (Constant)
```java
class Constants {
    public static final double PI = 3.14159;
    public static final int MAX_USERS = 100;
}

// Usage
System.out.println(Constants.PI);
```

#### c) Final Local Variable
```java
void method() {
    final int x = 10;
    // x = 20;  // ERROR: Cannot change
}
```

### Blank Final Variable
Final variable declared but not initialized. Must be initialized in constructor.

```java
class Example {
    final int value;  // Blank final

    Example() {
        value = 10;  // Must initialize in constructor
    }

    Example(int value) {
        this.value = value;  // Initialized with parameter
    }
}
```

---

## 2. Final Method

### Definition
Method that cannot be overridden in child classes.

```java
class Parent {
    // Final method - cannot override
    final void display() {
        System.out.println("Parent display");
    }

    void show() {
        System.out.println("Parent show");
    }
}

class Child extends Parent {
    // @Override
    // void display() { }  // ERROR: Cannot override final method

    @Override
    void show() {  // OK: Not final
        System.out.println("Child show");
    }
}
```

### Why Use Final Methods?
1. **Security** - Prevent alteration of critical behavior
2. **Performance** - Can be inlined by compiler
3. **Design** - Enforce behavior contract

---

## 3. Final Class

### Definition
Class that cannot be inherited (extended).

```java
final class Utility {
    static int add(int a, int b) {
        return a + b;
    }
}

// class SubUtility extends Utility { }  // ERROR: Cannot inherit final class
```

### Examples in Java
- `String` class
- `Math` class
- `System` class
- All wrapper classes (`Integer`, `Double`, etc.)

### Why Use Final Classes?
1. **Security** - Prevent subclassing
2. **Immutability** - Ensure objects cannot be modified via inheritance
3. **Design** - Complete implementation, no extension needed

---

## 4. Final Parameters

### Definition
Method parameters that cannot be modified inside method.

```java
void process(final int value) {
    System.out.println(value);
    // value = 20;  // ERROR: Cannot modify final parameter
}

void calculate(final int a, final int b) {
    // a = a + 1;  // ERROR
    int result = a + b;  // OK: Just using
}
```

---

## Files in This Folder

1. **FinalVariable.java** - Final variables examples
2. **FinalMethod.java** - Final methods cannot be overridden
3. **FinalClass.java** - Final classes cannot be inherited
4. **FinalParameter.java** - Final parameters
5. **BlankFinal.java** - Blank final variables
6. **ImmutableClass.java** - Creating immutable class

---

## Complete Example

```java
// Final class - cannot inherit
final class ImmutablePerson {
    // Final instance variables - cannot change
    private final String name;
    private final int age;

    // Constructor - initialize final variables
    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters only - no setters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Final method - cannot override (though class is already final)
    public final void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

// class ExtendedPerson extends ImmutablePerson { }  // ERROR: Cannot inherit

// Usage
ImmutablePerson person = new ImmutablePerson("Alice", 25);
System.out.println(person.getName());  // Alice
// person.name = "Bob";  // ERROR: final field
person.display();
```

---

## Final with Reference Types

### Important: Final Reference vs Final Object

```java
class Address {
    String city;

    Address(String city) {
        this.city = city;
    }
}

class Person {
    final Address address;  // Final reference

    Person(String city) {
        address = new Address(city);
    }

    void test() {
        // address = new Address("NYC");  // ERROR: Cannot reassign reference

        address.city = "Boston";  // OK: Can modify object content
        // Reference is final, but object content is not
    }
}
```

**Key Point:** `final` makes the reference constant, not the object!

To make object immutable too, make its fields final:

```java
class ImmutableAddress {
    final String city;  // Final field

    ImmutableAddress(String city) {
        this.city = city;
    }
}
```

---

## Creating Immutable Class

To create fully immutable class:

1. Declare class as `final`
2. Make all fields `private final`
3. Initialize fields in constructor
4. Provide only getters (no setters)
5. Don't provide methods that modify state

```java
final class Employee {
    private final String name;
    private final int id;
    private final double salary;

    public Employee(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public double getSalary() { return salary; }

    // No setters - immutable
}
```

---

## Final vs Static Final

```java
class Example {
    final int INSTANCE_CONSTANT = 100;        // Different for each object
    static final int CLASS_CONSTANT = 200;    // Shared by all objects

    Example() {
        // INSTANCE_CONSTANT gets value when object is created
    }
}

// Naming convention
// final: normalCase or camelCase
// static final: UPPER_SNAKE_CASE
```

---

## Initialization of Final Variables

### 1. At Declaration
```java
final int x = 10;
```

### 2. In Constructor
```java
final int x;
Example() {
    x = 10;
}
```

### 3. In Instance Initializer Block
```java
final int x;
{
    x = 10;
}
```

### 4. For Static Final - In Static Block
```java
static final int x;
static {
    x = 10;
}
```

---

## Rules Summary

### Final Variable
✓ Cannot change value once assigned
✓ Must be initialized (declaration, constructor, or initializer block)
✓ Naming convention: UPPER_SNAKE_CASE (for static final)

### Final Method
✓ Cannot be overridden
✓ Can be overloaded
✓ Inherited by child class (but cannot override)

### Final Class
✓ Cannot be extended
✓ All methods are implicitly final
✓ Can extend other classes

---

## Benefits of Final

1. **Immutability** - Thread-safe, predictable
2. **Security** - Prevent modification of critical code
3. **Performance** - Compiler optimizations
4. **Design** - Clear intent, better documentation
5. **Safety** - Prevent accidental modification

---

## Key Points

✓ `final` prevents modification
✓ Final variable = constant
✓ Final method = cannot override
✓ Final class = cannot inherit
✓ Final reference ≠ final object
✓ Must initialize final variables
✓ Use for constants, security, and immutability

---

## Real-World Analogy

**Birth Certificate:**
- **Name** - Written once, cannot change (final variable)
- **Format** - Standardized, cannot modify (final method)
- **Document type** - Birth certificate only, cannot become something else (final class)

Once created, it's permanent!
