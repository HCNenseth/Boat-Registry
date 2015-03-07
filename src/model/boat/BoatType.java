package model.boat;

/**
 * Created by alex on 3/1/15.
 */
public enum BoatType
{
    SAILBOAT("Sailboat"),
    MOTORBOAT("Motorboat"),
    SPEEDBOAT("Speedboat"),
    CANOE("Canoe"),
    CATAMARAN("Catamaran"),
    LIFEBOAT("Lifeboat"),
    ;

    private String str;

    private BoatType(String str) { this.str = str; }

    public String getStr() { return str; }

    @Override
    public String toString() { return getStr(); }
}
