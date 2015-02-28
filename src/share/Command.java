package share;

/**
 * Created by alex on 2/25/15.
 */
public interface Command<Item>
{
    public Item getPayload();

    public SignalOrigin getSignalOrigin();

    public SignalType getSignalType();

    public DataType getDataType();

    public abstract void execute();
}
