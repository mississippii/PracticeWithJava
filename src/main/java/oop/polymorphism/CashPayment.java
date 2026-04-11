package oop.polymorphism;

public class CashPayment extends Payment {

    public CashPayment(String ownerName) {
        super(ownerName);
    }

    @Override
    public void pay(double amount) {
        System.out.println(ownerName + " paid $" + amount + " in cash");
    }

    @Override
    public String getMethod() {
        return "Cash";
    }

    // Cash-specific method — only available on CashPayment, not on Payment
    public void printReceipt() {
        System.out.println("Printing paper receipt for " + ownerName);
    }
}
