package OOP.Plymorphism;

import java.util.ArrayList;

public class Cup {
    public ArrayList<Liquid> liquids = new ArrayList<>();
    public void addLiquid(Liquid liquid) {
        liquids.add(liquid);
    }
    public void mixing() {
        for(Liquid liquid :liquids){
            if(liquid instanceof Coffee coffee){
                coffee.addName();
            }
            liquid.swirl(true);
        }
    }
}
