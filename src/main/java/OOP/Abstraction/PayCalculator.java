package OOP.Abstraction;

public class PayCalculator {
    private static final double HOURLY_RATE = 70.0;
    private static final double FIXED_PAY = 750;
    public double getPay(Client client ,String method) throws IllegalAccessException {
        return switch (method){
            case "Hourly"->client.getHoursWorked()*HOURLY_RATE;
            case "Fixed"->client.getHoursWorked()*FIXED_PAY;
            default -> throw new IllegalAccessException("unknown method :"+method);
        };
    }
}
