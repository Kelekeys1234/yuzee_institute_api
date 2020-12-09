package com.yuzee.app.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Service;
import com.yuzee.app.exception.ValidationException;

public interface ServiceDao {

	public Service addUpdateService(Service service) throws ValidationException;

	public Optional<Service> getServiceById(String id);

	public Page<Service> getAllServices(Pageable pageable);

	public Service findByNameIgnoreCase(String name);
}
