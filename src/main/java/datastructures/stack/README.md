# Stack - LIFO Data Structure

## What is a Stack?

A **Stack** is a linear data structure that follows the **LIFO (Last In, First Out)** principle. Elements are added and removed from the same end, called the **top** of the stack.

```
TOP → [5]  ← Last element added, first to be removed
      [4]
      [3]
      [2]
      [1]  ← First element added, last to be removed
```

**"Like a stack of plates - you can only add or remove from the top!"**

---

## Why Stack?

### Problems Without Stack:
- ❌ No natural way to reverse order
- ❌ Difficult to track most recent items
- ❌ Complex backtracking operations
- ❌ Hard to implement undo functionality

### Stack Solutions:
- ✅ **Perfect for reversal** - Natural LIFO order
- ✅ **Track recent items** - Last added is first accessed
- ✅ **Easy backtracking** - Pop to go back
- ✅ **Undo/Redo operations** - Store previous states
- ✅ **Function call tracking** - Call stack in programming

---

## When to Use Stack?

### Use Stack When:
✓ Need LIFO access pattern
✓ Function call management (recursion)
✓ Undo/Redo functionality
✓ Expression evaluation and parsing
✓ Backtracking algorithms (DFS, maze solving)
✓ Bracket matching and syntax checking
✓ Browser history (back button)

### Don't Use Stack When:
✗ Need FIFO (First In First Out) → Use Queue
✗ Need random access by index → Use Array
✗ Need to search elements frequently → Use Hash Table
✗ Need sorted order → Use BST or Heap

---

## Core Operations

### 1. Push - Add element to top
**Time Complexity:** O(1)

```java
public void push(int value) {
    // Add element to top
}
```

### 2. Pop - Remove and return top element
**Time Complexity:** O(1)

```java
public int pop() {
    // Remove and return top element
    // Throw exception if stack is empty
}
```

### 3. Peek/Top - View top element without removing
**Time Complexity:** O(1)

```java
public int peek() {
    // Return top element without removing
    // Throw exception if stack is empty
}
```

### 4. isEmpty - Check if stack is empty
**Time Complexity:** O(1)

```java
public boolean isEmpty() {
    return size == 0;
}
```

### 5. Size - Get number of elements
**Time Complexity:** O(1)

```java
public int size() {
    return size;
}
```

---

## Implementation Approaches

### 1. Array-Based Stack

**Structure:**
```
Array:  [1] [2] [3] [4] [5] [ ] [ ] [ ]
                         ↑
                        top
```

**Pros:**
- ✅ Simple implementation
- ✅ Cache-friendly (contiguous memory)
- ✅ O(1) operations

**Cons:**
- ❌ Fixed size (or expensive resizing)
- ❌ Memory waste if not full
- ❌ Stack overflow if full

**Implementation:**
```java
class ArrayStack {
    private int[] array;
    private int top;
    private int capacity;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.array = new int[capacity];
        this.top = -1;  // Empty stack
    }

    public void push(int value) {
        if (top == capacity - 1) {
            throw new StackOverflowError("Stack is full");
        }
        array[++top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return array[top--];
    }

    public int peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return array[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}
```

### 2. Linked List-Based Stack

**Structure:**
```
TOP → [5|•] → [4|•] → [3|•] → [2|•] → [1|null]
```

**Pros:**
- ✅ Dynamic size
- ✅ No overflow (until memory full)
- ✅ Efficient for push/pop

**Cons:**
- ❌ Extra memory for pointers
- ❌ Not cache-friendly
- ❌ Slightly slower than array

**Implementation:**
```java
class LinkedListStack {
    private class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node top;
    private int size;

    public void push(int value) {
        Node newNode = new Node(value);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        int value = top.data;
        top = top.next;
        size--;
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
```

---

## Time & Space Complexity

| Operation | Array Stack | Linked List Stack |
|-----------|-------------|-------------------|
| Push      | O(1)*       | O(1)              |
| Pop       | O(1)        | O(1)              |
| Peek      | O(1)        | O(1)              |
| isEmpty   | O(1)        | O(1)              |
| Size      | O(1)        | O(1)              |
| Space     | O(n)        | O(n) + pointers   |

*\* Amortized O(1) if resizing is needed*

---

## Real-World Applications

### 1. Function Call Stack
Every programming language uses a stack for function calls:

```java
void functionA() {
    functionB();  // Push functionA's state
}

void functionB() {
    functionC();  // Push functionB's state
}

void functionC() {
    // Do work
    return;       // Pop functionC, return to functionB
}
```

**Call Stack:**
```
functionC() ← TOP (currently executing)
functionB()
functionA()
main()
```

### 2. Undo/Redo in Editors
```java
// Text editor undo
Stack<String> undoStack = new Stack<>();

// User types: "Hello"
undoStack.push("H");
undoStack.push("He");
undoStack.push("Hel");
undoStack.push("Hell");
undoStack.push("Hello");

// User presses undo
String prev = undoStack.pop();  // Back to "Hell"
```

### 3. Browser History (Back Button)
```java
Stack<String> history = new Stack<>();

history.push("google.com");
history.push("stackoverflow.com");
history.push("github.com");

// User clicks back
String previous = history.pop();  // Back to stackoverflow.com
```

### 4. Expression Evaluation

**Infix to Postfix:**
```
Infix:    (3 + 4) × 5
Postfix:  3 4 + 5 ×

Using stack to convert and evaluate!
```

**Evaluate Postfix:**
```java
// "3 4 + 5 ×"
Stack<Integer> stack = new Stack<>();
stack.push(3);
stack.push(4);
int b = stack.pop();  // 4
int a = stack.pop();  // 3
stack.push(a + b);    // Push 7
stack.push(5);
int x = stack.pop();  // 5
int y = stack.pop();  // 7
stack.push(y * x);    // Push 35
// Result: 35
```

### 5. Balanced Parentheses Check
```java
boolean isBalanced(String expr) {
    Stack<Character> stack = new Stack<>();

    for (char ch : expr.toCharArray()) {
        if (ch == '(' || ch == '{' || ch == '[') {
            stack.push(ch);
        } else if (ch == ')' || ch == '}' || ch == ']') {
            if (stack.isEmpty()) return false;

            char top = stack.pop();
            if (!isMatchingPair(top, ch)) return false;
        }
    }

    return stack.isEmpty();
}
```

### 6. Depth-First Search (DFS)
```java
void dfs(Graph graph, int start) {
    Stack<Integer> stack = new Stack<>();
    boolean[] visited = new boolean[graph.size()];

    stack.push(start);

    while (!stack.isEmpty()) {
        int node = stack.pop();

        if (!visited[node]) {
            System.out.print(node + " ");
            visited[node] = true;

            for (int neighbor : graph.getNeighbors(node)) {
                if (!visited[neighbor]) {
                    stack.push(neighbor);
                }
            }
        }
    }
}
```

---

## Advanced Stack Problems

### 1. Min Stack
Design a stack that supports push, pop, and **getMin()** in O(1).

**Solution:** Use two stacks - one for values, one for minimums.

```java
class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;

    public void push(int value) {
        stack.push(value);
        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
        }
    }

    public int pop() {
        int value = stack.pop();
        if (value == minStack.peek()) {
            minStack.pop();
        }
        return value;
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

### 2. Valid Parentheses
Check if brackets are balanced: `"({[]})"` → valid, `"({[}])"` → invalid

### 3. Next Greater Element
For each element, find the next greater element to its right.

```java
int[] nextGreaterElement(int[] arr) {
    int[] result = new int[arr.length];
    Stack<Integer> stack = new Stack<>();

    for (int i = arr.length - 1; i >= 0; i--) {
        while (!stack.isEmpty() && stack.peek() <= arr[i]) {
            stack.pop();
        }
        result[i] = stack.isEmpty() ? -1 : stack.peek();
        stack.push(arr[i]);
    }

    return result;
}
```

### 4. Largest Rectangle in Histogram
Find largest rectangular area in a histogram.

### 5. Evaluate Reverse Polish Notation
Evaluate postfix expressions.

---

## Common Interview Questions

### Easy:
1. Implement stack using array
2. Implement stack using linked list
3. Check balanced parentheses
4. Reverse a string using stack
5. Implement two stacks in one array

### Medium:
6. Min stack (O(1) getMin)
7. Evaluate postfix expression
8. Infix to postfix conversion
9. Next greater element
10. Valid parentheses with multiple types `(){}[]`
11. Sort a stack
12. Implement queue using stacks

### Hard:
13. Largest rectangle in histogram
14. Maximal rectangle in binary matrix
15. Trapping rainwater
16. Longest valid parentheses
17. Basic calculator

---

## Real-World Analogy

### Stack of Plates in a Cafeteria
Imagine a spring-loaded plate dispenser in a cafeteria:

```
    [Plate 5] ← TOP - Most recently added
    [Plate 4]
    [Plate 3]
    [Plate 2]
    [Plate 1] ← First plate added
    =========
     Spring
```

**Operations:**
- **Push (Add plate):** Place a new plate on top
- **Pop (Remove plate):** Take the top plate
- **Peek:** Look at the top plate without removing
- **You can ONLY access the top plate!**

**This is exactly how a stack works!**

---

## Stack vs Other Structures

| Feature | Stack | Queue | Array | Linked List |
|---------|-------|-------|-------|-------------|
| Access Pattern | LIFO | FIFO | Random | Sequential |
| Push/Add | O(1) | O(1) | O(1)* | O(1) |
| Pop/Remove | O(1) | O(1) | O(1)* | O(1) |
| Peek/Access | O(1) | O(1) | O(1) | O(n) |
| Search | O(n) | O(n) | O(n) | O(n) |
| Use Case | Backtracking | Scheduling | Random access | Dynamic size |

*\* At end; O(n) at beginning/middle*

---

## Files in This Folder

1. **ArrayStack.java** - Stack implementation using array
2. **LinkedListStack.java** - Stack implementation using linked list
3. **StackDemo.java** - Examples and usage demonstrations
4. **MinStack.java** - Stack with O(1) getMin() operation

---

## Key Points

✓ **LIFO principle** - Last In, First Out
✓ **O(1) operations** - Push, pop, peek all constant time
✓ **Two implementations** - Array-based and linked list-based
✓ **Function calls** - Programming languages use stack internally
✓ **Backtracking** - Natural fit for undo, DFS, recursion
✓ **Expression evaluation** - Infix, postfix, prefix conversion
✓ **Limited access** - Can only access top element

---

## Best Practices

1. **Check empty before pop/peek** - Avoid EmptyStackException
2. **Use generics** - Stack<T> for type safety
3. **Consider capacity** - For array-based, choose appropriate size
4. **Clear when done** - Release memory if stack is large
5. **Use built-in** - Java's Stack or Deque in production
6. **Prefer Deque** - More versatile than Stack class

---

## Common Mistakes to Avoid

❌ Not checking if stack is empty before pop/peek
❌ Using wrong data structure (stack when queue is needed)
❌ Forgetting to update size counter
❌ Array overflow in fixed-size implementation
❌ Not handling edge cases (empty stack, single element)
❌ Memory leaks (not clearing popped nodes in linked list)

---

## Java Built-in Stack

Java provides `Stack<E>` class in `java.util` package:

```java
Stack<Integer> stack = new Stack<>();

stack.push(10);
stack.push(20);
stack.push(30);

int top = stack.peek();  // 30
int popped = stack.pop();  // 30

boolean empty = stack.isEmpty();
int size = stack.size();

int position = stack.search(10);  // 2 (1-indexed from top)
```

**Note:** Java recommends using `Deque<E>` instead of `Stack<E>`:

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);
int top = stack.pop();
```

**Why Deque?**
- More complete and consistent set of operations
- Better performance
- Not synchronized (unlike Stack)
- More flexible (can be used as stack or queue)

---

## Practice Problems

Master stack by implementing:
1. All basic operations from scratch
2. Balanced parentheses checker
3. Infix to postfix converter
4. Evaluate postfix expression
5. Next greater element
6. Min stack
7. Sort a stack using recursion
8. Reverse a stack
9. Implement queue using two stacks
10. Check for duplicate parentheses

**Remember:** Master the stack, and you'll understand recursion better!
