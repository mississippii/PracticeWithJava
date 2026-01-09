# 11. Advanced OOP Concepts

## What's Covered?

1. Constructor Chaining
2. Method Overloading vs Overriding
3. Enums
4. Inner Classes
5. Anonymous Classes
6. Object Class Methods
7. Covariant Return Type

---

## 1. Constructor Chaining

### Definition
Calling one constructor from another constructor in the same class or parent class.

### Using `this()` - Same Class

```java
class Person {
    private String name;
    private int age;
    private String city;

    // Constructor 1
    Person() {
        this("Unknown", 0, "Unknown");  // Call constructor 3
    }

    // Constructor 2
    Person(String name, int age) {
        this(name, age, "Unknown");  // Call constructor 3
    }

    // Constructor 3 - Main constructor
    Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    void display() {
        System.out.println("Name: " + name + ", Age: " + age + ", City: " + city);
    }
}

// Usage
Person p1 = new Person();  // Unknown, 0, Unknown
Person p2 = new Person("Alice", 25);  // Alice, 25, Unknown
Person p3 = new Person("Bob", 30, "NYC");  // Bob, 30, NYC
```

### Using `super()` - Parent Class

```java
class Animal {
    String name;

    Animal() {
        System.out.println("Animal constructor");
    }

    Animal(String name) {
        this.name = name;
        System.out.println("Animal constructor with name");
    }
}

class Dog extends Animal {
    String breed;

    Dog() {
        super();  // Call parent's no-arg constructor
        System.out.println("Dog constructor");
    }

    Dog(String name, String breed) {
        super(name);  // Call parent's parameterized constructor
        this.breed = breed;
        System.out.println("Dog constructor with breed");
    }
}

// Usage
Dog d1 = new Dog();
/* Output:
Animal constructor
Dog constructor
*/

Dog d2 = new Dog("Buddy", "Golden Retriever");
/* Output:
Animal constructor with name
Dog constructor with breed
*/
```

**Rules:**
- `this()` or `super()` must be first statement
- Cannot use both in same constructor
- `super()` is called automatically if not specified

---

## 2. Method Overloading vs Overriding

### Comparison

| Feature              | Overloading                | Overriding                  |
|---------------------|----------------------------|-----------------------------|
| **Definition**      | Same name, diff parameters | Same signature in child     |
| **Where**           | Same class                 | Parent-child classes        |
| **Polymorphism**    | Compile-time               | Runtime                     |
| **Parameters**      | Must be different          | Must be same                |
| **Return type**     | Can be different           | Same or covariant           |
| **Access modifier** | Can be any                 | Same or less restrictive    |
| **static**          | Can be static              | Cannot override static      |
| **Binding**         | Early binding              | Late binding                |

### Example

```java
class Calculator {
    // Method Overloading - same name, different parameters
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}

class Animal {
    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    // Method Overriding - same signature, different implementation
    @Override
    void sound() {
        System.out.println("Woof!");
    }
}
```

---

## 3. Enums

### Definition
Special class representing a group of constants (unchangeable variables).

### Basic Enum

```java
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

// Usage
Day today = Day.MONDAY;
System.out.println(today);  // MONDAY

// Switch with enum
switch (today) {
    case MONDAY:
        System.out.println("Start of week");
        break;
    case FRIDAY:
        System.out.println("Almost weekend!");
        break;
    default:
        System.out.println("Regular day");
}
```

### Enum with Fields and Methods

```java
enum Size {
    SMALL(10), MEDIUM(20), LARGE(30), XLARGE(40);

    private int value;

    // Constructor (private by default)
    Size(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

// Usage
Size size = Size.LARGE;
System.out.println(size.getValue());  // 30
```

### Enum Methods

```java
enum Color {
    RED, GREEN, BLUE;

    // Custom method
    public String getDescription() {
        switch (this) {
            case RED: return "Red color";
            case GREEN: return "Green color";
            case BLUE: return "Blue color";
            default: return "Unknown";
        }
    }
}

// Built-in methods
Color.values();     // Array of all values
Color.valueOf("RED");  // Get enum by name
Color.RED.ordinal();   // Position (0, 1, 2...)
Color.RED.name();      // "RED"
```

---

## 4. Inner Classes

### Definition
A class defined inside another class.

### Types

#### a) Member Inner Class

```java
class Outer {
    private int outerValue = 10;

    class Inner {
        void display() {
            System.out.println("Outer value: " + outerValue);  // Can access outer members
        }
    }
}

// Usage
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
inner.display();
```

#### b) Static Nested Class

```java
class Outer {
    static int outerStatic = 10;
    int outerInstance = 20;

    static class Inner {
        void display() {
            System.out.println(outerStatic);     // OK
            // System.out.println(outerInstance); // ERROR
        }
    }
}

// Usage
Outer.Inner inner = new Outer.Inner();  // No outer object needed
```

#### c) Local Inner Class

```java
class Outer {
    void method() {
        class LocalInner {
            void display() {
                System.out.println("Local inner class");
            }
        }

        LocalInner inner = new LocalInner();
        inner.display();
    }
}
```

---

## 5. Anonymous Classes

### Definition
Class without a name, defined and instantiated in one expression.

### Example

```java
interface Greeting {
    void greet();
}

class Main {
    public static void main(String[] args) {
        // Anonymous class implementing interface
        Greeting greeting = new Greeting() {
            @Override
            public void greet() {
                System.out.println("Hello from anonymous class!");
            }
        };

        greeting.greet();
    }
}

// With abstract class
abstract class Animal {
    abstract void sound();
}

Animal dog = new Animal() {
    @Override
    void sound() {
        System.out.println("Woof!");
    }
};
```

---

## 6. Object Class Methods

Every class inherits from `Object` class. Key methods to override:

### toString()

```java
class Person {
    String name;
    int age;

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
```

### equals()

```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Person person = (Person) obj;
    return age == person.age && name.equals(person.name);
}
```

### hashCode()

```java
@Override
public int hashCode() {
    return Objects.hash(name, age);
}
```

---

## 7. Covariant Return Type

### Definition
Overriding method can return a subtype of the return type declared in parent.

```java
class Animal {
    Animal reproduce() {
        return new Animal();
    }
}

class Dog extends Animal {
    @Override
    Dog reproduce() {  // Covariant return type
        return new Dog();
    }
}

// Usage
Dog dog = new Dog();
Dog puppy = dog.reproduce();  // Returns Dog, not Animal
```

---

## Files in This Folder

1. **ConstructorChaining.java** *(existing)* - this() and super()
2. **OverloadingVsOverriding.java** - Comparison example
3. **EnumExample.java** - Enum with fields and methods
4. **InnerClassExample.java** - All types of inner classes
5. **AnonymousClass.java** - Anonymous class examples
6. **ObjectMethods.java** - toString, equals, hashCode
7. **CovariantReturn.java** - Covariant return type

---

## Key Points

✓ Constructor chaining uses `this()` or `super()`
✓ Overloading = compile-time, Overriding = runtime
✓ Enums are special classes for constants
✓ Inner classes have access to outer class members
✓ Anonymous classes are one-time use classes
✓ Override Object methods for custom behavior
✓ Covariant return allows returning subtype

---

## Real-World Analogy

**Constructor Chaining:**
- Assembly line - each step builds on previous step

**Enums:**
- Traffic lights - fixed set of states (RED, YELLOW, GREEN)

**Inner Classes:**
- Engine inside Car - engine is part of car

**Anonymous Classes:**
- Temporary worker - hired for one specific task
