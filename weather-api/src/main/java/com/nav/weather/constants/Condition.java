package com.nav.weather.constants;

public enum Condition {

    SUNNY("Sunny"),
    RAIN("Rain"),
    OTHERS("Others"),
    CLEAR_SKY("Clear sky");

    private String label;
    Condition(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
