package com.kornyshev.TurkeyInfoBotSpring.models.currencies;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {

    String base;
    String date;
    LinkedHashMap<String, Object> rates;

}
