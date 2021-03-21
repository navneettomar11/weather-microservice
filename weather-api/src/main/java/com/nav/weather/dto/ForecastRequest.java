package com.nav.weather.dto;

import com.nav.weather.constants.Language;
import com.nav.weather.constants.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastRequest {

    private String city;

    private Unit unit = Unit.METRIC;

    private Language lang = Language.ENGLISH;

}
