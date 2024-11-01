package my.labs.ds.linkedList;

public class LinkedListUtil {
    public static ListNode createLinkedList(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        ListNode head = new ListNode(array[0]);
        ListNode current = head;
        for (int i = 1; i < array.length; i++) {
            current.setNext(new ListNode(array[i]));
            current = current.getNext();
        }
        return head;
    }

    public static ListNode insertBeforeHead(ListNode linkedList, ListNode node) {
        if (linkedList == null || node == null) {
            return null;
        }
        node.setNext(linkedList);
        return node;
    }
}
