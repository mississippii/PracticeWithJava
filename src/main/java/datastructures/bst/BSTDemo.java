package datastructures.bst;

/**
 * Demonstration of Binary Search Tree operations
 * Shows practical usage and examples
 */
public class BSTDemo {

    public static void main(String[] args) {
        System.out.println("=== BINARY SEARCH TREE DEMO ===\n");

        BinarySearchTree bst = new BinarySearchTree();

        // Demo 1: Insertion
        System.out.println("1. INSERTION OPERATIONS");
        System.out.println("-".repeat(50));

        int[] values = {50, 30, 70, 20, 40, 60, 80};
        System.out.print("Inserting values: ");
        for (int value : values) {
            System.out.print(value + " ");
            bst.insert(value);
        }
        System.out.println("\n\nTree structure:");
        bst.display();
        System.out.println("Size: " + bst.size());
        System.out.println();

        // Demo 2: Traversals
        System.out.println("2. TREE TRAVERSALS");
        System.out.println("-".repeat(50));
        bst.inorder();      // Should be sorted: 20 30 40 50 60 70 80
        bst.preorder();     // Root-Left-Right
        bst.postorder();    // Left-Right-Root
        bst.levelOrder();   // Level by level
        System.out.println();

        // Demo 3: Search Operations
        System.out.println("3. SEARCH OPERATIONS");
        System.out.println("-".repeat(50));
        System.out.println("Search 40: " + bst.search(40));
        System.out.println("Search 100: " + bst.search(100));
        System.out.println("Search 20 (iterative): " + bst.searchIterative(20));
        System.out.println();

        // Demo 4: Min/Max Operations
        System.out.println("4. MIN/MAX OPERATIONS");
        System.out.println("-".repeat(50));
        System.out.println("Minimum value: " + bst.findMin());
        System.out.println("Maximum value: " + bst.findMax());
        System.out.println();

        // Demo 5: Tree Properties
        System.out.println("5. TREE PROPERTIES");
        System.out.println("-".repeat(50));
        System.out.println("Height: " + bst.height());
        System.out.println("Total nodes: " + bst.countNodes());
        System.out.println("Leaf nodes: " + bst.countLeaves());
        System.out.println("Is valid BST: " + bst.isValidBST());
        System.out.println("Is balanced: " + bst.isBalanced());
        System.out.println();

        // Demo 6: Advanced Operations
        System.out.println("6. ADVANCED OPERATIONS");
        System.out.println("-".repeat(50));
        System.out.println("3rd smallest element: " + bst.kthSmallest(3));
        System.out.println("5th smallest element: " + bst.kthSmallest(5));
        System.out.println("LCA of 20 and 40: " + bst.lowestCommonAncestor(20, 40));
        System.out.println("LCA of 20 and 80: " + bst.lowestCommonAncestor(20, 80));
        System.out.println("Range sum [30, 70]: " + bst.rangeSum(30, 70));
        System.out.println();

        // Demo 7: Deletion Operations
        System.out.println("7. DELETION OPERATIONS");
        System.out.println("-".repeat(50));

        System.out.println("\nDeleting leaf node (20):");
        bst.delete(20);
        bst.display();
        bst.inorder();

        System.out.println("\nDeleting node with one child (30):");
        bst.delete(30);
        bst.display();
        bst.inorder();

        System.out.println("\nDeleting node with two children (50):");
        bst.delete(50);
        bst.display();
        bst.inorder();
        System.out.println();

        // Demo 8: Building Balanced vs Unbalanced Tree
        System.out.println("8. BALANCED VS UNBALANCED TREE");
        System.out.println("-".repeat(50));

        // Balanced tree
        BinarySearchTree balancedBST = new BinarySearchTree();
        int[] balancedValues = {50, 25, 75, 12, 37, 62, 87};
        for (int val : balancedValues) {
            balancedBST.insert(val);
        }
        System.out.println("Balanced Tree:");
        balancedBST.display();
        System.out.println("Height: " + balancedBST.height());
        System.out.println("Is balanced: " + balancedBST.isBalanced());

        // Unbalanced (skewed) tree
        BinarySearchTree unbalancedBST = new BinarySearchTree();
        for (int i = 1; i <= 7; i++) {
            unbalancedBST.insert(i * 10);
        }
        System.out.println("\nUnbalanced (Skewed) Tree:");
        unbalancedBST.display();
        System.out.println("Height: " + unbalancedBST.height());
        System.out.println("Is balanced: " + unbalancedBST.isBalanced());
        System.out.println();

        // Demo 9: Edge Cases
        System.out.println("9. EDGE CASES");
        System.out.println("-".repeat(50));

        BinarySearchTree emptyBST = new BinarySearchTree();
        System.out.println("Empty tree:");
        System.out.println("Is empty: " + emptyBST.isEmpty());
        emptyBST.display();

        BinarySearchTree singleNodeBST = new BinarySearchTree();
        singleNodeBST.insert(42);
        System.out.println("\nSingle node tree:");
        singleNodeBST.display();
        System.out.println("Height: " + singleNodeBST.height());
        System.out.println("Is balanced: " + singleNodeBST.isBalanced());
        singleNodeBST.inorder();
        System.out.println();

        // Demo 10: Duplicate Handling
        System.out.println("10. DUPLICATE HANDLING");
        System.out.println("-".repeat(50));
        BinarySearchTree dupBST = new BinarySearchTree();
        dupBST.insert(50);
        dupBST.insert(30);
        dupBST.insert(50);  // Duplicate - will be ignored
        System.out.println("After inserting 50, 30, 50 (duplicate):");
        dupBST.display();
        System.out.println("Size: " + dupBST.size() + " (duplicate not added)");
        System.out.println();

        // Demo 11: Building BST from Sorted Array
        System.out.println("11. BST FROM SORTED ARRAY");
        System.out.println("-".repeat(50));
        BinarySearchTree sortedBST = new BinarySearchTree();
        int[] sortedArray = {10, 20, 30, 40, 50, 60, 70};

        // Inserting in sorted order creates skewed tree
        System.out.println("Inserting sorted values in order (creates skewed tree):");
        for (int val : sortedArray) {
            sortedBST.insert(val);
        }
        sortedBST.display();
        System.out.println("Height: " + sortedBST.height() + " (skewed!)");
        System.out.println("Is balanced: " + sortedBST.isBalanced());

        // Better approach: insert middle first (creates balanced tree)
        BinarySearchTree betterBST = new BinarySearchTree();
        insertBalanced(betterBST, sortedArray, 0, sortedArray.length - 1);
        System.out.println("\nInserting with divide-and-conquer (creates balanced tree):");
        betterBST.display();
        System.out.println("Height: " + betterBST.height() + " (balanced!)");
        System.out.println("Is balanced: " + betterBST.isBalanced());

        System.out.println("\n=== DEMO COMPLETED ===");
    }

    /**
     * Helper method to insert sorted array in balanced manner
     * Insert middle element first, then recursively do left and right
     */
    private static void insertBalanced(BinarySearchTree bst, int[] arr, int start, int end) {
        if (start > end) {
            return;
        }

        int mid = start + (end - start) / 2;
        bst.insert(arr[mid]);

        insertBalanced(bst, arr, start, mid - 1);
        insertBalanced(bst, arr, mid + 1, end);
    }
}
