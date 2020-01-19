package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.seeka.app.bean.Currency;
import com.seeka.app.bean.CurrencyRate;
import com.seeka.app.exception.NotFoundException;

public interface ICurrencyRateService {

    public void save(CurrencyRate obj);

    public void update(Currency obj);

    public Currency get(BigInteger id);

    public List<Currency> getAll();

    public List<Currency> getCourseTypeByCountryId(BigInteger countryID);

    public List<Currency> getCurrencyByCountryId(BigInteger countryId);

    public Currency getCurrencyByCode(String currencyCode);

    public Map<String, Object> getAllCurrencies();

    List<CurrencyRate> getAllCurrencyRate();
    // public Map<String, Object> currencyConversion(@Valid CurrencyConvertorRequest convertorRequest);

	CurrencyRate getCurrencyRate(String currencyCode);

	List<CurrencyRate> getChangedCurrency();
	
	Double getConversionRate(String currencyCode) throws NotFoundException;
}
