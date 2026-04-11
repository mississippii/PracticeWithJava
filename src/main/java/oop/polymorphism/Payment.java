package oop.polymorphism;

// Parent class — defines the contract
// Every payment type MUST know how to pay() and getMethod()
public class Payment {

    protected String ownerName;

    public Payment(String ownerName) {
        this.ownerName = ownerName;
    }

    // To be overridden by each payment type
    public void pay(double amount) {
        System.out.println(ownerName + " paid $" + amount);
    }

    public String getMethod() {
        return "Unknown";
    }
}
