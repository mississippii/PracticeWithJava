# Data Structures - Foundation of Efficient Programming

## What are Data Structures?

Data structures are specialized formats for **organizing, processing, storing, and retrieving data** efficiently. They define the relationship between data elements and the operations that can be performed on them.

**"The right data structure makes your algorithm efficient; the wrong one makes it impossible."**

---

## Why Data Structures?

1. **Efficiency** - Optimize time and space complexity
2. **Organization** - Structure data logically for easy access
3. **Reusability** - Build once, use everywhere
4. **Problem Solving** - Every algorithm needs the right data structure
5. **Interview Success** - Core requirement for technical interviews

---

## Data Structure Categories

### 1. Linear Data Structures
Data elements arranged sequentially, accessed in a linear order.

- **Array** - Fixed-size, contiguous memory, O(1) access
- **Linked List** - Dynamic size, node-based, O(n) access
- **Stack** - LIFO (Last In First Out) - think plates
- **Queue** - FIFO (First In First Out) - think lines

### 2. Non-Linear Data Structures
Data elements arranged hierarchically or in a network.

- **Tree** - Hierarchical structure with root and children
- **Binary Search Tree (BST)** - Ordered tree for fast search
- **Heap** - Complete binary tree for priority operations
- **Trie** - Tree for storing strings with common prefixes
- **Graph** - Network of vertices and edges

### 3. Hash-Based Structures
Use hashing for O(1) average-case operations.

- **Hash Table** - Key-value pairs with hash function
- **Hash Set** - Unique elements, no duplicates

---

## Time Complexity Comparison

| Data Structure | Access | Search | Insert | Delete | Space |
|---------------|--------|--------|--------|--------|-------|
| Array         | O(1)   | O(n)   | O(n)   | O(n)   | O(n)  |
| Linked List   | O(n)   | O(n)   | O(1)*  | O(1)*  | O(n)  |
| Stack         | O(n)   | O(n)   | O(1)   | O(1)   | O(n)  |
| Queue         | O(n)   | O(n)   | O(1)   | O(1)   | O(n)  |
| BST (balanced)| O(log n)| O(log n)| O(log n)| O(log n)| O(n) |
| Hash Table    | N/A    | O(1)†  | O(1)†  | O(1)†  | O(n)  |
| Trie          | O(k)   | O(k)   | O(k)   | O(k)   | O(n×k)|

*\* When you have reference to the node*
*† Average case, worst case O(n)*
*k = length of string*

---

## How to Choose the Right Data Structure?

### Ask These Questions:

1. **What operations do I need?**
   - Frequent access by index → Array
   - Frequent insertions/deletions → Linked List
   - LIFO operations → Stack
   - FIFO operations → Queue
   - Fast search with order → BST
   - Fast lookup by key → Hash Table

2. **What are my constraints?**
   - Memory limited → Choose space-efficient structure
   - Speed critical → Choose time-efficient structure
   - Both matter → Find the balance

3. **What's my access pattern?**
   - Sequential access → Array, Linked List
   - Random access → Array, Hash Table
   - Hierarchical → Tree
   - Network relationships → Graph

---

## Custom Implementations in This Package

### 1. **LinkedList** (`linkedlist/`)
Dynamic, node-based linear structure perfect for frequent insertions/deletions.
- Singly Linked List
- Doubly Linked List
- Operations: insert, delete, search, reverse

### 2. **Binary Search Tree** (`bst/`)
Ordered binary tree for efficient searching, insertion, and deletion.
- BST implementation with recursive and iterative approaches
- Operations: insert, delete, search, traversals (inorder, preorder, postorder)
- Height, min, max, validation

### 3. **Stack** (`stack/`)
LIFO structure implemented with array and linked list.
- Push, pop, peek operations
- Applications: expression evaluation, backtracking, undo operations

### 4. **Queue** (`queue/`)
FIFO structure implemented with array and linked list.
- Enqueue, dequeue, peek operations
- Circular queue implementation
- Applications: BFS, scheduling, buffering

---

## Learning Path

### Beginner
1. Start with **Array** fundamentals (already in Java)
2. Learn **LinkedList** - understand node-based structures
3. Master **Stack** and **Queue** - simple but powerful

### Intermediate
4. Dive into **Binary Search Tree** - introduce recursion and trees
5. Explore **Hash Table** - understand hashing concepts
6. Learn **Heap** - priority operations

### Advanced
7. Master **Trie** - string processing
8. Understand **Graph** - complex relationships
9. Study **Balanced Trees** (AVL, Red-Black)

---

## Real-World Analogies

### Stack = Stack of Plates
- Add plate on top (push)
- Remove plate from top (pop)
- Can only access the top plate
- Last plate added is first to be removed

### Queue = Line at Coffee Shop
- People join at the back (enqueue)
- First person in line gets served first (dequeue)
- Fair ordering - FIFO principle

### Linked List = Treasure Hunt
- Each clue (node) points to the next location
- Must follow the chain sequentially
- Can easily add/remove clues anywhere

### Binary Search Tree = Family Tree
- One ancestor (root)
- Each person has at most two children
- Organized by rules (left smaller, right larger)

### Hash Table = Library Index Card System
- Book title (key) tells you exact location (value)
- No need to search every shelf
- Direct access in constant time

---

## Interview Importance

Data structures are the **#1 topic** in technical interviews:

- **30-40%** of coding questions directly test data structure knowledge
- **60-70%** require choosing the right data structure to optimize
- **FAANG companies** heavily focus on trees, graphs, and heaps
- **Understanding trade-offs** (time vs space) is critical

### Common Interview Questions:
- "Implement a LRU Cache" → Doubly Linked List + Hash Map
- "Find K closest points" → Heap
- "Word search in dictionary" → Trie
- "Detect cycle in graph" → Graph traversal with visited set
- "Design rate limiter" → Sliding window + Queue

---

## Key Principles

✓ **Choose based on requirements, not familiarity**
✓ **Understand time-space trade-offs**
✓ **Master the basics before advanced structures**
✓ **Practice implementation from scratch**
✓ **Know when to use built-in vs custom implementation**
✓ **Think about edge cases (empty, single element, duplicates)**

---

## Implementation Philosophy

In this package, we implement data structures from scratch to:

1. **Understand internals** - How it really works under the hood
2. **Learn algorithms** - Operations and their complexity
3. **Interview preparation** - Common whiteboard questions
4. **Appreciate built-ins** - Why Java's collections are well-designed

**Note:** In production, always use Java's built-in collections (ArrayList, LinkedList, HashMap, etc.) unless you have a specific reason to implement custom structures.

---

## Java Built-in Collections vs Custom

| Custom Implementation | Java Built-in |
|-----------------------|---------------|
| **Learning** - Understand how it works | **Production** - Battle-tested, optimized |
| **Interviews** - Show you can code it | **Real Projects** - Reliable and maintained |
| **Flexibility** - Customize to your needs | **Standard** - Everyone understands |
| **Practice** - Improve problem-solving | **Performance** - Highly optimized |

---

## Next Steps

1. Start with **LinkedList** - Master node-based thinking
2. Move to **Stack/Queue** - Understand access patterns
3. Deep dive into **BST** - Learn tree recursion
4. Explore each subdirectory for detailed implementation

Each subdirectory contains:
- Comprehensive README with theory
- Full implementation with comments
- Example usage and test cases
- Interview questions and solutions

Happy Learning!
