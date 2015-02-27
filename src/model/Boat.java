/**
 * Created by alex on 2/17/15.
 */

package model;

import java.io.Serializable;

public final class Boat implements Comparable<Boat>, Serializable
{
    private static final long serialVersionUID = 1000L;

    private String regnr;
    private String type;
    private int year;
    private double length;
    private double power;
    private String color;
    private Member owner;

    public static class Builder implements share.Builder
    {
        // Required parameters
        private final String regnr;
        private final String type;
        // Optional parameter
        private int year = 2000;
        private double length = 20;
        private double power = 100;
        private String color = "Blue";
        private Member member = null;

        public Builder(String regnr, String type)
        {
            this.regnr = regnr;
            this.type = type;
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

        public Boat build()
        {
            return new Boat(this);
        }

    }

    private Boat(Builder b)
    {
        this.regnr = b.regnr;
        this.type = b.type;
        this.year = b.year;
        this.length = b.length;
        this.power = b.power;
        this.color = b.color;
        this.owner = b.member;
    }

    public String getRegnr() { return regnr; }
    public String getType() { return type; }
    public String getColor() { return color; }
    public int getYear() { return year; }
    public double getLength() { return length; }
    public double getPower() { return power; }

    // Mutable data ...
    public Member getOwner() { return owner; }

    public void setRegnr(String regnr)
    {
        this.regnr = regnr;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public void setPower(double power)
    {
        this.power = power;
    }

    public void setOwner(Member member)
    {
        this.owner = member;
    }

    @Override
    public int compareTo(Boat test)
    {
        final int NOT_EQUAL = -1;
        final int EQUAL = 0;

        if (! regnr.equals(test.getRegnr())) { return NOT_EQUAL; }
        if (! type.equals(test.getType())) { return NOT_EQUAL; }
        if (! color.equals(test.getColor())) { return NOT_EQUAL; }

        if (year != test.getYear()) { return NOT_EQUAL; }

        if (Double.compare(length, test.getLength()) < EQUAL) { return NOT_EQUAL; }
        if (Double.compare(power, test.getLength()) < EQUAL) { return NOT_EQUAL; }

        if (owner.compareTo(test.getOwner()) < EQUAL) { return NOT_EQUAL; }

        return EQUAL;
    }
}
