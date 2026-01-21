package others;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

public class Others {

    private static int factorial(int x){
        if(x==1 || x==0)return 1;
        return x*factorial(--x);
    }

    private static void twoVariables(int a, int b){
        System.out.println(a+" "+b);
    }
    public static void main(String[] args) throws IOException {
        int x =10;
        twoVariables(x++,x++);
    }
}
