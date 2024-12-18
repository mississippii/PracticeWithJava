package designpattern;

import designpattern.creational.singleton.Singleton;

public class DesignPatternMain {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton);
    }
}
