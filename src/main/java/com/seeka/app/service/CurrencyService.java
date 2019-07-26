package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.bean.Currency;
import com.seeka.app.dao.ICurrencyDAO;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class CurrencyService implements ICurrencyService {

    @Autowired
    private ICurrencyDAO dao;

    @Override
    public void save(Currency obj) {
        dao.save(obj);
    }

    @Override
    public void update(Currency obj) {
        dao.update(obj);
    }

    @Override
    public Currency get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public List<Currency> getAll() {
        return dao.getAll();
    }

    @Override
    public List<Currency> getCourseTypeByCountryId(BigInteger countryID) {
        return dao.getCourseTypeByCountryId(countryID);
    }

    @Override
    public List<Currency> getCurrencyByCountryId(BigInteger countryId) {
        return dao.getCurrencyByCountryId(countryId);
    }

    @Override
    public Currency getCurrencyByCode(String currencyCode) {
        return dao.getCurrencyByCode(currencyCode);
    }

    @Override
    public Map<String, Object> getAllCurrencies() {
        Map<String, Object> response = new HashMap<String, Object>();
        String status = IConstant.SUCCESS;
        List<Currency> currencies = new ArrayList<Currency>();
        try {
            currencies = dao.getAll();
            if (currencies.isEmpty()) {
                currencies = saveCurrencies();
            }
        } catch (Exception exception) {
            status = IConstant.FAIL;
        }
        response.put("status", 200);
        response.put("message", status);
        response.put("currencies", currencies);
        return response;
    }

    @SuppressWarnings("unchecked")
    private List<Currency> saveCurrencies() {
        System.out.println("CurrencyConversionRateUtil: Job Started: " + new Date());
        List<Currency> currencies = null;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://data.fixer.io/api/symbols?access_key=95bdc53aa11d07169765f1b413275ba2&format=1", String.class);
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
}
