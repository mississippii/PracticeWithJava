package org.example;

import org.OOP.*;

import javax.xml.catalog.Catalog;
import java.util.StringJoiner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Liquid genericLequid = new Liquid();
        Liquid coffee = new Coffee();
        Liquid milk = new Milk();
        Cup cup = new Cup();
        coffee.swirl(true);
        cup.addLiquid(coffee);
        cup.addLiquid(milk);
        cup.addLiquid(genericLequid);
        cup.mixed();

    }
}