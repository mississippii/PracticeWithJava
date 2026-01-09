package oop._06_interface;

/**
 * Demonstrates Interfaces
 * - Interface as contract
 * - Multiple interfaces implementation
 * - Default methods (Java 8+)
 * - Static methods in interface
 * - Interface constants
 */
public class PaymentSystemExample {
    public static void main(String[] args) {
        System.out.println("=== Interface Demo ===\n");

        // Creating objects
        PaymentMethod creditCard = new CreditCardPayment("1234-5678-9012-3456");
        PaymentMethod paypal = new PayPalPayment("user@example.com");
        PaymentMethod cash = new CashPayment();

        // Process payments
        System.out.println("--- Processing Payments ---");
        processPayment(creditCard, 100.50);
        processPayment(paypal, 250.75);
        processPayment(cash, 50.00);

        // Multiple interfaces
        System.out.println("\n--- Refundable Payments ---");
        if (creditCard instanceof Refundable) {
            ((Refundable) creditCard).refund(20.00);
        }

        if (paypal instanceof Refundable) {
            ((Refundable) paypal).refund(50.00);
        }

        // Using default method
        System.out.println("\n--- Using Default Methods ---");
        creditCard.printReceipt();
        paypal.printReceipt();

        // Using static method from interface
        System.out.println("\n--- Validating Amount ---");
        System.out.println("Is $100 valid? " + PaymentMethod.isValidAmount(100));
        System.out.println("Is $-50 valid? " + PaymentMethod.isValidAmount(-50));

        // Interface constants
        System.out.println("\n--- Tax Calculation ---");
        double amount = 100;
        double tax = amount * PaymentMethod.TAX_RATE;
        System.out.println("Amount: $" + amount);
        System.out.println("Tax (" + (PaymentMethod.TAX_RATE * 100) + "%): $" + tax);
        System.out.println("Total: $" + (amount + tax));
    }

    // Method accepting interface type (polymorphism)
    static void processPayment(PaymentMethod payment, double amount) {
        payment.processPayment(amount);
        payment.getPaymentDetails();
        System.out.println();
    }
}

// Interface 1 - Payment Method
interface PaymentMethod {
    // Constants (public static final by default)
    double TAX_RATE = 0.10;
    int MAX_TRANSACTION_AMOUNT = 10000;

    // Abstract methods (public abstract by default)
    void processPayment(double amount);
    void getPaymentDetails();

    // Default method (Java 8+)
    default void printReceipt() {
        System.out.println("*** RECEIPT ***");
        System.out.println("Payment processed successfully");
        System.out.println("***************");
    }

    // Static method (Java 8+)
    static boolean isValidAmount(double amount) {
        return amount > 0 && amount <= MAX_TRANSACTION_AMOUNT;
    }
}

// Interface 2 - Refundable
interface Refundable {
    void refund(double amount);

    default void printRefundPolicy() {
        System.out.println("Refunds processed within 7-10 business days");
    }
}

// Class implementing one interface
class CreditCardPayment implements PaymentMethod, Refundable {
    private String cardNumber;

    CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void processPayment(double amount) {
        if (PaymentMethod.isValidAmount(amount)) {
            System.out.println("Processing Credit Card payment: $" + amount);
            System.out.println("Card: ****-****-****-" + cardNumber.substring(cardNumber.length() - 4));
        } else {
            System.out.println("Invalid payment amount");
        }
    }

    @Override
    public void getPaymentDetails() {
        System.out.println("Payment Method: Credit Card");
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding $" + amount + " to credit card");
        printRefundPolicy();
    }

    // Override default method
    @Override
    public void printReceipt() {
        System.out.println("*** CREDIT CARD RECEIPT ***");
        System.out.println("Card Number: ****-****-****-" + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Payment processed");
        System.out.println("***************************");
    }
}

// Class implementing multiple interfaces
class PayPalPayment implements PaymentMethod, Refundable {
    private String email;

    PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment: $" + amount);
        System.out.println("Email: " + email);
    }

    @Override
    public void getPaymentDetails() {
        System.out.println("Payment Method: PayPal");
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding $" + amount + " to PayPal account: " + email);
    }
}

// Class implementing one interface
class CashPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Cash payment: $" + amount);
        System.out.println("Cash received");
    }

    @Override
    public void getPaymentDetails() {
        System.out.println("Payment Method: Cash");
    }

    // Using default printReceipt() from interface
}
