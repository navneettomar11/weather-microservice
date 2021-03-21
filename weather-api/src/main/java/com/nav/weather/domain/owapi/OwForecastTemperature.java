package com.nav.weather.domain.owapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OwForecastTemperature {

    private float temp;

    @JsonProperty("temp_min")
    private float tempMin;

    @JsonProperty("temp_max")
    private float tempMax;

    private int humidity;
}
