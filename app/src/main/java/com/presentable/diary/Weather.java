package com.presentable.diary;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by SaraTang on 15-12-30.
 */
public class Weather implements Serializable {
    public static final String UNKNOWN = "(none)";
    public static final String SUNNY = "sunny";
    public static final String CLOUDY = "cloudy";
    public static final String RAINY = "rainy";
    public static final String SNOWY = "snowy";
    public static final String WINDY = "windy";
    public static final String THUNDERSTORM = "stormy";
    public static final String[] weathers = {UNKNOWN, SUNNY, CLOUDY, RAINY, SNOWY, WINDY, THUNDERSTORM};

    private String weather;

    public Weather(String weather) throws InvalidWeatherException {
        if (Arrays.asList(weathers).contains(weather)) {
            this.weather = weather;
        } else {
            throw new InvalidWeatherException();
        }
    }

    public Weather() {
        this.weather = Weather.UNKNOWN;
    }

    public String getWeather() {
        return weather;
    }
}
