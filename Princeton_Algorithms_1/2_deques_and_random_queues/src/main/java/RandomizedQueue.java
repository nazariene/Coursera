import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;
    private int tail;
    private int head;
    private int n;

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
        head = -1;
        tail = -1;
    }

    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        int i = 0;
        for (int j = head; j < tail; j++) {
            temp[i++] = a[j];
        }

        a = temp;
        head = 0;
        tail = i;
        n = i;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            head = 0;
            tail = 0;
        }

        a[tail] = item;
        tail++;

        n++;

        if (tail >= a.length) {
            resize(a.length * 2);
        } else if (tail - head < a.length / 2) {
            resize(a.length / 2);
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(head, tail);
        Item temp = a[head];
        a[head] = a[index];
        a[index] = temp;

        Item result = a[head];
        a[head] = null;
        head++;

        n--;

        if (tail >= a.length) {
            resize(a.length * 2);
        } else if (tail - head < a.length / 2) {
            resize(a.length / 2);
        }

        return result;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(head, tail);
        return a[random];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private int position = 0;
        private Item[] array;

        private RandomIterator() {
            array = (Item[]) new Object[n];
            int i = 0;
            for (int j = head; j < tail; j++) {
                array[i++] = a[j];
            }

            StdRandom.shuffle(array);
        }

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return array[position++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(0);
        rq.enqueue(0);
        rq.enqueue(4);
        rq.enqueue(1);
        System.out.println(rq.size());
    }
}