package com.nav.weather.constants;

public enum OwWeatherConditionCode {

    DRIZZLE(300, 321),
    RAIN(500, 531),
    CLEAR(800, 800);

    private int min;
    private int max;

    private static boolean isExist(OwWeatherConditionCode condition, Integer code)  {
        return code >= condition.min && code <= condition.max;
    }

    OwWeatherConditionCode(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static OwWeatherConditionCode of(Integer code) {
        for(OwWeatherConditionCode owWeatherConditionCode : values()) {
            if(isExist(owWeatherConditionCode, code)) {
                return owWeatherConditionCode;
            }
        }
        return null;
     }
}
