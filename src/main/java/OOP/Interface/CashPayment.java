package OOP.Interface;

public class CashPayment implements Payment{
    private final double amount;

    public CashPayment(double amount){
        this.amount= amount;
    }
    @Override
    public double getAmount(){
        return amount;
    }
}
