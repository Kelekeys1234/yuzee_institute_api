package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.InstituteService;
import com.yuzee.app.dao.InstituteServiceDetailsDao;
import com.yuzee.app.dto.InstituteServiceDto;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteServiceProcessor {

	@Autowired
	private InstituteServiceDetailsDao instituteServiceDao;
	
	//Asif needs to look into these method, as per discussion with Asif comment it for now
	
	public void addInstituteService (String userId ,String instituteId , InstituteServiceDto instituteServiceDto) throws Exception {/*
		List<InstituteService> listOfServiceToBeSaved = new ArrayList<InstituteService>();
		log.debug("inside addInstituteService() method");
		log.info("Getting all exsisting services");
	//	userAccessUtils.validateUserAccess(userId, instituteId, "service page", "add");
		List<InstituteService> listOfExsistingInstituteServices  = instituteServiceDao.getAllInstituteService(instituteId);
		if (CollectionUtils.isEmpty(listOfExsistingInstituteServices )) {
			log.info("No institute service present in DB saving all services passed in request into DB");
			instituteServiceDto.getServices().stream().forEach(serviceDto -> {
				InstituteService instituteService = new InstituteService(instituteId, serviceDto.getServiceId(), true, new Date(), new Date(), "API", "API");
				log.info("Adding service with name into DB "+serviceDto.getServiceName());
				listOfServiceToBeSaved.add(instituteService);
			});
		} else {
			log.info("checking all exsisting services to match with service passed in request ");
			instituteServiceDto.getServices().stream().forEach(serviceDto -> {
				InstituteService instituteServiceFromDB = listOfExsistingInstituteServices.stream().filter(serviceFromDB -> serviceFromDB.getServiceId().equalsIgnoreCase(serviceDto.getServiceId())).findAny().orElse(null);
				if (ObjectUtils.isEmpty(instituteServiceFromDB)) {
					log.info("No service present for service id "+ serviceDto.getServiceId() + " adding it to list");
					InstituteService instituteService = new InstituteService(instituteId, serviceDto.getServiceId(), true, new Date(), new Date(), "API", "API");
					listOfServiceToBeSaved.add(instituteService);
				} else {
					log.info("service present for service id "+ serviceDto.getServiceId() + " skipping it");
				}
			});	
		}
		log.info("Persisting resource list to DB ");
		instituteServiceDao.saveInstituteServices(listOfServiceToBeSaved);
	*/}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteInstituteService (String userId, String instituteId , List<String> instituteServiceId ) throws Exception { 
		log.debug("inside deleteInstituteService() method");
//		userAccessUtils.validateUserAccess(userId, instituteId, "service page", "delete");
		if (!CollectionUtils.isEmpty(instituteServiceId)) {
			instituteServiceId.stream().forEach(serviceId -> {
				log.info("deleting services having service Id "+serviceId+ " and institute id "+instituteId);
				instituteServiceDao.deleteServiceByIdAndInstituteId(serviceId, instituteId);
			});
		} else {
			log.warn("no service id passed in request");
		}
	}
	
	public InstituteServiceDto getServiceByInstituteId( String userId , String instituteId ) throws Exception {
		InstituteServiceDto instituteServiceDto = new InstituteServiceDto ();
		log.debug("inside getServiceByInstituteId() method");
		//userAccessUtils.validateUserAccess(userId, instituteId, "service page", "get");
		log.info("Getting all services for institute id "+instituteId);
		List<InstituteService> listOfExsistingInstituteServices  = instituteServiceDao.getAllInstituteService(instituteId);
		if (!CollectionUtils.isEmpty(listOfExsistingInstituteServices)) {
			log.info("Service from db not empty for institute id "+instituteId);
			//instituteServiceDto = DTOUtils.createInstituteServiceResponseDto(listOfExsistingInstituteServices);
		}
		return instituteServiceDto;	
	}
	
	public InstituteServiceDto getPublicServiceByInstituteId(String instituteId ) {
		InstituteServiceDto instituteServiceDto = new InstituteServiceDto ();
		log.debug("inside getServiceByInstituteId() method");
		log.info("Getting all services for institute id "+instituteId);
		List<InstituteService> listOfExsistingInstituteServices  = instituteServiceDao.getAllInstituteService(instituteId);
		if (!CollectionUtils.isEmpty(listOfExsistingInstituteServices)) {
			log.info("Service from db not empty for institute id "+instituteId);
			//instituteServiceDto = DTOUtils.createInstituteServiceResponseDto(listOfExsistingInstituteServices);
		}
		return instituteServiceDto;	
	}

    public void save(InstituteService instituteService) {
    	instituteServiceDao.save(instituteService);
    }

    public void update(InstituteService instituteService) {
    	instituteServiceDao.update(instituteService);
    }

    public InstituteService get(String id) {
        return instituteServiceDao.get(id);
    }

    public List<InstituteService> getAll() {
        return instituteServiceDao.getAll();
    }

    public List<String> getAllServices(String instituteId) {
    	List<String> instituteServiceNames = new ArrayList<>();
    	List<InstituteService> instituteServices = instituteServiceDao.getAllServices(instituteId);
    	if(!CollectionUtils.isEmpty(instituteServices)) {
			instituteServices.stream().forEach(instituteService -> {
				instituteServiceNames.add(instituteService.getServiceName());
			});
		}
        return instituteServiceNames;
    }


}
