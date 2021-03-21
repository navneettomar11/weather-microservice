package com.nav.weather.domain.owapi;

import lombok.Data;

import java.time.Instant;

@Data
public class OwCity {

    private String name;

    private OwCoordinate coord;

    private String country;

    private long population;

    private long sunrise;

    private long sunset;
}
