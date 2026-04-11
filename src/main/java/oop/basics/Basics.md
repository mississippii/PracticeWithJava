# Basics — Classes, Objects & Constructors

## What is a Class?

A class is a **blueprint** that defines what an object will look like and what it can do.

- **Fields** — what the object *has* (data)
- **Methods** — what the object *does* (behavior)
- **Constructor** — how the object is *created* (initialization)

```java
class Student {
    // Fields — what a student has
    private String name;
    private int age;

    // Constructor — how to create a student
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Method — what a student can do
    public void introduce() {
        System.out.println("Hi, I'm " + name);
    }
}
```

> Notice fields are `private` — that is intentional. See the Encapsulation guide for why.

---

## What is an Object?

An object is a **concrete instance** of a class — the actual thing created from the blueprint.

```java
Student s1 = new Student("Alice", 20);
Student s2 = new Student("Bob", 22);

s1.introduce();  // Hi, I'm Alice
s2.introduce();  // Hi, I'm Bob
```

Each object has its own copy of the fields. `s1` and `s2` are completely independent.

---

## Memory: Where do objects live?

```
Stack                    Heap
─────────────────        ─────────────────────────────
s1  →  ref ──────────→  [ Student: name="Alice", age=20 ]
s2  →  ref ──────────→  [ Student: name="Bob",   age=22 ]
```

- **Stack** — stores variable names and references (addresses)
- **Heap** — stores the actual object data
- `new` creates the object on the heap and returns its address

---

## Constructors

A constructor runs automatically when you use `new`. It puts the object into a valid initial state.

**Rules:**
- Same name as the class
- No return type (not even `void`)
- Can be overloaded (multiple constructors with different parameters)

### Types

**1. Default Constructor** — no parameters, sets safe defaults
```java
public Student() {
    this.name = "Unknown";
    this.age = 0;
}
```

**2. Parameterized Constructor** — caller provides values
```java
public Student(String name, int age) {
    this.name = name;
    this.age = age;
}
```

**3. Copy Constructor** — creates a new object from an existing one
```java
public Student(Student other) {
    this.name = other.name;
    this.age = other.age;
}
```

---

## Constructor Overloading

Same class, multiple constructors with **different parameter lists**.

```java
Student s1 = new Student();                          // default
Student s2 = new Student("Alice", 20);               // 2-param
Student s3 = new Student("Bob", 22, "Male");         // 3-param
Student s4 = new Student("Carol", 21, "Female", "CSE"); // 4-param
Student s5 = new Student(s4);                        // copy
```

Java picks the right constructor based on the arguments you pass.

---

## Constructor Chaining — `this(...)`

Instead of repeating field assignments, one constructor can **call another** using `this(...)`.

```java
public Student(String name, int age) {
    this.name = name;
    this.age = age;
}

public Student(String name, int age, String gender) {
    this(name, age);        // reuses the 2-param constructor
    this.gender = gender;   // then adds gender
}

public Student(String name, int age, String gender, String department) {
    this(name, age, gender);      // reuses the 3-param constructor
    this.department = department; // then adds department
}
```

**Rule:** `this(...)` must be the **first statement** in the constructor.

---

## The `this` Keyword

`this` is a reference to the **current object** — the one the method or constructor is running on.

### Use 1 — Resolve naming conflict between field and parameter
```java
public Student(String name, int age) {
    this.name = name;  // this.name = field, name = parameter
    this.age = age;
}
```

Without `this`, the parameter shadows the field and the assignment does nothing useful.

### Use 2 — Constructor chaining
```java
public Student() {
    this("Unknown", 0);  // calls Student(String, int)
}
```

### Use 3 — Method chaining (return current object)
```java
public Student setName(String name) {
    this.name = name;
    return this;  // allows: student.setName("Alice").setAge(20)
}
```

---

## `toString()` — Print objects meaningfully

By default, `System.out.println(student)` prints something like `Student@6d06d69c` — a memory address, useless for debugging.

Override `toString()` to fix this:

```java
@Override
public String toString() {
    return "Student{name='" + name + "', age=" + age + "}";
}
```

Now:
```java
Student s = new Student("Alice", 20);
System.out.println(s);  // Student{name='Alice', age=20}
```

**Always override `toString()`.** It costs 5 seconds and saves hours of debugging.

---

## Common Mistakes

### Using public fields
```java
// Bad — anyone can set any value, no control
class Student {
    public String name;
    public int age;
}

student.age = -999;  // nothing stops this
```

Use `private` fields + getters/setters (covered in Encapsulation).

---

### Forgetting `this` when names clash
```java
public Student(String name) {
    name = name;  // assigns the parameter to itself — field stays null!
}
```

---

### Declaring without initializing
```java
Student student;           // just a reference, points to nothing
student.introduce();       // NullPointerException!

// Correct:
Student student = new Student("Alice", 20);
```

---

### Not overriding `toString()`
```java
System.out.println(student);  // Student@6d06d69c — tells you nothing
```

---

## Key Takeaways

| Concept | What it is |
|---|---|
| Class | Blueprint — defines structure and behavior |
| Object | Instance — actual data living on the heap |
| Constructor | Initializer — runs on `new`, sets up the object |
| `this` | Reference to the current object |
| `toString()` | Controls how an object prints |

---

## What's Next

- **Encapsulation** — control who can read/write your fields
- **Inheritance** — share behavior between related classes
- **Polymorphism** — one interface, many behaviors
- **Abstraction** — hide complexity behind a clean contract
