package datastructures.bst;

class Node{
    int data;
    Node left;
    Node right;

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
public class BinarySearchTree {

    private static Node insertIntoBst(Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insertIntoBst(node.left, data);
        } else {
            node.right = insertIntoBst(node.right, data);
        }

        return node;
    }


    public static void main(String[] args){
        Node Root = null;
        int[] ara ={50,30,90,100,75,45,20,15};
        for (int i=0;i<ara.length;i++){
            Root = insertIntoBst(Root, ara[i]);
        }
        Node head = Root;
    }
}
