package designpattern.creational.builders;

public class Phone {
    private String os;
    private int ram;
    private int cpu;
    private String processor;
    private String screenSize;
    private int battery;
    private int camera;

    public Phone(String os, int ram, int cpu, String processor, String screenSize, int battery, int camera) {
        this.os = os;
        this.ram = ram;
        this.cpu = cpu;
        this.processor = processor;
        this.screenSize = screenSize;
        this.battery = battery;
        this.camera = camera;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "os='" + os + '\'' +
                ", ram= " + ram +
                ", cpu= " + cpu  +
                ", processor= " + processor +
                ", screenSize= " + screenSize +
                ", battery='" + battery + '\'' +" mAh"+
                ", camera='" + camera + '\'' +" MP"+
                '}';
    }
}
