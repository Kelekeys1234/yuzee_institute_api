package com.seeka.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.service.ICurrencyRateService;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

	@Autowired
	private ICurrencyRateService currencyService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllCurrencies() throws Exception {
		return ResponseEntity.accepted().body(currencyService.getAllCurrencies());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rate", produces = "application/json")
	public ResponseEntity<?> getConversionRateForCurrency(@RequestParam(value = "currencyCode") String currencyCode)
			throws Exception {
		return new GenericResponseHandlers.Builder().setData(currencyService.getConversionRate(currencyCode))
				.setMessage("Conversion Rate Fetched successfully").setStatus(HttpStatus.OK).create();
	}

//    @RequestMapping(value = "/conversion", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//    public ResponseEntity<?> currencyConversion(@Valid @RequestBody final CurrencyConvertorRequest convertorRequest) throws Exception {
//        return ResponseEntity.accepted().body(currencyService.currencyConversion(convertorRequest));
//    }
}
