# Inheritance & Runtime Polymorphism

## What is Inheritance?

A child class acquires the fields and methods of a parent class using the `extends` keyword.

**"IS-A" relationship** — Dog IS-A Animal, Cow IS-A Animal.

```java
class Animal { }
class Dog extends Animal { }  // Dog inherits everything from Animal
```

---

## What the child gets from the parent

- All `public` and `protected` fields and methods
- Does NOT get `private` fields/methods
- Does NOT get constructors — must call parent constructor using `super()`

---

## `super` keyword

Used to call the parent constructor or parent methods from the child class.

```java
class Dog extends Animal {
    public Dog(String name, String color) {
        super(name, color);  // calls Animal's constructor — must be the first line
    }
}
```

Without `super(name, color)`, the parent's fields `name` and `color` would never be initialized.

---

## Method Overriding

Child class provides its own implementation of a method that already exists in the parent.

```java
class Animal {
    protected void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {
    @Override                          // optional but recommended
    public void eat() {
        System.out.println("Dog is eating");
    }
}
```

**Rules:**
1. Same method name and parameters as the parent
2. Return type must be the same (or a subtype)
3. Access modifier can be same or less restrictive (`protected` → `public` is fine)
4. Cannot override `final`, `static`, or `private` methods

---

## What can and cannot be overridden

### `final` method — inherited but cannot be overridden

The child can call a `final` method, but cannot provide its own version.

```java
class Animal {
    public final void breathe() {
        System.out.println("Animal is breathing");
    }
}

class Dog extends Animal {
    @Override
    public void breathe() { }   // COMPILE ERROR: cannot override final method
}

// But the child can still call it — it IS inherited
Dog dog = new Dog("Rex", "White");
dog.breathe();   // Animal is breathing ✓
```

**Why `final`?** The parent is saying: "This behavior must stay exactly as I defined it. No child is allowed to change it."

---

### `static` method — inherited but cannot be overridden (method hiding)

Static methods belong to the **class**, not to any object. When a child defines a static method with the same name, it **hides** the parent's version — it does not override it.

The key difference shows up with a parent reference:

```java
class Animal {
    public static void describe() {
        System.out.println("I am an Animal");
    }
}

class Dog extends Animal {
    public static void describe() {   // hides Animal's describe(), does NOT override
        System.out.println("I am a Dog");
    }
}

Dog.describe();    // I am a Dog      ✓ — called directly on Dog class

Animal a = new Dog("Rex", "White");
a.describe();      // I am an Animal  ← parent's version! decided at compile time
```

With regular instance methods — Java decides at **runtime** based on the actual object.  
With static methods — Java decides at **compile time** based on the reference type.

This is why static methods do not participate in runtime polymorphism.

---

### `private` method — NOT inherited at all

Private methods are completely invisible to the child class.

```java
class Animal {
    private void secret() {
        System.out.println("Animal secret");
    }
}

class Dog extends Animal {
    public void test() {
        secret();   // COMPILE ERROR: secret() has private access in Animal
    }
}
```

---

### Summary table

| Method type | Inherited? | Can override? | Polymorphism? |
|---|---|---|---|
| `public` / `protected` | Yes | Yes | Yes — runtime |
| `final` | Yes (child can call it) | No — compile error | No |
| `static` | Yes (accessible via child class) | No — method hiding | No — compile time |
| `private` | No | No | No |

---

## Runtime Polymorphism

A **parent reference** can hold a **child object**.  
Java decides at **runtime** which version of the method to call — based on the actual object, not the reference type.

```java
Animal a1 = new Dog("Husky", "Black");
Animal a2 = new Cow("Bessie", "Brown");

a1.eat();   // Dog is eating  — Java picks Dog's eat() at runtime
a2.eat();   // Cow is eating  — Java picks Cow's eat() at runtime
```

Both references are of type `Animal`, but Java looks at the **actual object** (`Dog`, `Cow`) to decide which `eat()` to run. That decision happens while the program is running — hence **runtime** polymorphism.

### Why is this useful?

You can write one loop that works for every animal — no if/else needed:

```java
Animal[] animals = { new Dog("Rex", "White"), new Cow("Bessie", "Brown") };

for (Animal animal : animals) {
    animal.eat();   // each calls its own eat()
}
// Output:
// Dog is eating
// Cow is eating
```

If you add a new `Cat` class tomorrow, this loop works without any changes.

---

## Does `@Override` affect runtime polymorphism?

**No.** Runtime polymorphism works with or without `@Override`.

```java
// Works perfectly — no @Override needed for polymorphism to work
class Cow extends Animal {
    public void eat() {
        System.out.println("Cow is eating");
    }
}

Animal cow = new Cow("Milk", "White");
cow.eat();   // Cow is eating ✓
```

Java overrides the method based on the actual object type at runtime.  
`@Override` is only a **compile-time safety check** — it protects you from typos:

```java
// Without @Override — typo creates a silent bug
class Cow extends Animal {
    public void eet() {              // typo! different method name
        System.out.println("Cow is eating");
    }
}

Animal cow = new Cow("Milk", "White");
cow.eat();   // Animal eats ← wrong! parent's eat() runs, no error
```

```java
// With @Override — compiler catches it immediately
class Cow extends Animal {
    @Override
    public void eet() {              // COMPILE ERROR: does not override anything
        System.out.println("Cow is eating");
    }
}
```

| | Without `@Override` | With `@Override` |
|---|---|---|
| Runtime polymorphism works? | Yes | Yes |
| Typo protection | No — silent bug | Yes — compile error |
| Recommended? | No | Yes |

---

## What is NOT overridden

`makeSound()` exists in `Animal` but neither `Dog` nor `Cow` overrides it.  
When called on a child object, Java walks up to `Animal` and uses its version:

```java
Animal dog = new Dog("Rex", "White");
dog.makeSound();   // Animal makes sound — parent's version runs
```

This is still inheritance — the child simply uses the parent method as-is.

---

## Compile-time vs Runtime Polymorphism

| | Compile-time Polymorphism | Runtime Polymorphism |
|---|---|---|
| Also called | Method Overloading | Method Overriding |
| Decided when? | At compile time | At runtime |
| Where? | Same class, different parameters | Parent + child class, same signature |
| Example | `add(int, int)` vs `add(double, double)` | `Animal a = new Dog(); a.eat()` |

---

## Common Mistake — reference type vs object type

```java
Animal a = new Dog("Rex", "White");
a.eat();    // Dog is eating ✓  — runtime picks Dog's eat()
a.bark();   // COMPILE ERROR   — bark() is not defined in Animal
```

The **reference type** (`Animal`) decides what methods you can call.  
The **object type** (`Dog`) decides which version actually runs.

---

## Files in this folder

| File | What it shows |
|---|---|
| `Animal.java` | Parent class with `eat()` and `makeSound()` |
| `Dog.java` | Child — overrides `eat()` |
| `Cow.java` | Child — overrides `eat()` |
| `InheritanceDemo.java` | Direct call vs runtime polymorphism demo |
