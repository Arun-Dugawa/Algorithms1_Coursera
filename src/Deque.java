import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = first;
        size = 0;
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        validate(item);
        ++size;
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null) oldFirst.prev = first;
        if (last == null) {
            last = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        validate(item);
        ++size;
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (oldLast != null) oldLast.next = last;
        if (first == null) first = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        --size;
        Item item = first.item;
        first = first.next;
        if (first == null) last = first;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException();
        --size;
        Item item = last.item;
        last = last.prev;
        if (last == null) first = last;
        else last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void validate(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(5);
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println();
        d.addFirst(4);
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println(d.size());
        d.addFirst(3);
        for (int e : d) {
            System.out.print(e + " ");
        }
        d.removeFirst();
        System.out.println();
        d.addLast(6);
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println();
        d.addLast(7);
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println();
        d.removeFirst();
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println(d.size());
        d.removeFirst();
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println(d.isEmpty());
        d.removeFirst();
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println(d.isEmpty());
        d.removeFirst();
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println(d.isEmpty());
        d.removeFirst();
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println(d.isEmpty());
        d.addFirst(2);
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println();
        d.addFirst(1);
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println();
        d.addLast(8);
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println();
        d.addLast(9);
        for (int e : d) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

}