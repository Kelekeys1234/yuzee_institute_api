package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteDetails;

public interface IInstituteDetailsDAO {

    void save(InstituteDetails obj);

    void update(InstituteDetails obj);

    InstituteDetails get(BigInteger id);

    List<InstituteDetails> getAllInstituteByCountry(BigInteger countryId);

    List<InstituteDetails> getAll();

    List<InstituteDetails> findByInstituteId(BigInteger id);
}
