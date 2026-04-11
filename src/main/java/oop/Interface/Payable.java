package oop.Interface;

// Interface — defines WHAT to do, not HOW.
// Every class that implements Payable must fulfill this contract.

public interface Payable {

    // -------------------------------------------------------
    // 1. Constant — public static final by default
    //    Every implementing class can read this, nobody can change it.
    // -------------------------------------------------------
    double MAX_AMOUNT = 100_000.0;

    // -------------------------------------------------------
    // 2. Abstract methods — public abstract by default
    //    Every implementing class MUST provide its own version.
    // -------------------------------------------------------
    void pay(double amount);

    String getPaymentMethod();

    // -------------------------------------------------------
    // 3. Default method — Java 8+
    //    Has a body. Implementing class gets it for free.
    //    Can be overridden if a class wants different behavior.
    // -------------------------------------------------------
    default void printReceipt(double amount) {
        System.out.println("[Receipt] " + getPaymentMethod()
                + " — " + formatAmount(amount));
    }

    default void printSummary(double amount) {
        System.out.println("[Summary] Paid " + formatAmount(amount)
                + " via " + getPaymentMethod());
    }

    // -------------------------------------------------------
    // 4. Static method — Java 8+
    //    Belongs to the interface itself, not to any object.
    //    Called as Payable.validate(...), not on an instance.
    // -------------------------------------------------------
    static void validate(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("Amount exceeds limit of " + MAX_AMOUNT);
        }
    }

    // -------------------------------------------------------
    // 5. Private method — Java 9+
    //    Helper shared by default methods inside this interface.
    //    Cannot be accessed from outside or from implementing classes.
    // -------------------------------------------------------
    private String formatAmount(double amount) {
        return String.format("$%.2f", amount);
    }
}
