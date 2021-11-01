package com.vjy.deliveroo.cron;

import com.vjy.deliveroo.cron.fields.DayOfMonthField;
import com.vjy.deliveroo.cron.fields.DayOfWeekField;
import com.vjy.deliveroo.cron.fields.FieldType;
import com.vjy.deliveroo.cron.fields.SimpleField;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;


/**
 * <p>
 * <p>
 * Parser for unix-like cron expressions: Cron expressions allow specifying combinations of criteria for time
 * such as: &quot;Each Monday-Friday &quot; or &quot; 1,2,3 days of month &quot;
 * <p>
 * A cron expressions consists of 6 mandatory fields separated by space. <br>
 * These are:
 *
 * <table cellspacing="8">
 * <tr>
 * <th align="left">Field</th>
 * <th align="left">&nbsp;</th>
 * <th align="left">Allowable values</th>
 * <th align="left">&nbsp;</th>
 * <th align="left">Special Characters</th>
 * </tr>
 *
 * <tr>
 * <td align="left"><code>Minutes</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>0-59</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>, - * /</code></td>
 * </tr>
 * <tr>
 * <td align="left"><code>Hours</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>0-23</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>, - * /</code></td>
 * </tr>
 * <tr>
 * <td align="left"><code>Day of month</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>1-31</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>, - *  / </code></td>
 * </tr>
 * <tr>
 * <td align="left"><code>Month</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>1-12 or JAN-DEC (note: english abbreviations)</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>, - * /</code></td>
 * </tr>
 * <tr>
 * <td align="left"><code>Day of week</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>1-7 or MON-SUN (note: english abbreviations)</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>, - * / </code></td>
 * </tr>
 * <tr>
 * <td align="left"><code>Command</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code>User command ex. /usr/bin/find</code></td>
 * <td align="left">&nbsp;</th>
 * <td align="left"><code></code></td>
 * </tr>
 * </table>
 *
 * <p>
 * '*' Can be used in all fields and means 'for all values'. E.g. &quot;*&quot; in minutes, means 'for all minutes'
 * <p>
 * '-' Used to specify a time interval. E.g. &quot;10-12&quot; in Hours field means 'for hours 10, 11 and 12'
 * <p>
 * ',' Used to specify multiple values for a field. E.g. &quot;MON,WED,FRI&quot; in Day-of-week field means &quot;for
 * monday, wednesday and friday&quot;
 * <p>
 * '/' Used to specify increments. E.g. &quot;0/15&quot; in Minutes field means &quot;for minutes 0, 15, 30, ad
 * 45&quot;. And &quot;5/15&quot; in minutes field means &quot;for minute 5, 20, 35, and 50&quot;. If '*' s specified
 * before '/' it is the same as saying it starts at 0. For every field there's a list of values that can be turned on or
 * off. Minutes these range from 0-59. For Hours from 0 to 23, For Day-of-month it's 1 to 31, For Months
 * 1 to 12. &quot;/&quot; character helps turn some of these values back on. Thus &quot;7/6&quot; in Months field
 * specify just Month 7. It doesn't turn on every 6 month following, since cron fields never roll over
 * <p>
 *
 * <b>Case-sensitive</b> No fields are case-sensitive
 * <p>
 * <b>Dependencies between fields</b> Fields are always evaluated independently, but the expression doesn't match until
 * the constraints of each field are met. Overlap of intervals are not allowed. That is: for
 * Day-of-week field &quot;FRI-MON&quot; is invalid,but &quot;FRI-SUN,MON&quot; is valid
 * @author vsolanki
 */

public class CronExpression {

    public static final String COMMAND = "command";
    public static final int MAX_COLM_LENGTH = 14;
    public final int EXPECTED_PART_LENGTH = 6;
    public final SimpleField minuteField;
    public final SimpleField hourField;
    public final DayOfMonthField dayOfMonthField;
    public final SimpleField monthField;
    public final DayOfWeekField dayOfWeekField;
    public final String cmd;
    public final String expression;

    public CronExpression(String exp) {
        this.expression = exp;
        if (null == exp || exp.trim().isEmpty()) {
            throw new IllegalArgumentException("null or empty expression is not allowed");
        }

        String[] parts = exp.trim().split("\\s+");
        if (parts.length != EXPECTED_PART_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Invalid cron expression [%s], expected %s fields, actual %s", exp,
                            EXPECTED_PART_LENGTH, parts.length));
        }

        // construct fields from expression
        int idx = 0;
        minuteField = new SimpleField(FieldType.MINUTE, parts[idx++]);

        hourField = new SimpleField(FieldType.HOUR, parts[idx++]);

        dayOfMonthField = new DayOfMonthField(FieldType.DAY_OF_MONTH, parts[idx++]);

        monthField = new SimpleField(FieldType.MONTH, parts[idx++]);

        dayOfWeekField = new DayOfWeekField(FieldType.DAY_OF_WEEK, parts[idx++]);

        cmd = parts[idx];

    }

    /**
     *  Describe expression execution pattern include all expression fields and command.
     * @return String
     */
    public String describe() {

        StringBuilder result = new StringBuilder();

        Set<Integer> min = minuteField.get();
        result.append(getString(FieldType.MINUTE.getIdentifier(), min));
        result.append("\n");

        Set<Integer> hour = hourField.get();
        result.append(getString(FieldType.HOUR.getIdentifier(), hour));
        result.append("\n");

        Set<Integer> dayOfMonth = dayOfMonthField.get();
        result.append(getString(FieldType.DAY_OF_MONTH.getIdentifier(), dayOfMonth));
        result.append("\n");

        Set<Integer> month = monthField.get();
        result.append(getString(FieldType.MONTH.getIdentifier(), month));
        result.append("\n");

        Set<Integer> dayOfWeek = dayOfWeekField.get();
        result.append(getString(FieldType.DAY_OF_WEEK.getIdentifier(), dayOfWeek));
        result.append("\n");

        result.append(getString(COMMAND, Collections.<Integer>emptySet(), cmd));
        return result.toString();

    }

    /**
     * Merge and Convert to Single string, representing column like structure where first column width is 14.
     * @param identifier type of field
     * @param input values
     * @param cmd command
     * @return StringBuilder formatted string combining field, values and command.
     */
    public StringBuilder getString(String identifier, Set<Integer> input, String... cmd) {

        StringBuilder result = new StringBuilder(identifier);
        while (result.length() <= MAX_COLM_LENGTH) {
            result.append(" ");
        }

        if (null != cmd && cmd.length > 0) {
            result.append(cmd[0]);
        } else {
            Optional<String> mergedStr = input.stream().map(t -> Integer.toString(t)).reduce((s1, s2) -> s1 + " " + s2);
            result.append(mergedStr.isPresent() ? mergedStr.get() : "");
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(new CronExpression(args[0]).describe());
    }

    @Override
    public String toString() {
        return expression;
    }
}
