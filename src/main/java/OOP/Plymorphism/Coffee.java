package OOP.Plymorphism;

public class Coffee extends Liquid {
    @Override
    public void swirl(boolean clockwise){
        System.out.println("swirling coffee");
    }
    public void addName(){
        System.out.println("kemon acho bangladesh");
    }
}
