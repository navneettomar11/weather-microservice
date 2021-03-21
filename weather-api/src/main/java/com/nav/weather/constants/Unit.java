package com.nav.weather.constants;

public enum Unit {

    STANDARD("standard"),
    METRIC("metric"),
    IMPERIAL("imperial");

    private String label;

    Unit(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

}
