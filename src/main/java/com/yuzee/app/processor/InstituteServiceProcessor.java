package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteService;
import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dao.InstituteServiceDao;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.util.DTOUtils;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@Slf4j
public class InstituteServiceProcessor {

	@Autowired
	private InstituteServiceDao instituteServiceDao;

	@Autowired
	private InstituteDao instituteDao;

	@Autowired
	private ServiceDao serviceDao;

	@Autowired
	private ServiceProcessor serviceProcessor;

	@Autowired
	private StorageHandler storageHandler;

	public void addInstituteService(String userId, String instituteId, InstituteServiceDto instituteServiceDto)
			throws NotFoundException, ValidationException {
		log.debug("inside addInstituteService() method");
		log.info("Getting all exsisting services");
		Institute institute = instituteDao.get(instituteId);
		if (ObjectUtils.isEmpty(institute)) {
			log.error("invalid institute id: {}", instituteId);
			throw new ValidationException("Invalid institute id: " + instituteId);
		}

		//////////////////// code for adding the new services //////////////////////////

		List<ServiceDto> newServicesTobeAdded = new ArrayList<>(instituteServiceDto.getServices());

		newServicesTobeAdded.removeIf(e -> !StringUtils.isEmpty(e.getServiceId()));

		if (!newServicesTobeAdded.isEmpty()) {
			newServicesTobeAdded = serviceProcessor.saveAllServices(userId, newServicesTobeAdded);
			instituteServiceDto.getServices().removeIf(e -> StringUtils.isEmpty(e.getServiceId()));
			instituteServiceDto.getServices().addAll(newServicesTobeAdded);
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////

		Set<String> serviceIds = instituteServiceDto.getServices().stream().map(ServiceDto::getServiceId)
				.collect(Collectors.toSet());

		Map<String, Service> mapServices = serviceDao.getAllByIds(serviceIds.stream().collect(Collectors.toList()))
				.stream().collect(Collectors.toMap(Service::getId, e -> e));

		if (mapServices.size() != serviceIds.size()) {
			log.error("one or more service ids are invalid");
			throw new ValidationException("one or more service ids are invalid");
		}

		Set<String> existingServiceIds = instituteServiceDao.getAllInstituteService(instituteId).stream()
				.map(e -> e.getService().getId()).collect(Collectors.toSet());

		List<InstituteService> listOfServiceToBeSaved = new ArrayList<>();
		for (String serviceId : serviceIds) {

			if (!existingServiceIds.contains(serviceId)) {
				log.info("No service present for institute with service Id {} adding it to list", serviceId);

				Service service = mapServices.get(serviceId);
				if (ObjectUtils.isEmpty(service)) {
					log.error("Illegal service id: {}", serviceId);
					throw new NotFoundException("Illegal service id: " + serviceId);
				}
				InstituteService instituteService = new InstituteService(institute, service, new Date(), new Date(),
						userId, userId);
				listOfServiceToBeSaved.add(instituteService);
			} else {
				log.info("Institute service already present for institute service id {} skipping it", serviceId);
			}
		}
		log.info("Persisting resource list to DB ");
		instituteServiceDao.saveAll(listOfServiceToBeSaved);
	}

	public void deleteInstituteService(String instituteId, List<String> serviceIds) {
		log.debug("inside deleteInstituteService() method");
		instituteServiceDao.deleteByInstituteIdAndServiceByIds(instituteId, serviceIds);
	}

	public InstituteServiceDto getServicesByInstituteId(String instituteId) throws NotFoundException, InvokeException {
		InstituteServiceDto instituteServiceDto = new InstituteServiceDto();
		log.debug("inside getServiceByInstituteId() method");
		log.info("Getting all services for institute id {}", instituteId);

		List<InstituteService> listOfExsistingInstituteServices = instituteServiceDao
				.getAllInstituteService(instituteId);
		if (!CollectionUtils.isEmpty(listOfExsistingInstituteServices)) {
			log.info("Service from db not empty for institute id " + instituteId);
			instituteServiceDto = DTOUtils.createInstituteServiceResponseDto(listOfExsistingInstituteServices);
			List<ServiceDto> services = instituteServiceDto.getServices();
			if (!services.isEmpty()) {
				List<String> serviceIds = services.stream().map(ServiceDto::getServiceId).collect(Collectors.toList());
				List<StorageDto> storages = storageHandler.getStorages(serviceIds, EntityTypeEnum.SERVICE,
						EntitySubTypeEnum.LOGO);
				Map<String, String> serviceLogoMap = storages.stream()
						.collect(Collectors.toMap(StorageDto::getEntityId, StorageDto::getFileURL));
				services.stream().forEach(e -> e.setIcon(serviceLogoMap.get(e.getServiceId())));
			}
		}
		return instituteServiceDto;
	}
}
