package com.vjy.deliveroo.cron.fields;

import org.junit.Test;
import util.Utility;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DayOfMonthFieldTest {

    @Test
    public void parse_number() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "5");
        assertEquals(List.of(5), field.get());
    }

    @Test
    public void parse_number_with_increment() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "4/7");
        assertEquals(List.of(4, 11, 18, 25), field.get());
    }

    @Test
    public void parse_range() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "15-20");
        assertEquals(List.of(15, 16, 17, 18, 19, 20), field.get());
    }

    @Test
    public void parse_range_with_increment() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "2-12/3");
        assertEquals(List.of(2, 5, 8, 11), field.get());
    }

    @Test
    public void parse_last_day_with_all() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "L");
        List<Integer> values = Utility.getLastDayOfEveryMonth(0);
        assertEquals(values, field.get());
    }

    @Test
    public void parse_nth_day_from_last_day() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "3L");
        List<Integer> values = Utility.getLastDayOfEveryMonth(3);
        assertEquals(values, field.get());
    }

    @Test
    public void parse_dayOfMonth_closest_weekday_W() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "10W");
        List<Integer> values = Utility.getNearestWeekday(10, List.of(4));
        assertEquals(values, field.get(List.of(4)));
    }

    @Test
    public void parse_dayOfMonth_closest_weekday_first_day_of_month() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "1W");
        LocalDateTime dateTime = LocalDateTime.now(Clock.systemDefaultZone()).withYear(2021);
        List<Integer> values = Utility.getNearestWeekday(1, List.of(5), dateTime);
        assertEquals(values, field.get(List.of(5)));
    }

    @Test
    public void parse_dayOfMonth_closest_weekday_all() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "10W");
        List<Integer> values = Utility.getNearestWeekday(10, List.of());
        assertEquals(values, field.get());
    }

    @Test
    public void parse_dayOfMonth_last_weekday_all() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "LW");
        List<Integer> values = Utility.getLastWeekDayInMonth(List.of(2));
        System.out.println(values);
        System.out.println(field.get(List.of(2)));
        assertEquals(values, field.get(List.of(2)));
    }

    @Test
    public void parse_asterix() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "*");
        assertEquals(IntStream.range(1, 32).boxed().collect(Collectors.toList()), field.get());
    }


    @Test(expected = IllegalArgumentException.class)
    public void ignore_field_in_day_of_month() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "?");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_range_for_last_month() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "3L-3");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_increment_for_last_month() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "3L/3");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_list_for_last_month() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "3,4L");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = DateTimeException.class)
    public void invalid_month_for_weekday() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "29W");
        List<Integer> values = Utility.getNearestWeekday(29, List.of(2));
        assertEquals(values, field.get(List.of(2)));
    }
}