package OOP.Inheritance;

import OOP.Encapsulation.Dimension;

public class Product {
    public static final int SHIPPING_RATE =5;
    public static final double DIMENSION_CHARGE =1.5;
    private final String name;
    private final int weight;
    private final Dimension dimension;
    private final int price;
    private final int discount;

    public Product(String name,int price,int discount,int weight,Dimension dimension){
        this.name= name;
        this.price = price;
        this.discount = discount;
        this.weight = weight;
        this.dimension= dimension;
    }
    public int getPrice(){
        double productPrice =price*(100-discount)/100.0;
        productPrice+= weight*Product.SHIPPING_RATE;
            if(dimension.getVolume()>10){
                productPrice+= dimension.getVolume()* Product.DIMENSION_CHARGE;
            }
        return (int)productPrice;
    }
}
