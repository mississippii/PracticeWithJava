package oop._03_inheritance;

/**
 * Demonstrates:
 * - Single inheritance
 * - Multi-level inheritance
 * - Method overriding
 * - super keyword
 * - Constructor chaining in inheritance
 */
public class VehicleHierarchy {
    public static void main(String[] args) {
        System.out.println("=== Creating Vehicle ===");
        Vehicle vehicle = new Vehicle("Generic", 2020);
        vehicle.displayInfo();
        vehicle.start();

        System.out.println("\n=== Creating Car (Single Inheritance) ===");
        Car car = new Car("Toyota Camry", 2022, 4);
        car.displayInfo();
        car.start();
        car.honk();

        System.out.println("\n=== Creating ElectricCar (Multi-level Inheritance) ===");
        ElectricCar electricCar = new ElectricCar("Tesla Model 3", 2023, 4, 75);
        electricCar.displayInfo();
        electricCar.start();
        electricCar.charge();
        electricCar.honk();
    }
}

// Parent class (Base class)
class Vehicle {
    protected String model;
    protected int year;
    protected int speed;

    // Constructor
    Vehicle(String model, int year) {
        this.model = model;
        this.year = year;
        this.speed = 0;
        System.out.println("Vehicle constructor called");
    }

    // Method
    void start() {
        System.out.println("Vehicle is starting...");
    }

    void stop() {
        speed = 0;
        System.out.println("Vehicle stopped");
    }

    void accelerate(int increment) {
        speed += increment;
        System.out.println("Speed: " + speed + " km/h");
    }

    void displayInfo() {
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Speed: " + speed + " km/h");
    }
}

// Child class (Derived class) - Single Inheritance
class Car extends Vehicle {
    private int doors;

    // Constructor
    Car(String model, int year, int doors) {
        super(model, year);  // Call parent constructor
        this.doors = doors;
        System.out.println("Car constructor called");
    }

    // Override parent method
    @Override
    void start() {
        System.out.println("Car engine starting...");
        super.start();  // Call parent method
        System.out.println("Car is ready to drive");
    }

    // Override parent method
    @Override
    void displayInfo() {
        super.displayInfo();  // Call parent method
        System.out.println("Doors: " + doors);
    }

    // New method specific to Car
    void honk() {
        System.out.println("Beep beep!");
    }
}

// Grandchild class - Multi-level Inheritance
class ElectricCar extends Car {
    private int batteryCapacity;  // kWh
    private int batteryLevel;

    // Constructor
    ElectricCar(String model, int year, int doors, int batteryCapacity) {
        super(model, year, doors);  // Call parent (Car) constructor
        this.batteryCapacity = batteryCapacity;
        this.batteryLevel = 100;  // Full charge
        System.out.println("ElectricCar constructor called");
    }

    // Override Car's start method
    @Override
    void start() {
        if (batteryLevel > 0) {
            System.out.println("Electric motor starting silently...");
            System.out.println("Battery level: " + batteryLevel + "%");
        } else {
            System.out.println("Battery dead! Please charge.");
        }
    }

    // Override displayInfo
    @Override
    void displayInfo() {
        super.displayInfo();  // Call parent (Car) method
        System.out.println("Battery: " + batteryLevel + "% (" + batteryCapacity + " kWh)");
    }

    // New method specific to ElectricCar
    void charge() {
        batteryLevel = 100;
        System.out.println("Battery fully charged!");
    }

    void useBattery(int percent) {
        batteryLevel -= percent;
        if (batteryLevel < 0) batteryLevel = 0;
        System.out.println("Battery remaining: " + batteryLevel + "%");
    }
}
