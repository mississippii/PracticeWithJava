package others.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Calculator {
    public int add (int a,int b) {
        return a+b;
    }
    public int minimumOperations(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        travers(root,list);
        return 10;
    }
    private void travers(TreeNode root, List<Integer> nodeVal) {
        if (root == null) return;
        Queue<TreeNode>queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            nodeVal.add(node.val);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
    }
}
