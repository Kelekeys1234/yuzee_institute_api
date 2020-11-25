package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.commons.collections4.CollectionUtils;
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
import com.yuzee.app.exception.NotFoundException;
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

	public void addInstituteService(String userId, String instituteId, InstituteServiceDto instituteServiceDto)
			throws NotFoundException {
		List<InstituteService> listOfServiceToBeSaved = new ArrayList<>();
		log.debug("inside addInstituteService() method");
		log.info("Getting all exsisting services");
		Institute institute = instituteDao.get(instituteId);
		if (ObjectUtils.isEmpty(institute)) {
			log.error("invalid institute id: {}", instituteId);
			throw new ValidationException("Invalid institute id: " + instituteId);
		}

		Map<String, Service> mapServices = serviceDao.getAllByIds(
				instituteServiceDto.getServices().stream().map(e -> e.getServiceId()).collect(Collectors.toList()))
				.stream().collect(Collectors.toMap(Service::getId, e -> e));
		if (mapServices.size() != instituteServiceDto.getServices().size()) {
			log.error("one or more service ids are invalid");
			throw new ValidationException("one or more service ids are invalid");
		}
		Set<String> existingServiceIds = instituteServiceDao.getAllInstituteService(instituteId).stream()
				.map(e -> e.getService().getId()).collect(Collectors.toSet());

		for (ServiceDto serviceDto : instituteServiceDto.getServices()) {

			if (!existingServiceIds.contains(serviceDto.getServiceId())) {
				log.info("No service present for institute with service Id {} adding it to list",
						serviceDto.getServiceId());

				Service service = mapServices.get(serviceDto.getServiceId());
				if (ObjectUtils.isEmpty(service)) {
					log.error("Illegal service id: {}", serviceDto.getServiceId());
					throw new NotFoundException("Illegal service id: " + serviceDto.getServiceId());
				}
				InstituteService instituteService = new InstituteService(institute, service, new Date(), new Date(),
						userId, userId);

				listOfServiceToBeSaved.add(instituteService);
			} else {
				log.info("Institute service present for institute service id {} skipping it",
						serviceDto.getServiceId());
			}
		}
		log.info("Persisting resource list to DB ");
		instituteServiceDao.saveAll(listOfServiceToBeSaved);
	}

	public void deleteInstituteService(String instituteId, List<String> serviceIds) {
		log.debug("inside deleteInstituteService() method");
		instituteServiceDao.deleteByInstituteIdAndServiceByIds(instituteId, serviceIds);
	}

	public InstituteServiceDto getServiceByInstituteId(String instituteId) {
		InstituteServiceDto instituteServiceDto = new InstituteServiceDto();
		log.debug("inside getServiceByInstituteId() method");
		log.info("Getting all services for institute id {}", instituteId);

		List<InstituteService> listOfExsistingInstituteServices = instituteServiceDao
				.getAllInstituteService(instituteId);
		if (!CollectionUtils.isEmpty(listOfExsistingInstituteServices)) {
			log.info("Service from db not empty for institute id " + instituteId);
			instituteServiceDto = DTOUtils.createInstituteServiceResponseDto(listOfExsistingInstituteServices);
		}
		return instituteServiceDto;
	}
}
