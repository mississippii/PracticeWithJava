package designpattern.creational.singleton;

public class Singleton {

    private static Singleton instance;

    private Singleton() {
        System.out.println("Object Created successfully..");
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance==null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
