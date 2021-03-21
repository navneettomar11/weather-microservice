package com.nav.weather.domain.owapi;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;

@Data
public class OwForecastResponse {

    private OwCity city;

    private Collection<OwForecast> list = Collections.emptyList();

}
