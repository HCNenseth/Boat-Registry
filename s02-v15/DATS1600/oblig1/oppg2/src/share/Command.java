/**
 *
 * @filename Command.java
 *
 * @date 2015-02-25
 *
 * Interface used mainly for signal classes found in this package.
 */

package share;

public interface Command<Item>
{
    public Item getPayload();

    public SignalOrigin getSignalOrigin();

    public SignalType getSignalType();

    public DataType getDataType();

    public abstract void execute();
}
