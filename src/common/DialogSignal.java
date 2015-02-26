package common;

/**
 * Created by alex on 2/26/15.
 */
public class DialogSignal<Item> implements Command
{
    private Item payload;
    private SignalType signalType;
    private DataType dataType;

    private final SignalOrigin signalOrigin = SignalOrigin.DIALOG;


    public DialogSignal(Item i, SignalType s, DataType d)
    {
        payload = i; signalType = s; dataType = d;
    }

    public DialogSignal(SignalType s, DataType d)
    {
        signalType = s; dataType = d;
    }

    public DialogSignal(SignalType s)
    {
        signalType = s;
    }

    public void execute() {}

    @Override
    public Item getPayload()
    {
        return payload;
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
}
