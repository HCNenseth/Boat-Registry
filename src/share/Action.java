package share;

/**
 * Created by alex on 2/26/15.
 */
public class Action<Item> implements Command<Item>
{
    private Item item;
    private DataType dataType;
    private SignalType signalType;

    private final SignalOrigin signalOrigin = SignalOrigin.WINDOW;

    /*
        Ugly telescoping, consider refactoring.
     */
    public Action(Item i, SignalType s, DataType d)
    {
        item = i;
        signalType = s;
        dataType = d;
    }

    public Action(SignalType s, DataType d)
    {
        signalType = s;
        dataType = d;
    }

    public Action(SignalType s)
    {
        signalType = s;
    }

    /*
        GETTERS
     */

    @Override
    public Item getPayload() {
        return item;
    }

    @Override
    public SignalOrigin getSignalOrigin()
    {
        return signalOrigin;
    }

    @Override
    public SignalType getSignalType()
    {
        return signalType;
    }

    @Override
    public DataType getDataType()
    {
        return dataType;
    }

    @Override
    public void execute() {}
}