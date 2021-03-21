package com.nav.weather.service.owapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nav.weather.constants.Condition;
import com.nav.weather.domain.Weather;
import com.nav.weather.domain.owapi.OwForecastResponse;
import com.nav.weather.dto.ForecastRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = OpenWeatherApiConfig.class)
@TestPropertySource("classpath:owapi-config-test.properties")
@RestClientTest(OpenWeatherApiService.class)
class OpenWeatherApiServiceTest {

    private OpenWeatherApiService openWeatherApiService;

    private OpenWeatherApiConfig apiConfig;

    private ObjectMapper objectMapper;

    private OwForecastResponse forecastResponse;

    private RestTemplate template;

    @BeforeEach
    public void init() {
        RestTemplateBuilder templateBuilder = mock(RestTemplateBuilder.class);
        this.template = mock(RestTemplate.class);
        when(templateBuilder.build()).thenReturn(this.template);

        apiConfig = mock(OpenWeatherApiConfig.class);
        when(apiConfig.getApiKey()).thenReturn("xxx-xxx-xxxx");
        when(apiConfig.getApiUrl()).thenReturn("http://sample.openweathermap.org/data/2.5/forecast");

        openWeatherApiService = new OpenWeatherApiService(apiConfig, templateBuilder);
        objectMapper = new ObjectMapper();

    }

    @Test
    @DisplayName("Given forecast request will be return five day forecast")
    void givenForecastRequest_whenInvokingOWAPI_returnFiveDayForecast() {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            forecastResponse = objectMapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("owapi-london-response.json"), OwForecastResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseEntity<OwForecastResponse> response=new ResponseEntity<OwForecastResponse>(forecastResponse, HttpStatus.OK);
        when(template.exchange(Mockito.any(URI.class),
                Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<OwForecastResponse>> any()))
        .thenReturn(response);

        ForecastRequest request = new ForecastRequest();
        request.setCity("London");
        Collection<Weather> weathers = openWeatherApiService.forecast(request);
        assertNotNull(weathers);
        assertTrue(!weathers.isEmpty());
        assertTrue(weathers.stream()
                .anyMatch(weather -> weather.getCondition().getCondition().equals(Condition.OTHERS))
        );
    }


    @Test
    @DisplayName("Given forecast request will be return rainy day forecast")
    void givenForecastRequest_whenInvokingOWAPI_returnFiveRainyDayForecast() {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            forecastResponse = objectMapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("owapi-london-rainy-response.json"), OwForecastResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseEntity<OwForecastResponse> response=new ResponseEntity<OwForecastResponse>(forecastResponse, HttpStatus.OK);
        when(template.exchange(Mockito.any(URI.class),
                Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<OwForecastResponse>> any()))
                .thenReturn(response);

        ForecastRequest request = new ForecastRequest();
        request.setCity("London");
        Collection<Weather> weathers = openWeatherApiService.forecast(request);
        assertNotNull(weathers);
        assertTrue(!weathers.isEmpty());
        assertTrue(weathers.stream()
                .anyMatch(weather -> weather.getCondition().getCondition().equals(Condition.RAIN))
        );
    }



    @Test
    @DisplayName("Given forecast request will be return sunny day forecast")
    void givenForecastRequest_whenInvokingOWAPI_returnFiveSunnyDayForecast() {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            forecastResponse = objectMapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("owapi-london-sunny-response.json"), OwForecastResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseEntity<OwForecastResponse> response=new ResponseEntity<OwForecastResponse>(forecastResponse, HttpStatus.OK);
        when(template.exchange(Mockito.any(URI.class),
                Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<OwForecastResponse>> any()))
                .thenReturn(response);

        ForecastRequest request = new ForecastRequest();
        request.setCity("London");
        Collection<Weather> weathers = openWeatherApiService.forecast(request);
        assertNotNull(weathers);
        assertTrue(!weathers.isEmpty());
        assertTrue(weathers.stream()
                .anyMatch(weather -> weather.getCondition().getCondition().equals(Condition.SUNNY))
        );
    }

    @Test
    @DisplayName("Given forecast request will be return clear day forecast")
    void givenForecastRequest_whenInvokingOWAPI_returnFiveClearDayForecast() {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            forecastResponse = objectMapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("owapi-london-clear-response.json"), OwForecastResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseEntity<OwForecastResponse> response=new ResponseEntity<OwForecastResponse>(forecastResponse, HttpStatus.OK);
        when(template.exchange(Mockito.any(URI.class),
                Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<?>> any(),
                Mockito.<Class<OwForecastResponse>> any()))
                .thenReturn(response);

        ForecastRequest request = new ForecastRequest();
        request.setCity("London");
        Collection<Weather> weathers = openWeatherApiService.forecast(request);
        assertNotNull(weathers);
        assertTrue(!weathers.isEmpty());

        assertTrue(weathers.stream()
                .anyMatch(weather -> weather.getCondition().getCondition().equals(Condition.CLEAR_SKY))
        );
    }

}