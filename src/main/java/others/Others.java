package others;

import others.generic.DynamicArray;

import java.util.Stack;

public class Others {
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
    }
}
