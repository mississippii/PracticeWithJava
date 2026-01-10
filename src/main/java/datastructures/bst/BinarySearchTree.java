package datastructures.bst;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Binary Search Tree (BST) Implementation
 *
 * A BST is a binary tree where:
 * - Left subtree contains nodes with values less than the node
 * - Right subtree contains nodes with values greater than the node
 * - Both subtrees are also BSTs (recursive definition)
 *
 * Time Complexity (Average case - balanced tree):
 * - Search: O(log n)
 * - Insert: O(log n)
 * - Delete: O(log n)
 * - Find Min/Max: O(log n)
 *
 * Time Complexity (Worst case - skewed tree):
 * - All operations: O(n)
 *
 * Space Complexity: O(n) for n nodes, O(h) for recursion stack (h = height)
 */
public class BinarySearchTree {

    /**
     * Node class represents each element in the BST
     */
    private class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    /**
     * Constructor - Initialize empty BST
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    // ==================== INSERTION ====================

    /**
     * Insert a value into the BST
     * Time Complexity: O(log n) average, O(n) worst
     */
    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node node, int value) {
        // Base case: found the position to insert
        if (node == null) {
            size++;
            return new Node(value);
        }

        // Duplicate handling: ignore duplicates (can be modified to allow)
        if (value == node.data) {
            return node;
        }

        // Insert in left subtree if value is smaller
        if (value < node.data) {
            node.left = insertRecursive(node.left, value);
        }
        // Insert in right subtree if value is larger
        else {
            node.right = insertRecursive(node.right, value);
        }

        return node;
    }

    /**
     * Iterative insert (alternative approach)
     * Time Complexity: O(log n) average, O(n) worst
     */
    public void insertIterative(int value) {
        Node newNode = new Node(value);

        if (root == null) {
            root = newNode;
            size++;
            return;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            parent = current;

            if (value < current.data) {
                current = current.left;
            } else if (value > current.data) {
                current = current.right;
            } else {
                return;  // Duplicate, don't insert
            }
        }

        if (value < parent.data) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        size++;
    }

    // ==================== SEARCH ====================

    /**
     * Search for a value in the BST
     * Time Complexity: O(log n) average, O(n) worst
     */
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

        // Search left subtree if value is smaller
        if (value < node.data) {
            return searchRecursive(node.left, value);
        }

        // Search right subtree if value is larger
        return searchRecursive(node.right, value);
    }

    /**
     * Iterative search (alternative approach)
     * Time Complexity: O(log n) average, O(n) worst
     */
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

    // ==================== DELETION ====================

    /**
     * Delete a value from the BST
     * Time Complexity: O(log n) average, O(n) worst
     *
     * Three cases:
     * 1. Node is a leaf (no children) - simply remove
     * 2. Node has one child - replace with child
     * 3. Node has two children - replace with inorder successor
     */
    public boolean delete(int value) {
        if (!search(value)) {
            return false;
        }
        root = deleteRecursive(root, value);
        return true;
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
            size--;

            // Case 1: Leaf node or one child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Case 2: Two children
            // Find inorder successor (smallest in right subtree)
            int successorValue = findMinValue(node.right);
            node.data = successorValue;

            // Delete the inorder successor
            node.right = deleteRecursive(node.right, successorValue);
        }

        return node;
    }

    /**
     * Find minimum value in subtree (helper for deletion)
     */
    private int findMinValue(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    // ==================== MIN/MAX OPERATIONS ====================

    /**
     * Find minimum value in the BST
     * Time Complexity: O(log n) average, O(n) worst
     */
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

    /**
     * Find maximum value in the BST
     * Time Complexity: O(log n) average, O(n) worst
     */
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

    // ==================== TRAVERSALS ====================

    /**
     * Inorder Traversal (Left-Root-Right)
     * Result: Sorted order
     * Time Complexity: O(n)
     */
    public void inorder() {
        System.out.print("Inorder: ");
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

    /**
     * Preorder Traversal (Root-Left-Right)
     * Use case: Creating a copy of the tree
     * Time Complexity: O(n)
     */
    public void preorder() {
        System.out.print("Preorder: ");
        preorderRecursive(root);
        System.out.println();
    }

    private void preorderRecursive(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preorderRecursive(node.left);
            preorderRecursive(node.right);
        }
    }

    /**
     * Postorder Traversal (Left-Right-Root)
     * Use case: Deleting the tree
     * Time Complexity: O(n)
     */
    public void postorder() {
        System.out.print("Postorder: ");
        postorderRecursive(root);
        System.out.println();
    }

    private void postorderRecursive(Node node) {
        if (node != null) {
            postorderRecursive(node.left);
            postorderRecursive(node.right);
            System.out.print(node.data + " ");
        }
    }

    /**
     * Level Order Traversal (Breadth-First)
     * Time Complexity: O(n)
     * Space Complexity: O(w) where w is max width
     */
    public void levelOrder() {
        if (root == null) {
            System.out.println("Level Order: (empty tree)");
            return;
        }

        System.out.print("Level Order: ");
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.data + " ");

            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
        System.out.println();
    }

    // ==================== TREE PROPERTIES ====================

    /**
     * Get height of the tree
     * Height = longest path from root to leaf
     * Time Complexity: O(n)
     */
    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(Node node) {
        if (node == null) {
            return -1;  // Height of empty tree is -1
        }
        return 1 + Math.max(heightRecursive(node.left), heightRecursive(node.right));
    }

    /**
     * Get number of nodes
     * Time Complexity: O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Check if tree is empty
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Count total nodes (recursive calculation)
     * Time Complexity: O(n)
     */
    public int countNodes() {
        return countNodesRecursive(root);
    }

    private int countNodesRecursive(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodesRecursive(node.left) + countNodesRecursive(node.right);
    }

    /**
     * Count leaf nodes
     * Time Complexity: O(n)
     */
    public int countLeaves() {
        return countLeavesRecursive(root);
    }

    private int countLeavesRecursive(Node node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;  // Leaf node
        }
        return countLeavesRecursive(node.left) + countLeavesRecursive(node.right);
    }

    // ==================== ADVANCED OPERATIONS ====================

    /**
     * Validate if the tree is a valid BST
     * Time Complexity: O(n)
     */
    public boolean isValidBST() {
        return isValidBSTRecursive(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValidBSTRecursive(Node node, int min, int max) {
        if (node == null) {
            return true;
        }

        // Check BST property: min < node.data < max
        if (node.data <= min || node.data >= max) {
            return false;
        }

        // Recursively validate left and right subtrees
        return isValidBSTRecursive(node.left, min, node.data) &&
               isValidBSTRecursive(node.right, node.data, max);
    }

    /**
     * Find kth smallest element (1-indexed)
     * Time Complexity: O(k) in average, O(n) in worst case
     */
    public int kthSmallest(int k) {
        if (k < 1 || k > size) {
            throw new IllegalArgumentException("Invalid k: " + k);
        }

        int[] count = {0};
        int[] result = {-1};
        kthSmallestHelper(root, k, count, result);
        return result[0];
    }

    private void kthSmallestHelper(Node node, int k, int[] count, int[] result) {
        if (node == null || result[0] != -1) {
            return;
        }

        // Inorder traversal
        kthSmallestHelper(node.left, k, count, result);

        count[0]++;
        if (count[0] == k) {
            result[0] = node.data;
            return;
        }

        kthSmallestHelper(node.right, k, count, result);
    }

    /**
     * Find lowest common ancestor (LCA) of two nodes
     * Time Complexity: O(log n) average, O(n) worst
     */
    public int lowestCommonAncestor(int value1, int value2) {
        if (!search(value1) || !search(value2)) {
            throw new IllegalArgumentException("One or both values not in tree");
        }

        Node lca = lcaRecursive(root, value1, value2);
        return lca.data;
    }

    private Node lcaRecursive(Node node, int value1, int value2) {
        if (node == null) {
            return null;
        }

        // Both values are in left subtree
        if (value1 < node.data && value2 < node.data) {
            return lcaRecursive(node.left, value1, value2);
        }

        // Both values are in right subtree
        if (value1 > node.data && value2 > node.data) {
            return lcaRecursive(node.right, value1, value2);
        }

        // Values are on different sides, or one equals node
        return node;
    }

    /**
     * Check if tree is balanced
     * A tree is balanced if height difference between left and right subtrees <= 1
     * Time Complexity: O(n)
     */
    public boolean isBalanced() {
        return isBalancedRecursive(root) != -1;
    }

    private int isBalancedRecursive(Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = isBalancedRecursive(node.left);
        if (leftHeight == -1) return -1;

        int rightHeight = isBalancedRecursive(node.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;  // Not balanced
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Get range sum (sum of all values in range [low, high])
     * Time Complexity: O(n) worst case, O(log n + k) average where k = result size
     */
    public int rangeSum(int low, int high) {
        return rangeSumRecursive(root, low, high);
    }

    private int rangeSumRecursive(Node node, int low, int high) {
        if (node == null) {
            return 0;
        }

        int sum = 0;

        // If current node is in range, include it
        if (node.data >= low && node.data <= high) {
            sum += node.data;
        }

        // Explore left subtree if there might be values in range
        if (node.data > low) {
            sum += rangeSumRecursive(node.left, low, high);
        }

        // Explore right subtree if there might be values in range
        if (node.data < high) {
            sum += rangeSumRecursive(node.right, low, high);
        }

        return sum;
    }

    /**
     * Clear the entire tree
     * Time Complexity: O(1)
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Visual display of tree structure
     */
    public void display() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        displayRecursive(root, "", true);
    }

    private void displayRecursive(Node node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data);

            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    displayRecursive(node.left, prefix + (isTail ? "    " : "│   "), node.right == null);
                }
                if (node.right != null) {
                    displayRecursive(node.right, prefix + (isTail ? "    " : "│   "), true);
                }
            }
        }
    }
}
