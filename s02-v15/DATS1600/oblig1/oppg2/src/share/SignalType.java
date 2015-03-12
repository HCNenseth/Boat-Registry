/**
 *
 * @filename SignalType.java
 *
 * @date 2015-02-26
 *
 */

package share;

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
