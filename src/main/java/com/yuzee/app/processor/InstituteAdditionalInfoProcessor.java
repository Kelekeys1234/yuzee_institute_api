package com.yuzee.app.processor;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteAdditionalInfo;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.InstituteAdditionalInfoDto;
import com.yuzee.app.exception.NotFoundException;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteAdditionalInfoProcessor {
	
	@Autowired
	private InstituteDao iInstituteDAO;
	
	@Transactional(rollbackOn = Throwable.class)
	public void addUpdateInstituteAdditionalInfo (String userId, String instituteId , InstituteAdditionalInfoDto instituteAdditionalInfoDto) throws Exception {
		log.debug("Inside addInstituteAdditionalInfo() method");
		//TODO validate user ID passed in request have access to modify resource
		log.info("Getting institute having institute id "+instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id "+instituteId);
			throw new NotFoundException("No institute found for institute having id "+instituteId);
		}
		Institute institute = instituteFromFb.get();
		log.info("Getting institute additional info for institute id "+instituteId);
		InstituteAdditionalInfo instituteAdditionalInfoFromDB = institute.getInstituteAdditionalInfo();
		if (ObjectUtils.isEmpty(instituteAdditionalInfoFromDB)) {
			log.info("Institute dont have any additional info adding new one");
			InstituteAdditionalInfo instituteAdditionalInfo = new  InstituteAdditionalInfo(instituteId, instituteAdditionalInfoDto.getNumberOfStudent(), instituteAdditionalInfoDto.getNumberOfEmployee(), 
					instituteAdditionalInfoDto.getNumberOfTeacher(), instituteAdditionalInfoDto.getNumberOfClassRoom(), instituteAdditionalInfoDto.getSizeOfCampus(), instituteAdditionalInfoDto.getNumberOfLectureHall(), 
					instituteAdditionalInfoDto.getNumberOfFaculty(), instituteAdditionalInfoDto.getEmploymentRate(), new Date(), new Date(), "API", "API");
			institute.setInstituteAdditionalInfo(instituteAdditionalInfo);
		} else {
			log.info("Institute have additional info updating exsisting one");
			instituteAdditionalInfoFromDB.setNumberOfClassRoom(instituteAdditionalInfoDto.getNumberOfClassRoom());
			instituteAdditionalInfoFromDB.setNumberOfEmployee(instituteAdditionalInfoDto.getNumberOfEmployee());
			instituteAdditionalInfoFromDB.setNumberOfStudent(instituteAdditionalInfoDto.getNumberOfStudent());
			instituteAdditionalInfoFromDB.setNumberOfTeacher(instituteAdditionalInfoDto.getNumberOfTeacher());
			instituteAdditionalInfoFromDB.setSizeOfCampus(instituteAdditionalInfoDto.getSizeOfCampus());
			instituteAdditionalInfoFromDB.setNumberOfLectureHall(instituteAdditionalInfoDto.getNumberOfLectureHall());
			instituteAdditionalInfoFromDB.setNumberOfFaculty(instituteAdditionalInfoDto.getNumberOfFaculty());
			instituteAdditionalInfoFromDB.setRateOfEmployment(instituteAdditionalInfoDto.getEmploymentRate());
			instituteAdditionalInfoFromDB.setUpdatedBy("API");
			instituteAdditionalInfoFromDB.setUpdatedOn(new Date());
			institute.setInstituteAdditionalInfo(instituteAdditionalInfoFromDB);
		}
		log.info("Persisting institute into Db with updated institute addition info");
		iInstituteDAO.addUpdateInstitute(institute);
	}
	
	public InstituteAdditionalInfoDto getInstituteAdditionalInfo (String userId , String instituteId, String caller) throws Exception {
		InstituteAdditionalInfoDto instituteAdditionalInfoDto = new InstituteAdditionalInfoDto();
		log.debug("Inside getInstituteBasicInfo() method");
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
		log.info("Getting Institute Additional Info for institute id "+instituteId);
	
		InstituteAdditionalInfo instituteAdditionalInfoFromDB = institute.getInstituteAdditionalInfo();
		if (!ObjectUtils.isEmpty(instituteAdditionalInfoFromDB)) {
			log.info("Institute Additional Info not null for institute id "+instituteId+ " creating response DTO");
			instituteAdditionalInfoDto.setNumberOfClassRoom(instituteAdditionalInfoFromDB.getNumberOfClassRoom());
			instituteAdditionalInfoDto.setNumberOfEmployee(instituteAdditionalInfoFromDB.getNumberOfEmployee());
			instituteAdditionalInfoDto.setNumberOfStudent(instituteAdditionalInfoFromDB.getNumberOfStudent());
			instituteAdditionalInfoDto.setNumberOfTeacher(instituteAdditionalInfoFromDB.getNumberOfTeacher());
			instituteAdditionalInfoDto.setSizeOfCampus(instituteAdditionalInfoFromDB.getSizeOfCampus());
			instituteAdditionalInfoDto.setNumberOfLectureHall(instituteAdditionalInfoFromDB.getNumberOfLectureHall());
			instituteAdditionalInfoDto.setNumberOfFaculty(instituteAdditionalInfoFromDB.getNumberOfFaculty());
			instituteAdditionalInfoDto.setEmploymentRate(instituteAdditionalInfoFromDB.getRateOfEmployment());
		}
		
		return instituteAdditionalInfoDto;
	}	
	
}
