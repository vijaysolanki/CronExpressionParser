package com.vjy.deliveroo.cron;

import org.junit.Test;
import util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static util.Utility.buildDescription;
import static util.Utility.getDayOfMonths;

public class CronExpressionTest {

    @Test(expected = IllegalArgumentException.class)
    public void give_error_if_invalid_count_field() {
        new CronExpression("* 3 *");
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_invalid_input() {
        new CronExpression(null);
    }

    @Test
    public void check_all() {
        CronExpression cronExpr = new CronExpression("* * * * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = IntStream.range(0, 60).boxed().collect(Collectors.toList());
        List<Integer> hour = IntStream.range(0, 24).boxed().collect(Collectors.toList());
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_minute_number() {
        CronExpression cronExpr = new CronExpression("3 * * * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(3);
        List<Integer> hour = IntStream.range(0, 24).boxed().collect(Collectors.toList());
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_minute_increment() {
        CronExpression cronExpr = new CronExpression("0/15 * * * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0, 15, 30, 45);
        List<Integer> hour = IntStream.range(0, 24).boxed().collect(Collectors.toList());
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");
        assertEquals(buildDescription, des);

    }

    @Test
    public void check_minute_list() {
        CronExpression cronExpr = new CronExpression("7,19 * * * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(7, 19);
        List<Integer> hour = IntStream.range(0, 24).boxed().collect(Collectors.toList());
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_hour_number() {
        CronExpression cronExpr = new CronExpression("* 3 * * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = IntStream.range(0, 60).boxed().collect(Collectors.toList());
        List<Integer> hour = List.of(3);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());


        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_hour_increment() {
        CronExpression cronExpr = new CronExpression("* 0/15 * * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = IntStream.range(0, 60).boxed().collect(Collectors.toList());
        List<Integer> hour = List.of(0, 15);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_hour_list() {
        CronExpression cronExpr = new CronExpression("* 7,19 * * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = IntStream.range(0, 60).boxed().collect(Collectors.toList());
        List<Integer> hour = List.of(7, 19);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);

    }

    @Test
    public void check_dayOfMonth_number() {
        CronExpression cronExpr = new CronExpression("* * 3 * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = IntStream.range(0, 60).boxed().collect(Collectors.toList());
        List<Integer> hour = IntStream.range(0, 24).boxed().collect(Collectors.toList());
        List<Integer> dayOfMonth = List.of(3);
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_dayOfMonth_increment() {
        CronExpression cronExpr = new CronExpression("0 0 1/15 * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = List.of(1, 16, 31);
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);

    }

    @Test
    public void check_dayOfMonth_list() {
        CronExpression cronExpr = new CronExpression("0 0 7,19 * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = List.of(7, 19);
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);

    }

    @Test(expected = IllegalArgumentException.class)
    public void check_dayOfMonth_invalid_modifier() {
        new CronExpression("0 0 9X * * /usr/bin/find");
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_dayOfMonth_invalid_increment_modifier() {
        new CronExpression("0 0 9#2 * * /usr/bin/find");
    }

    @Test
    public void check_month_number() {
        CronExpression cronExpr = new CronExpression("0 0 1 5 * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = List.of(1);
        List<Integer> month = List.of(5);
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_month_increment() {
        CronExpression cronExpr = new CronExpression("0 0 1 5/2 * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = List.of(1);
        List<Integer> month = List.of(5, 7, 9, 11);
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_month_list() {
        CronExpression cronExpr = new CronExpression("0 0 1 3,7,12 * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = List.of(1);
        List<Integer> month = List.of(3, 7, 12);
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_month_list_by_name() {
        CronExpression cronExpr = new CronExpression("0 0 1 MAR,JUL,DEC * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = List.of(1);
        List<Integer> month = List.of(3, 7, 12);
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_dayOfMonth_last() {
        CronExpression cronExpr = new CronExpression("0 0 L * * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfMonth = getDayOfMonths(month, 0);
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_dayOfMonth_number_last_L() {
        CronExpression cronExpr = new CronExpression("0 0 3L 1,3,6 * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> month = List.of(1, 3, 6);
        List<Integer> dayOfMonth = getDayOfMonths(month, 3);
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");
        assertEquals(buildDescription, des);
    }

    @Test
    public void check_dayOfMonth_closest_weekday_W() {
        CronExpression cronExpr = new CronExpression("0 0 10W 5 * /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> month = List.of(5);
        List<Integer> dayOfMonth = Utility.getNearestWeekday(10, List.of(5)/*, dateTime*/);
        List<Integer> dayOfWeek = IntStream.range(1, 8).boxed().collect(Collectors.toList());

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");
        assertEquals(buildDescription, des);
    }


    @Test(expected = IllegalArgumentException.class)
    public void check_dayofmonth_invalid_modifier() {
        new CronExpression("0 0 1 L-3 * /usr/bin/find");
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_dayofmonth_last_with_list() {
        new CronExpression("0 0 1 1,2,3L * /usr/bin/find");
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_month_invalid_modifier() {
        new CronExpression("0 0 1 ? * /usr/bin/find");
    }


    @Test
    public void check_dayOfWeek_number() {
        CronExpression cronExpr = new CronExpression("0 0 * * 3 /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = List.of(3);

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);

    }

    @Test
    public void check_dayOfWeek_complex() {
        CronExpression cronExpr = new CronExpression("0 0 * * 1-2,3/2 /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = List.of(1, 2, 3, 5, 7);

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);

    }


    @Test
    public void check_dayOfWeek_increment() {
        CronExpression cronExpr = new CronExpression("0 0 * * 3/2 /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = List.of(3, 5, 7);

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_dayOfWeek_list() {
        CronExpression cronExpr = new CronExpression("0 0 * * 1,5,7 /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = List.of(1, 5, 7);

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test
    public void check_dayOfWeek_list_by_name() {
        CronExpression cronExpr = new CronExpression("0 0 * * MON,FRI,SUN /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = List.of(1, 5, 7);

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_dayOfWeek_invalid_modifier() {
        new CronExpression("0 0 * * 5W /usr/bin/find");
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_dayOfWeek_invalid_increment_modifier() {
        new CronExpression("0 0 * * 5?3 /usr/bin/find");
    }

    @Test
    public void check_dayOfWeek_shall_interpret_0_as_sunday() {
        CronExpression cronExpr = new CronExpression("0 0 * * 0 /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = List.of(7);

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);

    }

    @Test
    public void check_dayOfWeek_shall_interpret_7_as_sunday() {
        CronExpression cronExpr = new CronExpression("0 0 * * 7 /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(0);
        List<Integer> hour = List.of(0);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = List.of(7);

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");

        assertEquals(buildDescription, des);

    }

    @Test
    public void check_dayOfWeek_last_friday_in_month() {
        CronExpression cronExpr = new CronExpression("1 3 * * L /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(1);
        List<Integer> hour = List.of(3);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = IntStream.range(1, 13).boxed().collect(Collectors.toList());
        List<Integer> dayOfWeek = Utility.getLastDayOfWeekInMonths(0, new ArrayList<>(month));

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");
        assertEquals(buildDescription, des);
    }

    @Test
    public void check_dayOfWeek_nth_day_in_month() {
        CronExpression cronExpr = new CronExpression("1 3 * 4,5,6 3#2 /usr/bin/find");
        String des = cronExpr.describe();

        List<Integer> min = List.of(1);
        List<Integer> hour = List.of(3);
        List<Integer> dayOfMonth = IntStream.range(1, 32).boxed().collect(Collectors.toList());
        List<Integer> month = List.of(4, 5, 6);
        List<Integer> dayOfWeek = Utility.getNthDayOfWeekInMonth(3, 2, month);

        String buildDescription = buildDescription(cronExpr, min, hour, dayOfMonth, month, dayOfWeek, "/usr/bin/find");
        assertEquals(buildDescription, des);
    }

    @Test
    public void expression_tostring() {
        CronExpression cronExpr = new CronExpression("0 0 * * 7 /usr/bin/find");
        assertEquals("0 0 * * 7 /usr/bin/find", cronExpr.toString());
    }

    @Test
    public void check_cli_execution() {
        CronExpression.main(new String[]{"0 0 * * 7 /usr/bin/find"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_cli_execution_fail() {
        CronExpression.main(new String[]{"0 0 * * 7"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void shall_not_not_support_rolling_period() {
        new CronExpression("* 5-1 * * * /usr/bin/find");
    }
}
