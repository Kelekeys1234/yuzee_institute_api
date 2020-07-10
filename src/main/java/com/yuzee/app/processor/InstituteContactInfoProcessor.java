package com.yuzee.app.processor;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.InstituteContactInfoDto;
import com.yuzee.app.exception.NotFoundException;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteContactInfoProcessor {
	
	@Autowired
	private InstituteDao iInstituteDAO;

	@Transactional(rollbackOn = Throwable.class)
	public void addUpdateInstituteContactInfo(String userId, String instituteId, InstituteContactInfoDto instituteContactInfoDto) throws Exception {
		log.debug("Inside addUpdateInstituteContactInfo() method");
		//TODO validate user ID passed in request have access to modify resource
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		Institute institute = instituteFromFb.get();
		log.info("adding updating contact info for institute id "+instituteId+ " by user id "+userId);
		institute.setAddress(instituteContactInfoDto.getAddress());
//		institute.setOpeningFrom(instituteContactInfoDto.getOfficeHoursFrom());
//		institute.setOpeningTo(instituteContactInfoDto.getOfficeHoursTo());
		institute.setWebsite(instituteContactInfoDto.getWebsite());
		institute.setPhoneNumber(instituteContactInfoDto.getContactNumber());
		institute.setEmail(instituteContactInfoDto.getEmail());
		institute.setAvgCostOfLiving(instituteContactInfoDto.getAveragelivingCost());
		institute.setLatitude(instituteContactInfoDto.getLatitute());
		institute.setLongitude(instituteContactInfoDto.getLongitude());
		log.info("persisting institute having id "+instituteId+ " with updated basic info");
		iInstituteDAO.save(institute);
	}
	
	public InstituteContactInfoDto getInstituteContactInfo (String userId , String instituteId, String caller) throws Exception {
		log.debug("Inside getInstituteContactInfo() method");
		if (caller.equalsIgnoreCase("PRIVATE")) {
			//TODO validate user ID passed in request have access to modify resource
		}
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		Institute institute = instituteFromFb.get();
		log.info("Setting institute contact info values in response DTO");
		InstituteContactInfoDto instituteContactInfoDto = new InstituteContactInfoDto(institute.getLatitude(), institute.getLongitude(), institute.getAddress(), null, 
				null, institute.getWebsite(), institute.getPhoneNumber(), institute.getEmail(), institute.getAvgCostOfLiving());
		return instituteContactInfoDto;
	}
}
