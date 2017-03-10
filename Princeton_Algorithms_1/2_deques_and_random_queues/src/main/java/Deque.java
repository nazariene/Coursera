import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        Node node = new Node(item);

        if (!isEmpty()) {
            head.prev = node;
            node.next = head;
        } else {
            tail = node;
        }

        head = node;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node node = new Node(item);

        if (!isEmpty()) {
            tail.next = node;
            node.prev = tail;
        } else {
            head = node;
        }

        tail = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node result = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        result.next = null;

        size--;

        return result.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node result = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        }
        result.prev = null;

        size--;

        return result.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println("True:   " + deque.isEmpty());
        System.out.println("Size 0:   " + deque.size());

        deque.addFirst("111");
        System.out.println("False:   " + deque.isEmpty());
        System.out.println("Size 1:   " + deque.size());
        System.out.println("True:   " + deque.isEmpty());
        System.out.println("True:   " + deque.isEmpty());
        System.out.println("True:   " + deque.isEmpty());
        System.out.println("True:   " + deque.isEmpty());
        System.out.println("True:   " + deque.isEmpty());
        System.out.println("True:   " + deque.isEmpty());
        System.out.println("True:   " + deque.isEmpty());

    }

    private class Node {

        private Item item;
        private Node next;
        private Node prev;

        private Node(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}