package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Currency;

public interface ICurrencyDAO {

    public void save(Currency obj);

    public void update(Currency obj);

    public Currency get(String id);

    public List<Currency> getAll();

    public List<Currency> getCourseTypeByCountryId(String countryID);

    public List<Currency> getCurrencyByCountryId(String countryId);

    public Currency getCurrencyByCode(String currencyCode);
}
