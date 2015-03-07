/**
 *
 * @filename SpeedBoat.java
 *
 * @date 2015-03-01
 *
 * DISCLAIMER
 *
 * This boat class add no special abilities or
 * properties to boat. It is just here as a proof
 * of concept. (hence the class accessor)
 */

package model.boat;

class SpeedBoat extends BoatSkeleton
{
    SpeedBoat(Builder b)
    {
        super(b);
    }

    @Override
    public int compareTo(BoatSkeleton test)
    {
        int parent = super.compareTo(test);

        return parent;
    }
}
