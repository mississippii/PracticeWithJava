# Binary Search Tree (BST) - Ordered Tree Structure

## What is a Binary Search Tree?

A **Binary Search Tree** is a hierarchical data structure where each node has at most two children (left and right), following a specific ordering property:

**BST Property:**
- All values in the **left subtree** are **less than** the node's value
- All values in the **right subtree** are **greater than** the node's value
- Both left and right subtrees are also BSTs (recursive definition)

```
        50
       /  \
      30   70
     / \   / \
    20 40 60 80
```

**"A sorted binary tree that enables efficient search, insertion, and deletion."**

---

## Why Binary Search Tree?

### Problems with Linear Structures:
- ❌ **Array:** Fast access O(1) but slow insertion/deletion O(n)
- ❌ **Linked List:** Slow search O(n)
- ❌ **Sorted Array:** Fast search O(log n) with binary search but expensive insertion O(n)

### BST Solutions:
- ✅ **Fast search** - O(log n) average case
- ✅ **Fast insertion** - O(log n) average case
- ✅ **Fast deletion** - O(log n) average case
- ✅ **Ordered traversal** - Inorder gives sorted sequence
- ✅ **Dynamic size** - Grows and shrinks efficiently

---

## When to Use BST?

### Use BST When:
✓ Need fast search, insert, and delete operations
✓ Need to maintain sorted order
✓ Range queries (find all elements between X and Y)
✓ Finding min/max efficiently
✓ Predecessor/successor operations

### Don't Use BST When:
✗ Need O(1) access by index (use Array)
✗ Need guaranteed O(log n) worst case (use Balanced BST like AVL or Red-Black Tree)
✗ Frequent updates to unbalanced tree (degenerates to O(n))
✗ Need fast lookups without order (use Hash Table)

---

## BST Structure

### Node Structure
```java
class Node {
    int data;         // The value
    Node left;        // Left child (smaller values)
    Node right;       // Right child (larger values)

    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
```

### BST Example
```
           50
          /  \
        30    70
       / \    / \
      20 40  60  80
     /
    10

Left subtree of 50: {10, 20, 30, 40} - all < 50
Right subtree of 50: {60, 70, 80} - all > 50
```

---

## Core Operations

### 1. Search - O(log n) average, O(n) worst

```java
public boolean search(int value) {
    return searchRecursive(root, value);
}

private boolean searchRecursive(Node node, int value) {
    // Base case: empty tree or not found
    if (node == null) {
        return false;
    }

    // Found the value
    if (node.data == value) {
        return true;
    }

    // Value is smaller, search left subtree
    if (value < node.data) {
        return searchRecursive(node.left, value);
    }

    // Value is larger, search right subtree
    return searchRecursive(node.right, value);
}
```

**Iterative approach:**
```java
public boolean searchIterative(int value) {
    Node current = root;

    while (current != null) {
        if (value == current.data) {
            return true;
        } else if (value < current.data) {
            current = current.left;
        } else {
            current = current.right;
        }
    }

    return false;
}
```

### 2. Insertion - O(log n) average, O(n) worst

```java
public void insert(int value) {
    root = insertRecursive(root, value);
}

private Node insertRecursive(Node node, int value) {
    // Base case: found the position
    if (node == null) {
        return new Node(value);
    }

    // Duplicate values not allowed (can be modified)
    if (value == node.data) {
        return node;
    }

    // Insert in left subtree
    if (value < node.data) {
        node.left = insertRecursive(node.left, value);
    }
    // Insert in right subtree
    else {
        node.right = insertRecursive(node.right, value);
    }

    return node;
}
```

### 3. Deletion - O(log n) average, O(n) worst

**Three Cases:**

**Case 1: Node is a leaf (no children)**
```
Delete 20:
    50              50
   /  \            /  \
  20   70   →     30   70
   \
   30
```
Simply remove the node.

**Case 2: Node has one child**
```
Delete 30:
    50              50
   /  \            /  \
  30   70   →     40   70
    \
    40
```
Replace node with its child.

**Case 3: Node has two children**
```
Delete 50:
    50              60
   /  \            /  \
  30   70   →     30   70
      / \              \
     60  80            80
```
Replace node with:
- **Inorder successor** (smallest in right subtree), OR
- **Inorder predecessor** (largest in left subtree)

```java
public void delete(int value) {
    root = deleteRecursive(root, value);
}

private Node deleteRecursive(Node node, int value) {
    if (node == null) {
        return null;
    }

    // Find the node to delete
    if (value < node.data) {
        node.left = deleteRecursive(node.left, value);
    } else if (value > node.data) {
        node.right = deleteRecursive(node.right, value);
    } else {
        // Found the node to delete

        // Case 1: Leaf node or one child
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }

        // Case 2: Two children
        // Find inorder successor (smallest in right subtree)
        node.data = findMin(node.right);

        // Delete the inorder successor
        node.right = deleteRecursive(node.right, node.data);
    }

    return node;
}

private int findMin(Node node) {
    while (node.left != null) {
        node = node.left;
    }
    return node.data;
}
```

---

## Tree Traversals

### 1. Inorder (Left-Root-Right) - Gives Sorted Order
```java
public void inorder() {
    inorderRecursive(root);
    System.out.println();
}

private void inorderRecursive(Node node) {
    if (node != null) {
        inorderRecursive(node.left);
        System.out.print(node.data + " ");
        inorderRecursive(node.right);
    }
}
```

**Example:**
```
    50
   /  \
  30   70
 / \
20 40

Inorder: 20 30 40 50 70  (Sorted!)
```

### 2. Preorder (Root-Left-Right)
```java
private void preorderRecursive(Node node) {
    if (node != null) {
        System.out.print(node.data + " ");
        preorderRecursive(node.left);
        preorderRecursive(node.right);
    }
}
```

**Example:** `50 30 20 40 70`
**Use case:** Creating a copy of the tree

### 3. Postorder (Left-Right-Root)
```java
private void postorderRecursive(Node node) {
    if (node != null) {
        postorderRecursive(node.left);
        postorderRecursive(node.right);
        System.out.print(node.data + " ");
    }
}
```

**Example:** `20 40 30 70 50`
**Use case:** Deleting the tree (delete children before parent)

### 4. Level Order (Breadth-First)
```java
public void levelOrder() {
    if (root == null) return;

    Queue<Node> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
        Node current = queue.poll();
        System.out.print(current.data + " ");

        if (current.left != null) queue.add(current.left);
        if (current.right != null) queue.add(current.right);
    }
}
```

**Example:** `50 30 70 20 40`
**Use case:** Level-by-level processing

---

## Time Complexity Analysis

| Operation | Average Case | Worst Case | Best Case |
|-----------|--------------|------------|-----------|
| Search    | O(log n)     | O(n)*      | O(1)      |
| Insert    | O(log n)     | O(n)*      | O(1)      |
| Delete    | O(log n)     | O(n)*      | O(1)      |
| Find Min  | O(log n)     | O(n)*      | O(1)      |
| Find Max  | O(log n)     | O(n)*      | O(1)      |
| Traversal | O(n)         | O(n)       | O(n)      |

*\* Worst case O(n) happens when tree becomes **unbalanced** (like a linked list)*

### Balanced vs Unbalanced BST

**Balanced BST** (height = log n):
```
      50
     /  \
    30   70
   / \   / \
  20 40 60 80
```
**Operations:** O(log n)

**Unbalanced BST** (height = n):
```
10
 \
 20
  \
  30
   \
   40  (Looks like a linked list!)
```
**Operations:** O(n) - Bad!

**Solution:** Use self-balancing trees (AVL, Red-Black Tree)

---

## Space Complexity

- **Storage:** O(n) - n nodes
- **Recursion stack:** O(h) where h = height
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

## Additional Operations

### Find Minimum
```java
public int findMin() {
    if (root == null) {
        throw new NoSuchElementException("Tree is empty");
    }
    Node current = root;
    while (current.left != null) {
        current = current.left;
    }
    return current.data;
}
```

### Find Maximum
```java
public int findMax() {
    if (root == null) {
        throw new NoSuchElementException("Tree is empty");
    }
    Node current = root;
    while (current.right != null) {
        current = current.right;
    }
    return current.data;
}
```

### Height of Tree
```java
public int height() {
    return heightRecursive(root);
}

private int heightRecursive(Node node) {
    if (node == null) {
        return -1;  // Height of empty tree is -1
    }
    return 1 + Math.max(heightRecursive(node.left),
                        heightRecursive(node.right));
}
```

### Count Nodes
```java
public int countNodes() {
    return countRecursive(root);
}

private int countRecursive(Node node) {
    if (node == null) {
        return 0;
    }
    return 1 + countRecursive(node.left) + countRecursive(node.right);
}
```

### Validate BST
```java
public boolean isValidBST() {
    return isValidBSTRecursive(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
}

private boolean isValidBSTRecursive(Node node, int min, int max) {
    if (node == null) {
        return true;
    }

    if (node.data <= min || node.data >= max) {
        return false;
    }

    return isValidBSTRecursive(node.left, min, node.data) &&
           isValidBSTRecursive(node.right, node.data, max);
}
```

---

## Common Interview Questions

### Easy:
1. Insert a node in BST
2. Search for a value in BST
3. Find min/max element
4. Count nodes in BST
5. Calculate height of BST

### Medium:
6. Delete a node from BST
7. Validate if a tree is a BST
8. Find kth smallest element
9. Lowest common ancestor (LCA)
10. Convert sorted array to balanced BST
11. Inorder successor/predecessor
12. Check if two BSTs are identical

### Hard:
13. Serialize and deserialize BST
14. Recover BST (two nodes swapped)
15. Largest BST in binary tree
16. Construct BST from preorder/postorder
17. Merge two BSTs
18. Range sum query
19. Trim BST to range

---

## Real-World Applications

### 1. Databases - Indexing
- B-trees (variation of BST) used for database indexing
- Fast search, insertion, and deletion of records

### 2. File Systems
- Directory structure (folders and files)
- Hierarchical organization

### 3. Autocomplete/Dictionary
- Storing words for fast lookup
- Prefix-based searching with tries (special BST)

### 4. Expression Trees
- Compiler design
- Evaluating arithmetic expressions

### 5. Priority Queues
- Heap (special complete binary tree)
- Task scheduling

---

## Real-World Analogy

### Family Tree
Imagine a family hierarchy:

```
        Grandfather
        /         \
    Father       Uncle
    /    \       /   \
  You  Sister  Cousin1 Cousin2
```

**BST Properties:**
- Each person has at most 2 children (binary)
- If we assign ages: younger children on left, older on right
- To find someone:
  - Compare with current person
  - Go left if younger, right if older
  - Repeat until found

**This is exactly how BST search works!**

---

## BST vs Other Data Structures

| Data Structure | Search | Insert | Delete | Ordered |
|---------------|--------|--------|--------|---------|
| **Array** (unsorted) | O(n) | O(1)† | O(n) | ❌ |
| **Array** (sorted) | O(log n)‡ | O(n) | O(n) | ✅ |
| **Linked List** | O(n) | O(1)* | O(1)* | ❌ |
| **BST** (balanced) | O(log n) | O(log n) | O(log n) | ✅ |
| **Hash Table** | O(1)§ | O(1)§ | O(1)§ | ❌ |
| **AVL/Red-Black Tree** | O(log n) | O(log n) | O(log n) | ✅ |

*† At end, O(n) in middle*
*‡ With binary search*
*\* With reference to node*
*§ Average case*

---

## Key Points

✓ **BST property** - Left < Root < Right (recursively)
✓ **Inorder traversal** - Gives sorted sequence
✓ **Average O(log n)** - For search, insert, delete
✓ **Worst O(n)** - When tree is skewed (unbalanced)
✓ **Recursive algorithms** - Natural fit for tree operations
✓ **Balance matters** - Use AVL or Red-Black trees for guaranteed performance
✓ **No duplicates** - Standard BST doesn't allow (can be modified)

---

## Advantages vs Disadvantages

### Advantages:
✓ Fast search, insert, delete (if balanced)
✓ Maintains sorted order
✓ Dynamic size
✓ Efficient range queries
✓ Natural recursive structure

### Disadvantages:
✗ Can become unbalanced → O(n) operations
✗ No O(1) access by index
✗ More complex than array/linked list
✗ Extra memory for pointers
✗ Not cache-friendly (scattered in memory)

---

## Files in This Folder

1. **BinarySearchTree.java** - Complete BST implementation with all operations
2. **BSTDemo.java** - Examples and usage demonstrations
3. **AVLTree.java** *(future)* - Self-balancing BST

---

## Best Practices

1. **Handle edge cases** - Empty tree, single node, duplicates
2. **Use recursion** - Natural fit for tree operations
3. **Keep track of size** - For O(1) size query
4. **Consider balance** - Use self-balancing trees for production
5. **Iterative vs Recursive** - Iterative saves stack space
6. **Validate input** - Check for null, handle duplicates appropriately

---

## Common Mistakes to Avoid

❌ Not handling null nodes
❌ Forgetting base cases in recursion
❌ Not updating parent references during deletion
❌ Ignoring unbalanced trees in production
❌ Allowing duplicates without clear policy
❌ Stack overflow with deep recursion on skewed trees

---

## Practice Problems

Master BST by implementing:
1. All basic operations from scratch
2. All traversal methods
3. Find kth smallest/largest element
4. Lowest common ancestor
5. Validate BST
6. Convert sorted array to BST
7. Serialize and deserialize
8. Range sum query

**Remember:** Master BST, and you'll understand trees in general!
