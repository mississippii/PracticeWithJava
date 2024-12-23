package designpattern;

import designpattern.creational.builders.Phone;
import designpattern.creational.builders.PhoneBuilder;

public class DesignPatternMain {
    public static void main(String[] args) {
        Phone phone = new PhoneBuilder()
                .setBattery(4000)
                .setCamera(12)
                .setOs(Phone.OS.linux)
                .setRam(8)
                .setCpu(16)
                .setProcessor("Snapdragon")
                .setScreenSize("6.1\"")
                .build();
        System.out.println(phone);
    }
}
