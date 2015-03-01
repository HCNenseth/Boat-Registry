package model.boat;


import model.member.Member;

/**
 * Created by alex on 3/1/15.
 */
public interface Boat
{
    /*
        GETTERS
     */
    public String getRegnr();
    public BoatType getType();
    public String getColor();
    public int getYear();
    public double getLength();
    public double getPower();
    public Member getOwner();

    /*
        SETTERS
     */
    public void setRegnr(String regnr);
    public void setType(BoatType type);
    public void setColor(String color);
    public void setYear(int year);
    public void setLength(double length);
    public void setPower(double power);
    public void setOwner(Member member);
    public boolean hasOwner();
}
