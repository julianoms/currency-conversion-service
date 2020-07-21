package com.udemy.microservice.currencyconversionservice;

import com.udemy.microservice.currencyconversionservice.config.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    CurrencyExchangeProxy exchangeProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionDTO getConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {


        ResponseEntity<CurrencyConversionDTO> currencyExchange = new RestTemplate()
                .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionDTO.class, from, to);

        CurrencyConversionDTO response = currencyExchange.getBody();

        return CurrencyConversionDTO.builder()
                .id(response.getId())
                .from(from)
                .to(to)
                .conversionMultiple(response.getConversionMultiple())
                .quantity(quantity)
                .totalCalculatedAmount(quantity.multiply(response.getConversionMultiple()))
                .build();
    }

    @GetMapping("/v2/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionDTO getConversion2(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversionDTO response = exchangeProxy.getExchangeValue(from, to);

        return CurrencyConversionDTO.builder()
                .id(response.getId())
                .from(from)
                .to(to)
                .conversionMultiple(response.getConversionMultiple())
                .quantity(quantity)
                .totalCalculatedAmount(quantity.multiply(response.getConversionMultiple()))
                .build();
    }
}
