package com.yuzee.app.processor;

import java.util.Locale;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.InstituteContactInfoDto;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class InstituteContactInfoProcessor {

	@Autowired
	private InstituteDao iInstituteDAO;

	@Autowired
	private MessageTranslator messageTranslator;

	public void addUpdateInstituteContactInfo(String userId, String instituteId,
			InstituteContactInfoDto instituteContactInfoDto) throws Exception {
		log.debug("Inside addUpdateInstituteContactInfo() method");
		// TODO validate user ID passed in request have access to modify resource
		log.info("Getting institute having institute id " + instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (instituteFromFb.isEmpty()) {
			log.error(messageTranslator.toLocale("institute_info.id.notFound", instituteId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute_info.id.notFound", instituteId));
		}
		Institute institute = instituteFromFb.get();
		log.info("adding updating contact info for institute id " + instituteId + " by user id " + userId);
		institute.setAddress(instituteContactInfoDto.getAddress());
		institute.setOfficeHoursFrom(instituteContactInfoDto.getOfficeHoursFrom());
		institute.setOfficeHoursTo(instituteContactInfoDto.getOfficeHoursTo());
		institute.setWebsite(instituteContactInfoDto.getWebsite());
		institute.setPhoneNumber(instituteContactInfoDto.getContactNumber());
		institute.setEmail(instituteContactInfoDto.getEmail());
		institute.setAvgCostOfLiving(instituteContactInfoDto.getAverageLivingCost());
		// institute.setLatitude(instituteContactInfoDto.getLatitude());
		// institute.setLongitude(instituteContactInfoDto.getLongitude());
//		for (Location location : institute.getLocation()) {
//			Location locations = new Location(UUID.randomUUID().toString(), location.getLatitute(),
//					location.getLongtitute());
//			List<Location> list = new ArrayList<>();
//			list.add(locations);

		institute.setLocation(institute.getLocation());
		log.info("persisting institute having id " + instituteId + " with updated basic info");
		iInstituteDAO.addUpdateInstitute(institute);

	}

	public InstituteContactInfoDto getInstituteContactInfo(String userId, String instituteId, String caller)
			throws Exception {
		log.debug("Inside getInstituteContactInfo() method");
		if (caller.equalsIgnoreCase("PRIVATE")) {
			// TODO validate user ID passed in request have access to modify resource
		}
		InstituteContactInfoDto dto = new InstituteContactInfoDto();
		log.info("Getting institute having institute id " + instituteId);
		Optional<Institute> instituteFromFb = iInstituteDAO.getInstituteByInstituteId(UUID.fromString(instituteId));
		if (instituteFromFb.isEmpty()) {
			log.error(messageTranslator.toLocale("institute_info.id.notFound", instituteId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute_info.id.notFound", instituteId));
		}
		Institute institute = instituteFromFb.get();
		log.info("Setting institute contact info values in response DTO");
		// for (Location location : institute.getLocation()) {
		dto = new InstituteContactInfoDto(institute.getLocation().getLocation().getY(),
				institute.getLocation().getLocation().getY(), institute.getAddress(), institute.getOfficeHoursFrom(),
				institute.getOfficeHoursTo(), institute.getWebsite(), institute.getPhoneNumber(), institute.getEmail(),
				institute.getAvgCostOfLiving());
		// }
		return dto;

	}
}
