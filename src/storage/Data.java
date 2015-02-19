package storage;

import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Created by alex on 2/19/15.
 *
 * Test class for wrapping data and pushing it to file.
 *
 * EXPERIMENTAL!
 *
 */
public class Data implements Serializable
{
    private Node head;
    private Node tail;
    private int count;

    private class Node
    {
        Deque<?> list;
        Node next;
    }

    public Data()
    {

    }

    public boolean isEmpty()
    {
        return head == null;
    }

    public void push(Deque<?> list)
    {
        if (list == null)
            throw new NoSuchElementException("Null data!");

        Node n = new Node();
        n.list = list;

        if (isEmpty()) {
            head = tail = n;
        } else {
            n.next = head;
            head = n;
        }
        count++;
    }

}
