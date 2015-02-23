package storage;

import java.io.*;

/**
 * Created by alex on 2/18/15.
 *
 * Deque file storage class
 *
 */
public class DequeStorage
{
    // Singleton
    private static DequeStorage instance;

    private String filename;

    private DequeStorage(String filename)
    {
        this.filename = filename;
    }

    public static DequeStorage getInstance(String filename)
    {
        if (instance == null) { instance = new DequeStorage(filename); }
        return instance;
    }

    /**
     *
     * @param d Deque with 'any' Generic type.
     * @throws IOException
     */
    public void write(Deque<?> d) throws IOException
    {
        ObjectOutputStream handle = new ObjectOutputStream(
                new FileOutputStream(filename)
        );
        handle.writeObject(d);
        handle.close();
    }

    /**
     * @return Deque<?>
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * This method has some weakness in that the generic type is
     * lost on retrieval (on write actually). Try to solve this!
     */
    public Deque<?> read() throws IOException, ClassNotFoundException
    {
        ObjectInputStream handle = new ObjectInputStream(
                new FileInputStream(filename)
        );
        Deque<?> tmp = (Deque<?>) handle.readObject();
        handle.close();
        return tmp;
    }
}
