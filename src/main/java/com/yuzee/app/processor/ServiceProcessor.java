package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.util.PaginationUtil;

@org.springframework.stereotype.Service
@Transactional
public class ServiceProcessor {

	@Autowired
	private ServiceDao serviceDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StorageHandler storageHandler;

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

	public PaginationResponseDto getAllServices(final Integer pageNumber, final Integer pageSize)
			throws NotFoundException, InvokeException {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<Service> servicesPage = serviceDao.getAllServices(pageable);
		List<ServiceDto> serviceDtos = servicesPage.getContent().stream().map(e -> modelMapper.map(e, ServiceDto.class))
				.collect(Collectors.toList());
		if (!serviceDtos.isEmpty()) {
			List<String> serviceIds = serviceDtos.stream().map(ServiceDto::getServiceId).collect(Collectors.toList());
			List<StorageDto> storages = storageHandler.getStorages(serviceIds, EntityTypeEnum.SERVICE,
					EntitySubTypeEnum.LOGO);
			Map<String, String> serviceLogoMap = storages.stream()
					.collect(Collectors.toMap(StorageDto::getEntityId, StorageDto::getFileURL));
			serviceDtos.stream().forEach(e -> e.setIcon(serviceLogoMap.get(e.getServiceId())));
		}
		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, ((Long) servicesPage.getTotalElements()).intValue(), serviceDtos);
	}
}
