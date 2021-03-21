package com.nav.weather.service.owapi;

import com.nav.weather.constants.Condition;
import com.nav.weather.constants.OwWeatherConditionCode;
import com.nav.weather.domain.Weather;
import com.nav.weather.domain.WeatherCondition;
import com.nav.weather.domain.owapi.OwForecast;
import com.nav.weather.domain.owapi.OwForecastResponse;
import com.nav.weather.domain.owapi.OwWeather;
import com.nav.weather.dto.ForecastRequest;
import com.nav.weather.exception.OwapiException;
import com.nav.weather.service.ForecastService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class OpenWeatherApiService implements ForecastService {

    private static final int HOUR_FORECAST = 3;

    private static final int NUMBER_OF_DAYS = 3;

    private static final float MAX_SUNNY_TEMP = 40f;

    private OpenWeatherApiConfig apiConfig;

    private RestTemplate restTemplate;

    public OpenWeatherApiService(OpenWeatherApiConfig apiConfig, RestTemplateBuilder restTemplateBuilder) {
        this.apiConfig = apiConfig;
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Returning 5 days forecast
     * @param forecastRequest
     * @return
     */
    @Override
    public Collection<Weather> forecast(ForecastRequest forecastRequest) {

        OwForecastResponse forecastResponse = invokeApi(forecastRequest)
                .orElseThrow( () -> new OwapiException("Failed to get the response from Open Weather API"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime maxDateTime = now.plusDays(NUMBER_OF_DAYS);
        LocalDateTime currentDateTime =  now;
        List<OwForecast> forecastList=  new ArrayList<>();
        while(!currentDateTime.isEqual(maxDateTime)) {
            LocalDateTime tempDateTime = currentDateTime;

            forecastList.addAll(forecastResponse.getList().stream()
                    .filter(forecast ->
                            ChronoUnit.HOURS.between(tempDateTime,forecast.getDt()) > 0  &&
                            ChronoUnit.HOURS.between(tempDateTime, forecast.getDt()) <= HOUR_FORECAST)
                    .collect(Collectors.toList()));
            currentDateTime = currentDateTime.plusDays(1l);
        }

        return forecastList.stream().map(this::transformOwForecast).collect(Collectors.toList());
    }

    /**
     * Get the condition code from Open API Weather Condition codes.
     * Current Temp is used for checking if temp above  MAX TEMP then it will be Sunny day
     * @param conditionCode
     * @param currentTemp
     * @return
     */
    private Condition getConditionFromOwWeatherCondition(OwWeatherConditionCode conditionCode, float currentTemp) {
        if(conditionCode == null) {
            return Condition.OTHERS;
        }
        if(conditionCode.equals(OwWeatherConditionCode.CLEAR)) {
            if(currentTemp > MAX_SUNNY_TEMP) {
                return Condition.SUNNY;
            }
            return Condition.CLEAR_SKY;
        }
        return Condition.RAIN;

    }

    /**
     * Transforming the Open Weather API response to our domain response.
     * @param forecast
     * @return
     */
    private Weather transformOwForecast(OwForecast forecast) {
        Weather weather = new Weather();
        weather.setDatetime(forecast.getDt());
        weather.setCurrTemp(forecast.getMain().getTemp());
        weather.setMaxTemp(forecast.getMain().getTempMax());
        weather.setMinTemp(forecast.getMain().getTempMin());

        if(!forecast.getWeather().isEmpty()) {
            WeatherCondition weatherCondition  = new WeatherCondition();
            OwWeather owWeather = forecast.getWeather().get(0); //primary
            OwWeatherConditionCode conditionCode = OwWeatherConditionCode.of(owWeather.getId());
            weatherCondition.setCondition(getConditionFromOwWeatherCondition(conditionCode, weather.getCurrTemp()));
            weatherCondition.setIcon(owWeather.getIcon());
            weatherCondition.setDescription(owWeather.getDescription());
            weather.setCondition(weatherCondition);
        }
        return weather;
    }

    /**
     * Invoking the Open Weather API.
     * @param request
     * @return
     */
    private Optional<OwForecastResponse> invokeApi(ForecastRequest request) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("city", request.getCity());
        uriVariables.put("lang", request.getLang().getCode());
        uriVariables.put("unit", request.getUnit().toString());
        uriVariables.put("APIkey", apiConfig.getApiKey());
        URI uri = new UriTemplate(apiConfig.getApiUrl()).expand(uriVariables);
        try {
            ResponseEntity<OwForecastResponse> response = this.restTemplate.exchange(uri, HttpMethod.GET, HttpEntity.EMPTY, OwForecastResponse.class);
            return Optional.of(response.getBody());
        } catch (HttpClientErrorException ex) {
            String message = ex.getStatusCode().equals(HttpStatus.NOT_FOUND) ? "City not found" : ex.getStatusText();
            throw new OwapiException(message);
        }

    }
}
