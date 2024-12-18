package others;

import others.generic.DynamicArray;

public class Others {
    public static void main(String[] args){
        DynamicArray<String> str = new DynamicArray<>();
        str.add("Tanveer");
        str.add("Afreen");
        str.add("Amber");
        System.out.println(str.size());
        for(int i=0; i<str.size(); i++){
            System.out.println(str.get(i));
        }
    }
}
