package com.seeka.app.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.bean.Currency;
import com.seeka.app.service.ICurrencyService;

@Component
public class CurrencyConversionRateUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyConversionRateUtil.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
	@Autowired
	ICurrencyService currencyService;
	
    @Scheduled(fixedRate = 5000000, initialDelay = 5000)
    public void reportCurrentTime() {
    	 log.info("CurrencyConversionRateUtil: The time is now {}", dateFormat.format(new Date()));
         System.out.println("CurrencyConversionRateUtil: The time is now {}"+ dateFormat.format(new Date()));
         //run();
    }
     
    public void run() {
    	System.out.println("CurrencyConversionRateUtil: Job Started: "+new Date());
    	RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://data.fixer.io/api/latest?access_key=95bdc53aa11d07169765f1b413275ba2&format=1", String.class);
    	JSONObject jsonObject = new JSONObject(result);	
    	JSONObject obj = jsonObject.getJSONObject("rates");
    	Map<String,Double> map = new HashMap<String,Double>();
    	Iterator<String> iter = obj.keys();
    	while(iter.hasNext()){
    	   String key = (String) iter.next();
    	   Double value = obj.getDouble(key);
    	   map.put(key,value);
    	}
    	Double usdAgainstEuro = map.get("USD");
    	Double euroAgainstUsd =  1 / usdAgainstEuro;
    	List<Currency> list = currencyService.getAll();
    	Date now = new Date();
    	for (Currency currency : list) {
    		Double curValue = map.get(currency.getCode());
    		if(null == curValue ) {
			 continue;
    		}
    		Double curConversion = usdAgainstEuro / curValue;
    		Double curAgainstDollar = 1 / curConversion;
    		currency.setConversionRate(curAgainstDollar);
    		currency.setUpdatedDate(now);
    		currencyService.update(currency);
		}
    	
		System.out.println("CurrencyConversionRateUtil: Job Completed: "+new Date());
    }
    
    
}
