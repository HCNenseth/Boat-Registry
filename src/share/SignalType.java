package share;

/**
 * Created by alex on 2/26/15.
 */
public enum SignalType
{
    CLOSE,
    QUIT,
    ERROR,

    CREATE, // post
    UPDATE, // put
    NEW,    // get new
    EDIT,   // get edit
    DELETE  // delete
}
