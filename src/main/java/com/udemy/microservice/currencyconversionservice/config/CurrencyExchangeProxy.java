package com.udemy.microservice.currencyconversionservice.config;

import com.udemy.microservice.currencyconversionservice.CurrencyConversionDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange-service")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionDTO getExchangeValue(@PathVariable String from, @PathVariable String to);

}
