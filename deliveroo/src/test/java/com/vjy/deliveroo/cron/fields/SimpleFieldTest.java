package com.vjy.deliveroo.cron.fields;

import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SimpleFieldTest {

    @Test
    public void parse_number() {
        SimpleField field = new SimpleField(FieldType.MINUTE, "5");
        assertEquals(Set.of(5), field.get());
    }

    @Test
    public void parse_number_with_increment() {
        SimpleField field = new SimpleField(FieldType.MINUTE, "0/15");
        assertEquals(Set.of(0, 15, 30, 45), field.get());
    }

    @Test
    public void parse_range() {
        SimpleField field = new SimpleField(FieldType.MINUTE, "5-10");
        assertEquals(Set.of(5, 6, 7, 8, 9, 10), field.get());
    }

    @Test
    public void parse_range_with_increment() {
        SimpleField field = new SimpleField(FieldType.MINUTE, "20-30/2");
        assertEquals(Set.of(20, 22, 24, 26, 28, 30), field.get());
    }

    @Test
    public void parse_asterix() {
        SimpleField field = new SimpleField(FieldType.MINUTE, "*");
        assertEquals(IntStream.range(0, 60).boxed().collect(Collectors.toSet()), field.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void give_error_if_minute_field_ignored() {
        new SimpleField(FieldType.MINUTE, "?");
    }

    @Test
    public void parse_number_hour() {
        SimpleField field = new SimpleField(FieldType.HOUR, "5");
        assertEquals(Set.of(5), field.get());
    }

    @Test
    public void parse_range_hour() {
        SimpleField field = new SimpleField(FieldType.HOUR, "5-10");
        assertEquals(Set.of(5, 6, 7, 8, 9, 10), field.get());
    }

    @Test
    public void parse_range_hour_with_increment() {
        SimpleField field = new SimpleField(FieldType.HOUR, "5-20/7");
        assertEquals(Set.of(5, 12, 19), field.get());
    }

    @Test
    public void parse_number_hour_with_increment() {
        SimpleField field = new SimpleField(FieldType.HOUR, "0/15");
        assertEquals(Set.of(0, 15), field.get());
    }

    @Test
    public void parse_asterix_hour() {
        SimpleField field = new SimpleField(FieldType.HOUR, "*");
        assertEquals(IntStream.range(0, 24).boxed().collect(Collectors.toSet()), field.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void give_error_if_hour_field_ignored() {
        new SimpleField(FieldType.HOUR, "?");
    }

    @Test
    public void parse_number_month() {
        SimpleField field = new SimpleField(FieldType.MONTH, "5");
        assertEquals(Set.of(5), field.get());
    }

    @Test
    public void parse_range_month() {
        SimpleField field = new SimpleField(FieldType.MONTH, "5-10");
        assertEquals(Set.of(5, 6, 7, 8, 9, 10), field.get());
    }

    @Test
    public void parse_range_month_with_increment() {
        SimpleField field = new SimpleField(FieldType.MONTH, "5-12/7");
        assertEquals(Set.of(5, 12), field.get());
    }

    @Test
    public void parse_number_month_with_increment() {
        SimpleField field = new SimpleField(FieldType.MONTH, "1/5");
        assertEquals(Set.of(1, 6, 11), field.get());
    }

    @Test
    public void parse_string_month_with_range() {
        SimpleField field = new SimpleField(FieldType.MONTH, "FEB-NOV");
        assertEquals(Set.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 11), field.get());
    }

    @Test
    public void parse_asterix_month() {
        SimpleField field = new SimpleField(FieldType.MONTH, "*");
        assertEquals(IntStream.range(1, 13).boxed().collect(Collectors.toSet()), field.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void give_error_if_month_field_ignored() {
        new SimpleField(FieldType.MONTH, "?");
    }
}