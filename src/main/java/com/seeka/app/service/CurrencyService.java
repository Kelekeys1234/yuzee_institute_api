package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.bean.Currency;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.dao.CurrencyRateDAO;
import com.seeka.app.dao.ICurrencyDAO;
import com.seeka.app.dto.CurrencyConvertorRequest;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class CurrencyService implements ICurrencyService {

    @Autowired
    private ICurrencyDAO dao;

    @Autowired
    private CurrencyRateDAO currencyRateDAO;

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
    public Map<String, Object> currencyConversion(@Valid CurrencyConvertorRequest convertorRequest) {
        Map<String, Object> response = new HashMap<String, Object>();
        Double convertedCurrcny = null;
        try {
            convertedCurrcny = convertCurrency(convertorRequest);
            response.put("message", "Currency converted successfully");
            response.put("status", HttpStatus.OK.value());
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", convertedCurrcny);
        return response;
    }

    public Double convertCurrency(@Valid CurrencyConvertorRequest convertorRequest) {
        Double convertedRate = null;
        CurrencyRate currencyRate = currencyRateDAO.getCurrencyRate(convertorRequest.getFromCurrencyId());
        if (currencyRate == null) {
            currencyRate = saveCurrencyRate(convertorRequest);
        }
        if (currencyRate != null) {
            convertedRate = getConvertedCurrency(currencyRate, convertorRequest.getToCurrencyId(), convertorRequest.getAmount());
        }
        return convertedRate;
    }

    @SuppressWarnings("rawtypes")
    private Double getConvertedCurrency(CurrencyRate currencyRate, BigInteger toCurrencyId, Double amount) {
        Double convertedRate = null;
        try {
            if (currencyRate.getRateDetail() != null) {
                Currency currency = dao.get(toCurrencyId);
                JSONParser parser = new JSONParser();
                org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(currencyRate.getRateDetail());
                org.json.simple.JSONObject rates = (org.json.simple.JSONObject) json.get("rates");
                System.out.println(rates);
                Double currencyValue = null;
                for (Iterator iterator = rates.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    if (key.equalsIgnoreCase(currency.getCode())) {
                        if (rates.get(key) != null && String.valueOf(rates.get(key)).contains(".")) {
                            currencyValue = (Double) rates.get(key);
                        } else {
                            currencyValue = Double.valueOf((Long) rates.get(key));
                        }
                        break;
                    }
                }
                if (currencyValue != null) {
                    convertedRate = currencyValue * amount;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedRate;
    }

    private CurrencyRate saveCurrencyRate(@Valid CurrencyConvertorRequest convertorRequest) {
        CurrencyRate currencyRate = null;
        Currency currency = dao.get(convertorRequest.getFromCurrencyId());
        String currencyResponse = CommonUtil.getCurrencyDetails(currency.getCode());
        if (currencyResponse != null) {
            currencyRate = new CurrencyRate();
            currencyRate.setCreatedDate(DateUtil.getUTCdatetimeAsStringYYYY_MM_DD());
            currencyRate.setBaseCurrencyId(convertorRequest.getFromCurrencyId());
            currencyRate.setRateDetail(currencyResponse);
            currencyRateDAO.save(currencyRate);
        }
        return currencyRate;
    }
}
