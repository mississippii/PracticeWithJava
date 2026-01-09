# 08. Static - Class Level Members

## What is Static?
Members (variables, methods, blocks, nested classes) that belong to the CLASS rather than to instances (objects).

**Shared by ALL objects of the class.**

---

## Why Static?

1. **Memory Efficiency** - One copy shared by all objects
2. **Utility Methods** - Methods that don't need object state
3. **Constants** - Shared constant values
4. **Counters** - Track count across all objects

---

## Types of Static

1. Static Variables
2. Static Methods
3. Static Blocks
4. Static Nested Classes

---

## 1. Static Variables (Class Variables)

### Definition
Variables shared by all objects. Only one copy exists.

```java
class Counter {
    static int count = 0;  // Shared by all objects
    String name;           // Separate for each object

    Counter(String name) {
        this.name = name;
        count++;  // Increment shared counter
    }

    void display() {
        System.out.println(name + ": Count = " + count);
    }
}

// Usage
Counter c1 = new Counter("First");
Counter c2 = new Counter("Second");
Counter c3 = new Counter("Third");

c1.display();  // First: Count = 3
c2.display();  // Second: Count = 3
c3.display();  // Third: Count = 3

System.out.println(Counter.count);  // 3 (access using class name)
```

### Properties
- Shared by all objects
- Memory allocated once (class loading time)
- Can be accessed using class name
- Can be accessed using object reference (not recommended)

---

## 2. Static Methods (Class Methods)

### Definition
Methods that belong to class, not objects. Can be called without creating object.

```java
class MathUtils {
    // Static method - no object needed
    static int add(int a, int b) {
        return a + b;
    }

    static int multiply(int a, int b) {
        return a * b;
    }
}

// Usage - No object creation needed
int sum = MathUtils.add(5, 10);
int product = MathUtils.multiply(5, 10);
```

### Rules for Static Methods
1. **Can access:** Static variables and static methods only
2. **Cannot access:** Instance variables and instance methods directly
3. **Cannot use:** `this` or `super` keywords
4. Can be overloaded but **cannot be overridden** (it's hiding, not overriding)

```java
class Example {
    static int staticVar = 10;
    int instanceVar = 20;

    static void staticMethod() {
        System.out.println(staticVar);     // OK
        // System.out.println(instanceVar); // ERROR: Cannot access instance member
        // this.instanceVar = 30;           // ERROR: Cannot use 'this'
    }

    void instanceMethod() {
        System.out.println(staticVar);     // OK
        System.out.println(instanceVar);   // OK
        staticMethod();                    // OK
    }
}
```

---

## 3. Static Blocks

### Definition
Block of code that runs once when class is loaded into memory. Used for static initialization.

```java
class Database {
    static String connectionURL;

    // Static block - runs once when class is loaded
    static {
        System.out.println("Loading Database class...");
        connectionURL = "jdbc:mysql://localhost:3306/mydb";
        // Complex initialization logic
    }

    static void connect() {
        System.out.println("Connecting to: " + connectionURL);
    }
}

// First time Database is used, static block executes
Database.connect();

/* Output:
Loading Database class...
Connecting to: jdbc:mysql://localhost:3306/mydb
*/
```

### Properties
- Executed when class is loaded (before main method)
- Executed only once
- Can have multiple static blocks (executed in order)
- Cannot access instance members
- Cannot throw checked exceptions

---

## 4. Static Nested Class

### Definition
A static class defined inside another class.

```java
class Outer {
    static int outerStatic = 10;
    int outerInstance = 20;

    static class Inner {
        void display() {
            System.out.println(outerStatic);     // OK: static member
            // System.out.println(outerInstance); // ERROR: instance member
        }
    }
}

// Usage
Outer.Inner obj = new Outer.Inner();  // No outer object needed
obj.display();
```

---

## Files in This Folder

1. **StaticVariable.java** - Static variable examples
2. **StaticMethod.java** - Static method examples
3. **StaticBlock.java** - Static block initialization
4. **StaticNested.java** - Static nested classes
5. **StaticCounter.java** - Counter example
6. **StaticUtility.java** - Utility class pattern

---

## Complete Example

```java
class Student {
    // Static variable - shared by all students
    static String school = "ABC High School";
    static int totalStudents = 0;

    // Instance variables - separate for each student
    String name;
    int rollNo;

    // Static block - runs once when class is loaded
    static {
        System.out.println("Student class loaded");
        System.out.println("School: " + school);
    }

    // Constructor
    Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
        totalStudents++;  // Increment static counter
    }

    // Instance method - can access both static and instance members
    void display() {
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNo);
        System.out.println("School: " + school);
    }

    // Static method - can only access static members
    static void displaySchool() {
        System.out.println("School: " + school);
        System.out.println("Total Students: " + totalStudents);
        // System.out.println(name);  // ERROR: Cannot access instance variable
    }

    // Static method
    static void changeSchool(String newSchool) {
        school = newSchool;  // Changes for all students
    }
}

// Usage
Student.displaySchool();  // Can call without object

Student s1 = new Student("Alice", 101);
Student s2 = new Student("Bob", 102);

s1.display();
s2.display();

Student.displaySchool();  // Total Students: 2

Student.changeSchool("XYZ High School");
s1.display();  // Now shows XYZ High School
s2.display();  // Now shows XYZ High School
```

---

## Static vs Instance

| Feature              | Static                  | Instance                |
|---------------------|-------------------------|-------------------------|
| **Belongs to**      | Class                   | Object                  |
| **Memory**          | One copy                | Copy per object         |
| **Access**          | Class name              | Object reference        |
| **Can access**      | Only static members     | Both static & instance  |
| **this/super**      | ❌ Not allowed          | ✅ Allowed              |
| **When loaded**     | Class loading time      | Object creation time    |

---

## Common Use Cases

### 1. Constants
```java
class Constants {
    public static final double PI = 3.14159;
    public static final int MAX_USERS = 100;
}
```

### 2. Utility Classes
```java
class MathUtils {
    static int max(int a, int b) {
        return a > b ? a : b;
    }

    static double sqrt(double n) {
        return Math.sqrt(n);
    }
}
```

### 3. Singleton Pattern
```java
class Singleton {
    private static Singleton instance;

    private Singleton() { }  // Private constructor

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

### 4. Counters
```java
class IDGenerator {
    private static int counter = 0;

    static int generateID() {
        return ++counter;
    }
}
```

---

## main() Method

```java
public static void main(String[] args) {
    // Why static?
    // JVM calls main without creating object
}
```

**Why public?** - JVM needs to access it from outside
**Why static?** - JVM calls it without creating object
**Why void?** - Doesn't return anything to JVM
**Why main?** - Convention, JVM looks for "main"

---

## Key Points

✓ Static members belong to class, not objects
✓ Static variables are shared by all objects
✓ Static methods can only access static members
✓ Static blocks run once when class is loaded
✓ Access static members using class name
✓ Cannot use `this` or `super` in static context
✓ Use static for utility methods and constants
✓ One copy in memory for all objects

---

## Real-World Analogy

**School:**
- **Static:** School name (same for all students)
- **Instance:** Student name (different for each student)
- All students share the school name
- Each student has their own name

This is static vs instance!
