package com.kornyshev.TurkeyInfoBotSpring.service;

import com.kornyshev.TurkeyInfoBotSpring.constants.CurrencyEndpoints;
import com.kornyshev.TurkeyInfoBotSpring.models.currencies.CurrencyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class CurrencyController {

    public CurrencyResponse getExchangeRates(String baseCurrency, List<String> symbols) {
        final CurrencyResponse currencyResponse =
                new RestTemplate().getForObject(CurrencyEndpoints.URL_WITH_PARAMS, CurrencyResponse.class,
                        baseCurrency, String.join(",", symbols));
        log.info("Response 'CurrencyBody': {}", currencyResponse);
        return currencyResponse;
    }

}
