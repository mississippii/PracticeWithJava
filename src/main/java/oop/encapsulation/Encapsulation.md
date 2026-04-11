# Encapsulation — Data Hiding & Controlled Access

## What is it?

Encapsulation means **bundling fields and methods together in one class** and **restricting direct access to the fields** from outside.

The idea: hide your data, expose only what you want, and control it through methods.

```
Outside world
      │
      │  can NOT touch fields directly
      │
  ┌───▼──────────────────────┐
  │  Class                   │
  │  ─────────────────────   │
  │  private fields          │  ← hidden
  │                          │
  │  public getters/setters  │  ← controlled door
  └──────────────────────────┘
```

---

## Why does it matter?

### Without encapsulation — no control

```java
class Student {
    public double cgpa;  // anyone can touch this
}

student.cgpa = 150.0;  // invalid, but nothing stops it
student.cgpa = -5.0;   // same problem
```

### With encapsulation — validation is enforced

```java
class Student {
    private double cgpa;  // hidden

    public void setCgpa(double cgpa) {
        if (cgpa >= 0.0 && cgpa <= 10.0) {
            this.cgpa = cgpa;
        } else {
            throw new IllegalArgumentException("CGPA must be between 0 and 10");
        }
    }
}

student.setCgpa(150.0);  // throws exception — bad data rejected
```

---

## How to achieve it — 3 steps

**Step 1: Make all fields `private`**
```java
private String name;
private int age;
private double cgpa;
```

**Step 2: Add public getters to allow reading**
```java
public String getName() { return name; }
public int getAge()     { return age; }
public double getCgpa() { return cgpa; }
```

**Step 3: Add public setters with validation to allow writing**
```java
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
```

---

## Access patterns

Not every field needs both a getter and setter. Design it intentionally.

| Pattern | How | When |
|---|---|---|
| Read + Write | getter + setter | mutable data (name, age) |
| Read-only | getter only, no setter | ID, account number — set once in constructor |
| Write-only | setter only, no getter | passwords, secrets |
| No access | neither | purely internal, like a cache |

```java
// Read-only: rollNumber set in constructor, never changed
public String getRollNumber() { return rollNumber; }
// No setRollNumber() — intentional

// Read + Write with validation
public void setCgpa(double cgpa) { ... }
public double getCgpa()          { return cgpa; }
```

---

## Business methods — not just getters/setters

Encapsulation is not just about hiding fields. It also means **putting logic that belongs to the data inside the class**.

```java
// Instead of letting the caller decide:
if (student.getCgpa() >= 6.0) { ... }  // scattered across the codebase

// Put the rule inside the class:
public boolean isEligibleForPlacement() {
    return cgpa >= 6.0;
}

// Caller just asks:
if (student.isEligibleForPlacement()) { ... }
```

If the rule changes (e.g., threshold moves to 7.0), you fix it in one place — inside the class.

---

## Real-world example: TicTacToe

The `TicTacToe` class in this folder is a good encapsulation example. The internal game state — the board, player names, whose turn it is, who won — is all **private**. The outside world interacts through one public method:

```java
TicTacToe game = new TicTacToe();
game.startGame();  // only this is exposed
```

The caller cannot:
- Directly read the board
- Change whose turn it is
- Set a winner manually

Everything goes through the class's own logic. That is encapsulation.

---

## `toString()` and encapsulation

Fields are private, but you still want to be able to print an object for debugging. Override `toString()` — it's the controlled, read-only window into your object's state.

```java
@Override
public String toString() {
    return "Student{roll='" + rollNumber + "', name='" + name
            + "', age=" + age + ", cgpa=" + cgpa + "}";
}

// Usage
System.out.println(student);  // Student{roll='CS101', name='Alice', age=20, cgpa=8.5}
```

---

## Common Mistakes

### Public fields
```java
public double balance;   // anyone sets any value — no encapsulation
public int age;          // same problem
```

---

### Setter with no validation
```java
public void setAge(int age) {
    this.age = age;  // accepts -999, 9999, anything
}
```
If you're not validating, you might as well make the field public.

---

### Returning a mutable object directly

```java
// Bad — caller gets the real list and can modify it
public List<String> getCourses() {
    return courses;
}

// Good — return a defensive copy
public List<String> getCourses() {
    return new ArrayList<>(courses);
}
```

---

### The `static` field trap

```java
// Bug: static means shared across ALL instances
private static char[][] gameBoard = new char[3][3];

TicTacToe game1 = new TicTacToe();
TicTacToe game2 = new TicTacToe();
// game1 and game2 share the same board — moves in one affect the other!

// Fix: instance field, each object gets its own copy
private final char[][] gameBoard = new char[3][3];
```

This was a real bug in the TicTacToe code. Constants (`EMPTY_BOX`, `PLAYER_ONE_SYMBOL`) are fine as `static final` — they never change. Mutable state like the board must be an instance field.

---

## Encapsulation vs Abstraction

These are related but different:

| | Encapsulation | Abstraction |
|---|---|---|
| **Focus** | Hiding *data* | Hiding *implementation* |
| **Tool** | `private` fields + getters/setters | Abstract classes, interfaces |
| **Question** | Who can access the data? | What does the caller need to know? |
| **Level** | Class level | Design level |

Encapsulation: `balance` is private — you can only touch it through `deposit()` and `withdraw()`.  
Abstraction: the caller doesn't know *how* `deposit()` works internally, only that it exists.

---

## Key Takeaways

1. **Private fields** — hide your data
2. **Public methods** — controlled, validated access
3. **Setters validate** — reject bad data at the boundary
4. **Design access intentionally** — not everything needs a getter *and* setter
5. **Business logic belongs inside** — don't scatter rules across the codebase
6. **Instance vs static** — mutable state is always instance, constants can be static
