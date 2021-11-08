package com.vjy.deliveroo.cron.fields;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Class representing Day of Week field.
 *
 * @author vsolanki
 */
public class DayOfWeekField extends SimpleField {

    public DayOfWeekField(FieldType type, String fldExp) {
        super(type, fldExp);
    }

    @Override
    protected int mapToValue(String value) {
        // 0 and 7 are represented as sunday
        return "0".equals(value) ? 7 : super.mapToValue(value);
    }

    public Collection<Integer> getDaysOfWeek(Collection<Integer> months) {
        return build(months);
    }


    @Override
    public Collection<Integer> get() {
        return get(List.of());
    }

    public Collection<Integer> get(Collection<Integer> months) {
        Collection<Integer> result;

        if (FieldType.DAY_OF_WEEK == type) {
            result = getDaysOfWeek(months);
        } else {
            result = super.get();
        }
        return result;
    }

    @Override
    protected void parse(String exp) {
        // handle range for expression part
        super.parse(exp);
        //allow only range
        validateField(exp);
    }


    protected Collection<Integer> build(Collection<Integer> months) {

        Collection<Integer> dayOfWeekInMonth = new ArrayList<>();

        parts.forEach(fieldPart -> {

            if (null != fieldPart.metaChar) { // for L  meta char
                if ("L".equals(fieldPart.metaChar)) {
                    int day = fieldPart.from == -1 ? 7 : fieldPart.from;
                    if (null != months && !months.isEmpty()) {
                        for (int month : months) {
                            dayOfWeekInMonth.add(buildMonthDayOfWeek(day, month));
                        }
                    } else {
                        int count = FieldType.MONTH.getFrom();
                        while (count <= FieldType.MONTH.getTo()) {
                            dayOfWeekInMonth.add(buildMonthDayOfWeek(day, count));
                            count++;
                        }
                    }
                }
            } else if ("#".equals(fieldPart.incrementMetaChar)) {
                int dayOfWeek = fieldPart.from == 0 ? 7 : fieldPart.from;
                int week = fieldPart.incrementValue;
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
            } else {
                extractFieldsValue(dayOfWeekInMonth, fieldPart);
            }
        });

        return dayOfWeekInMonth;
    }

    private int buildMonthDayOfWeek(int dayOfWeek, int month) {

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
    }

    @Override
    protected void partMetaValidation(FieldPart part) {
        if (part.metaChar != null && !Objects.equals("L", part.metaChar)) {
            throw new IllegalArgumentException(String.format("Invalid meta character  [%s]", part.metaChar));
        } else if (part.metaChar != null && null != part.incrementMetaChar) {
            throw new IllegalArgumentException("Invalid meta character '" + part.incrementMetaChar + "' for field [" + type + "]");
        } else if (part.incrementMetaChar != null && !List.of("/", "#").contains(part.incrementMetaChar)) {
            throw new IllegalArgumentException(String.format("Invalid increment modifier [%s]", part.incrementMetaChar));
        }

    }

    @Override
    protected void rangeValidation(FieldPart part) {
        if ((part.from != -1 && part.from < type.getFrom()) || (part.to != -1 && part.to > type.getTo())) {
            throw new IllegalArgumentException(String.format("Invalid interval [%s-%s], must be [%s-%s] for field [%s]",
                    part.from, part.to, type.getFrom(), type.getTo(), type));
        } else if (part.from != -1 && part.to != -1 && part.from > part.to) {
            throw new IllegalArgumentException(String.format(
                    "Invalid interval [%s-%s].  Rolling periods are not supported (ex. 4-1, only 1-4). Must be %s<=_<=%s",
                    part.from, part.to, type.getFrom(), type.getTo()));
        } else if (part.incrementValue != -1 && (part.incrementValue > type.getTo() || part.incrementValue < type.getFrom())) {
            throw new IllegalArgumentException(String.format("Invalid input [%s-%s], Must be %s<=_<=%s for field [%s]",
                    part.from, part.incrementValue, type.getFrom(), type.getTo(), type));
        }
    }

    private void validateField(String exp) {
        if (parts.size() > 1) {
            parts.forEach(part -> {
                if (null != part.metaChar) {
                    throw new IllegalArgumentException("Invalid cron input '" + exp + "' for field [" + type + "]");
                } else if ("#".equals(part.incrementMetaChar)) {
                    throw new IllegalArgumentException("Invalid cron input '" + exp + "' for field [" + type + "]");
                }
            });
        } else {
            //dirty fix for invalid expression like 1-5#3
            if (exp.matches(".*-.*#.*")) {
                throw new IllegalArgumentException("Invalid cron input '" + exp + "' for field [" + type + "]");
            }
        }
    }
}
