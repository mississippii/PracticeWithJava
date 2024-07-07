package OOP.Abstraction;

public class Client {
    public final String name;
    public final int hoursWorked;

    public Client(String name, int hoursWorked) {
        this.name = name;
        this.hoursWorked = hoursWorked;
    }

    public String getName() {
        return name;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }
}
