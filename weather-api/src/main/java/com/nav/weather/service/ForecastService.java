package com.nav.weather.service;

import com.nav.weather.domain.Weather;
import com.nav.weather.dto.ForecastRequest;

import java.util.Collection;

public interface ForecastService {

    Collection<Weather> forecast(ForecastRequest forecastRequest);
}
