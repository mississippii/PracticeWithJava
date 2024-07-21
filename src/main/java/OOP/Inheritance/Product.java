package OOP.Inheritance;

public abstract class Product {
    private final String name;
    private final double price;
    private final double discount;

    public Product(String name,int price,int discount){
        this.name= name;
        this.price = price;
        this.discount = discount;
    }
    public abstract double calculatePrice();

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }
    public double getPrice(){
        return price;
    }

}
