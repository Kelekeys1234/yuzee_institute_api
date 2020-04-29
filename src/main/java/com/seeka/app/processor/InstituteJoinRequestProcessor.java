package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.InstituteJoinRequest;
import com.seeka.app.constant.InstituteJoinStatus;
import com.seeka.app.controller.handler.StorageHandler;
import com.seeka.app.dao.InstituteJoinRequestDao;
import com.seeka.app.dto.InstituteJoinRequestDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteJoinRequestProcessor {

	@Autowired
	private InstituteJoinRequestDao instituteJoinRequestDao;
	
	@Autowired
	private StorageHandler storageHandler;

	public InstituteJoinRequestDto instituteJoinRequest (String userId, InstituteJoinRequestDto instituteJoinRequestDto) throws Exception {
		log.debug("inside instituteJoinRequest() method");
		log.info("getting institute page request for institute name "+instituteJoinRequestDto.getInstituteName()+ " and user id "+userId);
		InstituteJoinRequest instituteJoinRequestFromDb = instituteJoinRequestDao.getInstituteJoinRequestByInstituteNameAndUserId(instituteJoinRequestDto.getInstituteName().toLowerCase(), userId);
		
		if (!ObjectUtils.isEmpty(instituteJoinRequestFromDb)) {
			log.error("Institute join request for institute name "+instituteJoinRequestDto.getInstituteName()+ " from user id " +userId+ " have been received");
			throw new ValidationException("Institute join request for institute name "+instituteJoinRequestDto.getInstituteName()+ " from user id " +userId+ " have been received");
		}
		log.info("Creating new join request for institute name "+instituteJoinRequestDto.getInstituteName());
		InstituteJoinRequest instituteJoinRequest = new InstituteJoinRequest(userId, instituteJoinRequestDto.getInstituteName().toLowerCase(), instituteJoinRequestDto.getInstituteCountry(), 
				instituteJoinRequestDto.getInstituteType(), instituteJoinRequestDto.getType(), instituteJoinRequestDto.getFirstName(), instituteJoinRequestDto.getLastName(),
				instituteJoinRequestDto.getTitle(), instituteJoinRequestDto.getEmail(), instituteJoinRequestDto.getPhoneNumber(), instituteJoinRequestDto.getManagementName(),
				instituteJoinRequestDto.getManagementEmail(), instituteJoinRequestDto.getManagementPhoneNumber(), 
				InstituteJoinStatus.PENDING, new Date(),  new Date(), "API", "API"); 
		InstituteJoinRequest instituteJoinRequestPersist = instituteJoinRequestDao.addInstituteJoinRequest(instituteJoinRequest);
		instituteJoinRequestDto.setInstituteJoinRequestId(instituteJoinRequestPersist.getId());
		instituteJoinRequestDto.setUserId(userId);
		return instituteJoinRequestDto;
	
	}
	
	public List<InstituteJoinRequestDto> getInstituteJoinRequestByStatus(String status) {
		List<InstituteJoinRequestDto> listOfInstituteJoinRequestDto = new ArrayList<InstituteJoinRequestDto>();
		log.debug("inside getInstituteJoinRequestByStatus() method");
		log.info("getting institute join request by status "+status);
		List<InstituteJoinRequest> listOfInstituteJoinRequest = instituteJoinRequestDao.getInstituteJoinRequestByStatus(InstituteJoinStatus.valueOf(status));
		if (!CollectionUtils.isEmpty(listOfInstituteJoinRequest)) {
			log.info("list of institute join request for status "+status+ " is not empty");
			listOfInstituteJoinRequest.stream().forEach(instituteJoinRequest -> {
				List<StorageDto> listOfCertificates = null;
				log.info("calling storage service for getting certificate associated with id "+instituteJoinRequest.getId());
				try {
					listOfCertificates =storageHandler.getCertificates(instituteJoinRequest.getId(), "JOIN REQUEST");
				} catch (Exception e) {
					log.error("Exception "+e);
				}
				InstituteJoinRequestDto instituteJoinRequestDto = new InstituteJoinRequestDto(instituteJoinRequest.getId(), instituteJoinRequest.getUserId(), instituteJoinRequest.getInstituteCountry(),
						instituteJoinRequest.getInstituteName(), instituteJoinRequest.getTypeOfInstitute(), instituteJoinRequest.getType(), instituteJoinRequest.getFirstName(), instituteJoinRequest.getLastName(), instituteJoinRequest.getTitle(), instituteJoinRequest.getWorkEmail(), instituteJoinRequest.getWorkPhoneNumber(), 
						instituteJoinRequest.getManagementName(), instituteJoinRequest.getManagementEmail(), instituteJoinRequest.getManagementPhoneNumber(), listOfCertificates);
				listOfInstituteJoinRequestDto.add(instituteJoinRequestDto);
			});
		}
		return listOfInstituteJoinRequestDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void updateInstituteJoinRequestStatus(String instituteJoinRequestId, String status) throws Exception{
		log.debug("inside updateInstituteJoinRequestStatus() method");
		log.info("Updating institute join request for id "+instituteJoinRequestId+ " to status "+status );
		Optional<InstituteJoinRequest> instituteJoinRequestFromDb = instituteJoinRequestDao.getInstituteJoinRequestById(instituteJoinRequestId);
		if (!instituteJoinRequestFromDb.isPresent()) {
			log.error("No institute join request found for id "+instituteJoinRequestId);
			throw new NotFoundException("No institute join request found for id "+instituteJoinRequestId);
		}
		InstituteJoinRequest instituteJoinRequest = instituteJoinRequestFromDb.get();
		if (!instituteJoinRequest.getInstituteJoinStatus().equals(InstituteJoinStatus.PENDING)) {
			log.error("Can only change status from Pending to granted or rejected");
			throw new ValidationException("Can only change status from Pending to granted or rejected");
		}
		
		instituteJoinRequest.setInstituteJoinStatus(InstituteJoinStatus.valueOf(status));
		instituteJoinRequest.setUpdatedBy("API");
		instituteJoinRequest.setUpdatedOn(new Date());
		instituteJoinRequestDao.addInstituteJoinRequest(instituteJoinRequest);			
	}
}
