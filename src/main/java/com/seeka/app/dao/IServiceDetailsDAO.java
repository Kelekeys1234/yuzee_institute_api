package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Service;

public interface IServiceDetailsDAO {

    void save(Service obj);

    void update(Service obj);

    Service get(String id);

    List<Service> getAllInstituteByCountry(String countryId);

    List<Service> getAll();

    com.seeka.app.bean.Service getServiceById(String id);
}
