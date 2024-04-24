import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int size;
    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == q.length) resize(2 * q.length);
        q[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();
        int random = StdRandom.uniformInt(size);
        Item item = q[random];
        if (random != size - 1) {
            q[random] = q[size - 1];
        }
        q[size - 1] = null;
        size--;
        if (size > 0 && size * 4 == q.length) resize(q.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        int random = StdRandom.uniformInt(size);
        Item item = q[random];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] copy = (Item[]) new Object[q.length];
        private int i = size;

        RandomizedQueueIterator() {
            for (int k = 0; k < q.length; ++k) {
                copy[k] = q[k];
            }
        }
        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int random = StdRandom.uniformInt(i);
            Item item = copy[random];
            if (random != i - 1) {
                copy[random] = copy[i - 1];
            }
            copy[i - 1] = null;
            i--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int len) {
        Item[] copy = (Item[]) new Object[len];
        for (int i = 0; i < size; ++i) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(481);
        queue.enqueue(211);
        queue.enqueue(29);
        queue.enqueue(240);
        queue.enqueue(397);
        queue.enqueue(159);
        queue.enqueue(278);
        for (int e : queue) {
            System.out.println(e);
        }
    }

}