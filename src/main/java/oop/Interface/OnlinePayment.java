package oop.Interface;

// Implements Refundable — which itself extends Payable.
// So OnlinePayment must implement:
//   - pay()              (from Payable via Refundable)
//   - getPaymentMethod() (from Payable via Refundable)
//   - refund()           (from Refundable)

public class OnlinePayment implements Refundable {

    private final String ownerName;

    public OnlinePayment(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public void pay(double amount) {
        Payable.validate(amount);
        System.out.println(ownerName + " paid $" + amount + " online");
        printReceipt(amount);  // from Payable's default method
    }

    @Override
    public String getPaymentMethod() {
        return "Online";
    }

    @Override
    public void refund(double amount) {
        System.out.println(ownerName + " received a refund of $" + amount);
    }
}
