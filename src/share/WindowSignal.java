package share;

import sun.misc.Signal;

/**
 * Created by alex on 2/26/15.
 */
public class WindowSignal<Item> implements Command<Item>
{
    private Item item;
    private DataType dataType;
    private SignalType signalType;

    private final SignalOrigin signalOrigin = SignalOrigin.WINDOW;

    private WindowSignal(Builder<Item> b)
    {
        this.item = b.payload;
        this.dataType = b.dataType;
        this.signalType = b.signalType;

    }

    public static class Builder<Item> implements share.Builder
    {
        private SignalType signalType;
        private Item payload = null;
        private DataType dataType = null;

        public Builder(SignalType s)
        {
            signalType = s;
        }

        public Builder payload(Item payload)
        {
            this.payload = payload;
            return this;
        }

        public Builder dataType(DataType dataType)
        {
            this.dataType = dataType;
            return this;
        }

        public WindowSignal build()
        {
            return new WindowSignal(this);
        }
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