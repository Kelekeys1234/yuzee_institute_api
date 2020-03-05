package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.bean.Currency;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.dao.CurrencyRateDAO;
import com.seeka.app.dao.ICurrencyDAO;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class CurrencyRateService implements ICurrencyRateService {

    @Autowired
    private ICurrencyDAO dao;

    @Autowired
    private CurrencyRateDAO currencyRateDAO;

    @Override
    public void save(CurrencyRate obj) {
    	currencyRateDAO.save(obj);
    }

    @Override
    public void update(Currency obj) {
        dao.update(obj);
    }

    @Override
    public Currency get(String id) {
        return dao.get(id);
    }

    @Override
    public List<Currency> getAll() {
        return dao.getAll();
    }

    @Override
    public List<CurrencyRate> getAllCurrencyRate(){
    	return currencyRateDAO.getAllCurrencyRate();
    }
    
    @Override
    public List<Currency> getCourseTypeByCountryId(String countryID) {
        return dao.getCourseTypeByCountryId(countryID);
    }

    @Override
    public List<Currency> getCurrencyByCountryId(String countryId) {
        return dao.getCurrencyByCountryId(countryId);
    }

    @Override
    public Currency getCurrencyByCode(String currencyCode) {
        return dao.getCurrencyByCode(currencyCode);
    }

    @Override
    public Map<String, Object> getAllCurrencies() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Currency> currencies = new ArrayList<Currency>();
        try {
            currencies = dao.getAll();
            if (currencies.isEmpty()) {
                currencies = saveCurrencies();
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("status", HttpStatus.OK);
        response.put("message", IConstant.ARTICLE_GET_SUCCESS);
        response.put("currencies", currencies);
        return response;
    }

    @SuppressWarnings("unchecked")
    private List<Currency> saveCurrencies() {
        System.out.println("CurrencyConversionRateUtil: Job Started: " + new Date());
        List<Currency> currencies = null;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(IConstant.CURRENCY_URL + "symbols?access_key=" + IConstant.API_KEY + "&format=1", String.class);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            JSONObject obj = jsonObject.getJSONObject("symbols");
            Iterator<String> iter = obj.keys();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Currency currency = new Currency();
                currency.setCode(key);
                currency.setName(obj.getString(key));
                currency.setUpdatedDate(new Date());
                dao.save(currency);
            }
            currencies = dao.getAll();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currencies;
    }

	@Override
	public CurrencyRate getCurrencyRate(String currencyCode) {
		return currencyRateDAO.getCurrencyRateByCurrencyCode(currencyCode);
	}

	@Override
	public List<CurrencyRate> getChangedCurrency() {
		return currencyRateDAO.getChangedCurrency();
	}

	@Override
	public Double getConversionRate(String currencyCode) throws NotFoundException {
		CurrencyRate currencyRate = currencyRateDAO.getCurrencyRateByCurrencyCode(currencyCode);
		if(currencyRate == null || currencyRate.getId() == null || currencyRate.getConversionRate() == 0) {
			throw new NotFoundException("Currency Rate not availbale for currency - "+currencyCode);
		}
		return currencyRate.getConversionRate();
	}

}
