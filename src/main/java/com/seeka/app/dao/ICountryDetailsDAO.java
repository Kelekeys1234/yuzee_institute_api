package com.seeka.app.dao;

import com.seeka.app.bean.CountryDetails;

public interface ICountryDetailsDAO {

    public void save(CountryDetails convertCountryDetailsDTOToBean);

    public CountryDetails getDetailsByCountryId(String id);

}
