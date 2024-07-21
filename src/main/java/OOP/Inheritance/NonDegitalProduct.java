package OOP.Inheritance;

public class NonDegitalProduct extends Product{
    public static final double SHIPPING_RATE=5;
    public static final double DIMENSION_CHANGE=1.5;
    private final double weight;
    private final Dimension dimension;

    public NonDegitalProduct(String name, int price, int discount, double weight, Dimension dimension) {
        super(name, price, discount);
        this.weight = weight;
        this.dimension = dimension;
    }

    @Override
    public double calculatePrice(){
        double totalPrice = getPrice()*(1000-getDiscount())/100.0;
        totalPrice+=weight*SHIPPING_RATE;
        if (dimension.getVolume()>10){
            totalPrice+=dimension.getVolume()*DIMENSION_CHANGE;
        }
        return totalPrice;
    }
}
