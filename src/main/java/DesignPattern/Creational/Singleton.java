package DesignPattern.Creational;

public class Singleton {
    private static Singleton instance;
    private static final String name="Tanveer";
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
