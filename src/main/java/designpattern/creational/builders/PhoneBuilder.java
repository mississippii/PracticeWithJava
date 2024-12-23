package designpattern.creational.builders;

public class PhoneBuilder {
    int ram;
    int cpu;
    String processor;
    String screenSize;
    int battery;
    int camera;
    Phone.OS os;

    public PhoneBuilder setOs(Phone.OS os) {
        this.os = os;
        return this;
    }

    public PhoneBuilder setRam(int ram) {
        this.ram = ram;
        return this;
    }

    public PhoneBuilder setCpu(int cpu) {
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
        return new Phone(this);
    }
}
