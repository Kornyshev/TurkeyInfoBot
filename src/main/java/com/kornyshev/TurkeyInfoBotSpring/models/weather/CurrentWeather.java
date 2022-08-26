package com.kornyshev.TurkeyInfoBotSpring.models.weather;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentWeather {

    String temp_c;
    String humidity;
    SkyCondition condition;

}
