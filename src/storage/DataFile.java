package storage;

import java.io.*;

/**
 * Created by alex on 2/18/15.
 *
 * Very spesific Data file storage class
 *
 * EXPERIMENTAL!
 *
 */
public class DataFile
{
    private String path;

    public DataFile(String path)
    {
        this.path = path;
    }

    public void write(Data d) throws IOException
    {
        ObjectOutputStream handle = new ObjectOutputStream(
                new FileOutputStream(path)
        );
        handle.writeObject(d);
    }

    public Data read() throws IOException, ClassNotFoundException
    {
        ObjectInputStream handle = new ObjectInputStream(
                new FileInputStream(path)
        );

        return (Data) handle.readObject();
    }
}
