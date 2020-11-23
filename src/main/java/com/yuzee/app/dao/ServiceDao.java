package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Service;

public interface ServiceDao {

    public void addUpdateServiceDetails(Service service);

    public Optional<Service> getServiceById(String id);

    public Page<Service> getAllServices(Pageable pageable);

	public List<Service> getAllByIds(List<String> id);
}
