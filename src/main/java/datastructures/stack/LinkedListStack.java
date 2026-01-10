package datastructures.stack;

import java.util.EmptyStackException;

/**
 * Linked List-Based Stack Implementation
 *
 * A stack implemented using a singly linked list.
 * Follows LIFO (Last In, First Out) principle.
 *
 * Time Complexity:
 * - Push: O(1)
 * - Pop: O(1)
 * - Peek: O(1)
 * - isEmpty: O(1)
 * - Size: O(1)
 *
 * Space Complexity: O(n) where n is number of elements
 *
 * Advantages over Array-based:
 * - Dynamic size (no resizing needed)
 * - No capacity limit
 * - No wasted space
 *
 * Disadvantages:
 * - Extra memory for node pointers
 * - Not cache-friendly
 */
public class LinkedListStack<T> {

    /**
     * Node class represents each element in the stack
     */
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;   // Top of the stack
    private int size;   // Number of elements

    /**
     * Constructor - Initialize empty stack
     */
    public LinkedListStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Push an element onto the stack
     * Time Complexity: O(1)
     */
    public void push(T value) {
        Node newNode = new Node(value);
        newNode.next = top;
        top = newNode;
        size++;
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

        T value = top.data;
        top = top.next;
        size--;
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
        return top.data;
    }

    /**
     * Check if stack is empty
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Get number of elements in stack
     * Time Complexity: O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Clear all elements from stack
     * Time Complexity: O(1)
     */
    public void clear() {
        top = null;
        size = 0;
    }

    /**
     * Search for an element (returns position from top, 1-indexed)
     * Time Complexity: O(n)
     *
     * @return position from top (1-indexed), or -1 if not found
     */
    public int search(T value) {
        Node current = top;
        int position = 1;

        while (current != null) {
            if (current.data.equals(value)) {
                return position;
            }
            current = current.next;
            position++;
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
     * Display stack contents (for debugging)
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }

        System.out.print("Stack (top to bottom): ");
        Node current = top;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" → ");
            }
            current = current.next;
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
        Node current = top;

        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * Reverse the stack
     * Time Complexity: O(n)
     * Space Complexity: O(n) for recursion stack
     */
    public void reverse() {
        if (isEmpty() || size == 1) {
            return;
        }
        reverseRecursive();
    }

    private void reverseRecursive() {
        if (isEmpty()) {
            return;
        }

        T temp = pop();
        reverseRecursive();
        insertAtBottom(temp);
    }

    /**
     * Insert element at the bottom of stack
     * Time Complexity: O(n)
     * Space Complexity: O(n) for recursion stack
     */
    private void insertAtBottom(T value) {
        if (isEmpty()) {
            push(value);
            return;
        }

        T temp = pop();
        insertAtBottom(value);
        push(temp);
    }

    /**
     * Sort the stack (ascending order, smallest on top)
     * Time Complexity: O(n²)
     * Space Complexity: O(n) for recursion stack
     */
    public void sort() {
        if (!isEmpty()) {
            sortRecursive();
        }
    }

    private void sortRecursive() {
        if (isEmpty()) {
            return;
        }

        T temp = pop();
        sortRecursive();
        sortedInsert(temp);
    }

    /**
     * Insert element in sorted order
     */
    private void sortedInsert(T value) {
        if (isEmpty() || compare(value, peek()) > 0) {
            push(value);
            return;
        }

        T temp = pop();
        sortedInsert(value);
        push(temp);
    }

    /**
     * Compare two values (assumes Comparable)
     */
    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (a instanceof Comparable) {
            return ((Comparable<T>) a).compareTo(b);
        }
        return 0;
    }

    /**
     * Get the minimum element in stack
     * Time Complexity: O(n)
     */
    public T getMin() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        T min = top.data;
        Node current = top.next;

        while (current != null) {
            if (compare(current.data, min) < 0) {
                min = current.data;
            }
            current = current.next;
        }

        return min;
    }

    /**
     * Get the maximum element in stack
     * Time Complexity: O(n)
     */
    public T getMax() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        T max = top.data;
        Node current = top.next;

        while (current != null) {
            if (compare(current.data, max) > 0) {
                max = current.data;
            }
            current = current.next;
        }

        return max;
    }
}
