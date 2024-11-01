package my.labs.ds;

public interface Queue {
    // the capacity of a queue
    int capacity();

    // the current size of a queue
    int size();

    void push(int num);

    int pop();
}
