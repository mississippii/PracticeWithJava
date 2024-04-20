package org.example;

import java.util.StringJoiner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String [] division= {"Dhaka","Chittagong","Barishal","Dinazpur","Rajshahi"};
        StringJoiner joinString= new StringJoiner(",","(",")");
        for(String divisions: division){
            joinString.add(divisions);
        }
        System.out.println("Division of Bangladesh\t: "+joinString);
        Circle circle1 = new Circle(5);
        Circle circle2= new Circle(5);
        Circle circle3 = new Circle(5);
        System.out.println(circle1.objectCreated);
        System.out.println(circle2.objectCreated);
        System.out.println(circle3.objectCreated);
    }
}