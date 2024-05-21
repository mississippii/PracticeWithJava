package Practice.OOP.Abstraction;

public class Lion extends Animal{
    public Lion(){
        super("Lion",4);
    }
    @Override
    public String getDiets(){
        return "Lion eat meet";
    }
}
