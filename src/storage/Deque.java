package storage;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by alex on 2/17/15.
 */
public final class Deque<Item extends Comparable>
        implements Iterable<Item>, Comparable<Deque>, Comparator<Item>, Serializable
{
    private static final long serialVersionUID = 3001L;

    private Node first;
    private Node last;
    private int count;

    private final class Node implements Serializable
    {
        private static final long serialVersionUID = 4001L;

        Item item;
        Node next;
    }

    public Deque()
    {
        count = 0;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public int size()
    {
        return count;
    }

    /**
     *
     * @param item
     */
    public void addLast(Item item)
    {
        if (item == null)
            throw new NullPointerException("Item is null!");

        Node newLast = new Node();
        newLast.item = item;

        if (isEmpty()) {
            first = last = newLast;
        } else {
            last.next = newLast;
            last = newLast;
        }
        count++;
    }

    /**
     *
     * @param item
     */
    public void addFirst(Item item)
    {
        if (item == null)
            throw new NullPointerException("Item is null!");

        Node newFirst = new Node();
        newFirst.item = item;

        if (isEmpty()) {
            first = last = newFirst;
        } else {
            newFirst.next = first;
            first = newFirst;
        }
        count++;
    }

    public Item removeFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException("Empty list!");

        Node tmp = first;
        first = first.next;

        count--;
        return tmp.item;
    }

    /**
     * Remove item from list.
     * @param item
     * @return
     */
    public Item remove(Item item)
    {
        if (item == null)
            throw new NullPointerException("Item is null!");

        if (size() == 0)
            throw new NoSuchElementException("List is empty!");

        if (first.item.equals(item)) {
            Item tmp = first.item;
            first = first.next;
            count--;
            return tmp;
        }

        Node tmp = first;

        while (tmp.next != null) {
            if (tmp.next.item.compareTo(item) >= 0) {
                Item tmpItem = tmp.next.item;

                tmp.next = tmp.next.next;

                count--;
                return tmpItem;
            }
            tmp = tmp.next;
        }

        throw new NullPointerException("Element does not exists in list!");
    }

    /**
     * Get a numbered item from list.
     * @param i
     * @return
     */
    public Item get(int i)
    {
        if (isEmpty())
            throw new NoSuchElementException("Empty list!");
        if (i > size())
            throw new NoSuchElementException("Out of bounds!");

        Node tmp = first;
        int c = 0;

        while (tmp != null) {
            if (c++ == i) { return tmp.item; }
            tmp = tmp.next;
        }
        return null;
    }

    /**
     *
     * @param item
     * @return
     */
    public boolean has(Item item)
    {
        if (isEmpty())
            throw new NoSuchElementException("Empty list!");

        Node tmp = first;

        while (tmp.next != null) {
            if (tmp.item.compareTo(item) < 0) { return false; }

            tmp = tmp.next;
        }

        return true;
    }

    /**
     * Make a copy of the list and return it.
     * @return
     */
    public Deque<Item> copy()
    {
        Deque<Item> tmp = new Deque<>();
        for (Item i : this)
            tmp.addLast(i);

        return tmp;
    }

    @Override
    public int compare(Item i1, Item i2)
    {
        return i1.compareTo(i2);
    }

    @Override
    public int compareTo(Deque d)
    {
        if (d == null)
            throw new NullPointerException("Arg is null!");

        // first test size
        if (d.size() != size()) { return -1; }

        for (int i = 0; i < size(); i++) {
            if (! get(i).equals(d.get(i))) { return -1; }
        }

        return 0;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>()
        {
            private Node current = first;

            @Override
            public boolean hasNext()
            {
                return current != null;
            }

            @Override
            public Item next()
            {
                if (current == null)
                    throw new NoSuchElementException("No more elements!");

                Item item = current.item;
                current = current.next;
                return item;
            }

            @Override
            public boolean hasPrevious()
            {
                return false;
            }

            @Override
            public Item previous()
            {
                return null;
            }

            @Override
            public int nextIndex()
            {
                return count;
            }

            @Override
            public int previousIndex()
            {
                return 0;
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException("Not supported!");
            }

            @Override
            public void set(Item item)
            {

            }

            @Override
            public void add(Item item)
            {

            }
        };
    }
}
