/**
 *
 * @filename WidgetSignal.java
 *
 * @date 2015-02-26
 *
 * Used for signaling between widgets and the Mediator -> Colleague
 */

package share;

public class WidgetSignal<Item> implements Command
{
    private Item payload;
    private SignalType signalType;
    private DataType dataType;

    private final SignalOrigin signalOrigin = SignalOrigin.WIDGET;


    public WidgetSignal(Item i, SignalType s, DataType d)
    {
        payload = i; signalType = s; dataType = d;
    }

    public WidgetSignal(SignalType s, DataType d)
    {
        signalType = s; dataType = d;
    }

    public WidgetSignal(SignalType s)
    {
        signalType = s;
    }

    /*
        GETTERS
     */
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

    @Override
    public void execute() {}
}
