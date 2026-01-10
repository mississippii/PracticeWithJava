package datastructures.stack;

/**
 * Demonstration of Stack operations
 * Shows practical usage and examples
 */
public class StackDemo {

    public static void main(String[] args) {
        System.out.println("=== STACK DEMO ===\n");

        // Demo 1: Array-Based Stack
        System.out.println("1. ARRAY-BASED STACK");
        System.out.println("-".repeat(50));
        arrayStackDemo();
        System.out.println();

        // Demo 2: Linked List-Based Stack
        System.out.println("2. LINKED LIST-BASED STACK");
        System.out.println("-".repeat(50));
        linkedListStackDemo();
        System.out.println();

        // Demo 3: Practical Applications
        System.out.println("3. PRACTICAL APPLICATIONS");
        System.out.println("-".repeat(50));
        practicalApplications();
        System.out.println();

        System.out.println("=== DEMO COMPLETED ===");
    }

    private static void arrayStackDemo() {
        ArrayStack<Integer> stack = new ArrayStack<>(5);

        // Push operations
        System.out.println("Pushing: 10, 20, 30, 40, 50");
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);

        stack.display();
        System.out.println("Size: " + stack.size());
        System.out.println("Capacity: " + stack.capacity());

        // Peek operation
        System.out.println("Peek (top element): " + stack.peek());

        // Pop operations
        System.out.println("\nPopping 2 elements:");
        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());
        stack.display();

        // Search operation
        System.out.println("Search 20 (position from top): " + stack.search(20));
        System.out.println("Contains 100: " + stack.contains(100));

        // Dynamic resizing
        System.out.println("\nTesting dynamic resizing:");
        System.out.println("Pushing 60, 70, 80 (will trigger resize)");
        stack.push(60);
        stack.push(70);
        stack.push(80);
        stack.display();
        System.out.println("New capacity: " + stack.capacity());

        // toString
        System.out.println("toString(): " + stack);
    }

    private static void linkedListStackDemo() {
        LinkedListStack<String> stack = new LinkedListStack<>();

        // Push operations
        System.out.println("Pushing: Java, Python, C++, JavaScript");
        stack.push("Java");
        stack.push("Python");
        stack.push("C++");
        stack.push("JavaScript");

        stack.display();
        System.out.println("Size: " + stack.size());

        // Peek and pop
        System.out.println("Peek: " + stack.peek());
        System.out.println("Pop: " + stack.pop());
        stack.display();

        // Search
        System.out.println("Search 'Java': " + stack.search("Java"));

        // Reverse
        System.out.println("\nReversing stack:");
        stack.reverse();
        stack.display();

        // Sort
        LinkedListStack<Integer> numStack = new LinkedListStack<>();
        numStack.push(50);
        numStack.push(20);
        numStack.push(80);
        numStack.push(10);
        numStack.push(40);

        System.out.println("\nBefore sort:");
        numStack.display();
        numStack.sort();
        System.out.println("After sort (ascending):");
        numStack.display();

        // Min/Max
        System.out.println("Min: " + numStack.getMin());
        System.out.println("Max: " + numStack.getMax());
    }

    private static void practicalApplications() {
        // Application 1: Balanced Parentheses
        System.out.println("Application 1: Balanced Parentheses Check");
        System.out.println("Expression: ({[]})");
        System.out.println("Is balanced: " + isBalanced("({[]})"));
        System.out.println("Expression: ({[}])");
        System.out.println("Is balanced: " + isBalanced("({[}])"));

        // Application 2: Reverse a String
        System.out.println("\nApplication 2: Reverse String");
        String original = "Hello, World!";
        String reversed = reverseString(original);
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);

        // Application 3: Evaluate Postfix Expression
        System.out.println("\nApplication 3: Evaluate Postfix Expression");
        String postfix = "5 3 + 2 *";  // (5 + 3) * 2 = 16
        System.out.println("Postfix: " + postfix);
        System.out.println("Result: " + evaluatePostfix(postfix));

        // Application 4: Next Greater Element
        System.out.println("\nApplication 4: Next Greater Element");
        int[] arr = {4, 5, 2, 10, 8};
        int[] result = nextGreaterElement(arr);
        System.out.print("Array: ");
        printArray(arr);
        System.out.print("Next Greater: ");
        printArray(result);

        // Application 5: Undo/Redo Simulation
        System.out.println("\nApplication 5: Text Editor Undo/Redo");
        textEditorDemo();
    }

    /**
     * Check if parentheses are balanced
     */
    private static boolean isBalanced(String expr) {
        ArrayStack<Character> stack = new ArrayStack<>();

        for (char ch : expr.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();
                if ((ch == ')' && top != '(') ||
                    (ch == '}' && top != '{') ||
                    (ch == ']' && top != '[')) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    /**
     * Reverse a string using stack
     */
    private static String reverseString(String str) {
        ArrayStack<Character> stack = new ArrayStack<>();

        // Push all characters
        for (char ch : str.toCharArray()) {
            stack.push(ch);
        }

        // Pop all characters
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

        return reversed.toString();
    }

    /**
     * Evaluate postfix expression
     */
    private static int evaluatePostfix(String expr) {
        ArrayStack<Integer> stack = new ArrayStack<>();
        String[] tokens = expr.split(" ");

        for (String token : tokens) {
            if (isOperator(token)) {
                int b = stack.pop();
                int a = stack.pop();
                int result = applyOperator(token, a, b);
                stack.push(result);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") ||
               token.equals("*") || token.equals("/");
    }

    private static int applyOperator(String op, int a, int b) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default: return 0;
        }
    }

    /**
     * Find next greater element for each element
     */
    private static int[] nextGreaterElement(int[] arr) {
        int[] result = new int[arr.length];
        ArrayStack<Integer> stack = new ArrayStack<>();

        // Traverse from right to left
        for (int i = arr.length - 1; i >= 0; i--) {
            // Pop elements smaller than current
            while (!stack.isEmpty() && stack.peek() <= arr[i]) {
                stack.pop();
            }

            // If stack is empty, no greater element
            result[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current element
            stack.push(arr[i]);
        }

        return result;
    }

    /**
     * Text editor with undo/redo functionality
     */
    private static void textEditorDemo() {
        ArrayStack<String> undoStack = new ArrayStack<>();
        ArrayStack<String> redoStack = new ArrayStack<>();

        String text = "";

        // Type some text
        System.out.println("Typing: H -> He -> Hel -> Hell -> Hello");
        text = "H";
        undoStack.push(text);
        System.out.println("Text: " + text);

        text = "He";
        undoStack.push(text);
        System.out.println("Text: " + text);

        text = "Hel";
        undoStack.push(text);
        System.out.println("Text: " + text);

        text = "Hell";
        undoStack.push(text);
        System.out.println("Text: " + text);

        text = "Hello";
        undoStack.push(text);
        System.out.println("Text: " + text);

        // Undo
        System.out.println("\nUndo (Ctrl+Z):");
        if (!undoStack.isEmpty()) {
            String current = undoStack.pop();
            redoStack.push(current);
            text = undoStack.isEmpty() ? "" : undoStack.peek();
            System.out.println("Text: " + text);
        }

        // Undo again
        System.out.println("Undo again:");
        if (!undoStack.isEmpty()) {
            String current = undoStack.pop();
            redoStack.push(current);
            text = undoStack.isEmpty() ? "" : undoStack.peek();
            System.out.println("Text: " + text);
        }

        // Redo
        System.out.println("\nRedo (Ctrl+Y):");
        if (!redoStack.isEmpty()) {
            String restore = redoStack.pop();
            undoStack.push(restore);
            text = restore;
            System.out.println("Text: " + text);
        }
    }

    private static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
