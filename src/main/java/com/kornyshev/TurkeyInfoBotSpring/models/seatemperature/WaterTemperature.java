package com.kornyshev.TurkeyInfoBotSpring.models.seatemperature;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaterTemperature {

    String meto;
    String noaa;
    String sq;

}
