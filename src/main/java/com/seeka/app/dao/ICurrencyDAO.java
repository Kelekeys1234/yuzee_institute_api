package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Currency;

public interface ICurrencyDAO {

    public void save(Currency obj);

    public void update(Currency obj);

    public Currency get(BigInteger id);

    public List<Currency> getAll();

    public List<Currency> getCourseTypeByCountryId(BigInteger countryID);

    public List<Currency> getCurrencyByCountryId(BigInteger countryId);

    public Currency getCurrencyByCode(String currencyCode);
}
