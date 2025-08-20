package my.labs.ds.linkedList;

public class LinkedListUtil {
    public static ListNode createLinkedList(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        ListNode head = new ListNode(array[0]);
        ListNode current = head;
        for (int i = 1; i < array.length; i++) {
            current.next = new ListNode(array[i]);
            current = current.next;
        }
        return head;
    }

    public static ListNode insertBeforeHead(ListNode linkedList, ListNode node) {
        if (linkedList == null || node == null) {
            return null;
        }
        node.next = linkedList;
        return node;
    }

    public static ListNode appendTail(ListNode list, ListNode node) {
        ListNode tail = list;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = node;
        return tail.next;
    }

    public static ListNode insertAt(ListNode currentList, ListNode node, int index) {
        if(currentList == null || node == null || index < 0) {
            return null;
        }
        ListNode p = currentList;
        for (int i = 0; i < index; i++){
            p = p.next;
        }
        node.next = p.next;
        p.next = node;
        return currentList;
    }

    public static void main(String[] args){
        ListNode list = createLinkedList(new int[] {1,2,3,4,5});
        list = insertAt(list,new ListNode(100),1);
        for (ListNode p = list; p != null; p = p.next){
            System.out.println(p.value);
        }
    }
}
