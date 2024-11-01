package my.labs.ds;


import my.labs.ds.array.MyArrayList;
import org.junit.Assert;
import org.junit.Test;

public class MyArrayListTest {

    @Test
   public void addFirst() {

        MyArrayList<Integer> arrayList = new MyArrayList<>();

        arrayList.addFirst(1);
        arrayList.addFirst(2);

        Assert.assertEquals(2,arrayList.size());
        Assert.assertEquals(2, arrayList.get(0).intValue());
        Assert.assertEquals(1, arrayList.get(1).intValue());

    }

    @Test
    public void addLast() {
        MyArrayList<Integer> arrayList = new MyArrayList<>(1);
        arrayList.addLast(1);
        arrayList.addLast(2);
        Assert.assertEquals(2,arrayList.size());
        Assert.assertEquals(2,arrayList.capacity());
        Assert.assertEquals(1, arrayList.get(0).intValue());
        Assert.assertEquals(2, arrayList.get(1).intValue());
    }

    @Test
    public void remove() {
        MyArrayList<Integer> arrayList = new MyArrayList<>(1);
        arrayList.addLast(1);
        arrayList.print();
        arrayList.addLast(2);
        arrayList.print();
        arrayList.addLast(3);
        arrayList.print();
        arrayList.addLast(4);
        arrayList.print();
        arrayList.addLast(5);
        arrayList.print();
        arrayList.addLast(6);
        arrayList.print();
        arrayList.addLast(7);
        arrayList.print();
        arrayList.addLast(8);
        arrayList.print();
        arrayList.addLast(9);
        arrayList.print();
        arrayList.addLast(10);
        arrayList.print();

        arrayList.removeLast();
        arrayList.print();
        arrayList.removeFirst();
        arrayList.print();
        arrayList.remove(3);
        arrayList.print();
        arrayList.remove(1);
        arrayList.print();
        arrayList.removeLast();
        arrayList.removeLast();
        arrayList.print();
    }

    @Test
    public void testSet() {
        MyArrayList<Integer> array = new MyArrayList<>();
        array.addLast(10);
        array.addFirst(1);
        array.print();
        Integer oldValue = array.set(1, 2);
        Assert.assertEquals(10, oldValue.intValue());
        array.print();
    }

}