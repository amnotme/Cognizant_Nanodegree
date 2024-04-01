package helper;

import java.util.*;

/**
 * The type Time. Helper function to create date objects.
 */
public class Time {

    /**
     * TIME_ERRORS constant
     */
    private static final HashMap<String, String> TIME_ERRORS = new HashMap<>() {{
        put("incorrectLength", "The date you passed is incorrect.  Please format as (mm/dd/yyyy):");
        put("incorrectSlashPosition", "Please format as follows. (mm/dd/yyyy)");
    }};

    /**
     * Length of valid date entry.
     */
    private static final int LENGTH_OF_DATE = 10;

    /**
     * Left index position of a dash in mm/dd for a date.
     */
    private static final int LEFT_DASH_POSITION = 2;

    /**
     * Right index position of a dash in dd/yyyy for a date.
     */
    private static final int RIGHT_DASH_POSITION = 5;

    /**
     * The constant calendarMonths. The hashmap was created as months are zero-indexed. That is, they start from 0 rather than 1.
     */
    private static final HashMap<Integer, Integer> calendarMonths = new HashMap<Integer, Integer>() {{
        put(1, Calendar.JANUARY);
        put(2, Calendar.FEBRUARY);
        put(3, Calendar.MARCH);
        put(4, Calendar.APRIL);
        put(5, Calendar.MAY);
        put(6, Calendar.JUNE);
        put(7, Calendar.JULY);
        put(8, Calendar.AUGUST);
        put(9, Calendar.SEPTEMBER);
        put(10, Calendar.OCTOBER);
        put(11, Calendar.NOVEMBER);
        put(12, Calendar.DECEMBER);
    }};

    /**
     * Create date date.
     *
     * Helper function that helps create a Date object.
     * We remove 1900 from the year as the new functionality adds 1900 to the year passed.
     * TODO: Date is a deprecated function as of JDK 11. However part of the project states to use this.
     *
     * @param date the date
     * @return the date
     */
    public static Date createDate(String date) {
        String[] parsedDate;
        isValidDate(date);
        parsedDate = date.split("/");
        return new Date(
                Integer.parseInt(parsedDate[2]) - 1900,
                calendarMonths.get(Integer.parseInt(parsedDate[0])),
                Integer.valueOf(parsedDate[1])
        );
    }

    public static void isValidDate(String date) throws InputMismatchException {
        int leftSlash;
        int rightSlash;

        if (date.length() != LENGTH_OF_DATE)
            throw new InputMismatchException("[" + date + "] " + TIME_ERRORS.get("incorrectLength"));

        leftSlash = date.indexOf('/');
        rightSlash = date.indexOf('/',3);
        if (leftSlash != LEFT_DASH_POSITION || rightSlash != RIGHT_DASH_POSITION)
            throw new InputMismatchException("[" + date + "] " + TIME_ERRORS.get("incorrectSlashPosition"));
    }
}
