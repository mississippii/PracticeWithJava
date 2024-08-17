package DesignPattern;

import DesignPattern.Creational.Singleton;

public class DesignPatternMain {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Singleton singleton1 = Singleton.getInstance();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Singleton singleton1 = Singleton.getInstance();
            }
        }).start();
    }
}
