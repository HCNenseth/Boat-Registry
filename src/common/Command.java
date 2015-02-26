package common;

/**
 * Created by alex on 2/25/15.
 */
public interface Command<Item>
{
    public Item getPayload();

    public SignalOrigin getSignalOrigin();

    public abstract void execute();

    public SignalType getSignalType();

    public DataType getDataType();
}
