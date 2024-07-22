package Others.Generic;

import java.util.Arrays;

public class DynamicArray<T> {
    private T [] elements;
    private int size=0;
    public DynamicArray(){
       elements =(T[]) new Object[1];
    }
    public DynamicArray(int val){
        elements =(T[]) new Object[val];
    }
    public int size(){return size;}
    public boolean isEmpty(){return size==0;}
    public void add(T element){
        if(size== elements.length){
             grow();
        }
        elements[size++]=element;
    }
    private void grow(){
        elements = Arrays.copyOf(elements, elements.length*2);
    }
    public T get(int index){
        if(index<0 && index>=size){
            throw  new IndexOutOfBoundsException();
        }
        return  elements[index];
    }
}
