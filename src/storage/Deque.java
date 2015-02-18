package storage;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by alex on 2/17/15.
 */
public class Deque<Item> implements Iterable<Item>
{
    private Node first;
    private Node last;
    private int count;

    private class Node
    {
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

    public void addLast(Item item)
    {
        if (item == null)
            throw new NullPointerException("Item is null1");

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

    public void addFirst(Item item)
    {
        if (item == null)
            throw new NullPointerException("Item is null1");

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

        return tmp.item;
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
