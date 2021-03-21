package com.nav.weather.api;

import com.nav.weather.domain.Weather;
import com.nav.weather.dto.ForecastRequest;
import com.nav.weather.service.ForecastService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/forecast")
public class ForecastResource {

    private ForecastService forecastService;

    public ForecastResource(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping
    public Collection<Weather> forecast(@RequestParam(value = "city", required = true)  String city) {
        ForecastRequest forecastRequest = new ForecastRequest();
        forecastRequest.setCity(city);
        return this.forecastService.forecast(forecastRequest);
    }

}
