package OOP.Abstraction;

public class FixedPayCalculator extends  PayCalculator{
    private final double fixedRate;
    public FixedPayCalculator(double fixedRate) {
        this.fixedRate = fixedRate;
    }
    @Override
    public double getPay(Client client){
        return fixedRate;
    }
}
