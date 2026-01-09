# 01. Basics - Classes & Objects

## What You'll Learn
- What is a Class?
- What is an Object?
- Constructors (Default, Parameterized, Copy)
- `this` keyword
- Object class methods

---

## Concepts

### 1. Class
A blueprint for creating objects. Defines structure (fields) and behavior (methods).

### 2. Object
An instance of a class with specific values.

### 3. Constructor
Special method called when object is created. Same name as class, no return type.

**Types:**
- **Default:** No parameters
- **Parameterized:** Takes parameters
- **Copy:** Creates object from another object

### 4. `this` Keyword
Reference to current object. Used to:
- Differentiate between instance and local variables
- Call other constructors
- Pass current object as parameter

### 5. Object Class Methods
Every class inherits from Object class:
- `toString()` - String representation
- `equals()` - Compare objects
- `hashCode()` - Hash value

---

## Files in This Folder

1. **ClassAndObject.java** - Basic class and object creation
2. **ConstructorTypes.java** - Default, parameterized, copy constructors
3. **ThisKeyword.java** - Usage of `this` keyword
4. **ObjectClassMethods.java** - Override toString, equals, hashCode

---

## Quick Example

```java
class Person {
    // Fields
    private String name;
    private int age;

    // Default constructor
    public Person() {
        this("Unknown", 0);
    }

    // Parameterized constructor
    public Person(String name, int age) {
        this.name = name;  // 'this' differentiates
        this.age = age;
    }

    // Copy constructor
    public Person(Person other) {
        this.name = other.name;
        this.age = other.age;
    }

    // toString method
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

// Usage
Person p1 = new Person("Alice", 25);
Person p2 = new Person(p1);  // Copy
System.out.println(p1);  // toString() called
System.out.println(p1.equals(p2));  // true
```

---

## Key Points

✓ Class is a template, Object is an instance
✓ Constructor initializes object
✓ `this` refers to current object
✓ Always override toString, equals, hashCode for custom classes
✓ Constructor name must match class name
✓ Constructor has no return type (not even void)
