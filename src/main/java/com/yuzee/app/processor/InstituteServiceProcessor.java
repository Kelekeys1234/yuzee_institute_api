package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteService;
import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dao.InstituteServiceDao;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.dto.InstituteServiceResponseDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.StorageHandler;

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

	@Autowired
	private ModelMapper modelMapper;

	public void addInstituteService(String userId, String instituteId, List<InstituteServiceDto> instituteServiceDtos)
			throws NotFoundException, ValidationException {
		log.debug("inside addInstituteService() method");
		log.info("Getting all exsisting services");
		Institute institute = instituteDao.get(instituteId);
		if (ObjectUtils.isEmpty(institute)) {
			log.error("invalid institute id: {}", instituteId);
			throw new ValidationException("Invalid institute id: " + instituteId);
		}

		List<ServiceDto> serviceDtos = instituteServiceDtos.stream().map(e -> e.getService())
				.collect(Collectors.toList());
		//////////////////// code for adding the new services //////////////////////////

		List<ServiceDto> newServicesTobeAdded = new ArrayList<>(serviceDtos);

		newServicesTobeAdded.removeIf(e -> !StringUtils.isEmpty(e.getServiceId()));

		if (!newServicesTobeAdded.isEmpty()) {
			newServicesTobeAdded = serviceProcessor.saveAllServices(userId, newServicesTobeAdded);
			serviceDtos.removeIf(e -> StringUtils.isEmpty(e.getServiceId()));
			serviceDtos.addAll(newServicesTobeAdded);
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////

		Set<String> serviceIds = serviceDtos.stream().map(ServiceDto::getServiceId).collect(Collectors.toSet());

		List<Service> servicesFromDb = serviceDao.getAllByIds(serviceIds.stream().collect(Collectors.toList()));
		Map<String, Service> mapServicesById = servicesFromDb.stream()
				.collect(Collectors.toMap(Service::getId, e -> e));
		Map<String, Service> mapServicesByName = servicesFromDb.stream()
				.collect(Collectors.toMap(Service::getName, e -> e));

		if (serviceIds.size() != servicesFromDb.size()) {
			log.error("one or more service ids are invalid");
			throw new ValidationException("one or more service ids are invalid");
		}

		List<InstituteService> existingInstituteServices = instituteServiceDao.getAllInstituteService(instituteId);
		Set<String> existingServiceIds = existingInstituteServices.stream().map(e -> e.getService().getId())
				.collect(Collectors.toSet());

		List<InstituteService> listOfServiceToBeSaved = new ArrayList<>();
		for (InstituteServiceDto instituteServiceDto : instituteServiceDtos) {

			String serviceId = instituteServiceDto.getService().getServiceId();
			if (!StringUtils.isEmpty(instituteServiceDto.getService().getServiceName())) {
				serviceId = mapServicesByName.get(instituteServiceDto.getService().getServiceName()).getId();
			}
			if (!existingServiceIds.contains(serviceId)) {
				log.info("No service present for institute with service Id {} adding it to list", serviceId);

				Service service = mapServicesById.get(serviceId);
				if (ObjectUtils.isEmpty(service)) {
					log.error("Illegal service id: {}", serviceId);
					throw new NotFoundException("Illegal service id: " + serviceId);
				}
				InstituteService instituteService = new InstituteService(institute, service,
						instituteServiceDto.getDescription(), new Date(), new Date(), userId, userId);
				listOfServiceToBeSaved.add(instituteService);
			} else {
				String serviceIdCopy = serviceId;// only to make service id effective final to use in stream
				log.info("Institute service already present for institute service id {} skipping it", serviceId);
				Optional<InstituteService> optionalInstituteService = existingInstituteServices.stream()
						.filter(e -> e.getService().getId().equals(serviceIdCopy)).findFirst();
				if (optionalInstituteService.isPresent()) {
					InstituteService existingInstituteService = optionalInstituteService.get();
					existingInstituteService.setDescription(instituteServiceDto.getDescription());
					existingInstituteService.setUpdatedOn(new Date());
					existingInstituteService.setUpdatedBy(userId);
					listOfServiceToBeSaved.add(existingInstituteService);
				}
			}
		}
		log.info("Persisting resource list to DB ");
		instituteServiceDao.saveAll(listOfServiceToBeSaved);
	}

	public void deleteInstituteService(String instituteId, List<String> serviceIds) {
		log.debug("inside deleteInstituteService() method");
		instituteServiceDao.deleteByInstituteIdAndServiceByIds(instituteId, serviceIds);
	}

	public List<InstituteServiceResponseDto> getInstituteServices(String instituteId)
			throws NotFoundException, InvokeException {
		log.debug("inside getServiceByInstituteId() method");
		log.info("Getting all services for institute id {}", instituteId);

		List<InstituteService> instituteServices = instituteServiceDao.getAllInstituteService(instituteId);
		List<InstituteServiceResponseDto> instituteServiceResponseDtos = instituteServices.stream()
				.map(e -> modelMapper.map(e, InstituteServiceResponseDto.class)).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(instituteServiceResponseDtos)) {
			log.info("Service from db not empty for institute id " + instituteId);
//////////////// getting logo for services /////////////////////////////////////// 
			List<String> serviceIds = instituteServiceResponseDtos.stream().map(e -> e.getService().getServiceId())
					.collect(Collectors.toList());
			List<StorageDto> serviceLogos = storageHandler.getStorages(serviceIds, EntityTypeEnum.SERVICE,
					EntitySubTypeEnum.LOGO);
			Map<String, String> mapServiceIcons = serviceLogos.stream()
					.collect(Collectors.toMap(StorageDto::getEntityId, StorageDto::getFileURL));
///////////////////////////////////////////////////////////////////////////////////////////
//////////////// getting media for instiute services /////////////////////////////////////
			List<StorageDto> mediaStorages = storageHandler.getStorages(
					instituteServiceResponseDtos.stream().map(InstituteServiceDto::getId).collect(Collectors.toList()),
					EntityTypeEnum.INSTITUTE_SERVICE, EntitySubTypeEnum.MEDIA);
////////////////////////////////////////////////////////////////////////////////////////
			instituteServiceResponseDtos.stream().forEach(e -> {
				List<StorageDto> serviceMediaStorages = mediaStorages.stream()
						.filter(m -> m.getEntityId().equals(e.getId())).collect(Collectors.toList());
				e.setMedia(serviceMediaStorages);
				e.getService().setIcon(mapServiceIcons.get(e.getService().getServiceId()));
			});
		}
		return instituteServiceResponseDtos;
	}
}
