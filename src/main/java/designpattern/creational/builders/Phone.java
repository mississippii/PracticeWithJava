package designpattern.creational.builders;

public class Phone {
    public enum OS{
        android,
        ios,
        linux,
        windows
    }
    private OS os;
    private int ram;
    private int cpu;
    private String processor;
    private String screenSize;
    private int battery;
    private int camera;

    public Phone(PhoneBuilder phoneBuilder) {
        this.os = phoneBuilder.os;
        this.ram = phoneBuilder.ram;
        this.cpu = phoneBuilder.cpu;
        this.processor = phoneBuilder.processor;
        this.screenSize = phoneBuilder.screenSize;
        this.battery = phoneBuilder.battery;
        this.camera = phoneBuilder.camera;
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
