package com.nav.weather.domain;

import com.nav.weather.constants.Condition;
import lombok.Data;

@Data
public class WeatherCondition {

    private Condition condition;

    private String icon;

    private String description;
}
