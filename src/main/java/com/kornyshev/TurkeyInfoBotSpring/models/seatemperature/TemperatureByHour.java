package com.kornyshev.TurkeyInfoBotSpring.models.seatemperature;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureByHour {

    String time;
    WaterTemperature waterTemperature;

}
