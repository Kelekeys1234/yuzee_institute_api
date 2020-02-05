package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Service;

public interface IServiceDetailsDAO {

    void save(Service obj);

    void update(Service obj);

    Service get(BigInteger id);

    List<Service> getAllInstituteByCountry(BigInteger countryId);

    List<Service> getAll();

    com.seeka.app.bean.Service getServiceById(String id);
}
