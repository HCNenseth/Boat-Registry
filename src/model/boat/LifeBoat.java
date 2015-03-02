package model.boat;


/**
 * Created by alex on 3/1/15.
 */
class LifeBoat extends BoatSkeleton
{
    LifeBoat(Builder b)
    {
        super(b);
    }

    /**
     * DISCLAIMER
     *
     * This boat class add no special abilities or
     * properties to boat. It is just here as a proof
     * of concept. (hence the class accessor)
     */

    @Override
    public int compareTo(BoatSkeleton test)
    {
        if (test == null)
            throw new NullPointerException("Cannot test against null object!");

        final int NOT_EQUAL = -1;
        final int EQUAL = 0;

        if (! regnr.equals(test.getRegnr())) { return NOT_EQUAL; }
        if (! type.equals(test.getType())) { return NOT_EQUAL; }
        if (! color.equals(test.getColor())) { return NOT_EQUAL; }

        if (year != test.getYear()) { return NOT_EQUAL; }

        if (Double.compare(length, test.getLength()) < EQUAL) { return NOT_EQUAL; }
        if (Double.compare(power, test.getLength()) < EQUAL) { return NOT_EQUAL; }

        // if both has owners, test them.
        if (this.hasOwner() && test.hasOwner()) {
            if (owner.compareTo(test.getOwner()) < EQUAL)
                return NOT_EQUAL;
        }

        if (this.hasOwner() != test.hasOwner()) {
            return NOT_EQUAL;
        }

        return EQUAL;
    }
}
