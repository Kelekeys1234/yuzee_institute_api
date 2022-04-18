package com.yuzee.app.processor;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

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
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstituteAdditionalInfoProcessor {

	@Autowired
	private InstituteDao instituteDAO;

	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MessageTranslator messageTranslator;
	
	@Transactional(rollbackOn = Throwable.class)
	public void addUpdateInstituteAdditionalInfo(String userId, String instituteId,
			InstituteAdditionalInfoDto instituteAdditionalInfoDto) throws NotFoundException {
		log.debug("Inside addInstituteAdditionalInfo() method");
		// TODO validate user ID passed in request have access to modify resource
		log.info("Getting institute having institute id: {}", instituteId);
		Optional<Institute> instituteFromFb = instituteDAO.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (!instituteFromFb.isPresent()) {
			log.error(messageTranslator.toLocale("institute_info.id.notfound",instituteId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute_info.id.notfound",instituteId));
		}
		Institute institute = instituteFromFb.get();
		log.info("Getting institute additional info for institute id: {}", instituteId);
		if (!StringUtils.equals(institute.getAboutInfo(), instituteAdditionalInfoDto.getAboutInfo())) {
			institute.setAboutInfo(instituteAdditionalInfoDto.getAboutInfo());
			institute.setUpdatedBy(userId);
			institute.setUpdatedOn(new Date());
		}
		InstituteAdditionalInfo instituteAdditionalInfoFromDB = institute.getInstituteAdditionalInfo();
		InstituteAdditionalInfo instituteAdditionalInfo = modelMapper.map(instituteAdditionalInfoDto,
				InstituteAdditionalInfo.class);
		institute.setInstituteAdditionalInfo(instituteAdditionalInfo);
		log.info("Persisting institute into Db with updated institute addition info");
		instituteDAO.addUpdateInstitute(institute);
	}

	public InstituteAdditionalInfoDto getInstituteAdditionalInfo(String instituteId) throws NotFoundException {
		log.debug("Inside getInstituteAdditionalInfo() method");

		log.info("Getting institute having institute id: {}", instituteId);
		Optional<Institute> instituteFromFb = instituteDAO.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (!instituteFromFb.isPresent()) {
			log.error(messageTranslator.toLocale("institute_info.id.notfound",instituteId,Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute_info.id.notfound",instituteId));
		} else {
			Institute institute = instituteFromFb.get();
			log.info("Getting Institute Additional Info for institute id: {}", instituteId);

			InstituteAdditionalInfo instituteAdditionalInfoFromDB = institute.getInstituteAdditionalInfo();
			if (!ObjectUtils.isEmpty(instituteAdditionalInfoFromDB)) {
				log.info("Institute Additional Info not null for institute id: {}",
						instituteId + " creating response DTO");
				InstituteAdditionalInfoDto instituteAdditionalInfoDto = modelMapper.map(instituteAdditionalInfoFromDB,
						InstituteAdditionalInfoDto.class);
				try {
					instituteAdditionalInfoDto.setMedia(storageHandler.getStorages(instituteId,
							EntityTypeEnum.INSTITUTE, EntitySubTypeEnum.ABOUT_US));
				} catch (NotFoundException | InvokeException e) {
					log.error(e.getMessage());
				}
				instituteAdditionalInfoDto.setVerified(institute.isVerified());
				return instituteAdditionalInfoDto;
			} else {
				return new InstituteAdditionalInfoDto();
			}
		}
	}

}
