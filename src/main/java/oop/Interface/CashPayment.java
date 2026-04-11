package oop.Interface;

// Implements Payable — must provide pay() and getPaymentMethod().
// Uses printReceipt() and printSummary() from Payable as-is (no override).

public class CashPayment implements Payable {

    private final String ownerName;

    public CashPayment(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public void pay(double amount) {
        Payable.validate(amount);  // call static method from interface
        System.out.println(ownerName + " paid " + amount + " in cash");
        printReceipt(amount);      // inherited default method
    }

    @Override
    public String getPaymentMethod() {
        return "Cash";
    }
}
