package oop.polymorphism;

public class PolymorphismDemo {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // PART 1 — Compile-Time Polymorphism (Method Overloading)
        // -------------------------------------------------------
        // Java picks the right add() at COMPILE TIME based on arguments.

        System.out.println("=== Compile-Time Polymorphism (Overloading) ===");

        Calculator calc = new Calculator();

        System.out.println(calc.add(5, 10));          // add(int, int)       → 15
        System.out.println(calc.add(5, 10, 15));      // add(int, int, int)  → 30
        System.out.println(calc.add(2.5, 3.5));       // add(double, double) → 6.0
        System.out.println(calc.add("Hello", "World")); // add(String, String) → Hello World

        System.out.println();

        // -------------------------------------------------------
        // PART 2 — Runtime Polymorphism (Method Overriding)
        // -------------------------------------------------------
        // Parent reference holds child object.
        // Java picks the right pay() at RUNTIME based on actual object.

        System.out.println("=== Runtime Polymorphism (Overriding) ===");

        Payment p1 = new CashPayment("Alice");
        Payment p2 = new CardPayment("Bob", "1234567890123456");
        Payment p3 = new MobilePayment("Carol", "017XXXXXXXX");

        p1.pay(500);    // Alice paid $500.0 in cash
        p2.pay(1200);   // Bob paid $1200.0 via card ending in 3456
        p3.pay(300);    // Carol paid $300.0 via mobile 017XXXXXXXX

        System.out.println();

        // -------------------------------------------------------
        // PART 3 — Polymorphic Array (the real power)
        // -------------------------------------------------------
        // All stored as Payment — each pay() call does the right thing.

        System.out.println("=== Polymorphic Array ===");

        Payment[] payments = {
            new CashPayment("Alice"),
            new CardPayment("Bob", "1234567890123456"),
            new MobilePayment("Carol", "017XXXXXXXX"),
            new CashPayment("David")
        };

        double bill = 800;
        for (Payment payment : payments) {
            payment.pay(bill);   // correct pay() picked at runtime for each
        }

        System.out.println();

        // -------------------------------------------------------
        // PART 4 — Upcasting vs Downcasting
        // -------------------------------------------------------

        System.out.println("=== Upcasting & Downcasting ===");

        // Upcasting — child object stored in parent reference (automatic, safe)
        Payment payment = new CashPayment("Alice");
        payment.pay(500);           // works — pay() is in Payment
        // payment.printReceipt();  // COMPILE ERROR — printReceipt() not in Payment

        // Downcasting — go back to child type to access child-specific methods
        if (payment instanceof CashPayment cash) {   // pattern matching (Java 16+)
            cash.printReceipt();    // now can call CashPayment-specific method
        }

        System.out.println();

        // -------------------------------------------------------
        // PART 5 — instanceof (type checking before downcast)
        // -------------------------------------------------------

        System.out.println("=== instanceof ===");

        Payment[] mixed = {
            new CashPayment("Alice"),
            new CardPayment("Bob", "1234567890123456"),
            new MobilePayment("Carol", "017XXXXXXXX")
        };

        for (Payment p : mixed) {
            System.out.println(p.ownerName + " uses: " + p.getMethod());

            if (p instanceof CashPayment cash) {
                cash.printReceipt();
            } else if (p instanceof CardPayment card) {
                card.checkCardLimit();
            } else if (p instanceof MobilePayment mobile) {
                mobile.sendSmsConfirmation();
            }
        }
    }
}
