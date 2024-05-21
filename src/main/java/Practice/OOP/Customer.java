package Practice.OOP;

public class Customer {
    private String name;
    private CreditCard creditCard;
    public Customer(String name, CreditCard creditCard) {
        this.name = name;
        this.creditCard = creditCard;
    }
    public Customer(String name,long number, int cvv, String validThrough) {
        this.name = name;
        this.creditCard = new CreditCard(number, cvv, validThrough);
    }
}
