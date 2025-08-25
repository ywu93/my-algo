package my.labs.ds.linkedList;

public class MyLinkedList <E> {

    private final Node<E> head,tail;
    private int size;

    private static class Node<E> {
        E value;
        Node<E> prev;
        Node<E> next;
        Node (E value){
            this.value = value;
        }
    }

    public MyLinkedList() {
      this.head = new Node<>(null);
      this.tail = new Node<>(null);
      head.next = tail;
      tail.prev = head;
      this.size = 0;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        Node<E> tmp = tail.prev;
        tmp.next = newNode;
        newNode.prev = tmp;
        newNode.next = tail;
        tail.prev = newNode;
        size ++;
    }

    // head <->2<->tail
    public void addFirst(E e) {
        Node<E> node = new Node<E>(e);
        Node<E> tmp = head.next;
        node.next = tmp;
        tmp.prev = node;
        head.next = node;
        node.prev = head;
        size ++;
    }


}
