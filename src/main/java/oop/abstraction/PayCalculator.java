package oop.abstraction;

public abstract class PayCalculator {
    public static final double HOURLY_RATE =70.0;
    public static final double FIXED_RATE =70.0;

    public abstract double getPay(Client client);
}
