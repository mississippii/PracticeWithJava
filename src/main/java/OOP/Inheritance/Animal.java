package OOP.Inheritance;

public abstract class Animal {
    private final String name;
    private final int leg;
    public Animal(String name,int leg){
        this.name = name;
        this.leg = leg;
    }

    public abstract String getDiets();
    @Override
    public String toString(){
        return (name+" has "+leg+" legs ");
    }
}
