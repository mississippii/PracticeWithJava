package OOP.Encapsulation;

import OOP.Plymorphism.Liquid;

import java.util.ArrayList;

public class Cup {
    public ArrayList<Liquid>liquids = new ArrayList<>();
    public void addLiquid(Liquid liquid){
        String str = liquid.getClass().getTypeName();
        liquids.add(liquid);
        System.out.println(str);
    }
    public void mixed(){
        for(Liquid liquid : liquids){
            liquid.swirl(true);
        }
    }
}
