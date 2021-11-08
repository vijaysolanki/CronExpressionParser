package com.vjy.deliveroo.cron.fields;

import org.junit.Test;
import util.Utility;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DayOfWeekFieldTest {

    @Test
    public void parse_number() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "5");
        assertEquals(List.of(5), field.get());
    }

    @Test
    public void parse_number_with_increment() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "2/3");
        assertEquals(List.of(2, 5), field.get());
    }

    @Test
    public void parse_range() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "1-6");
        assertEquals(List.of(1, 2, 3, 4, 5, 6), field.get());
    }

    @Test
    public void parse_range_with_increment() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "2-7/2");
        assertEquals(List.of(2, 4, 6), field.get());
    }

    @Test
    public void parse_dayOfWeek_complex() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "1,3-4,5-7/2");
        assertEquals(List.of(1, 3, 4, 5, 7), field.get());
    }

    @Test
    public void parse_dayOfWeek_last_sunday_in_month_all() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "L");
        List<Integer> values = Utility.getLastDayOfWeekInMonths(7, List.of(3));
        assertEquals(values, field.get(List.of(3)));
    }

    @Test
    public void parse_dayOfWeek_last_tuesday_in_month() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "2L");
        List<Integer> values = Utility.getLastDayOfWeekInMonths(2, List.of(1, 2, 3));
        assertEquals(values, field.get(List.of(1, 2, 3)));
    }

    @Test
    public void parse_dayOfWeek_last_friday_string_in_month() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "FRIL");
        List<Integer> values = Utility.getLastDayOfWeekInMonths(5, List.of());
        assertEquals(values, field.get(List.of()));
    }

    @Test
    public void parse_dayOfWeek_nth_day_in_month() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "7#4");
        List<Integer> values = Utility.getNthDayOfWeekInMonth(7, 4, List.of());
        assertEquals(values, field.get(List.of()));
    }

    @Test
    public void parse_dayOfWeek_nth_day_in_month_skip_month() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "7#5");
        List<Integer> values = Utility.getNthDayOfWeekInMonth(7, 5, List.of());
        assertEquals(values, field.get(List.of()));
    }

    @Test
    public void parse_string_range_with_range() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "MON-FRI");
        assertEquals(List.of(1, 2, 3, 4, 5), field.get());
    }

    @Test
    public void parse_asterix() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "*");
        assertEquals(IntStream.range(1, 8).boxed().collect(Collectors.toList()), field.get());
    }

    @Test
    public void parse_asterix_with_increment() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "*/2");
        assertEquals(List.of(1, 3, 5, 7), field.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_field_in_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "?");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_range_in_day_of_week_with_last() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "1-3L");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_increment_in_day_of_week_with_last() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "3L/3");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_list_in_day_of_week_with_last() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "1,3L,6");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_increment_weekday_in_nth_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "5#3/5");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_list_weekday_in_nth_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "5#3,5");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_weekday_in_nth_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "5#3-8");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_option_in_nth_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "2W");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_range_weekday_in_nth_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "1-5#3");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_day_in_nth_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "15#3");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_week_in_nth_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "5#10");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_range_weekday_in_nth_day_of_week_with_last() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "4L-3");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_weekday_in_nth_day_of_week_with_last() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "8L");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_increment_weekday_in_nth_day_of_week_with_last() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "4L/3");
        assertFalse(field.get().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_string_no_rollover() {
        new DayOfWeekField(FieldType.DAY_OF_WEEK, "sun-fri");
    }
}