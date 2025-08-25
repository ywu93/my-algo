package my.labs.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LRUCache_146 {
    private Map<Integer, Node> cacheMap;
    private int capacity;
    private int size;

    private Node head, tail;

    static class Node {
        int key;
        int value;
        Node prev, next;
        Node (int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    LRUCache_146 (int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>();
        this.size = 0;
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        this.head.next = tail;
        tail.prev = head;
    }
                      //node
    // head <-> 1 <-> 2 <-> tail

    public int get(int key) {
        Node node = cacheMap.get(key);
        if (node == null){
            return -1;
        }
        // update the node in the linked list
        moveToHead(node);
        return node.value;
    }

    private void moveToHead (Node node) {
        // h <-> 1 <-> tail
        node.prev.next = node.next;
        node.next.prev = node.prev;

        addToHead(node);
    }

    public void put(int key, int value){
        Node node = cacheMap.get(key);
        if (node != null){
            node.value = value;
            moveToHead(node);
            return;
        }
        Node newNode = new Node(key,value);
        addToHead(newNode);
        cacheMap.put(key,newNode);
        size ++;

        if(size > capacity) {
            // remove last node from the list
            int lastNodeKey = removeLast();
            cacheMap.remove(lastNodeKey);
            size --;
        }
    }

    private int removeLast() {
        Node lastNode = tail.prev;
        lastNode.prev.next = tail;
        tail.prev = lastNode.prev;
        return lastNode.key;
    }

    private void addToHead (Node node) {
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public static void main(String[] args){
        LRUCache_146 cache = new LRUCache_146(3);
        cache.put(1,1);
        cache.put(2,2);
        cache.get(1);
        cache.put(3,3);
        cache.put(4,4);
    }

}
