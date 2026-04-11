package oop.polymorphism;

public class MobilePayment extends Payment {

    private String phoneNumber;

    public MobilePayment(String ownerName, String phoneNumber) {
        super(ownerName);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println(ownerName + " paid $" + amount
                + " via mobile " + phoneNumber);
    }

    @Override
    public String getMethod() {
        return "Mobile";
    }

    // Mobile-specific method
    public void sendSmsConfirmation() {
        System.out.println("SMS confirmation sent to " + phoneNumber);
    }
}
