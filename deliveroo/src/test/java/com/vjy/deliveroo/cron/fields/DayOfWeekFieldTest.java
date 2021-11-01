package com.vjy.deliveroo.cron.fields;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DayOfWeekFieldTest {

    public void testMapToValue() {
    }

    public void testGetDaysOfWeek() {
    }

    public void testGet() {
    }

    @Test
    public void parse_number() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "5");
        assertEquals(new HashSet<>(Arrays.asList(5)), field.get());
    }

    @Test
    public void parse_number_with_increment() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "2/3");
        assertEquals(new HashSet<>(Arrays.asList(2, 5)), field.get());
    }

    @Test
    public void parse_range() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "1-6");
        assertEquals(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6)), field.get());
    }

    @Test
    public void parse_range_with_increment() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "2-7/2");
        assertEquals(new HashSet<>(Arrays.asList(2, 4, 6)), field.get());
    }

    @Test
    public void parse_string_range_with_range() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "MON-FRI");
        assertEquals(new HashSet<>(Arrays.asList(1,2,3,4,5)), field.get());
    }

    @Test
    public void parse_asterix() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "*");
        assertEquals(new HashSet<>(IntStream.range(1, 8).boxed().collect(Collectors.toList())), field.get());
    }


    @Test
    public void parse_asterix_with_increment() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "*/2");
        assertEquals(new HashSet<>(Arrays.asList(1, 3, 5, 7)), field.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ignore_field_in_day_of_week() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "?");
        assertFalse(field.get().isEmpty());
    }

     @Test(expected = IllegalArgumentException.class)
    public void parse_string_no_rollover() {
        DayOfWeekField field = new DayOfWeekField(FieldType.DAY_OF_WEEK, "sun-fri");
    }
}