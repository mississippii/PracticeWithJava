# 04. Polymorphism - Many Forms

## What is Polymorphism?
The ability of an object to take many forms. Same method/operation behaves differently in different contexts.

**"Poly" = Many, "Morph" = Forms**

---

## Types of Polymorphism

### 1. Compile-Time Polymorphism (Static)
- **Method Overloading**
- Decided at compile time
- Same method name, different parameters

### 2. Runtime Polymorphism (Dynamic)
- **Method Overriding**
- Decided at runtime
- Same method signature in parent and child

---

## 1. Compile-Time Polymorphism (Method Overloading)

### Definition
Multiple methods with same name but different parameters in the same class.

### Ways to Overload
1. Different number of parameters
2. Different types of parameters
3. Different order of parameters

### Example

```java
class Calculator {
    // Same method name, different parameters

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    String add(String a, String b) {
        return a + b;
    }
}

// Usage
Calculator calc = new Calculator();
System.out.println(calc.add(5, 10));           // 15 (int)
System.out.println(calc.add(5.5, 10.5));       // 16.0 (double)
System.out.println(calc.add(1, 2, 3));         // 6 (int)
System.out.println(calc.add("Hello", "World"));  // HelloWorld (String)
```

### Rules
- Parameters must be different (number, type, or order)
- Return type alone is NOT enough
  ```java
  // Not valid overloading
  int method(int a) { }
  double method(int a) { }  // ERROR: Same signature
  ```
- Can change access modifier
- Can change return type (if parameters differ)

---

## 2. Runtime Polymorphism (Method Overriding)

### Definition
Child class provides specific implementation of parent's method.

### Example

```java
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }

    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks: Woof!");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat meows: Meow!");
    }
}

// Usage - Dynamic Method Dispatch
Animal animal1 = new Dog();  // Upcasting
Animal animal2 = new Cat();  // Upcasting

animal1.sound();  // Dog barks: Woof! (decided at runtime)
animal2.sound();  // Cat meows: Meow! (decided at runtime)
```

### Dynamic Method Dispatch
JVM decides which method to call at runtime based on actual object type.

```java
Animal animal = new Dog();
animal.sound();  // Calls Dog's sound() - Runtime decision
```

---

## Upcasting vs Downcasting

### Upcasting (Implicit)
Child object → Parent reference

```java
Dog dog = new Dog();
Animal animal = dog;  // Upcasting (automatic)
// or
Animal animal = new Dog();  // Direct upcasting
```

**Features:**
- Automatic/Implicit
- Safe
- Can only access parent members

### Downcasting (Explicit)
Parent reference → Child object

```java
Animal animal = new Dog();  // Upcasting first
Dog dog = (Dog) animal;     // Downcasting (manual)
dog.bark();                 // Now can access Dog methods
```

**Features:**
- Manual/Explicit
- Risky (can cause ClassCastException)
- Use `instanceof` to check

---

## `instanceof` Operator

Check if object is instance of a class.

```java
Animal animal = new Dog();

if (animal instanceof Dog) {
    Dog dog = (Dog) animal;  // Safe downcasting
    dog.bark();
} else if (animal instanceof Cat) {
    Cat cat = (Cat) animal;
    cat.meow();
}
```

### Pattern Matching (Java 16+)

```java
if (animal instanceof Dog dog) {
    dog.bark();  // Automatically cast
}
```

---

## Files in This Folder

1. **MethodOverloading.java** - Compile-time polymorphism
2. **MethodOverriding.java** - Runtime polymorphism
3. **DynamicMethodDispatch.java** - Runtime method selection
4. **UpcastingDowncasting.java** - Type casting examples
5. **InstanceofExample.java** - instanceof operator
6. **Cup.java, Liquid.java** *(existing)* - Polymorphic collection
7. **Coffee.java, Milk.java** *(existing)* - Concrete implementations

---

## Complete Example

```java
// Parent class
class Shape {
    void draw() {
        System.out.println("Drawing shape");
    }

    double area() {
        return 0;
    }
}

// Child classes
class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    void draw() {
        System.out.println("Drawing circle");
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    void draw() {
        System.out.println("Drawing rectangle");
    }

    @Override
    double area() {
        return length * width;
    }
}

// Usage - Polymorphic behavior
Shape[] shapes = new Shape[3];
shapes[0] = new Circle(5);        // Upcasting
shapes[1] = new Rectangle(4, 6);  // Upcasting
shapes[2] = new Shape();

for (Shape shape : shapes) {
    shape.draw();  // Different behavior at runtime
    System.out.println("Area: " + shape.area());
}

/* Output:
Drawing circle
Area: 78.53981633974483
Drawing rectangle
Area: 24.0
Drawing shape
Area: 0.0
*/
```

---

## Benefits of Polymorphism

1. **Flexibility** - Write code that works with parent type
2. **Extensibility** - Add new subclasses without changing existing code
3. **Code Reusability** - Same interface, different implementations
4. **Loose Coupling** - Depend on abstractions, not implementations

---

## Polymorphism vs Overloading vs Overriding

| Feature              | Overloading                | Overriding                  |
|---------------------|----------------------------|-----------------------------|
| **Type**            | Compile-time polymorphism  | Runtime polymorphism        |
| **Where**           | Same class                 | Parent-child classes        |
| **Parameters**      | Must be different          | Must be same                |
| **Return type**     | Can be different           | Same or covariant           |
| **Access modifier** | Can be different           | Same or less restrictive    |
| **Keyword**         | None                       | `@Override` (annotation)    |

---

## Key Points

✓ Polymorphism = One interface, many implementations
✓ Overloading = Same name, different parameters (compile-time)
✓ Overriding = Same signature, different implementation (runtime)
✓ Use parent reference to refer child objects (upcasting)
✓ Use `instanceof` before downcasting
✓ Polymorphism enables writing flexible, extensible code

---

## Real-World Analogy

**Remote Control:**
- Same button (interface) on different remotes
- "Power" button turns on TV, AC, or Fan
- Button behavior depends on what device it controls
- Same action, different results

This is polymorphism!
