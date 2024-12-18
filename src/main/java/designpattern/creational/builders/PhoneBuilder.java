package designpattern.creational.builders;

public class PhoneBuilder {
    private String os;
    private String ram;
    private String cpu;
    private String processor;
    private String screenSize;
    private int battery;
    private int camera;

    public PhoneBuilder setOs(String os) {
        this.os = os;
        return this;
    }

    public PhoneBuilder setRam(String ram) {
        this.ram = ram;
        return this;
    }

    public PhoneBuilder setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public PhoneBuilder setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    public PhoneBuilder setScreenSize(String screenSize) {
        this.screenSize = screenSize;
        return this;
    }

    public PhoneBuilder setBattery(int battery) {
        this.battery = battery;
        return this;
    }

    public PhoneBuilder setCamera(int camera) {
        this.camera = camera;
        return this;
    }

    public Phone build() {
        return new Phone(os, ram, cpu, processor, screenSize, battery, camera);
    }
}
