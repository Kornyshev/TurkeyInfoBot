package com.kornyshev.TurkeyInfoBotSpring.constants;

public class WeatherEndpoints {

    public static final String BASE_URL = "http://api.weatherapi.com/v1";
    public static final String CURRENT_WEATHER = "/current.json?key={key}&q={q}&aqi=yes";

    public static final String FORECAST = "/forecast.json";
    public static final String HISTORY = "/history.json";
    public static final String FUTURE = "/future.json";
    public static final String ASTRONOMY = "/astronomy.json";

}
