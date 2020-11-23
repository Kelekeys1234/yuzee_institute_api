package com.yuzee.app.processor;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteAdditionalInfo;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.InstituteAdditionalInfoDto;
import com.yuzee.app.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstituteAdditionalInfoProcessor {

	@Autowired
	private InstituteDao iInstituteDAO;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional(rollbackOn = Throwable.class)
	public void addUpdateInstituteAdditionalInfo(String userId, String instituteId,
			InstituteAdditionalInfoDto instituteAdditionalInfoDto) throws NotFoundException {
		log.debug("Inside addInstituteAdditionalInfo() method");
		// TODO validate user ID passed in request have access to modify resource
		log.info("Getting institute having institute id: {}", instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id: {}", instituteId);
			throw new NotFoundException("No institute found for institute having id: " + instituteId);
		}
		Institute institute = instituteFromFb.get();
		log.info("Getting institute additional info for institute id: {}", instituteId);
		if (!StringUtils.equals(institute.getAboutInfo(), instituteAdditionalInfoDto.getAboutInfo())) {
			institute.setAboutInfo(instituteAdditionalInfoDto.getAboutInfo());
			institute.setUpdatedBy(userId);
			institute.setUpdatedOn(new Date());
		}
		InstituteAdditionalInfo instituteAdditionalInfoFromDB = institute.getInstituteAdditionalInfo();
		if (ObjectUtils.isEmpty(instituteAdditionalInfoFromDB)) {
			log.info("Institute dont have any additional info adding new one");
			InstituteAdditionalInfo instituteAdditionalInfo = new InstituteAdditionalInfo();
			instituteAdditionalInfo = modelMapper.map(instituteAdditionalInfoDto, InstituteAdditionalInfo.class);
			instituteAdditionalInfo.setCreatedBy(userId);
			instituteAdditionalInfo.setCreatedOn(new Date());
			instituteAdditionalInfo.setUpdatedBy(userId);
			instituteAdditionalInfo.setUpdatedOn(new Date());
			instituteAdditionalInfo.setInstitute(institute);
			institute.setInstituteAdditionalInfo(instituteAdditionalInfo);
		} else {
			log.info("Institute have additional info updating exsisting one");
			instituteAdditionalInfoFromDB = modelMapper.map(instituteAdditionalInfoDto, InstituteAdditionalInfo.class);
			instituteAdditionalInfoFromDB.setUpdatedBy("API");
			instituteAdditionalInfoFromDB.setUpdatedOn(new Date());
		}
		log.info("Persisting institute into Db with updated institute addition info");
		iInstituteDAO.addUpdateInstitute(institute);
	}

	public InstituteAdditionalInfoDto getInstituteAdditionalInfo(String instituteId) throws NotFoundException {
		log.debug("Inside getInstituteAdditionalInfo() method");

		log.info("Getting institute having institute id: {}", instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(instituteId);
		if (!instituteFromFb.isPresent()) {
			log.error("No institute found for institute having id: {}", instituteId);
			throw new NotFoundException("No institute found for institute having id " + instituteId);
		} else {
			Institute institute = instituteFromFb.get();
			log.info("Getting Institute Additional Info for institute id: {}", instituteId);

			InstituteAdditionalInfo instituteAdditionalInfoFromDB = institute.getInstituteAdditionalInfo();
			if (!ObjectUtils.isEmpty(instituteAdditionalInfoFromDB)) {
				log.info("Institute Additional Info not null for institute id: {}",
						instituteId + " creating response DTO");
				return modelMapper.map(instituteAdditionalInfoFromDB, InstituteAdditionalInfoDto.class);
			} else {
				return new InstituteAdditionalInfoDto();
			}
		}
	}

}
