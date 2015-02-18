/**
 * Created by alex on 2/17/15.
 */

package model;

public class Boat
{
    private String regnr;
    private String type;
    private int year;
    private float length;
    private float power;
    private String color;

    public static class Builder
    {
        // Required parameters
        private final String regnr;
        private final String type;
        // Optional parameter
        private int year = 2000;
        private float length = 20;
        private float power = 100;
        private String color = "Blue";

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

        public Builder length(float val)
        {
            this.length = val;
            return this;
        }

        public Builder power(float val)
        {
            this.power = val;
            return this;
        }

        public Builder color(String val)
        {
            this.color = val;
            return this;
        }

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
    }
}
