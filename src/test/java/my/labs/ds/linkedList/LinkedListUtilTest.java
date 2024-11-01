package my.labs.ds.linkedList;

import org.junit.Test;

public class LinkedListUtilTest {

    @Test
    public void createLinkedList() {
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        ListNode node = LinkedListUtil.createLinkedList(array);
        while (node != null) {
            System.out.println(node.getValue());
            node = node.getNext();
        }

    }

    @Test
    public void insertBeforeHeqd(){
        ListNode linkedList = LinkedListUtil.createLinkedList(new int []{1, 2, 3, 4, 5, 6, 7});
        ListNode head = LinkedListUtil.insertBeforeHead(linkedList,new ListNode(0));

        while (head != null) {
            System.out.println(head.getValue());
            head = head.getNext();
        }
    }
}