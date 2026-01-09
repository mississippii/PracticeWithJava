# 10. Relationships - Object Connections

## What are Relationships?
How classes and objects are connected or interact with each other.

**"HAS-A" relationship (not "IS-A" like inheritance)**

---

## Types of Relationships

1. **Association** - General relationship ("uses-a")
2. **Aggregation** - Weak "has-a" (parts can exist independently)
3. **Composition** - Strong "has-a" (parts cannot exist independently)
4. **Dependency** - One class depends on another

---

## 1. Association

### Definition
General relationship between two classes. Objects have independent lifecycles.

**"Uses-a" or "Has-a" relationship**

### Types
- **Unidirectional:** One class knows about other
- **Bidirectional:** Both classes know about each other

### Example

```java
// Unidirectional Association
class Teacher {
    private String name;

    void teach(Student student) {
        System.out.println(name + " is teaching " + student.getName());
    }
}

class Student {
    private String name;

    public String getName() {
        return name;
    }
}

// Teacher knows Student, but Student doesn't know Teacher
```

```java
// Bidirectional Association
class Doctor {
    private String name;
    private List<Patient> patients;

    void addPatient(Patient patient) {
        patients.add(patient);
        patient.setDoctor(this);  // Set bidirectional link
    }
}

class Patient {
    private String name;
    private Doctor doctor;

    void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}

// Both classes know about each other
```

**Properties:**
- Independent lifecycles
- Can exist separately
- Loose coupling

---

## 2. Aggregation

### Definition
Special form of Association. "Has-a" relationship where parts can exist independently.

**Weak ownership - Parts can survive without the whole**

### Example

```java
class Department {
    private String name;
    private List<Professor> professors;  // Has professors

    Department(String name) {
        this.name = name;
        professors = new ArrayList<>();
    }

    void addProfessor(Professor professor) {
        professors.add(professor);
    }

    void removeProfessor(Professor professor) {
        professors.remove(professor);
    }
}

class Professor {
    private String name;
    private String subject;

    Professor(String name, String subject) {
        this.name = name;
        this.subject = subject;
    }
}

// Usage
Professor prof1 = new Professor("Dr. Smith", "Math");
Professor prof2 = new Professor("Dr. Jones", "Physics");

Department mathDept = new Department("Mathematics");
mathDept.addProfessor(prof1);
mathDept.addProfessor(prof2);

// Even if department is destroyed, professors still exist
mathDept = null;  // Department destroyed
// prof1 and prof2 still exist
```

**Properties:**
- "Has-a" relationship
- Parts can exist independently
- Parts created outside
- Weak ownership

**Real-world examples:**
- Department HAS Professors (professors can exist without department)
- Car HAS Wheels (wheels can exist without car)
- Team HAS Players (players can exist without team)

---

## 3. Composition

### Definition
Strong "has-a" relationship where parts CANNOT exist independently.

**Strong ownership - Parts cannot survive without the whole**

### Example

```java
class House {
    private String address;
    private Room bedroom;  // Part of house
    private Room kitchen;  // Part of house

    House(String address) {
        this.address = address;
        // Rooms created WITH house
        bedroom = new Room("Bedroom", 200);
        kitchen = new Room("Kitchen", 150);
    }

    void displayRooms() {
        System.out.println("House at " + address);
        bedroom.display();
        kitchen.display();
    }
}

class Room {
    private String name;
    private int area;

    Room(String name, int area) {
        this.name = name;
        this.area = area;
    }

    void display() {
        System.out.println(name + ": " + area + " sq ft");
    }
}

// Usage
House house = new House("123 Main St");
house.displayRooms();

// When house is destroyed, rooms are also destroyed
house = null;  // Rooms also destroyed (garbage collected)
```

**Properties:**
- Strong "has-a" relationship
- Parts cannot exist independently
- Parts created inside
- Strong ownership
- Part lifecycle tied to whole

**Real-world examples:**
- House HAS Rooms (rooms cannot exist without house)
- Car HAS Engine (engine is part of car)
- Human HAS Heart (heart cannot exist without human)

---

## 4. Dependency

### Definition
One class depends on another class. Weakest relationship.

```java
class EmailService {
    void sendEmail(String to, String message) {
        System.out.println("Sending email to " + to);
    }
}

class User {
    void register(EmailService emailService) {
        // User depends on EmailService
        emailService.sendEmail("user@example.com", "Welcome!");
    }
}
```

---

## Files in This Folder

1. **Association.java** - Association example
2. **Aggregation.java** - Department-Professor example
3. **Composition.java** - House-Room example
4. **Comparison.java** - All three in one example
5. **BidirectionalAssociation.java** - Two-way relationship

---

## Complete Comparison Example

```java
// Composition - Engine is PART OF Car
class Engine {
    private String type;

    Engine(String type) {
        this.type = type;
    }

    void start() {
        System.out.println(type + " engine starting");
    }
}

class Car {
    private String model;
    private Engine engine;  // Composition - created inside

    Car(String model) {
        this.model = model;
        engine = new Engine("V8");  // Engine created WITH car
    }

    void start() {
        System.out.println(model + " car starting");
        engine.start();
    }
}

// Aggregation - Driver uses Car
class Driver {
    private String name;
    private Car car;  // Aggregation - exists independently

    Driver(String name) {
        this.name = name;
    }

    void assignCar(Car car) {
        this.car = car;  // Car created elsewhere
    }

    void drive() {
        if (car != null) {
            System.out.println(name + " is driving");
            car.start();
        }
    }
}

// Usage
Car myCar = new Car("Tesla Model S");  // Car created independently
Driver driver = new Driver("John");
driver.assignCar(myCar);  // Assign existing car
driver.drive();

// Car can exist without driver
driver = null;  // Driver destroyed
// myCar still exists

// But engine cannot exist without car
myCar = null;  // Car destroyed, engine also destroyed
```

---

## Comparison Table

| Feature              | Association        | Aggregation         | Composition          |
|---------------------|--------------------|---------------------|----------------------|
| **Relationship**    | Uses-a             | Has-a (weak)        | Part-of (strong)     |
| **Ownership**       | None               | Weak                | Strong               |
| **Lifecycle**       | Independent        | Independent         | Dependent            |
| **Creation**        | Separate           | Separate            | Inside               |
| **Symbol (UML)**    | →                  | ◇→                  | ♦→                   |
| **Example**         | Teacher-Student    | Department-Professor| House-Room           |

---

## When to Use What?

### Use Association When:
- Objects just need to communicate
- No ownership relationship
- Example: Teacher teaches Student

### Use Aggregation When:
- "Has-a" relationship
- Parts can exist independently
- Weak ownership
- Example: Library has Books

### Use Composition When:
- "Part-of" relationship
- Parts cannot exist independently
- Strong ownership
- Example: Body has Heart

---

## Code Characteristics

### Association
```java
class A {
    void method(B b) {  // B passed as parameter
        b.doSomething();
    }
}
```

### Aggregation
```java
class A {
    private B b;  // B as field

    void setB(B b) {  // B created outside
        this.b = b;
    }
}
```

### Composition
```java
class A {
    private B b;

    A() {
        b = new B();  // B created inside
    }
}
```

---

## Inheritance vs Composition

### Inheritance (IS-A)
```java
class Dog extends Animal {
    // Dog IS-A Animal
}
```

### Composition (HAS-A)
```java
class Car {
    private Engine engine;  // Car HAS-A Engine
}
```

**Favor Composition over Inheritance:**
- More flexible
- Loose coupling
- Easy to change

---

## Key Points

✓ Association = General relationship (uses-a)
✓ Aggregation = Weak has-a (parts can exist independently)
✓ Composition = Strong part-of (parts cannot exist independently)
✓ Aggregation: Parts created outside
✓ Composition: Parts created inside
✓ Use composition for strong ownership
✓ Prefer composition over inheritance for flexibility

---

## Real-World Analogy

**University System:**

- **Association:** Professor teaches Course (independent)
- **Aggregation:** Department has Professors (professors can change departments)
- **Composition:** University has Departments (department cannot exist without university)

Different strengths of relationships!
