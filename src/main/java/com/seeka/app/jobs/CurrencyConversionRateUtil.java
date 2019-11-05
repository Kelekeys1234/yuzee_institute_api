package com.seeka.app.jobs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.service.ICourseService;
import com.seeka.app.service.ICurrencyRateService;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Component
public class CurrencyConversionRateUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyConversionRateUtil.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
	@Autowired
	private ICurrencyRateService currencyRateService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ICourseService courseService;
	
    @Scheduled(fixedRate = 86400000, initialDelay = 5000)
    public void reportCurrentTime() {
    	 log.info("CurrencyConversionRateUtil: The time is now {}", dateFormat.format(new Date()));
         System.out.println("CurrencyConversionRateUtil: The time is now {}"+ dateFormat.format(new Date()));
         run();
         updateCourses();
    }
     
    public void run() {
    	System.out.println("CurrencyConversionRateUtil: Job Started: "+new Date());
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			String result = restTemplate.getForObject(
					IConstant.CURRENCY_URL +"latest?access_key="+IConstant.API_KEY+"&base=" + IConstant.USD_CODE,
					String.class);
			
			JsonParser jp = new JsonParser();
			JsonElement jsonElement = jp.parse(result);
			JsonObject obj = jsonElement.getAsJsonObject().get("rates").getAsJsonObject();
			Map<String, Double> map =objectMapper.readValue(obj.toString(), HashMap.class);

			for (Map.Entry<String, Double> currency : map.entrySet()) {
				String currencyCode = currency.getKey();
				CurrencyRate currencyRate = currencyRateService.getCurrencyRate(currencyCode);
				currencyRate.setConversionRate(Double.parseDouble(String.valueOf(currency.getValue())));
				currencyRate.setUpdatedAt(DateUtil.getUTCdatetimeAsDate());
				Integer thresholdValue = IConstant.CURRENCY_THRESHOLD;
				if (thresholdValue != null && thresholdValue != 0) {
					Double oldRate = currencyRate.getConversionRate();
					boolean isGreaterThanThreshold = checkForDifferenceGreaterThanThreshold(oldRate,
							Double.valueOf(String.valueOf(currency.getValue())), thresholdValue);
					if (isGreaterThanThreshold) {
						currencyRate.setHasChanged(true);
						currencyRateService.save(currencyRate);
					}
				} else if (thresholdValue <= 0) {
					currencyRate.setHasChanged(true);
					currencyRateService.save(currencyRate);
				}
			}
		} catch (JsonParseException e) {
			log.error("JsonParseException Error in currency rate scheduler "+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.error("JsonMappingException Error in currency rate scheduler "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException Error in currency rate scheduler "+e.getMessage());
			e.printStackTrace();
		}
		System.out.println("CurrencyConversionRateUtil: Job Completed: "+new Date());
    }

	private boolean checkForDifferenceGreaterThanThreshold(Double oldRate, Double newRate, Integer thresholdValue) {
		Double thresholdAmt = oldRate*thresholdValue/100;
		if((oldRate-thresholdAmt) < newRate && (oldRate + thresholdAmt) > newRate) {
			return false;
		} else {
			return true;
		}
	}
	
	public void updateCourses() {
		List<CurrencyRate> currencyRateList = currencyRateService.getChangedCurrency();
		for (CurrencyRate currencyRate : currencyRateList) {
			if(currencyRate.getToCurrencyCode().equalsIgnoreCase("AUD")) {
				courseService.updateCourseForCurrency(currencyRate);
			}
		}
	}
}
