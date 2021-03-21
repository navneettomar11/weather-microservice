package com.nav.weather.domain.owapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nav.weather.utilities.CustomLocalDateTimeDesSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
public class OwForecast {

    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime dt;

    @JsonProperty("dt_txt")
    private String dtTxt;

    private OwForecastTemperature main;

    private List<OwWeather> weather = Collections.emptyList();
}
