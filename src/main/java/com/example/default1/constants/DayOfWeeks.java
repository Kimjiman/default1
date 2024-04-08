package com.example.default1.constants;

import com.example.default1.exception.CustomException;

public enum DayOfWeeks {
    MONDAY(1, "월요일", "월", "Monday", "Mon."),
    TUESDAY(2, "화요일", "화", "Tuesday", "Tue."),
    WEDNESDAY(3, "수요일", "수", "Wednesday", "Wed."),
    THURSDAY(4, "목요일", "목", "Thursday", "Thu."),
    FRIDAY(5, "금요일", "금", "Friday", "Fri."),
    SATURDAY(6, "토요일", "토", "Saturday", "Sat."),
    SUNDAY(7, "일요일", "일", "Sunday", "Sun.");

    private int dayOfWeekNumber;
    private String kor;
    private String korShort;
    private String eng;
    private String engShort;

    DayOfWeeks(int dayOfWeekNumber, String kor, String korShort, String eng, String engShort) {
        this.dayOfWeekNumber = dayOfWeekNumber;
        this.kor = kor;
        this.korShort = korShort;
        this.eng = eng;
        this.engShort = engShort;
    }

    public int getDayOfWeekNumber() {
        return dayOfWeekNumber;
    }

    public String getKor() {
        return kor;
    }

    public String getKorShort() {
        return korShort;
    }

    public String getEng() {
        return eng;
    }

    public String getEngShort() {
        return engShort;
    }

    public static String getKor(int dayOfWeekNumber) {
        String val = null;
        for (DayOfWeeks dayOfweek : values()) {
            if (dayOfweek.dayOfWeekNumber == dayOfWeekNumber) {
                val = dayOfweek.getKor();
                break;
            }
        }
        if(val == null) throw new CustomException(2000, "getKor: Suggest dayOfWeekNumber is not exist in DayOfWeeks.");
        return val;
    }

    public static String getKorShort(int dayOfWeekNumber) {
        String val = null;
        for (DayOfWeeks dayOfweek : values()) {
            if (dayOfweek.dayOfWeekNumber == dayOfWeekNumber) {
                val = dayOfweek.getKorShort();
                break;
            }
        }
        if(val == null) throw new CustomException(2000, "getKor: Suggest dayOfWeekNumber is not exist in DayOfWeeks.");
        return val;
    }

    public static String getEng(int dayOfWeekNumber) {
        String val = null;
        for (DayOfWeeks dayOfweek : values()) {
            if (dayOfweek.dayOfWeekNumber == dayOfWeekNumber) {
                val = dayOfweek.getEng();
                break;
            }
        }
        if(val == null) throw new CustomException(2000, "getKor: Suggest dayOfWeekNumber is not exist in DayOfWeeks.");
        return val;
    }

    public static String getEngShort(int dayOfWeekNumber) {
        String val = null;
        for (DayOfWeeks dayOfweek : values()) {
            if (dayOfweek.dayOfWeekNumber == dayOfWeekNumber) {
                val = dayOfweek.getEngShort();
                break;
            }
        }
        if(val == null) throw new CustomException(2000, "getKor: Suggest dayOfWeekNumber is not exist in DayOfWeeks.");
        return val;
    }
}
