package com.seeka.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	ICurrencyService currencyService;
	
	@RequestMapping(value = "/get", method=RequestMethod.GET)
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
	
}
