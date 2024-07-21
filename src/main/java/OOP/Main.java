package OOP;

import OOP.Plymorphism.Coffee;
import OOP.Plymorphism.Cup;
import OOP.Plymorphism.Milk;


public class Main {
    public static void main(String[] args){
        Cup cup = new Cup();
        cup.addLiquid(new Milk());
        cup.addLiquid(new Coffee());
        cup.mixing();
    }
}