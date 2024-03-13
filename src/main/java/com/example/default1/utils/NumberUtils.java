package com.example.default1.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    /**
     * 부동소수점 계산
     * @param val 소수점 데이터
     * @param multiplier 곱할 변수
     * @param length 소수점 자리수
     * HALF_UP: 반올림. 소수 부분이 0.5 이상이면 올림, 0.5 미만이면 내림.
     * HALF_DOWN: 반올림. 소수 부분이 0.5 이상이면 올림, 0.5 미만이면 내림.
     * UP: 올림. 언제나 양수 방향으로 반올림.
     * DOWN: 내림. 언제나 음수 방향으로 반올림.
     * CEILING: 양의 무한대 방향으로 올림.
     * FLOOR: 음의 무한대 방향으로 내림.
     * HALF_EVEN: "짝수" 방식의 반올림. 소수 부분이 0.5일 경우, 짝수 자릿수에서 가장 가까운 짝수로 반올림.
     */
    public static <T extends Number> String multiplyAndRound(T val, T multiplier, int length) {
        BigDecimal originVal = new BigDecimal(val.toString());
        BigDecimal multipVal = originVal.multiply(new BigDecimal(multiplier.toString()));
        BigDecimal ret = multipVal.setScale(length, RoundingMode.HALF_UP);
        return ret.toString();
    }
}