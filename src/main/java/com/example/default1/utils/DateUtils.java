package com.example.default1.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy.MM.dd";
    private static final String DATETIME_FORMAT = "yyyy.MM.dd HH:mm:ss";
    private static final String TIME_FORMAT = "HH:mm:ss";

    public static Date toDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date stringToDateTime(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
        return formatter.parse(dateString);
    }

    public static LocalDateTime stringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        return LocalDateTime.parse(dateString, formatter);
    }

    public static String now() {
        return getDate(LocalDateTime.now());
    }

    public static String getDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String getDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String getDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String getDatetime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String getDatetime(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }

    public static String getDatetime(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }

    public static String getTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String getTime(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public static String getTime(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public static Integer getDayOfWeekNumber(Date date) {
        LocalDateTime date2 = toLocalDateTime(date);
        DayOfWeek dayOfWeek = date2.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public static Integer getDayOfWeekNumber(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public static Integer getDayOfWeekNumber(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public static int compareTo(Date date, Date date2) {
        return date.compareTo(date2);
    }

    public static int compareTo(LocalDateTime date, LocalDateTime date2) {
        return date.compareTo(date2);
    }

    public static int compareTo(LocalDate date, LocalDate date2) {
        return date.compareTo(date2);
    }

    public static boolean betweenTo(Date date, Date date2, Date targetDate) {
        Date startDate = (date.before(date2)) ? date : date2;
        Date endDate = (date.after(date2)) ? date : date2;

        return (targetDate.equals(startDate) || targetDate.after(startDate)) &&
                (targetDate.equals(endDate) || targetDate.before(endDate));
    }

    public static boolean betweenTo(LocalDateTime date, LocalDateTime date2, LocalDateTime targetDate) {
        LocalDateTime startDate = (date.isBefore(date2)) ? date : date2;
        LocalDateTime endDate = (date.isAfter(date2)) ? date : date2;

        return (targetDate.equals(startDate) || targetDate.isAfter(startDate)) &&
                (targetDate.equals(endDate) || targetDate.isBefore(endDate));
    }

    public static boolean betweenTo(LocalDate date, LocalDate date2, LocalDate targetDate) {
        LocalDate startDate = (date.isBefore(date2)) ? date : date2;
        LocalDate endDate = (date.isAfter(date2)) ? date : date2;

        return (targetDate.equals(startDate) || targetDate.isAfter(startDate)) &&
                (targetDate.equals(endDate) || targetDate.isBefore(endDate));
    }
}
