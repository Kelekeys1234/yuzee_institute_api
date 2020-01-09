package com.seeka.app.service;

import java.math.BigInteger;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.bean.Currency;
import com.seeka.app.dto.CurrencyConvertorRequest;

public interface ICurrencyService {

    public void save(Currency obj);

    public void update(Currency obj);

    public Currency get(BigInteger id);

    public List<Currency> getAll();

    public List<Currency> getCourseTypeByCountryId(BigInteger countryID);

    public List<Currency> getCurrencyByCountryId(BigInteger countryId);

    public Currency getCurrencyByCode(String currencyCode);

    public Map<String, Object> getAllCurrencies();

    public Map<String, Object> currencyConversion(@Valid CurrencyConvertorRequest convertorRequest);
}
