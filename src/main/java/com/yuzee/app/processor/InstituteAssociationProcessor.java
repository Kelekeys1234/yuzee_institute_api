package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteAssociation;
import com.yuzee.app.constant.InstituteAssociationStatus;
import com.yuzee.app.constant.InstituteAssociationType;
import com.yuzee.app.dao.InstituteAssociationDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.InstituteAssociationDto;
import com.yuzee.app.dto.InstituteAssociationResponseDto;
import com.yuzee.common.lib.dto.identity.UserInstituteAccessInternalResponseDto;
import com.yuzee.common.lib.dto.storage.StorageDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.exception.InternalServerException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.handler.UserInstituteAccessRoleHandler;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteAssociationProcessor {
 
	@Autowired
	private InstituteAssociationDao instituteAssociationDao;
	
	@Autowired
	private InstituteDao iInstituteDAO;
	
	@Autowired
	private UserInstituteAccessRoleHandler userInstituteAccessRoleHandler;
	
	@Autowired
	private StorageHandler storageHandler;
	
	@Value("${rejection.count}")
	public Integer rejectionCount;
	
	@Value("${association.message}")
	public String associationMessage;
	
	public void addInstituteAssociation (String userId , InstituteAssociationDto instituteAssociationDto) throws Exception {
		log.debug("Inside addInstituteAssociation() method ");
		// TODO check user id have access for institute id (source institute id)
		log.info("Getting source institute having institute id "+instituteAssociationDto.getSourceInstituteId());
		Optional<Institute> sourceInstituteFromDB = iInstituteDAO.getInstituteByInstituteId(instituteAssociationDto.getSourceInstituteId());
		if (!sourceInstituteFromDB.isPresent()) {
			log.error("No institute found for institute having id "+instituteAssociationDto.getSourceInstituteId());
			throw new NotFoundException("No institute found for institute having id "+instituteAssociationDto.getSourceInstituteId());
		}
		log.info("Getting destination institute having institute id "+instituteAssociationDto.getSourceInstituteId());
		Optional<Institute> destinationInstituteFromDB = iInstituteDAO.getInstituteByInstituteId(instituteAssociationDto.getDestinationInstituteId());
		if (!destinationInstituteFromDB.isPresent()) {
			log.error("No institute found for institute having id "+instituteAssociationDto.getSourceInstituteId());
			throw new NotFoundException("No institute found for institute having id "+instituteAssociationDto.getDestinationInstituteId());
		}
		log.info("Getting user access for institute id "+instituteAssociationDto.getDestinationInstituteId());
		List<UserInstituteAccessInternalResponseDto> listOfUserInstituteAccessInternalResponseDto = userInstituteAccessRoleHandler.getUserInstituteAccessInternal(instituteAssociationDto.getDestinationInstituteId(), "ACTIVE");
		
		if (CollectionUtils.isEmpty(listOfUserInstituteAccessInternalResponseDto) || !listOfUserInstituteAccessInternalResponseDto.stream().anyMatch(x -> x.getRole().equalsIgnoreCase("super admin") || x.getRole().equalsIgnoreCase("admin")) ) {
			log.error("There is no admin or super admin associated with institute id "+instituteAssociationDto.getDestinationInstituteId()  );
			throw new InternalServerException("There is no admin or super admin associated with institute id "+instituteAssociationDto.getDestinationInstituteId());
		}
		
		log.info("Getting association between institute id"+instituteAssociationDto.getSourceInstituteId()+ " and institute id"+instituteAssociationDto.getDestinationInstituteId()+ " association type "+instituteAssociationDto.getAssociationType());
		InstituteAssociation instituteAssociationFromDb = instituteAssociationDao.getInstituteAssociationBasedOnAssociationType(instituteAssociationDto.getSourceInstituteId(), instituteAssociationDto.getDestinationInstituteId(), InstituteAssociationType.valueOf(instituteAssociationDto.getAssociationType()));
		if (ObjectUtils.isEmpty(instituteAssociationFromDb)) {
			log.info("association not found between institute id"+instituteAssociationDto.getSourceInstituteId()+ " and institute id"+instituteAssociationDto.getDestinationInstituteId()+ " for association type "+instituteAssociationDto.getAssociationType()+ " creating new association");
			InstituteAssociation instituteAssociation = new InstituteAssociation(instituteAssociationDto.getSourceInstituteId(), instituteAssociationDto.getDestinationInstituteId(), InstituteAssociationType.valueOf(instituteAssociationDto.getAssociationType()), InstituteAssociationStatus.PENDING,0, new Date(), new Date(), "API", "API");
			instituteAssociationDao.addInstituteAssociation(instituteAssociation);
			listOfUserInstituteAccessInternalResponseDto.stream().forEach(userInstituteAccess -> {
				if (userInstituteAccess.getRole().equalsIgnoreCase("super admin") || userInstituteAccess.getRole().equalsIgnoreCase("admin")) {
					log.info("sending push notification for association to user id "+userInstituteAccess.getUserId());
					/*
					 * try { iUserService.sendPushNotification(userInstituteAccess.getUserId(),
					 * associationMessage, NotificationType.ASSOCIATION_NOTIFICATION.toString()); }
					 * catch (ValidationException e) {
					 * log.error("Exception occured sending push notificaton to user id "
					 * +userInstituteAccess.getUserId()); }
					 */
				}	
			});
			
		} else if (instituteAssociationFromDb.getInstituteAssociationStatus().equals(InstituteAssociationStatus.valueOf("PENDING"))) {
			log.info("association has been already established between institute id"+instituteAssociationDto.getSourceInstituteId()+ " and institute id"+instituteAssociationDto.getDestinationInstituteId()+ " for association type "+instituteAssociationDto.getAssociationType()+ " creating new association");
		} else if (instituteAssociationFromDb.getInstituteAssociationStatus().equals(InstituteAssociationStatus.valueOf("ACTIVE"))) {
			log.info("association has been already established between institute id"+instituteAssociationDto.getSourceInstituteId()+ " and institute id"+instituteAssociationDto.getDestinationInstituteId()+ " for association type "+instituteAssociationDto.getAssociationType()+ " creating new association");
		} else if (instituteAssociationFromDb.getInstituteAssociationStatus().equals(InstituteAssociationStatus.valueOf("REJECTED"))) {
			log.warn("association has been rejected between institute id"+instituteAssociationDto.getSourceInstituteId()+ " and institute id"+instituteAssociationDto.getDestinationInstituteId()+ " for association type "+instituteAssociationDto.getAssociationType()+ " creating new association");
			log.info("Checking if reject counter is less than "+rejectionCount);
			if (instituteAssociationFromDb.getRejectionCounter() <= rejectionCount) {
				Integer counter = instituteAssociationFromDb.getRejectionCounter() + 1;
				log.info("changing status of association to pending between institute id "+instituteAssociationDto.getSourceInstituteId()+ " and institute id"+instituteAssociationDto.getDestinationInstituteId()+ " for association type"+instituteAssociationDto.getAssociationType());
				instituteAssociationFromDb.setInstituteAssociationStatus(InstituteAssociationStatus.PENDING);
				instituteAssociationFromDb.setRejectionCounter(counter);
				instituteAssociationDao.addInstituteAssociation(instituteAssociationFromDb);
				listOfUserInstituteAccessInternalResponseDto.stream().forEach(userInstituteAccess -> {
					if (userInstituteAccess.getRole().equalsIgnoreCase("super admin") || userInstituteAccess.getRole().equalsIgnoreCase("admin")) {
						log.info("sending push notification for association to user id "+userInstituteAccess.getUserId());
						/*
						 * try { iUserService.sendPushNotification(userInstituteAccess.getUserId(),
						 * associationMessage, NotificationType.ASSOCIATION_NOTIFICATION.toString()); }
						 * catch (ValidationException e) {
						 * log.error("Exception occured sending push notificaton to user id "
						 * +userInstituteAccess.getUserId()); }
						 */
					}	
				});
			} else {
				log.error("Cannot add any new request as rejection/revoke counter is greated than "+rejectionCount);
				throw new InternalServerException("Cannot add any new request as rejection/revoke counter is greated than "+rejectionCount);
			}
		} else if (instituteAssociationFromDb.getInstituteAssociationStatus().equals(InstituteAssociationStatus.valueOf("REVOKED"))) {
			log.warn("association has been revoked between institute id"+instituteAssociationDto.getSourceInstituteId()+ " and institute id"+instituteAssociationDto.getDestinationInstituteId()+ " for association type "+instituteAssociationDto.getAssociationType()+ " creating new association");
			log.info("Checking if reject counter is less than "+rejectionCount);
			if (instituteAssociationFromDb.getRejectionCounter() <= rejectionCount) {
				Integer counter = instituteAssociationFromDb.getRejectionCounter() + 1;
				log.info("changing status of association to pending between institute id "+instituteAssociationDto.getSourceInstituteId()+ " and institute id"+instituteAssociationDto.getDestinationInstituteId()+ " for association type"+instituteAssociationDto.getAssociationType());
				instituteAssociationFromDb.setInstituteAssociationStatus(InstituteAssociationStatus.PENDING);
				instituteAssociationFromDb.setRejectionCounter(counter);
				instituteAssociationDao.addInstituteAssociation(instituteAssociationFromDb);
				listOfUserInstituteAccessInternalResponseDto.stream().forEach(userInstituteAccess -> {
					if (userInstituteAccess.getRole().equalsIgnoreCase("super admin") || userInstituteAccess.getRole().equalsIgnoreCase("admin")) {
						log.info("sending push notification for association to user id "+userInstituteAccess.getUserId());
						/*
						 * try { iUserService.sendPushNotification(userInstituteAccess.getUserId(),
						 * associationMessage, NotificationType.ASSOCIATION_NOTIFICATION.toString()); }
						 * catch (ValidationException e) {
						 * log.error("Exception occured sending push notificaton to user id "
						 * +userInstituteAccess.getUserId()); }
						 */
					}	
				});
			} else {
				log.error("Cannot add any new request as rejection/revoke counter is greated than "+rejectionCount);
				throw new InternalServerException("Cannot add any new request as rejection/revoke counter is greated than "+rejectionCount);
			}
		}			
	}
	
	public List<InstituteAssociationResponseDto> getInstituteAssociationByAssociationType(String userId ,String instituteId,
			String associationType, String status, String caller) {
		if (caller.equalsIgnoreCase("PRIVATE")) {
			//TODO check user id have appropriated access for institute id
		}
		
		List<InstituteAssociationResponseDto> listOfUserInstituteAssociationResponseDto = new ArrayList<InstituteAssociationResponseDto>();
		log.info("Getting association for institute id " + instituteId + " having association type " + associationType
				+ " and status Active");
		List<InstituteAssociation> listOfInstituteAssociation = instituteAssociationDao
				.getInstituteAssociation(instituteId, InstituteAssociationType.valueOf(associationType), InstituteAssociationStatus.valueOf(status));
		if (!CollectionUtils.isEmpty(listOfInstituteAssociation)) {
			listOfInstituteAssociation.stream().forEach(userAssociation -> {
				 List<StorageDto> listOfStorageDto = null;
				if (userAssociation.getDestinationInstituteId().equals(instituteId)) {
					try {
						 log.info("calling storage to get logo for institute id "+userAssociation.getSourceInstituteId());
						 listOfStorageDto = storageHandler.getStorages(userAssociation.getSourceInstituteId(),EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.LOGO);
					} catch (Exception e) {
						log.error("Error occured while fetching logo for institute id "+userAssociation.getSourceInstituteId());
					}
					log.info("Getting institute for institute id "+userAssociation.getSourceInstituteId());
					Optional<Institute> sourceInstituteFromDB = iInstituteDAO.getInstituteByInstituteId(userAssociation.getSourceInstituteId());
					if (sourceInstituteFromDB.isPresent()) {
						log.info("Institute from DB found for institute id "+userAssociation.getSourceInstituteId());
						InstituteAssociationResponseDto instituteAssociationResponseDto = new InstituteAssociationResponseDto(userAssociation.getId(), sourceInstituteFromDB.get().getLatitude(), sourceInstituteFromDB.get().getLongitude(), sourceInstituteFromDB.get().getName(), sourceInstituteFromDB.get().getCityName(), sourceInstituteFromDB.get().getCountryName(),
							!CollectionUtils.isEmpty(listOfStorageDto) ?listOfStorageDto.get(0).getFileURL() : null, userAssociation.getInstituteAssociationType().toString());
						listOfUserInstituteAssociationResponseDto.add(instituteAssociationResponseDto);
					}
				} else {
					log.info("calling storage to get logo for institute id "+userAssociation.getDestinationInstituteId());
					try {
						 log.info("calling storage to get logo for institute id "+userAssociation.getDestinationInstituteId());
						 listOfStorageDto = storageHandler.getStorages(userAssociation.getDestinationInstituteId(), EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.LOGO);
					} catch (Exception e) {
						log.error("Error occured while fetching logo for institute id "+userAssociation.getDestinationInstituteId());
					}
					log.info("Getting institute for institute id "+userAssociation.getDestinationInstituteId());
					Optional<Institute> destinationInstituteFromDB = iInstituteDAO.getInstituteByInstituteId(userAssociation.getDestinationInstituteId());
					if (destinationInstituteFromDB.isPresent()) {
						log.info("Institute from DB found for institute id "+userAssociation.getDestinationInstituteId());
						InstituteAssociationResponseDto instituteAssociationResponseDto = new InstituteAssociationResponseDto(userAssociation.getId(), destinationInstituteFromDB.get().getLatitude(), destinationInstituteFromDB.get().getLongitude(), destinationInstituteFromDB.get().getName(), destinationInstituteFromDB.get().getCityName(), destinationInstituteFromDB.get().getCountryName(), !CollectionUtils.isEmpty(listOfStorageDto) ?listOfStorageDto.get(0).getFileURL() : null, userAssociation.getInstituteAssociationType().toString());
						listOfUserInstituteAssociationResponseDto.add(instituteAssociationResponseDto);
					}
				}
			});
		}
		return listOfUserInstituteAssociationResponseDto;
	}
	
	public void updateInstituteAssociation (String instituteAssociationId , String instituteId , String userId, String status) throws Exception {
		log.debug("Inside updateInstituteAssociation() method "); 
		//TODO check userId have access to institute id
		Optional<InstituteAssociation> optionalInstituteAssociation = instituteAssociationDao.getInstituteAssociationById(instituteAssociationId);
		if (!optionalInstituteAssociation.isPresent()) {
			log.error("No associationn found for institute association id "+instituteAssociationId);
			throw new NotFoundException("No associationn found for institute association id "+instituteAssociationId);
		}
		
		InstituteAssociation instituteAssociation  = optionalInstituteAssociation.get();
	
		if (!instituteAssociation.getSourceInstituteId().equalsIgnoreCase(instituteId) && !instituteAssociation.getDestinationInstituteId().equalsIgnoreCase(instituteId)) {
			log.error("Institute id pass in request does not match with institute ids save in db");
			throw new InternalServerException("Institute id pass in request does not match with institute ids save in db");
		}
		
		log.info("changing status of institute association with id "+instituteAssociationId+ " from status "+instituteAssociation.getInstituteAssociationStatus().toString()+ " to "+status);
		instituteAssociation.setInstituteAssociationStatus(InstituteAssociationStatus.valueOf(status));
		instituteAssociation.setUpdatedBy("API");
		instituteAssociation.setUpdatedOn(new Date());
		log.info("Persisting association into DB");
		instituteAssociationDao.addInstituteAssociation(instituteAssociation);
	}
}
