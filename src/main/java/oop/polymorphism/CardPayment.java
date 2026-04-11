package oop.polymorphism;

public class CardPayment extends Payment {

    private String cardNumber;

    public CardPayment(String ownerName, String cardNumber) {
        super(ownerName);
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println(ownerName + " paid $" + amount
                + " via card ending in " + cardNumber.substring(cardNumber.length() - 4));
    }

    @Override
    public String getMethod() {
        return "Card";
    }

    // Card-specific method
    public void checkCardLimit() {
        System.out.println("Checking card limit for " + ownerName);
    }
}
