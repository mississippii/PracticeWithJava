package OOP.Encapsulation;

import java.util.Arrays;

public class CharStack {
    private char[] elements;
    private int length;
    public CharStack() {
        this.elements = new char[10];
    }
    public boolean isEmpty() {return length==0;}
    public void push(char c) {
        if(isFull()){
            grow();
        }
        elements[length++] = c;
    }
    private boolean isFull(){return length==elements.length;}
    private void grow(){
        int newLength = elements.length * 2;
        elements = Arrays.copyOf(elements, newLength);
    }
    public char pop() {return elements[--length];}
    public int size() {return length;}
}
