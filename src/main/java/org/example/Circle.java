package org.example;

import java.awt.*;

public class Circle {
    private int raeius;
    private static double PI= Math.acos(-1);
    public static int objectCreated=0;
    public Circle(int raeius) {
        this.raeius = raeius;
        objectCreated++;
    }
    public double getArea(){
        return PI*raeius*raeius;
    }
}
