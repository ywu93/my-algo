package my.labs.ds;


// Dynamic array implementation
public class MyArrayList<E> {
    // The array to store elements
    private E[] data;

    // The capacity of the array
    private int capacity;

    // The number of elements in this array
    private int size;

    // The default capacity of an array when it's initialized
    private static final int INIT_CAPACITY = 10;


    public MyArrayList() {
        this(INIT_CAPACITY);
    }

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        this.data = (E[]) new Object[capacity];
        this.size = 0;
    }

    public void addFirst(E element) {
        add(0, element);
    }

    public void addLast(E element) {
        add(size, element);
    }

    private void add(int index, E element) {
        if (size == capacity) {
            resize(2 * capacity);
        }
        checkPositionIndex(index);
        if (size == index) {
            //add last
            data[index] = element;
            size++;
            return;
        }
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = element;
        size++;
    }

    public E get(int index) {
        checkElementIndex(index);
        return data[index];
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        E removeEle = data[size - 1];
        data[size - 1] = null;
        size--;
        if (size == capacity / 4) {
            resize(capacity / 2);
        }
        return removeEle;
    }

    public E remove(int index) {
        checkElementIndex(index);
        E removedElement = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
            data[i] = null;
        }
        size--;
        if (size == capacity / 4) {
            resize(capacity / 2);
        }
        return removedElement;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (i == data.length - 1) {
                builder.append(data[i]);
            } else {
                builder.append(data[i]).append(",");
            }
        }
        return builder.toString();
    }

    public void print(){
        System.out.println("Capacity:" + this.capacity + ", size:" + this.size);
        System.out.println(this);
    }

    private void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            temp[i] = data[i];
        }
        data = temp;
        this.capacity = capacity;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("The position's index is out of bounds, index=" + index);
        }
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("The element's index is out of bounds, index=" + index);
        }
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
}
