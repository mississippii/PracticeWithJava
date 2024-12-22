package others;

import others.generic.DynamicArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Others {
<<<<<<< HEAD
    public static void main(String[] args){
        String str = "Hello World";
        String str2 = new String("Hello World");
        if (str2==str){
            System.out.println("same");
        }
    }
    public static int[] finalPrices(int[] prices) {
        int n = prices.length;
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < n; i++){
            if(stack.isEmpty() || prices[stack.peek()] < prices[i]){
                stack.push(i);
            }else{
                while(!stack.isEmpty() && prices[stack.peek()] >= prices[i]){
                    int id = stack.pop();
                    prices[id]-=prices[i];
                }
                stack.push(i);
            }
        }
        return prices;
=======
    public static void main(String[] args) throws IOException {
        Path path = Path.of("/home/veer/Documents/Java/PracticeWithJava/src/main/resources/hello.txt");
        System.out.println(path);
>>>>>>> 498d744 (fixed some issue)
    }
}
