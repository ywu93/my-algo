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


    public static ListNode mergeTowSortedList(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        ListNode p1 = list1, p2 = list2;
        while (p1 != null && p2!= null) {
            if(p1.value < p2.value){
                current.next = p1;
                p1 = p1.next;
            }else {
                current.next = p2;
                p2 = p2.next;
            }
            current = current.next;
        }
        current.next = (p1 != null) ? p1 : p2;
        return dummy.next;
    }

    public static void main(String[] args){
        ListNode list1 = createLinkedList(new int[] {1,2,3,4,5});
        ListNode list2 = createLinkedList(new int[] {4,6,8,10,18});
        ListNode merged = mergeTowSortedList(list1,list2);
        while (merged != null){
            System.out.println(merged.value);
            merged = merged.next;
        }

    }
}
