package com.nav.weather.service.owapi;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "owapi")
@Data
public class OpenWeatherApiConfig {

    private String apiKey;

    private String apiUrl;
}
