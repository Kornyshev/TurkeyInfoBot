package com.kornyshev.TurkeyInfoBotSpring.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;
    @Value("${admin.chat.id}")
    String adminId;
    @Value("${bot.token}")
    String token;
    @Value("${main.city}")
    String mainCity;
    @Value("${weather.api.key}")
    String weatherApiKey;
    @Value("${water.temperature.api.key}")
    String waterTemperatureApiKey;

}
