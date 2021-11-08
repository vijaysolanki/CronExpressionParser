package com.vjy.deliveroo.cron.fields;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class representing Day of Month field.
 *
 * @author vsolanki
 */
public class DayOfMonthField extends SimpleField {

    public DayOfMonthField(FieldType type, String fldExp) {
        super(type, fldExp);
    }

    public Collection<Integer> getDaysOfMonth(Collection<Integer> months) {
        return build(months);
    }

    @Override
    public Collection<Integer> get() {
        return get(List.of());
    }

    public Collection<Integer> get(Collection<Integer> months) {
        Collection<Integer> result;

        if (FieldType.DAY_OF_MONTH == type) {
            result = getDaysOfMonth(months);
        } else {
            result = super.get();
        }
        return result;
    }

    @Override
    protected void parse(String exp) {
        // handle range for expression part
        super.parse(exp);
        validateField(exp);
    }

    protected Collection<Integer> build(Collection<Integer> months) {

        Collection<Integer> dayOfMonth = new ArrayList<>();

        parts.forEach(fieldPart -> {
            if (null != fieldPart.metaChar) { // for L, W, LW meta char
                if ("L".equals(fieldPart.metaChar)) {
                    LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone());
                    int dayBeforelast = (fieldPart.from > -1 ? fieldPart.from : 0);
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
                } else if ("W".equals(fieldPart.metaChar)) {
                    int day = fieldPart.from;
                    if (null != months && !months.isEmpty()) {
                        for (int month : months) {
                            buildNearestWeekday(day, dayOfMonth, month);
                        }
                    } else {
                        int count = FieldType.MONTH.getFrom();
                        while (count <= FieldType.MONTH.getTo()) {
                            buildNearestWeekday(day, dayOfMonth, count);
                            count++;
                        }
                    }
                } else if ("LW".equals(fieldPart.metaChar)) {

                    if (null != months && !months.isEmpty()) {
                        for (int month : months) {
                            dayOfMonth.add(buildLastWeekday(month));
                        }
                    } else {
                        int count = FieldType.MONTH.getFrom();
                        while (count <= FieldType.MONTH.getTo()) {
                            dayOfMonth.add(buildLastWeekday(count));
                            count++;
                        }
                    }
                }
            } else {
                extractFieldsValue(dayOfMonth, fieldPart);
            }
        });

        return dayOfMonth;
    }

    private void buildNearestWeekday(int day, Collection<Integer> dayOfMonth, int month) {

//        dateTime = dateTime.withMonth(month).withDayOfMonth(day);
        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone()).withMonth(month);
        int lastDay = dateTime.getMonth().length(dateTime.toLocalDate().isLeapYear());
        dateTime = dateTime.withMonth(month).withDayOfMonth(day);

        switch (dateTime.getDayOfWeek()) {
            case SATURDAY:
                dayOfMonth.add(dateTime.getDayOfMonth() == 1 ? dateTime.getDayOfMonth() + 2 :
                        dateTime.getDayOfMonth() - 1);
                break;
            case SUNDAY:
                dayOfMonth.add(dateTime.getDayOfMonth() == lastDay ? dateTime.getDayOfMonth() - 2 :
                        dateTime.getDayOfMonth() + 1);
                break;
            default:
                dayOfMonth.add(dateTime.getDayOfMonth());
        }
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

    @Override
    protected void partMetaValidation(FieldPart part) {
        if (part.metaChar != null && !List.of("L", "W", "LW").contains(part.metaChar)) {
            throw new IllegalArgumentException(String.format("Invalid meta character  [%s]", part.metaChar));
        } else if (part.metaChar != null && null != part.incrementMetaChar) {
            throw new IllegalArgumentException("Invalid meta character '" + part.incrementMetaChar + "' for field [" + type + "]");
        } else if (part.incrementMetaChar != null && !"/".equals(part.incrementMetaChar)) {
            throw new IllegalArgumentException(String.format("Invalid increment modifier [%s]", part.incrementMetaChar));
        }
    }

    private void validateField(String exp) {
        if (parts.size() > 1 && parts.stream().anyMatch(part -> null != part.metaChar)) {
            throw new IllegalArgumentException("Invalid cron input '" + exp + "' for field [" + type + "]");
        }
    }
}
