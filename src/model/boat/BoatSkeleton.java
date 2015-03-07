/**
 *
 * @filename BoatSkeleton
 *
 * @date 2015-02-17
 *
 * Boat skeleton as a 'typeless' boat all boats have to extend.
 */

package model.boat;

import model.member.Member;

import java.io.Serializable;

public abstract class BoatSkeleton implements Comparable<BoatSkeleton>, Serializable, Boat
{
    private static final long serialVersionUID = 1000L;

    protected String regnr;
    protected BoatType type;
    protected int year;
    protected double length;
    protected double power;
    protected String color;
    protected Member owner;

    public static class Builder implements share.Builder
    {
        // Required parameters
        private final String regnr;
        private final BoatType type;

        // Optional parameter
        private int year = 2000;
        private double length = 20;
        private double power = 100;
        private String color = "Blue";
        private Member member = null;

        public Builder(BoatType type, String regnr)
        {
            this.type = type;
            this.regnr = regnr;
        }

        public Builder year(int val)
        {
            this.year = val;
            return this;
        }

        public Builder length(double val)
        {
            this.length = val;
            return this;
        }

        public Builder power(double val)
        {
            this.power = val;
            return this;
        }

        public Builder color(String val)
        {
            this.color = val;
            return this;
        }

        public Builder member(Member m)
        {
            if (m == null)
                throw new NullPointerException("Member is null!");

            this.member = m;
            return this;
        }

        public Member getMember() { return member; }

        public BoatSkeleton build()
        {
            switch (type) {
                case SAILBOAT: return new SailBoat(this);
                case MOTORBOAT: return new MotorBoat(this);
                case SPEEDBOAT: return new SpeedBoat(this);
                case CANOE: return new Canoe(this);
                case CATAMARAN: return new Catamaran(this);
                case LIFEBOAT: return new LifeBoat(this);
                default:
                    throw new IllegalStateException("Unknown boat type");
            }
        }

    }

    protected BoatSkeleton(Builder b)
    {
        this.regnr = b.regnr;
        this.type = b.type;
        this.year = b.year;
        this.length = b.length;
        this.power = b.power;
        this.color = b.color;
        this.owner = b.member;
    }

    /*
        GETTERS
     */
    public String getRegnr() { return regnr; }

    public BoatType getType() { return type; }

    public String getColor() { return color; }

    public int getYear() { return year; }

    public double getLength() { return length; }

    public double getPower() { return power; }

    // Mutable data ...
    public Member getOwner() { return owner; }

    /*
        SETTERS
     */
    public void setRegnr(String regnr) { this.regnr = regnr; }

    public void setType(BoatType type) { this.type = type; }

    public void setColor(String color) { this.color = color; }

    public void setYear(int year) { this.year = year; }

    public void setLength(double length) { this.length = length; }

    public void setPower(double power) { this.power = power; }

    public void setOwner(Member member) { this.owner = member; }

    public boolean hasOwner() { return this.owner != null; }

    /**
     * The inherited classes has to define this method
     * themselves. This because of optional test specific
     * to that boat.
     * @param test
     * @return
     */
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

        if (this.hasOwner() != test.hasOwner()) { return NOT_EQUAL; }

        // if both has owners, test them.
        if (this.hasOwner() && test.hasOwner()) {
            if (owner.compareTo(test.getOwner()) < EQUAL)
                return NOT_EQUAL;
        }

        return EQUAL;
    }
}
