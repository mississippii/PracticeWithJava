package oop.Interface;

public class InterfaceDemo {
    public static void main(String[] args) {

        // -------------------------------------------------------
        // 1. Basic interface usage
        //    Interface reference holds implementing class object.
        //    Same as polymorphism — runtime picks the right method.
        // -------------------------------------------------------
        System.out.println("=== Basic Interface (Payable) ===");

        Payable cash   = new CashPayment("Alice");
        Payable bkash  = new BkashPayment("Bob", "017XXXXXXXX");

        cash.pay(500);
        System.out.println();
        bkash.pay(800);

        System.out.println();

        // -------------------------------------------------------
        // 2. Default method — used as-is vs overridden
        //    CashPayment uses Payable's default printReceipt().
        //    BkashPayment overrides it with its own SMS version.
        // -------------------------------------------------------
        System.out.println("=== Default Method (used vs overridden) ===");

        Payable p1 = new CashPayment("Alice");
        Payable p2 = new BkashPayment("Bob", "017XXXXXXXX");

        p1.printReceipt(500);   // Payable's default version runs
        p2.printReceipt(500);   // BkashPayment's overridden version runs

        System.out.println();

        // -------------------------------------------------------
        // 3. Static method — called on the interface, not on object
        // -------------------------------------------------------
        System.out.println("=== Static Method ===");

        Payable.validate(500);     // valid — no exception
        System.out.println("$500 is a valid amount");

        try {
            Payable.validate(-100);  // invalid — throws exception
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println();

        // -------------------------------------------------------
        // 4. Constant — accessible from interface or implementing class
        // -------------------------------------------------------
        System.out.println("=== Constant ===");

        System.out.println("Max allowed payment: $" + Payable.MAX_AMOUNT);
        System.out.println("Tax rate: "             + Taxable.TAX_RATE * 100 + "%");

        System.out.println();

        // -------------------------------------------------------
        // 5. Multiple interface implementation
        //    CardPayment implements both Payable and Taxable.
        // -------------------------------------------------------
        System.out.println("=== Multiple Interfaces (Payable + Taxable) ===");

        CardPayment card = new CardPayment("Carol", "4321");
        card.pay(1000);

        // Can be referenced as either interface type
        Payable  asPayable  = card;
        Taxable  asTaxable  = card;

        System.out.println("Tax on $1000: $" + asTaxable.calculateTax(1000));
        System.out.println("Total with tax: $" + asTaxable.totalWithTax(1000));

        System.out.println();

        // -------------------------------------------------------
        // 6. Interface extending interface
        //    Refundable extends Payable — OnlinePayment implements Refundable.
        //    OnlinePayment must fulfill both contracts.
        // -------------------------------------------------------
        System.out.println("=== Interface Extending Interface (Refundable) ===");

        OnlinePayment online = new OnlinePayment("David");
        online.pay(600);
        online.refund(200);

        // Can be referenced as Payable or Refundable
        Payable    asP = online;
        Refundable asR = online;

        asP.pay(300);    // Payable reference — can only call Payable methods
        asR.refund(100); // Refundable reference — can call refund() too

        System.out.println();

        // -------------------------------------------------------
        // 7. Polymorphic array with interface type
        // -------------------------------------------------------
        System.out.println("=== Polymorphic Array ===");

        Payable[] payments = {
            new CashPayment("Alice"),
            new BkashPayment("Bob", "017XXXXXXXX"),
            new CardPayment("Carol", "4321"),
            new OnlinePayment("David")
        };

        double bill = 750;
        for (Payable payment : payments) {
            payment.pay(bill);
            System.out.println();
        }

        // -------------------------------------------------------
        // 8. Functional interface — single abstract method, used with lambda
        // -------------------------------------------------------
        System.out.println("=== Functional Interface + Lambda ===");

        // Payable has more than one abstract method so it is NOT functional.
        // A functional interface has exactly ONE abstract method.
        // Java's built-in Runnable is a classic example:

        Runnable task = () -> System.out.println("Running a task via lambda");
        task.run();

        // Custom functional interface inline
        @FunctionalInterface
        interface Greeting {
            void greet(String name);  // only one abstract method
        }

        Greeting hello = name -> System.out.println("Hello, " + name + "!");
        Greeting bye   = name -> System.out.println("Goodbye, " + name + "!");

        hello.greet("Alice");
        bye.greet("Bob");
    }
}
