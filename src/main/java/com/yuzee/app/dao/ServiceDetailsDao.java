package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Service;

public interface ServiceDetailsDao {

    public void save(Service obj);

    public void update(Service obj);

    public Service get(String id);

    public List<Service> getAllInstituteByCountry(String countryId);

    public List<Service> getAll();

    public com.yuzee.app.bean.Service getServiceById(String id);

	public List<String> getServicesById(String id);

	public List<String> getServiceNameByInstituteId(String id);
}
