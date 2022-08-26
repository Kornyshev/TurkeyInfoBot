package com.kornyshev.TurkeyInfoBotSpring.models.seatemperature;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeaTemperatureResponse {

    List<TemperatureByHour> hours;

}
