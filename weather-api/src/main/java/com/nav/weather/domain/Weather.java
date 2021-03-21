package com.nav.weather.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class Weather {

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime datetime;

    private float currTemp;

    private float minTemp;

    private float maxTemp;

    private WeatherCondition condition;

}
