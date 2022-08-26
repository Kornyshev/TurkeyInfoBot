package com.kornyshev.TurkeyInfoBotSpring.service;

import com.kornyshev.TurkeyInfoBotSpring.constants.SeaTemperatureEndpoints;
import com.kornyshev.TurkeyInfoBotSpring.models.seatemperature.SeaTemperatureResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class SeaTemperatureController {

    public SeaTemperatureResponse getSeaTemperatureData(String authKey, String latitude, String longitude) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authKey);
        final SeaTemperatureResponse seaTemperatureResponse =
                restTemplate.exchange(SeaTemperatureEndpoints.BASE_URL, HttpMethod.GET, new HttpEntity<String>("parameters", headers), SeaTemperatureResponse.class, latitude, longitude).getBody();
        log.info("Response 'SeaTemperatureResponse': {}", seaTemperatureResponse);
        return seaTemperatureResponse;
    }

}
