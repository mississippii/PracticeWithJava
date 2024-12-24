package others.collections;

import lambda.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Calculator {
    public Double fun1(double x){
        return Math.pow(x,2.0)-4;
    }
    public Double fun2(double x){
        return 3*Math.pow(x-1,2.0)-5;
    }
    public static void serialozeToDisk(String fileName, List<Contact> contacts){
        File file = new File(fileName);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            oos.writeObject(contacts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Contact> deserialozeFromDisk(String fileName){
        File file = new File(fileName);
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            return (List<Contact>) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
