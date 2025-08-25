package my.labs.ds.linkedList;

public class DoubleListNodeUtil {

    public static DoubleListNode createDoubleListNode (int[] arr) {
        if(arr == null || arr.length == 0) {
            return null;
        }
        DoubleListNode head = new DoubleListNode(arr[0]);
        DoubleListNode current = head;
        for (int i = 1; i < arr.length; i++){
            DoubleListNode newNode = new DoubleListNode(arr[i]);
            current.next = newNode;
            newNode.prev = current;
            current = current.next;
        }
        return head;
    }

    public static void main(String[] args){
        DoubleListNode listNode = createDoubleListNode(new int[] {1,2,3,4,5});
        DoubleListNode tail = null;
        DoubleListNode p = listNode;
        while (p.next != null){
            System.out.println(p.value);
            p = p.next;
        }
        tail = p;
        while (tail.prev != null){
            System.out.println(tail.value);
            tail = tail.prev;
        }
    }
}
