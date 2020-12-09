package com.yuzee.app.processor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

	public InstituteServiceDto addInstituteService(String userId, String instituteId,
			InstituteServiceDto instituteServiceDto) throws ValidationException {
		log.debug("inside addInstituteService() method");
		log.info("Getting all exsisting services");
		Institute institute = instituteDao.get(instituteId);
		if (ObjectUtils.isEmpty(institute)) {
			log.error("invalid institute id: {}", instituteId);
			throw new ValidationException("Invalid institute id: " + instituteId);
		}

		ServiceDto serviceDto = instituteServiceDto.getService();

		// if service is not already present
		if (StringUtils.isEmpty(serviceDto.getServiceId())) {
			serviceDto = serviceProcessor.saveService(userId, serviceDto);
		}

		Optional<Service> serviceFromDbOptional = serviceDao.getServiceById(serviceDto.getServiceId());
		if (!serviceFromDbOptional.isPresent()) {
			log.error("invalid serviceId");
			throw new ValidationException("invalid serviceId");
		}
		Service service = serviceFromDbOptional.get();

		InstituteService existingInstituteService = instituteServiceDao.findByInstituteIdAndServiceId(instituteId,
				instituteServiceDto.getService().getServiceId());
		InstituteService instituteService = null;

		if (!ObjectUtils.isEmpty(existingInstituteService)) {
			log.info("Institute Service already present going to update it");
			instituteService = existingInstituteService;
			instituteService.setUpdatedBy(userId);
			instituteService.setUpdatedOn(new Date());
			instituteService.setDescription(instituteServiceDto.getDescription());
		} else {
			instituteService = new InstituteService(institute, service, instituteServiceDto.getDescription(),
					new Date(), new Date(), userId, userId);
		}

		InstituteServiceDto dto = modelMapper.map(instituteServiceDao.save(instituteService),
				InstituteServiceDto.class);
		List<StorageDto> serviceLogos = null;
		try {
			serviceLogos = storageHandler.getStorages(dto.getService().getServiceId(), EntityTypeEnum.SERVICE,
					EntitySubTypeEnum.LOGO);
		} catch (NotFoundException | InvokeException e) {
			log.error("error invoking storage service");
		}
		if (!CollectionUtils.isEmpty(serviceLogos)) {
			dto.getService().setIcon(serviceLogos.get(0).getFileURL());
		}
		return dto;
	}

	@Transactional(rollbackOn = Throwable.class)
	public void deleteInstituteService(String instituteServiceId) throws NotFoundException, InvokeException {
		log.debug("inside deleteInstituteService() method");
		instituteServiceDao.delete(instituteServiceId);
		storageHandler.deleteStorageBasedOnEntityId(instituteServiceId);
	}

	public List<InstituteServiceDto> getInstituteServices(String instituteId) {
		log.debug("inside getServiceByInstituteId() method");
		log.info("Getting all services for institute id {}", instituteId);

		List<InstituteService> instituteServices = instituteServiceDao.getAllInstituteService(instituteId);
		List<InstituteServiceDto> instituteServiceResponseDtos = instituteServices.stream()
				.map(e -> modelMapper.map(e, InstituteServiceDto.class)).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(instituteServiceResponseDtos)) {
			log.info("Service from db not empty for institute id " + instituteId);
//////////////// getting logo for services /////////////////////////////////////// 
			List<String> serviceIds = instituteServiceResponseDtos.stream().map(e -> e.getService().getServiceId())
					.collect(Collectors.toList());
			List<StorageDto> serviceLogos;
			try {
				serviceLogos = storageHandler.getStorages(serviceIds, EntityTypeEnum.SERVICE, EntitySubTypeEnum.LOGO);
				Map<String, String> mapServiceIcons = serviceLogos.stream()
						.collect(Collectors.toMap(StorageDto::getEntityId, StorageDto::getFileURL));
///////////////////////////////////////////////////////////////////////////////////////////
//////////////// getting media for instiute services /////////////////////////////////////
				List<StorageDto> mediaStorages = storageHandler
						.getStorages(
								instituteServiceResponseDtos.stream().map(InstituteServiceDto::getId)
										.collect(Collectors.toList()),
								EntityTypeEnum.INSTITUTE_SERVICE, EntitySubTypeEnum.MEDIA);
////////////////////////////////////////////////////////////////////////////////////////
				instituteServiceResponseDtos.stream().forEach(e -> {
					List<StorageDto> serviceMediaStorages = mediaStorages.stream()
							.filter(m -> m.getEntityId().equals(e.getId())).collect(Collectors.toList());
					e.setMedia(serviceMediaStorages);
					e.getService().setIcon(mapServiceIcons.get(e.getService().getServiceId()));
				});
			} catch (NotFoundException | InvokeException e1) {
				log.error("error invoking storage service");
			}
		}
		return instituteServiceResponseDtos;
	}
}
