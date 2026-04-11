package oop.Interface;

// Implements TWO interfaces — Payable and Taxable.
// This is multiple interface inheritance. Java allows this unlike multiple class inheritance.
// Must implement: pay(), getPaymentMethod(), calculateTax()
// Gets for free:  printReceipt(), printSummary(), totalWithTax()

public class CardPayment implements Payable, Taxable {

    private final String ownerName;
    private final String lastFourDigits;

    public CardPayment(String ownerName, String lastFourDigits) {
        this.ownerName = ownerName;
        this.lastFourDigits = lastFourDigits;
    }

    @Override
    public void pay(double amount) {
        Payable.validate(amount);
        double total = totalWithTax(amount);  // from Taxable default method
        System.out.println(ownerName + " paid $" + total
                + " via card ending in " + lastFourDigits
                + " (includes " + (TAX_RATE * 100) + "% tax)");
        printSummary(total);  // from Payable default method
    }

    @Override
    public String getPaymentMethod() {
        return "Card";
    }

    @Override
    public double calculateTax(double amount) {
        return amount * TAX_RATE;  // TAX_RATE constant from Taxable
    }
}
