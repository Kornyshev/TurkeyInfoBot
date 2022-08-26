package com.kornyshev.TurkeyInfoBotSpring.service;

import com.kornyshev.TurkeyInfoBotSpring.constants.WeatherEndpoints;
import com.kornyshev.TurkeyInfoBotSpring.models.weather.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class WeatherController {

    public WeatherResponse getWeatherData(String apiKey, String city) {
        final WeatherResponse weatherResponse =
                new RestTemplate().getForObject(WeatherEndpoints.BASE_URL + WeatherEndpoints.CURRENT_WEATHER, WeatherResponse.class, apiKey, city);
        log.info("Response 'WeatherResponse': {}", weatherResponse);
        return weatherResponse;
    }

}
