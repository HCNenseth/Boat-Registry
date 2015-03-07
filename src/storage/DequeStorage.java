/**
 *
 * @filename DequeStorage.java
 *
 * @date 2015-02-18
 *
 * Deque file storage class
 */

package storage;

import java.io.*;

class DequeStorage
{
    // Singleton
    private static DequeStorage instance = new DequeStorage();

    private DequeStorage() { }

    public static DequeStorage getInstance()
    {
        return instance;
    }

    /**
     *
     * @param d Deque with 'any' Generic type.
     * @throws IOException
     */
    public void write(Deque<?> d, String filename) throws IOException
    {
        ObjectOutputStream handle = new ObjectOutputStream(
                new FileOutputStream(filename));

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
    public Deque<?> read(String filename) throws IOException, ClassNotFoundException
    {
        ObjectInputStream handle = new ObjectInputStream(
                new FileInputStream(filename));

        Deque<?> tmp = (Deque<?>) handle.readObject();
        handle.close();
        return tmp;
    }
}
