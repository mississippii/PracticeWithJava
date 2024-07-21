package OOP.Interface;

public class BkashPayment implements Payment{
    private final double amount;
    public BkashPayment(double amount){
        this.amount = amount;
    }
    @Override
    public double getAmount(){
        return amount;
    }
}
