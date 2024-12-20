package designpattern;

import designpattern.creational.builders.Phone;
import designpattern.creational.builders.PhoneBuilder;

public class DesignPatternMain {
    public static void main(String[] args) {
        Phone phone = new PhoneBuilder()
                .setBattery(4000)
                .setCamera(12)
                .setOs("Android")
                .setRam("8")
                .build();
        System.out.println(phone);
    }
}
