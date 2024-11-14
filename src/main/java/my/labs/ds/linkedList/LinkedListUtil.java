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

    public static ListNode appendTail(ListNode list, ListNode node) {
        ListNode tail = list;
        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        tail.setNext(node);
        return tail.getNext();
    }

    public static ListNode insertNodeAtIndex(ListNode currentList, ListNode newNode, int index) {

        return null;
    }
}
