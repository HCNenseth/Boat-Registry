package validator;

/**
 * Created by alex on 2/22/15.
 */
public class StringMatcher
{
    private final static String email = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.\\w{2,4}";
    private final static String firstname = "\\w{2,20}";
    private final static String lastname = "\\w{2,20}";
    private final static String regnr  = "\\w{2,6}";
    private final static String type = "\\w{2,20}";
    private final static String year = "\\d{4}";
    private final static String length = "\\d{2,4}";
    private final static String power = "\\d{2,4}";
    private final static String color = "\\w{2,20}";

    public static boolean email(String val)
    {
        return val.matches(email);
    }

    public static boolean firstname(String val)
    {
        return val.matches(firstname);
    }

    public static boolean lastname(String val)
    {
        return val.matches(lastname);
    }

    public static boolean regnr(String val) {
        return val.matches(regnr);
    }

    public static boolean type(String val) {
        return val.matches(type);
    }

    public static boolean color(String val)
    {
        return val.matches(color);
    }

    public static boolean year(String val)
    {
        return val.matches(year);
    }

    public static boolean length(String val)
    {
        return val.matches(length);
    }

    public static boolean power(String val)
    {
        return val.matches(power);
    }
}
