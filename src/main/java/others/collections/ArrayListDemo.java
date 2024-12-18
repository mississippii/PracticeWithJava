package others.collections;


import java.util.*;


public class ArrayListDemo {
    public static void main(String[] args) {
        Vector<Integer>vec = new Vector<>();
        vec.add(1);
        vec.add(2);
        vec.add(3);
        vec.add(1,5);
        vec.forEach(System.out::println);
    }

    private static boolean isBalanced(String str) {
        Stack<Character> stack = new Stack<>();
        for (char c : str.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if ((top == '(' && c != ')') || (top == '{' && c != '}') || (top == '[' && c != ']')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
