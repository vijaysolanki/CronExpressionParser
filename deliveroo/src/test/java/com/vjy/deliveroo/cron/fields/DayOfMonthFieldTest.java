package com.vjy.deliveroo.cron.fields;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DayOfMonthFieldTest {

    public void testGetDaysOfMonth() {
    }

    public void testGet() {
    }

    @Test
    public void parse_number() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "5");
        assertEquals(new HashSet<>(Arrays.asList(5)), field.get());
    }

    @Test
    public void parse_number_with_increment() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "4/7");
        assertEquals(new HashSet<>(Arrays.asList(4, 11, 18, 25)), field.get());
    }

    @Test
    public void parse_range() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "15-20");
        assertEquals(new HashSet<>(Arrays.asList(15, 16, 17, 18, 19, 20)), field.get());
    }

    @Test
    public void parse_range_with_increment() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "2-12/3");
        assertEquals(new HashSet<>(Arrays.asList(2, 5, 8, 11)), field.get());
    }

    @Test
    public void parse_asterix() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "*");
        assertEquals(new HashSet<>(IntStream.range(1, 32).boxed().collect(Collectors.toList())), field.get());
    }


    @Test(expected = IllegalArgumentException.class)
    public void ignore_field_in_day_of_month() {
        DayOfMonthField field = new DayOfMonthField(FieldType.DAY_OF_MONTH, "?");
        assertFalse(field.get().isEmpty());
    }
}