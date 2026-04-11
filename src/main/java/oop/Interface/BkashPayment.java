package oop.Interface;

// Implements Payable AND overrides the default printReceipt() method.
// Bkash sends a digital receipt instead of a generic one.

public class BkashPayment implements Payable {

    private final String ownerName;
    private final String phoneNumber;

    public BkashPayment(String ownerName, String phoneNumber) {
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void pay(double amount) {
        Payable.validate(amount);
        System.out.println(ownerName + " paid " + amount + " via Bkash (" + phoneNumber + ")");
        printReceipt(amount);  // calls overridden version below
    }

    @Override
    public String getPaymentMethod() {
        return "Bkash";
    }

    // Overriding default method — Bkash sends SMS instead of printing
    @Override
    public void printReceipt(double amount) {
        System.out.println("[Bkash SMS] Payment of $" + amount
                + " confirmed. Sent to " + phoneNumber);
    }
}
