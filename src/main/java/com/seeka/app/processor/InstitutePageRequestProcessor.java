package com.seeka.app.processor;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.InstitutePageRequest;
import com.seeka.app.constant.PageRequestStatus;
import com.seeka.app.dao.InstitutePageRequestDao;
import com.seeka.app.dto.InstitutePageRequestDto;
import com.seeka.app.exception.ValidationException;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstitutePageRequestProcessor {
	
	@Autowired
	private InstitutePageRequestDao institutePageRequestDao;

	public InstitutePageRequestDto requestInstiutePageRequest (String userId, String instituteId , InstitutePageRequestDto institutePageRequestDto) throws Exception {
		log.debug("inside requestInstiutePageRequest() method");
		log.info("getting institute page request by institute id "+instituteId);
		List<InstitutePageRequest> listOfInstitutePageRequest = institutePageRequestDao.getInstitutePageRequestByInstituteId(instituteId);
		InstitutePageRequest institutePageRequestGranted = listOfInstitutePageRequest.stream().filter(institutePageRequest -> institutePageRequest.getPageRequestStatus().equals(PageRequestStatus.GRANTED)).findAny().orElse(null);
		if (!ObjectUtils.isEmpty(institutePageRequestGranted)) {
			log.error("page for institute id "+instituteId+ " have been alloted");
			throw new ValidationException("page for institute id "+instituteId+ " have been alloted");
		}
		log.info("getting institute page request by institute id "+instituteId+ " and user id "+userId);
		InstitutePageRequest institutePageRequestObjectForInstituteIdAndUserId = listOfInstitutePageRequest.stream().filter(institutePageRequest -> institutePageRequest.getUserId().equalsIgnoreCase(userId)).findAny().orElse(null);
		if (ObjectUtils.isEmpty(institutePageRequestObjectForInstituteIdAndUserId)) {
			log.info("No institute page request found for institute id "+instituteId+ " and user id "+userId+ " creating new request");
			InstitutePageRequest institutePageRequest = new InstitutePageRequest(instituteId, userId, institutePageRequestDto.getFirstName(), institutePageRequestDto.getLastName(), institutePageRequestDto.getTitle(), institutePageRequestDto.getEmail(),
					institutePageRequestDto.getPhoneNumber(), institutePageRequestDto.getManagementName(), institutePageRequestDto.getManagementEmail(),
					institutePageRequestDto.getManagementPhoneNumber(), PageRequestStatus.PENDING, new Date(), new Date(), "API", "API");
			InstitutePageRequest institutePageRequestSaved = institutePageRequestDao.addInstitutePageRequest(institutePageRequest);
			institutePageRequestDto.setInstitutePageRequestId(institutePageRequestSaved.getId());
		} else if (institutePageRequestObjectForInstituteIdAndUserId.getPageRequestStatus().equals(PageRequestStatus.PENDING)) {
			log.warn("institute page request for institute id "+instituteId+ " by user id " +userId+ " is already in pending state" );
			throw new ValidationException("institute page request for institute id "+instituteId+ " by user id " +userId+ " is already in pending state");
		} else if (institutePageRequestObjectForInstituteIdAndUserId.getPageRequestStatus().equals(PageRequestStatus.REJECTED)) {
			log.error("institute page request for institute id "+instituteId+ " by user id " +userId+ " is rejected" );
			throw new ValidationException("institute page request for institute id "+instituteId+ " by user id " +userId+ " is rejected");
		}
		return institutePageRequestDto;
	}
}
