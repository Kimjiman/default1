package com.example.default1.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {
    private static final String DATE_PATTERN = "yyyy.MM.dd";
    private static final String DATETIME_PATTERN = "yyyy.MM.dd HH:mm:ss";
    private static final String TIME_PATTERN = "HH:mm:ss";

    public static boolean isValidDateFormat(String dateString, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            formatter.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date stringToDate(String dateString, String pattern) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.parse(dateString);
    }

    public static Date localDateTimeToDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime stringToLocalDateTime(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateString, formatter);
    }

    public static String toDateString(LocalDateTime date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toDateString(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toDateString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String getDateString(LocalDateTime date) {
        return toDateString(date, DATE_PATTERN);
    }

    public static String getDateTimeString(LocalDateTime date) {
        return toDateString(date, DATETIME_PATTERN);
    }

    public static String getTimeString(LocalDateTime date) {
        return toDateString(date, TIME_PATTERN);
    }

    public static Integer getDayOfWeekNumber(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public static String getDayOfWeekString(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
            default:
                return "";
        }
    }

    public static int compareTo(LocalDateTime date, LocalDateTime date2) {
        return date.compareTo(date2);
    }

    public static long daysBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static boolean isBetween(LocalDateTime date, LocalDateTime date2, LocalDateTime targetDate) {
        LocalDateTime startDate = (date.isBefore(date2)) ? date : date2;
        LocalDateTime endDate = (date.isAfter(date2)) ? date : date2;

        return (targetDate.equals(startDate) || targetDate.isAfter(startDate)) &&
                (targetDate.equals(endDate) || targetDate.isBefore(endDate));
    }

    public static String getDateString(Date date) {
        return toDateString(date, DATE_PATTERN);
    }

    public static String getDateTimeString(Date date) {
        return toDateString(date, DATETIME_PATTERN);
    }

    public static String getTimeString(Date date) {
        return toDateString(date, TIME_PATTERN);
    }

    public static Integer getDayOfWeekNumber(Date date) {
        LocalDateTime date2 = dateToLocalDateTime(date);
        return getDayOfWeekNumber(date2);
    }

    public static String getDayOfWeekString(Date date) {
        LocalDateTime date2 = dateToLocalDateTime(date);
        return getDayOfWeekString(date2);
    }

    public static int compareTo(Date date, Date date2) {
        return date.compareTo(date2);
    }

    public static long daysBetween(Date startDate, Date endDate) {
        LocalDateTime start = dateToLocalDateTime(startDate);
        LocalDateTime end = dateToLocalDateTime(endDate);
        return daysBetween(start, end);
    }

    public static boolean isBetween(Date date, Date date2, Date targetDate) {
        Date startDate = (date.before(date2)) ? date : date2;
        Date endDate = (date.after(date2)) ? date : date2;

        return (targetDate.equals(startDate) || targetDate.after(startDate)) &&
                (targetDate.equals(endDate) || targetDate.before(endDate));
    }
}
