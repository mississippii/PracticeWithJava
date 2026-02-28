package designpattern.creational.singleton;

public class Singleton {
    private static Singleton instance;
    private Integer x= 10;

    private Singleton(){}
    public static Singleton getInstance(){
        if(instance==null){
            synchronized (Singleton.class){
                return instance = new Singleton();
            }
        }
        return instance;
    }
}
