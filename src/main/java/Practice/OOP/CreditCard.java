package Practice.OOP;

public class CreditCard {
    private final long number;
    private final int cvv;
    private final String validThrough;

    public CreditCard(long number, int cvv, String validThrough) {
        this.number = number;
        this.cvv = cvv;
        this.validThrough = validThrough;
    }
}
