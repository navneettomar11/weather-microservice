package com.nav.weather.domain.owapi;

import lombok.Data;

@Data
public class OwWeather {

    private int id;

    private String main;

    private String icon;

    private String description;
}
