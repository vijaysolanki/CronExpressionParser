package util;

import com.vjy.deliveroo.cron.CronExpression;
import com.vjy.deliveroo.cron.fields.FieldType;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final public class Utility {

    public static List<Integer> getLastDayOfEveryMonth(int exclude) {
        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone());
        int count = FieldType.MONTH.getFrom();
        dateTime = dateTime.withMonth(count);

        List<Integer> values = new ArrayList<>();

        while (count++ <= FieldType.MONTH.getTo()) {
            values.add(dateTime.getMonth().length(dateTime.toLocalDate().isLeapYear()) - exclude);
            dateTime = dateTime.plusMonths(1);
        }
        return values;
    }

    public static List<Integer> getNearestWeekday(int day, List<Integer> months, LocalDateTime... customDateTime) {
        LocalDateTime dateTime = (customDateTime != null && customDateTime.length > 0) ? customDateTime[0] : LocalDateTime.now(Clock.systemDefaultZone());
        List<Integer> dayOfMonth = new ArrayList<>();

        if (null != months && !months.isEmpty()) {
            for (int month : months) {
                buildNearestWeekday(day, dateTime, dayOfMonth, month);
            }
        } else {
            int count = FieldType.MONTH.getFrom();
            while (count <= FieldType.MONTH.getTo()) {
                buildNearestWeekday(day, dateTime, dayOfMonth, count);
                count++;
            }
        }
        return dayOfMonth;
    }

    private static void buildNearestWeekday(int day, LocalDateTime dateTime, List<Integer> dayOfMonth, int month) {

        dateTime = dateTime.withMonth(month).withDayOfMonth(day);
        switch (dateTime.getDayOfWeek()) {
            case SATURDAY:
                dayOfMonth.add(dateTime.getDayOfMonth() == 1 ? dateTime.getDayOfMonth() + 2 :
                        dateTime.getDayOfMonth() - 1);
                break;
            case SUNDAY:
                dayOfMonth.add(dateTime.getDayOfMonth() == FieldType.DAY_OF_MONTH.getTo() ? dateTime.getDayOfMonth() - 2 :
                        dateTime.getDayOfMonth() + 1);
                break;
            default:
                dayOfMonth.add(dateTime.getDayOfMonth());
        }
    }

    public static List<Integer> getDayOfMonths(List<Integer> months, int dayBeforelast) {
        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone());
        List<Integer> dayOfMonth = new ArrayList<>();

        if (null != months && !months.isEmpty()) {
            for (int month : months) {
                dateTime = dateTime.withMonth(month);
                dayOfMonth.add(dateTime.getMonth().length(dateTime.toLocalDate().isLeapYear()) - dayBeforelast);
            }
        } else {
            int count = FieldType.MONTH.getFrom();
            dateTime = dateTime.withMonth(count);
            while (count++ <= FieldType.MONTH.getTo()) {
                dayOfMonth.add(dateTime.getMonth().length(dateTime.toLocalDate().isLeapYear()) - dayBeforelast);
                dateTime = dateTime.plusMonths(1);
            }
        }
        return dayOfMonth;
    }

    public static String buildDescription(CronExpression exp, Collection<Integer> min, Collection<Integer> hour, Collection<Integer> dayOfMonth,
                                          Collection<Integer> month, Collection<Integer> dayOfWeek, String cmd) {

        StringBuilder result = new StringBuilder();
        result.append(exp.getString(FieldType.MINUTE.getIdentifier(), min));
        result.append("\n");

        result.append(exp.getString(FieldType.HOUR.getIdentifier(), hour));
        result.append("\n");

        result.append(exp.getString(FieldType.DAY_OF_MONTH.getIdentifier(), dayOfMonth));
        result.append("\n");

        result.append(exp.getString(FieldType.MONTH.getIdentifier(), month));
        result.append("\n");

        result.append(exp.getString(FieldType.DAY_OF_WEEK.getIdentifier(), dayOfWeek));
        result.append("\n");

        result.append(exp.getString("command", Collections.<Integer>emptySet(), cmd));
        return result.toString();
    }

    public static List<Integer> getLastDayOfWeekInMonths(int dayOfWeek, Collection<Integer> months) {
        List<Integer> dayOfWeekInMonth = new ArrayList<>();
        dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;

        if (null != months && !months.isEmpty()) {
            for (int month : months) {
                dayOfWeekInMonth.add(buildMonthDayOfWeek(dayOfWeek, month));
            }
        } else {
            int count = FieldType.MONTH.getFrom();
            while (count <= FieldType.MONTH.getTo()) {
                dayOfWeekInMonth.add(buildMonthDayOfWeek(dayOfWeek, count));
                count++;
            }
        }
        return dayOfWeekInMonth;
    }

    private static int buildMonthDayOfWeek(int dayOfWeek, int month) {

        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone()).withMonth(month).withDayOfMonth(1).withHour(0).withMinute(0);
        while (dayOfWeek != dateTime.getDayOfWeek().getValue()) {
            dateTime = dateTime.plusDays(1);
        }

        int currentDay = dateTime.getDayOfMonth();
        while (currentDay + 7 <= dateTime.getMonth().length(dateTime.toLocalDate().isLeapYear())) {
            currentDay = currentDay + 7;
        }

        return currentDay;
    }

    public static List<Integer> getNthDayOfWeekInMonth(int dayOfWeek, int week, Collection<Integer> months) {
        List<Integer> dayOfWeekInMonth = new ArrayList<>();
        dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;

        if (null != months && !months.isEmpty()) {
            for (int month : months) {
                int nthDay = buildMonthDayWithWeekRange(dayOfWeek, week, month);
                if(Integer.MIN_VALUE != nthDay) {
                    dayOfWeekInMonth.add(nthDay);
                }
            }
        } else {
            int count = FieldType.MONTH.getFrom();
            while (count <= FieldType.MONTH.getTo()) {
                int nthDay = buildMonthDayWithWeekRange(dayOfWeek, week, count);
                if(Integer.MIN_VALUE != nthDay) {
                    dayOfWeekInMonth.add(nthDay);
                }
                count++;
            }
        }
        return dayOfWeekInMonth;
    }

    private static int buildMonthDayWithWeekRange(int dayOfWeek, int week, int month) {

        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone()).withMonth(month).withDayOfMonth(1).withHour(0).withMinute(0);
        while (dayOfWeek != dateTime.getDayOfWeek().getValue()) {
            dateTime = dateTime.plusDays(1);
        }

        int currentDay = dateTime.getDayOfMonth();
        while (week > 1 && currentDay + 7 <= dateTime.getMonth().length(dateTime.toLocalDate().isLeapYear())) {
            currentDay = currentDay + 7;
            week --;
        }

        return week == 1 ? currentDay : Integer.MIN_VALUE;
//        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone()).withMonth(month).withDayOfMonth(1).withHour(0).withMinute(0);
//        dateTime = dateTime.plusWeeks(week - 1);
//
//        while (dayOfWeek != dateTime.getDayOfWeek().getValue()) {
//            dateTime = dateTime.plusDays(1);
//        }
//
//        return dateTime.getDayOfMonth();
    }

    public static List<Integer> getLastWeekDayInMonth(Collection<Integer> months) {

        List<Integer> lastDayOfMonth = new ArrayList<>();

        if (null != months && !months.isEmpty()) {
            for (int month : months) {
                lastDayOfMonth.add(buildLastWeekday(month));
            }
        } else {
            int count = FieldType.MONTH.getFrom();
            while (count <= FieldType.MONTH.getTo()) {
                lastDayOfMonth.add(buildLastWeekday(count));
                count++;
            }
        }
        return lastDayOfMonth;
    }

    private static int buildLastWeekday(int month) {
        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone()).withMonth(month);
        int lastDay = dateTime.getMonth().length(dateTime.toLocalDate().isLeapYear());
        dateTime = dateTime.withMonth(month).withDayOfMonth(lastDay);

        switch (dateTime.getDayOfWeek()) {
            case SATURDAY:
                return (dateTime.getDayOfMonth() == 1 ? dateTime.getDayOfMonth() + 2 :
                        dateTime.getDayOfMonth() - 1);
            case SUNDAY:
                return (dateTime.getDayOfMonth() == lastDay ? dateTime.getDayOfMonth() - 2 :
                        dateTime.getDayOfMonth() + 1);
            default:
                return (dateTime.getDayOfMonth());
        }
    }
}
