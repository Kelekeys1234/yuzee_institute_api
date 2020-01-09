package com.seeka.app.dao;

import java.math.BigInteger;

import com.seeka.app.bean.CountryDetails;

public interface ICountryDetailsDAO {

    public void save(CountryDetails convertCountryDetailsDTOToBean);

    public CountryDetails getDetailsByCountryId(BigInteger id);

}
