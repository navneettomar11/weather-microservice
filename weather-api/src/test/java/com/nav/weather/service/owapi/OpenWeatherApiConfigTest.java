package com.nav.weather.service.owapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = OpenWeatherApiConfig.class)
@TestPropertySource("classpath:owapi-config-test.properties")
class OpenWeatherApiConfigTest {

    @Autowired
    private OpenWeatherApiConfig apiConfig;

    @Test
    void givenOpenWeatherApi_whenBindingPropertiesFile_thenAllFieldsAreSet() {
        assertEquals("http://sample.openweathermap.org/data/2.5/forecast", apiConfig.getApiUrl());
        assertEquals("xxx-xxx-xxxxx", apiConfig.getApiKey());
    }
}