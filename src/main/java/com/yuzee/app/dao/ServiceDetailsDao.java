package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Service;

public interface ServiceDetailsDao {

    public void addUpdateServiceDetails(Service service);

    public Service getService(String id);

    public List<Service> getAllInstituteByCountry(String countryId);

    public List<Service> getAllServices();

    public com.yuzee.app.bean.Service getServiceById(String id);

	public List<String> getServicesById(String id);

	public List<String> getServiceNameByInstituteId(String id);
}
