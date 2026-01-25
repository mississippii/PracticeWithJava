package datastructures.linkedlist;

import java.util.HashSet;
import java.util.Set;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class SinglyLinkedList{
    public Node head;
    public Node tail;

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
    }
    public Node getHead() {
        return this.head;
    }
    public Node getTail() {
        return this.tail;
    }
    public void addNode(int data) {
        if(head == null) {
            head = new Node(data);
            tail = head;
        }else{
            tail.next = new Node(data);
            tail = tail.next;
        }
    }

    public Node detectCycle() {
        Node slow = this.head;
        Node fast = this.head;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast) {
                slow = this.head;

                while(slow!=fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow; // Start of cycle
            }
        }
        return null; // No cycle
    }
    public Node detectCycleWithSet(){
        Set<Node> set = new HashSet<>();
        Node current = this.head;
        while (current!=null){
            if(set.contains(current)){
                return current;
            }else{
                set.add(current);
                current = current.next;
            }
        }
        return null; // No cycle
    }

    public Node getMiddleNode() {
        Node slow = this.head;
        Node fast = this.head;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow; // Middle node
    }

    public void createCycleAt(int index) {
        int cnt=1;
        Node current = this.head;
        while(cnt<index && current!=null) {
            current = current.next;
            cnt++;
        }
        if(current != null) {
            this.tail.next = current;
        }
    }
}

public class LinkList {
    public static void main(String[] args) {
        SinglyLinkedList linkedList = new SinglyLinkedList();
        linkedList.addNode(10);
        linkedList.addNode(20);
        linkedList.addNode(30);
        linkedList.addNode(40);
        linkedList.addNode(50);
        linkedList.addNode(60);
        linkedList.addNode(70);
        linkedList.addNode(80);
        linkedList.addNode(90);
        linkedList.addNode(100);
        linkedList.createCycleAt(4); // Creating a cycle for testing;
        Node cycleNode = linkedList.detectCycleWithSet();
        if(cycleNode != null) {
            System.out.println("Cycle detected at node with data: " + cycleNode.data);
        } else {
            System.out.println("No cycle detected.");
        }
    }
}
