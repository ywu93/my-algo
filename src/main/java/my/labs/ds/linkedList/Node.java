package my.labs.ds.linkedList;

// bidirectional link list
public class Node <E>{
    private E element;
    private Node<E> pre;
    private Node<E> next;

    public Node(E element, Node<E> pre, Node<E> next){
        this.element = element;
        this.pre = pre;
        this.next = next;
    }
}
