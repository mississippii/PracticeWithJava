package OOP.Abstraction;

import OOP.Inheritance.Animal;

public class Zoo {
    private Animal[]animals;
    public Zoo(Animal []animals){
        this.animals = animals;
    }
    public void printInfo(){
        for(Animal animal : animals){
            System.out.println(animal.toString());
            System.out.println(animal.getDiets());
        }
    }
}
