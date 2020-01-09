package com.seeka.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.CurrencyConvertorRequest;
import com.seeka.app.service.ICurrencyService;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private ICurrencyService currencyService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllCurrencies() throws Exception {
        return ResponseEntity.accepted().body(currencyService.getAllCurrencies());
    }

    @RequestMapping(value = "/conversion", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> currencyConversion(@Valid @RequestBody final CurrencyConvertorRequest convertorRequest) throws Exception {
        return ResponseEntity.accepted().body(currencyService.currencyConversion(convertorRequest));
    }
}
