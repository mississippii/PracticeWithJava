package datastructures.linkedlist;

/**
 * Singly Linked List Implementation
 *
 * A linear data structure where each element (node) contains:
 * 1. Data - The actual value
 * 2. Next - Reference to the next node
 *
 * Time Complexity:
 * - Insert at beginning: O(1)
 * - Insert at end: O(1) with tail pointer
 * - Insert at position: O(n)
 * - Delete from beginning: O(1)
 * - Delete from end: O(n)
 * - Search: O(n)
 * - Access by index: O(n)
 *
 * Space Complexity: O(n)
 */

/**
 * Node class - represents each element in the linked list
 * Made package-private so it's accessible within the package
 */
class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

/**
 * Generic Singly Linked List
 * Supports any data type through generics
 */
class SinglyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Constructor - Initialize empty list
     */
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // ==================== INSERTION OPERATIONS ====================

    /**
     * Insert element at the beginning of the list
     * Time Complexity: O(1)
     */
    public void insertAtBeginning(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            // Empty list - new node is both head and tail
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Insert element at the end of the list
     * Time Complexity: O(1) with tail pointer
     */
    public void insertAtEnd(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            // Empty list - new node is both head and tail
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Insert element at specific position (0-indexed)
     * Time Complexity: O(n)
     */
    public void insertAt(T data, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        if (position == 0) {
            insertAtBeginning(data);
            return;
        }

        if (position == size) {
            insertAtEnd(data);
            return;
        }

        Node<T> newNode = new Node<>(data);
        Node<T> current = head;

        // Traverse to position - 1
        for (int i = 0; i < position - 1; i++) {
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    // ==================== DELETION OPERATIONS ====================

    /**
     * Delete element from the beginning
     * Time Complexity: O(1)
     */
    public T deleteFromBeginning() {
        if (isEmpty()) {
            throw new RuntimeException("Cannot delete from empty list");
        }

        T data = head.data;
        head = head.next;
        size--;

        if (head == null) {
            tail = null;  // List is now empty
        }

        return data;
    }

    /**
     * Delete element from the end
     * Time Complexity: O(n)
     */
    public T deleteFromEnd() {
        if (isEmpty()) {
            throw new RuntimeException("Cannot delete from empty list");
        }

        T data;

        if (head == tail) {
            // Only one node
            data = head.data;
            head = null;
            tail = null;
        } else {
            // Traverse to second-last node
            Node<T> current = head;
            while (current.next != tail) {
                current = current.next;
            }

            data = tail.data;
            current.next = null;
            tail = current;
        }

        size--;
        return data;
    }

    /**
     * Delete first occurrence of a value
     * Time Complexity: O(n)
     */
    public boolean delete(T value) {
        if (isEmpty()) {
            return false;
        }

        // Check if head needs to be deleted
        if (head.data.equals(value)) {
            deleteFromBeginning();
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(value)) {
                // Found the node to delete
                if (current.next == tail) {
                    tail = current;  // Update tail if deleting last node
                }
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;  // Value not found
    }

    // ==================== SEARCH OPERATIONS ====================

    /**
     * Search for a value in the list
     * Time Complexity: O(n)
     */
    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Get element at specific position
     * Time Complexity: O(n)
     */
    public T get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        Node<T> current = head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }

        return current.data;
    }

    /**
     * Find index of first occurrence of value
     * Time Complexity: O(n)
     */
    public int indexOf(T value) {
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            if (current.data.equals(value)) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1;  // Not found
    }

    // ==================== UTILITY OPERATIONS ====================

    /**
     * Check if list is empty
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Get size of the list
     * Time Complexity: O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Clear the entire list
     * Time Complexity: O(1)
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Display the list
     * Time Complexity: O(n)
     */
    public void printList() {
        if (isEmpty()) {
            System.out.println("List is empty");
            return;
        }

        Node<T> current = head;
        System.out.print("HEAD → ");
        while (current != null) {
            System.out.print(current.data + " → ");
            current = current.next;
        }
        System.out.println("NULL");
    }

    /**
     * Get string representation of the list
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;

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

    // ==================== ADVANCED OPERATIONS ====================

    /**
     * Reverse the linked list
     * Time Complexity: O(n)
     */
    public void reverse() {
        if (head == null || head.next == null) {
            return;  // Empty or single node
        }

        Node<T> prev = null;
        Node<T> current = head;
        Node<T> next = null;
        tail = head;  // Old head becomes new tail

        while (current != null) {
            next = current.next;    // Save next
            current.next = prev;    // Reverse the link
            prev = current;         // Move prev forward
            current = next;         // Move current forward
        }

        head = prev;  // Update head to new first node
    }

    /**
     * Find middle element using slow-fast pointer technique
     * Time Complexity: O(n)
     */
    public T findMiddle() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }

        Node<T> slow = head;
        Node<T> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow.data;
    }

    /**
     * Get head node (for external access if needed)
     */
    public Node<T> getHead() {
        return this.head;
    }
}

/**
 * Demo class to test LinkedList implementation
 */
public class LinkList {
    public static void main(String[] args) {
        System.out.println("=== LINKED LIST DEMO ===\n");

        // Demo 1: Integer List
        System.out.println("1. INTEGER LIST OPERATIONS");
        System.out.println("-".repeat(50));

        SinglyLinkedList<Integer> intList = new SinglyLinkedList<>();

        // Insert elements at end
        System.out.println("Inserting 0-9 at end:");
        for (int i = 0; i < 10; i++) {
            intList.insertAtEnd(i);
        }
        intList.printList();
        System.out.println("Size: " + intList.size());
        System.out.println("toString(): " + intList);

        // Insert at beginning
        System.out.println("\nInserting -1 at beginning:");
        intList.insertAtBeginning(-1);
        intList.printList();

        // Insert at position
        System.out.println("\nInserting 100 at position 5:");
        intList.insertAt(100, 5);
        intList.printList();

        // Search operations
        System.out.println("\nSEARCH OPERATIONS:");
        System.out.println("Contains 5: " + intList.contains(5));
        System.out.println("Contains 999: " + intList.contains(999));
        System.out.println("Index of 100: " + intList.indexOf(100));
        System.out.println("Element at position 3: " + intList.get(3));

        // Delete operations
        System.out.println("\nDELETE OPERATIONS:");
        System.out.println("Deleted from beginning: " + intList.deleteFromBeginning());
        intList.printList();

        System.out.println("Deleted from end: " + intList.deleteFromEnd());
        intList.printList();

        System.out.println("Delete value 100: " + intList.delete(100));
        intList.printList();

        // Advanced operations
        System.out.println("\nADVANCED OPERATIONS:");
        System.out.println("Middle element: " + intList.findMiddle());

        System.out.println("\nReversing list:");
        intList.reverse();
        intList.printList();

        // Demo 2: String List
        System.out.println("\n2. STRING LIST (GENERIC TYPE)");
        System.out.println("-".repeat(50));

        SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
        stringList.insertAtEnd("Java");
        stringList.insertAtEnd("Python");
        stringList.insertAtEnd("C++");
        stringList.insertAtEnd("JavaScript");
        stringList.insertAtEnd("Go");

        stringList.printList();
        System.out.println("Size: " + stringList.size());
        System.out.println("Contains 'Python': " + stringList.contains("Python"));
        System.out.println("Middle element: " + stringList.findMiddle());

        // Demo 3: Edge Cases
        System.out.println("\n3. EDGE CASES");
        System.out.println("-".repeat(50));

        SinglyLinkedList<Integer> emptyList = new SinglyLinkedList<>();
        System.out.println("Empty list:");
        emptyList.printList();
        System.out.println("Is empty: " + emptyList.isEmpty());

        SinglyLinkedList<Integer> singleList = new SinglyLinkedList<>();
        singleList.insertAtEnd(42);
        System.out.println("\nSingle element list:");
        singleList.printList();
        System.out.println("Middle: " + singleList.findMiddle());

        System.out.println("\n=== DEMO COMPLETED ===");
    }
}
