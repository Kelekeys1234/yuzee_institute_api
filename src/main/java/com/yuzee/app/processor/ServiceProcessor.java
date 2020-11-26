package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.exception.ValidationException;

@org.springframework.stereotype.Service
@Transactional
public class ServiceProcessor {

	@Autowired
	private ServiceDao serviceDao;

	@Autowired
	private ModelMapper modelMapper;

	public List<ServiceDto> saveAllServices(String userId, List<ServiceDto> serviceDtos) throws ValidationException {
		List<String> allNames = serviceDtos.stream().map(ServiceDto::getServiceName).collect(Collectors.toList());
		List<Service> existingServices = serviceDao.findByNameIgnoreCaseIn(allNames);
		List<String> existingNames = existingServices.stream().map(Service::getName).collect(Collectors.toList());
		serviceDtos.removeIf(e -> existingNames.contains(e.getServiceName()));
		List<Service> services = new ArrayList<>();
		if (!serviceDtos.isEmpty()) {
			services = serviceDtos.stream().map(e -> modelMapper.map(e, Service.class)).collect(Collectors.toList());
		}
		services.addAll(existingServices);
		services.stream().forEach(e -> {
			if (ObjectUtils.isEmpty(e.getCreatedOn())) {
				e.setCreatedBy(userId);
				e.setCreatedOn(new Date());
			}
			e.setUpdatedBy(userId);
			e.setUpdatedOn(new Date());
		});
		return serviceDao.addUpdateServices(services).stream().map(e -> modelMapper.map(e, ServiceDto.class))
				.collect(Collectors.toList());
	}

	public Optional<Service> getService(String serviceId) {
		return serviceDao.getServiceById(serviceId);
	}

	public Page<Service> getAllServices(final Integer pageNumber, final Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return serviceDao.getAllServices(pageable);
	}
}
