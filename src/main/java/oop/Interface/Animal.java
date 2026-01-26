package oop.Interface;

public interface Animal {

    default void makeSound(){
        System.out.println("Animal makes sound");
    }
}
