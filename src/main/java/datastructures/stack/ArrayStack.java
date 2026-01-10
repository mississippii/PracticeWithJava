package datastructures.stack;

import java.util.EmptyStackException;

/**
 * Array-Based Stack Implementation
 *
 * A stack implemented using a fixed-size array with dynamic resizing.
 * Follows LIFO (Last In, First Out) principle.
 *
 * Time Complexity:
 * - Push: O(1) amortized (O(n) when resizing)
 * - Pop: O(1)
 * - Peek: O(1)
 * - isEmpty: O(1)
 * - Size: O(1)
 *
 * Space Complexity: O(capacity)
 */
public class ArrayStack<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int RESIZE_FACTOR = 2;

    private T[] array;
    private int top;        // Index of top element
    private int capacity;

    /**
     * Constructor with default capacity
     */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.capacity = DEFAULT_CAPACITY;
        this.array = (T[]) new Object[capacity];
        this.top = -1;  // Empty stack
    }

    /**
     * Constructor with custom capacity
     */
    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.array = (T[]) new Object[capacity];
        this.top = -1;
    }

    /**
     * Push an element onto the stack
     * Time Complexity: O(1) amortized
     */
    public void push(T value) {
        // Resize if full
        if (top == capacity - 1) {
            resize();
        }

        array[++top] = value;
    }

    /**
     * Pop and return the top element
     * Time Complexity: O(1)
     *
     * @throws EmptyStackException if stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        T value = array[top];
        array[top] = null;  // Help garbage collection
        top--;

        // Shrink if needed (optional optimization)
        if (top > 0 && top < capacity / 4) {
            shrink();
        }

        return value;
    }

    /**
     * Return the top element without removing it
     * Time Complexity: O(1)
     *
     * @throws EmptyStackException if stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return array[top];
    }

    /**
     * Check if stack is empty
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Get number of elements in stack
     * Time Complexity: O(1)
     */
    public int size() {
        return top + 1;
    }

    /**
     * Get current capacity
     * Time Complexity: O(1)
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Clear all elements from stack
     * Time Complexity: O(n)
     */
    public void clear() {
        for (int i = 0; i <= top; i++) {
            array[i] = null;  // Help garbage collection
        }
        top = -1;
    }

    /**
     * Search for an element (returns position from top, 1-indexed)
     * Time Complexity: O(n)
     *
     * @return position from top (1-indexed), or -1 if not found
     */
    public int search(T value) {
        for (int i = top; i >= 0; i--) {
            if (array[i].equals(value)) {
                return top - i + 1;  // Position from top (1-indexed)
            }
        }
        return -1;  // Not found
    }

    /**
     * Check if stack contains an element
     * Time Complexity: O(n)
     */
    public boolean contains(T value) {
        return search(value) != -1;
    }

    /**
     * Resize array when full (double the capacity)
     * Time Complexity: O(n)
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= RESIZE_FACTOR;
        T[] newArray = (T[]) new Object[capacity];

        // Copy elements
        System.arraycopy(array, 0, newArray, 0, top + 1);

        array = newArray;
    }

    /**
     * Shrink array when too empty (halve the capacity)
     * Time Complexity: O(n)
     */
    @SuppressWarnings("unchecked")
    private void shrink() {
        capacity /= RESIZE_FACTOR;
        T[] newArray = (T[]) new Object[capacity];

        // Copy elements
        System.arraycopy(array, 0, newArray, 0, top + 1);

        array = newArray;
    }

    /**
     * Display stack contents (for debugging)
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }

        System.out.print("Stack (top to bottom): ");
        for (int i = top; i >= 0; i--) {
            System.out.print(array[i]);
            if (i > 0) {
                System.out.print(" → ");
            }
        }
        System.out.println();
    }

    /**
     * String representation of stack
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = top; i >= 0; i--) {
            sb.append(array[i]);
            if (i > 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Check if stack is full (useful for fixed-size stacks)
     * Time Complexity: O(1)
     */
    public boolean isFull() {
        return top == capacity - 1;
    }
}
