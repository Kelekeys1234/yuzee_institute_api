package com.seeka.app.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.Currency;
import com.seeka.app.service.ICurrencyService;

@Component
public class CurrencyConversionRateUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyConversionRateUtil.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	ICurrencyService currencyService;
	
	
    @Scheduled(fixedRate = 500000, initialDelay = 5000)
    public void reportCurrentTime() {
    	 log.info("The time is now {}", dateFormat.format(new Date()));
         System.out.println("The time is now {}"+ dateFormat.format(new Date()));
         run();
    }
     
    public void run() {
    	System.out.println("CurrencyConversionRateUtil: Job Started: "+new Date());
    	
    	List<Currency> list = currencyService.getAll();
    	
    	String currencyCode = "";
    	
    	int i = 0;
    	
    	for (Currency currency : list) {
    		i++;
    		if(i%50 == 0) {
    			System.out.println(currencyCode);
    			currencyCode = "";
    			i = 0;
    		}else {
    			currencyCode = currencyCode +","+currency.getCode();
    		}
		}
    	 
		System.out.println("CurrencyConversionRateUtil: Job Completed: "+new Date());
    }
    
    
}
