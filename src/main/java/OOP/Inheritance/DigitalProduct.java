package OOP.Inheritance;

public class DigitalProduct extends Product {
    private static final String JAVA_25="JAVA25";
    private static final String JAVA_2ND ="JAVA2ND";
    private String coupon;

    public DigitalProduct(String name, int price, int discount, String coupon) {
        super(name, price, discount);
        this.coupon = coupon;
    }

    public DigitalProduct(String name, int price, int discount) {
        super(name, price, discount);
    }
    @Override
    public double calculatePrice(){
        int couponDiscount=switch(coupon!=null?coupon:""){
            case JAVA_25 -> 3;
            case JAVA_2ND ->5;
            default -> 0;
        };
        double totalDiscount = couponDiscount+getDiscount();
        return getPrice()+(100-totalDiscount)/100.0;
    }

}
