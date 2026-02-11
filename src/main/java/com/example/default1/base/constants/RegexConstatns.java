package com.example.default1.base.constants;

public class RegexConstatns {
    // 아이디: 영문소문자,-,_,.숫자
    public static final String ID = "^[a-z\\-_.0-9]{6,16}$";
    // 비밀번호 : 숫자,문자,특수문자 혼용 8~20자
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
    // 이름 : 영문대소문자,한글완성형,특수문자허용 2~10자
    public static final String NAME = "^[a-zA-Z가-힣?=.*\\[~!@#$%^&()\\-_+\\]]{2,10}";
    // 이메일 : 이메일형식
    public static final String EMAIL = "[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]{3,4}$";
    // 휴대전화번호 : 숫자 10~11자
    public static final String MOBILE = "^[0-9]{10,11}$";
}
