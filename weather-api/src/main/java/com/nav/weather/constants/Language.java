package com.nav.weather.constants;

public enum Language {
    ARABIC("Arabic", "ar"),
    GERMAN("German", "de"),
    ENGLISH("English", "en"),
    FRENCH("French", "fr"),
    HINDI("Hindi", "hi"),
    ITALIAN("Italian", "it"),
    JAPANESE("Japanese", "ja"),
    PORTUGUESE("Portuguese", "pt"),
    SPANISH("Spanish", "es"),
    CHINESE_SIMPLIFIED("Chinese Simplified", "zh_cn");



    private String lang;
    private String code;
    Language(String lang, String code) {
        this.lang = lang;
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public String getCode() {
        return code;
    }

}
