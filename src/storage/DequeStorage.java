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
    private String path;

    public DequeStorage(String path)
    {
        this.path = path;
    }

    /**
     *
     * @param d Deque with 'any' Generic type.
     * @throws IOException
     */
    public void write(Deque<?> d) throws IOException
    {
        ObjectOutputStream handle = new ObjectOutputStream(
                new FileOutputStream(path)
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
                new FileInputStream(path)
        );
        Deque<?> tmp = (Deque<?>) handle.readObject();
        handle.close();
        return tmp;
    }
}
