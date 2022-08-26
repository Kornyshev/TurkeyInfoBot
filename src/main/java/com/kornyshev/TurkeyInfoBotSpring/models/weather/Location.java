package com.kornyshev.TurkeyInfoBotSpring.models.weather;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    String name;
    String lat;
    String lon;
    String tz_id;

}
