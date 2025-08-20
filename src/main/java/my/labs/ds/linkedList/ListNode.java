package my.labs.ds.linkedList;

// Use a one-way link with an integer value to simplify
public class ListNode {
    int value;
     ListNode next;


    public ListNode(){

    }

    public ListNode(int value) {
        this.value = value;
    }

    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }
}
