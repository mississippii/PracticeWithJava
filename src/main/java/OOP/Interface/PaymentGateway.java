package OOP.Interface;

public class PaymentGateway {
    public void acdeptPayment(Payment payment){
        System.out.println("Accepting payment for " + payment.getAmount());
    }
}
