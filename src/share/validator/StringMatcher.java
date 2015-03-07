/**
 *
 * @filename StringMatcher.java
 *
 * @date 2015-02-22
 *
 * Simple regex rules class (enum for singleton)
 */

package share.validator;

public enum StringMatcher
{
    INSTANCE;

    /**
     * Some simple rules. Edit here!
     */
    private final static String baseString = "[\\w\\s\\-æøåØÆÅ].{2,20}";
    private final static String baseFloat = "[0-9]+([\\\\,\\\\.][0-9]+)?";

    private final static String email = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.\\w{2,4}";
    private final static String firstname = baseString;
    private final static String lastname = baseString;
    private final static String color = baseString;

    private final static String regnr  = "\\w{2,6}";
    private final static String type = "\\w{2,20}";
    private final static String year = "\\d{4}";
    private final static String length = baseFloat;
    private final static String power = baseFloat;

    public static boolean email(String val) { return val.matches(email); }

    public static boolean firstname(String val) { return val.matches(firstname); }

    public static boolean lastname(String val) { return val.matches(lastname); }

    public static boolean regnr(String val) { return val.matches(regnr); }

    public static boolean type(String val) { return val.matches(type); }

    public static boolean color(String val) { return val.matches(color); }

    public static boolean year(String val) { return val.matches(year); }

    public static boolean length(String val) { return val.matches(length); }

    public static boolean power(String val) { return val.matches(power); }
}
