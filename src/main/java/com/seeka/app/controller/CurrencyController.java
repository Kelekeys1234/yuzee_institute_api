package com.seeka.app.controller;import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Currency;
import com.seeka.app.jobs.CurrencyUtil;
import com.seeka.app.service.ICurrencyService;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
	
	@Autowired
	private ICurrencyService currencyService;
	
	/*@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?>  getAll() {
		Map<String,Object> response = new HashMap<String, Object>();
		Collection<Currency> list = CurrencyUtil.getAllCurrencies();
		List<Currency> currencyList = new ArrayList<>(list);
		currencyList.sort((Currency c1, Currency c2)->c1.getName().compareTo(c2.getName()));
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("list",currencyList);
    	return ResponseEntity.accepted().body(response);
	}
	
	@RequestMapping(value = "/all", method=RequestMethod.GET)
	public ResponseEntity<?>  getAll1() {
		Map<String,Object> response = new HashMap<String, Object>();
		Collection<Currency> list = currencyService.getAll();
		List<Currency> currencyList = new ArrayList<>(list);
		currencyList.sort((Currency c1, Currency c2)->c1.getName().compareTo(c2.getName()));
		response.put("status", 1);
		response.put("message","Success.!");
    	response.put("list",currencyList);
    	return ResponseEntity.accepted().body(response);
	}
	
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ResponseEntity<?> saveCurrency() {
        Map<String, Object> response = new HashMap<String, Object>();

        Currency currency = new Currency();
        currency.setBaseCurrency("USD");
        currency.setCode("ULAGA");
        currency.setConversionRate(0.23244);
        currency.setName("ULAGANATHAN");
        currency.setSymbol("$");
        currency.setUpdatedDate(new Date());
        currencyService.save(currency);
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("currency", currency);
        return ResponseEntity.accepted().body(response);
    }*/
	
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllCurrencies() throws Exception {
        return ResponseEntity.accepted().body(currencyService.getAllCurrencies());
    }
}
