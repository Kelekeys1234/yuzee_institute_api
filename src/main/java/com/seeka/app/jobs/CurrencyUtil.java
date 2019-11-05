package com.seeka.app.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.service.ICurrencyRateService;

@Component
public class CurrencyUtil implements InitializingBean {
	
	@Autowired
	ICurrencyRateService currencyService;
    
    // private static Map<BigInteger, Currency> currencyIdMap = new HashMap<BigInteger, Currency>();
    // private static Map<String, Currency> currencyCodeMap = new HashMap<String, Currency>();
    private static Map<String, CurrencyRate> currencyCodeMap = new HashMap<>();
	
    
	@Override
	public void afterPropertiesSet() throws Exception {
		run();
	}
	
//	public static Currency getCurrencyObjById(BigInteger currencyId) {
//		return currencyIdMap.get(currencyId);
//	}
	
	public static CurrencyRate getCurrencyObjByCode(String currencyCode) {
		return currencyCodeMap.get(currencyCode.toLowerCase());
	}
	
//	public static Collection<Currency> getAllCurrencies() {
//		return currencyIdMap.values();
//	}
	
    public void run() {
    	//List<Currency> list = currencyService.getAll();
    	List<CurrencyRate> currencyRateList = currencyService.getAllCurrencyRate();
    	
    	Map<String, CurrencyRate> currencyCodeMapTemp = new HashMap<>();
    	for (CurrencyRate currency : currencyRateList) {
    		if(null == currency.getToCurrencyName() || currency.getToCurrencyName().isEmpty()) {
    			continue;
    		}
    		// currencyIdMapTemp.put(currency.getId(), currency);
    		currencyCodeMapTemp.put(currency.getToCurrencyCode().toLowerCase(), currency);
		}
    	// currencyIdMap.clear();
    	// currencyIdMap.putAll(currencyIdMapTemp);
    	currencyCodeMap.clear();
    	currencyCodeMap.putAll(currencyCodeMapTemp);
    }
    
}
