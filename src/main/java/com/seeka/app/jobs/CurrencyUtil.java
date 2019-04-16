package com.seeka.app.jobs;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.Currency;
import com.seeka.app.service.ICurrencyService;

@Component
public class CurrencyUtil implements InitializingBean {
	
	@Autowired
	ICurrencyService currencyService;
    
    private static Map<Integer, Currency> currencyIdMap = new HashMap<Integer, Currency>();
    private static Map<String, Currency> currencyCodeMap = new HashMap<String, Currency>();
	
    
	@Override
	public void afterPropertiesSet() throws Exception {
		run();
	}
	
	public static Currency getCurrencyObjById(Integer currencyId) {
		return currencyIdMap.get(currencyId);
	}
	
	public static Currency getCurrencyObjByCode(String currencyCode) {
		return currencyCodeMap.get(currencyCode.toLowerCase());
	}
	
	public static Collection<Currency> getAllCurrencies() {
		return currencyIdMap.values();
	}
	
    public void run() {
    	List<Currency> list = currencyService.getAll();
    	Map<Integer, Currency> currencyIdMapTemp = new HashMap<Integer, Currency>();
    	Map<String, Currency> currencyCodeMapTemp = new HashMap<String, Currency>();
    	for (Currency currency : list) {
    		if(null == currency.getName() || currency.getName().isEmpty()) {
    			continue;
    		}
    		currencyIdMapTemp.put(currency.getId(), currency);
    		currencyCodeMapTemp.put(currency.getCode().toLowerCase(), currency);
		}
    	currencyIdMap.clear();
    	currencyIdMap.putAll(currencyIdMapTemp);
    	currencyCodeMap.clear();
    	currencyCodeMap.putAll(currencyCodeMapTemp);
    }
    
}
