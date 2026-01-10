# Linked List - Dynamic Linear Data Structure

## What is a Linked List?

A **Linked List** is a linear data structure where elements are stored in **nodes**. Each node contains:
1. **Data** - The actual value
2. **Reference (pointer)** - Link to the next node

Unlike arrays with contiguous memory, linked list nodes are scattered in memory and connected through references.

**"A chain of nodes where each node knows the location of the next one."**

---

## Why Linked List?

### Problems with Arrays:
- ❌ Fixed size (or expensive resizing)
- ❌ Insertion/deletion in middle requires shifting elements - O(n)
- ❌ Memory waste if array is not fully utilized
- ❌ Contiguous memory requirement

### Linked List Solutions:
- ✅ Dynamic size - grow/shrink as needed
- ✅ Efficient insertion/deletion - O(1) if you have reference
- ✅ No memory waste
- ✅ No contiguous memory needed

---

## When to Use Linked List?

### Use Linked List When:
✓ Frequent insertions/deletions (especially at beginning)
✓ Size is unpredictable
✓ No random access needed
✓ Implementing stacks, queues, or adjacency lists

### Use Array When:
✓ Frequent random access by index - O(1)
✓ Size is known and fixed
✓ Memory locality matters (cache performance)
✓ Binary search needed

---

## Types of Linked Lists

### 1. Singly Linked List
Each node has reference to **next** node only.

```
[Data|Next] → [Data|Next] → [Data|Next] → null
```

**Pros:**
- Simple implementation
- Less memory (one reference per node)

**Cons:**
- Can only traverse forward
- Can't access previous node directly

### 2. Doubly Linked List
Each node has references to **both next and previous** nodes.

```
null ← [Prev|Data|Next] ↔ [Prev|Data|Next] ↔ [Prev|Data|Next] → null
```

**Pros:**
- Bidirectional traversal
- Easier deletion (no need for previous node reference)

**Cons:**
- More memory (two references per node)
- Slightly more complex implementation

### 3. Circular Linked List
Last node points back to the first node (forms a circle).

```
[Data|Next] → [Data|Next] → [Data|Next] → ⤴
       ↑___________________________|
```

**Use Case:** Round-robin scheduling, circular buffer

---

## Structure

### Node Structure (Singly)
```java
class Node {
    int data;        // The value
    Node next;       // Reference to next node

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}
```

### Node Structure (Doubly)
```java
class Node {
    int data;        // The value
    Node prev;       // Reference to previous node
    Node next;       // Reference to next node

    Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}
```

---

## Core Operations

### 1. Insertion

#### Insert at Beginning - O(1)
```java
public void insertAtBeginning(int data) {
    Node newNode = new Node(data);
    newNode.next = head;    // Point to current head
    head = newNode;         // Update head
    size++;
}
```

#### Insert at End - O(n) or O(1) with tail
```java
public void insertAtEnd(int data) {
    Node newNode = new Node(data);

    if (head == null) {
        head = newNode;
        return;
    }

    Node current = head;
    while (current.next != null) {
        current = current.next;
    }
    current.next = newNode;
    size++;
}
```

#### Insert at Position - O(n)
```java
public void insertAt(int data, int position) {
    if (position < 0 || position > size) {
        throw new IndexOutOfBoundsException();
    }

    if (position == 0) {
        insertAtBeginning(data);
        return;
    }

    Node newNode = new Node(data);
    Node current = head;

    // Traverse to position - 1
    for (int i = 0; i < position - 1; i++) {
        current = current.next;
    }

    newNode.next = current.next;
    current.next = newNode;
    size++;
}
```

### 2. Deletion

#### Delete from Beginning - O(1)
```java
public int deleteFromBeginning() {
    if (head == null) {
        throw new NoSuchElementException("List is empty");
    }

    int data = head.data;
    head = head.next;  // Move head to next node
    size--;
    return data;
}
```

#### Delete from End - O(n)
```java
public int deleteFromEnd() {
    if (head == null) {
        throw new NoSuchElementException("List is empty");
    }

    if (head.next == null) {  // Only one node
        int data = head.data;
        head = null;
        size--;
        return data;
    }

    Node current = head;
    while (current.next.next != null) {
        current = current.next;
    }

    int data = current.next.data;
    current.next = null;
    size--;
    return data;
}
```

#### Delete by Value - O(n)
```java
public boolean delete(int value) {
    if (head == null) return false;

    // If head needs to be deleted
    if (head.data == value) {
        head = head.next;
        size--;
        return true;
    }

    Node current = head;
    while (current.next != null) {
        if (current.next.data == value) {
            current.next = current.next.next;
            size--;
            return true;
        }
        current = current.next;
    }

    return false;  // Value not found
}
```

### 3. Search - O(n)
```java
public boolean search(int value) {
    Node current = head;
    while (current != null) {
        if (current.data == value) {
            return true;
        }
        current = current.next;
    }
    return false;
}
```

### 4. Traversal - O(n)
```java
public void display() {
    if (head == null) {
        System.out.println("List is empty");
        return;
    }

    Node current = head;
    while (current != null) {
        System.out.print(current.data + " → ");
        current = current.next;
    }
    System.out.println("null");
}
```

---

## Time Complexity Summary

| Operation | Singly LL | Doubly LL | Array |
|-----------|-----------|-----------|-------|
| Insert at Beginning | O(1) | O(1) | O(n) |
| Insert at End | O(n) or O(1)* | O(1)* | O(1)† |
| Insert at Position | O(n) | O(n) | O(n) |
| Delete from Beginning | O(1) | O(1) | O(n) |
| Delete from End | O(n) | O(1)* | O(1) |
| Delete by Value | O(n) | O(n) | O(n) |
| Search | O(n) | O(n) | O(n) |
| Access by Index | O(n) | O(n) | O(1) |

*\* With tail pointer*
*† Amortized, may need resizing*

---

## Space Complexity

- **Array:** O(n) - Just the data
- **Singly Linked List:** O(n) - Data + 1 reference per node
- **Doubly Linked List:** O(n) - Data + 2 references per node

**Memory Overhead:** Linked lists use extra memory for pointers (~50-100% overhead)

---

## Files in This Folder

1. **SinglyLinkedList.java** - Complete singly linked list implementation
2. **DoublyLinkedList.java** - Complete doubly linked list implementation
3. **LinkedListDemo.java** - Examples and usage demonstrations

---

## Advanced Operations

### 1. Reverse a Linked List - O(n)
```java
public void reverse() {
    Node prev = null;
    Node current = head;
    Node next = null;

    while (current != null) {
        next = current.next;    // Save next
        current.next = prev;    // Reverse link
        prev = current;         // Move prev
        current = next;         // Move current
    }

    head = prev;  // Update head
}
```

### 2. Find Middle Element - O(n)
```java
public int findMiddle() {
    Node slow = head;
    Node fast = head;

    // Fast moves 2x, slow moves 1x
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    return slow.data;  // Slow is at middle
}
```

### 3. Detect Cycle (Floyd's Algorithm) - O(n)
```java
public boolean hasCycle() {
    Node slow = head;
    Node fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;

        if (slow == fast) {
            return true;  // Cycle detected
        }
    }

    return false;  // No cycle
}
```

### 4. Remove Duplicates (Sorted List) - O(n)
```java
public void removeDuplicates() {
    Node current = head;

    while (current != null && current.next != null) {
        if (current.data == current.next.data) {
            current.next = current.next.next;
        } else {
            current = current.next;
        }
    }
}
```

---

## Common Interview Questions

### Easy:
1. Implement a singly linked list with insert, delete, search
2. Find the length of a linked list
3. Print linked list in reverse
4. Remove duplicates from sorted linked list

### Medium:
5. Detect cycle in linked list (Floyd's algorithm)
6. Find middle element of linked list
7. Reverse a linked list (iterative and recursive)
8. Merge two sorted linked lists
9. Check if linked list is palindrome
10. Remove Nth node from end

### Hard:
11. Clone a linked list with random pointers
12. Reverse nodes in k-groups
13. Find intersection point of two linked lists
14. Flatten a multilevel doubly linked list
15. LRU Cache implementation (Doubly LL + HashMap)

---

## Real-World Applications

### 1. Music Playlist
- Next/previous song navigation
- Doubly linked list for bidirectional control

### 2. Browser History
- Forward/backward buttons
- Doubly linked list implementation

### 3. Undo/Redo Functionality
- Text editors, Photoshop
- Each action is a node

### 4. Image Viewer
- Next/previous image
- Circular linked list for continuous loop

### 5. Hash Table Chaining
- Collision resolution
- Each bucket is a linked list

---

## Real-World Analogy

### Treasure Hunt Game
Imagine a treasure hunt where each clue leads to the next location:

- **Node** = A location with a clue
- **Data** = The treasure at this location
- **Next pointer** = Direction to next location
- **Head** = Starting point

**Journey:**
1. Start at head (first clue)
2. Follow the pointer (go to next location)
3. Continue until next = null (treasure found!)

**Adding a location:**
- Insert new location between existing ones
- Update pointers (directions)
- No need to rearrange all locations!

**This is exactly how a linked list works!**

---

## Array vs Linked List Trade-offs

### Choose Array If:
- Need fast random access: `array[index]` → O(1)
- Size is mostly fixed
- Need cache-friendly sequential access
- Memory efficiency matters (no pointer overhead)

### Choose Linked List If:
- Frequent insertions/deletions at beginning/middle
- Size varies drastically
- Don't need random access
- Want to avoid expensive array resizing

---

## Key Points

✓ **Dynamic size** - Grows and shrinks efficiently
✓ **Efficient insertion/deletion** - O(1) with reference
✓ **No shifting required** - Unlike arrays
✓ **Sequential access only** - Can't jump to index
✓ **Extra memory** - Pointers add overhead
✓ **No cache locality** - Nodes scattered in memory

---

## Best Practices

1. **Always check for null** - Before accessing node.next
2. **Handle edge cases** - Empty list, single node, inserting at boundaries
3. **Update size** - Keep track of list size for O(1) size()
4. **Use tail pointer** - For O(1) insertion at end
5. **Consider doubly** - If you need bidirectional traversal
6. **Free memory** - In languages with manual memory management

---

## Common Mistakes to Avoid

❌ Losing reference to head
❌ Forgetting to update size
❌ Not handling empty list
❌ Creating cycles accidentally
❌ Null pointer exceptions
❌ Off-by-one errors in position-based operations

---

## Practice Problems

Start with our implementations and try these:
1. Implement all basic operations from scratch
2. Reverse a linked list (both ways)
3. Detect and remove cycle
4. Merge K sorted linked lists
5. Add two numbers represented as linked lists

Master linked lists, and you'll master node-based thinking!
