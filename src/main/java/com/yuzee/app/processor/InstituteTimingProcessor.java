package com.yuzee.app.processor;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteTiming;
import com.yuzee.app.dao.InstituteTimingDao;
import com.yuzee.app.dto.InstituteTimingDto;
import com.yuzee.app.dto.InstituteTimingResponseDto;
import com.yuzee.app.enumeration.DaysEnum;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class InstituteTimingProcessor {

	@Autowired
	private InstituteTimingDao instituteTimingDao;
	
	public void saveInstituteTiming(List<InstituteTimingDto> instituteTimingDtos, Institute institute) {
		log.debug("Inside saveInstituteTiming() method");
		instituteTimingDtos.stream().forEach(instituteTimingDto -> {
			log.info("Fetching Institute timing from DB fro instituteId "+institute.getId());
			InstituteTiming instituteTiming = instituteTimingDao.getInstituteTimeByInstituteId(institute.getId());
			if(ObjectUtils.isEmpty(instituteTiming)) {
				log.info("No institute timing found in DN hence creating new entires");
				instituteTiming = new InstituteTiming();
				instituteTiming.setCreatedBy("API");
				instituteTiming.setCreatedOn(new Date());
			}
			log.info("Mathcing days with ENUM and adding opening and closing time in Object");
			if(DaysEnum.MONDAY.toString().equalsIgnoreCase(instituteTimingDto.getDay())) {
				log.info("Adding Monday's opening and closing time");
				instituteTiming.setMonday(instituteTimingDto.getOpeningFrom() + "-" + instituteTimingDto.getOpeningTo());
			}
			if(DaysEnum.TUESDAY.toString().equalsIgnoreCase(instituteTimingDto.getDay())) {
				log.info("Adding Tuesday's opening and closing time");
				instituteTiming.setTuesday(instituteTimingDto.getOpeningFrom() + "-" + instituteTimingDto.getOpeningTo());
			} 
			if(DaysEnum.WEDNESDAY.toString().equalsIgnoreCase(instituteTimingDto.getDay())) {
				log.info("Adding Wednesday's opening and closing time");
				instituteTiming.setWednesday(instituteTimingDto.getOpeningFrom() + "-" + instituteTimingDto.getOpeningTo());
			} 
			if(DaysEnum.THURSDAY.toString().equalsIgnoreCase(instituteTimingDto.getDay())) {
				log.info("Adding Thursday's opening and closing time");
				instituteTiming.setThursday(instituteTimingDto.getOpeningFrom() + "-" + instituteTimingDto.getOpeningTo());
			} 
			if(DaysEnum.FRIDAY.toString().equalsIgnoreCase(instituteTimingDto.getDay())) {
				log.info("Adding Friday's opening and closing time");
				instituteTiming.setFriday(instituteTimingDto.getOpeningFrom() + "-" + instituteTimingDto.getOpeningTo());
			}
			if(DaysEnum.SATURDAY.toString().equalsIgnoreCase(instituteTimingDto.getDay())) {
				log.info("Adding Saturday's opening and closing time");
				instituteTiming.setSaturday(instituteTimingDto.getOpeningFrom() + "-" + instituteTimingDto.getOpeningTo());
			} 
			if(DaysEnum.SUNDAY.toString().equalsIgnoreCase(instituteTimingDto.getDay())) {
				log.info("Adding Sunday's opening and closing time");
				instituteTiming.setSunday(instituteTimingDto.getOpeningFrom() + "-" + instituteTimingDto.getOpeningTo());
			}
			instituteTiming.setInstitute(institute);
			instituteTiming.setUpdatedBy("API");
			instituteTiming.setUpdatedOn(new Date());
			log.info("Calling DAO layer to save institute timing in DB for instituteId ="+institute.getId());
			instituteTimingDao.saveInstituteTiming(instituteTiming);
		});
	}
	
	public InstituteTimingResponseDto getInstituteTimeByInstituteId(String instituteId) {
		log.debug("Inside getInstituteTimeByInstituteId() method");
		log.info("fetching isntitute timing from DB for instituteId "+instituteId);
		InstituteTiming instituteTiming = instituteTimingDao.getInstituteTimeByInstituteId(instituteId);
		InstituteTimingResponseDto instituteTimingResponseDto = new InstituteTimingResponseDto();
		if(!ObjectUtils.isEmpty(instituteTiming)) {
			log.info("Institute timing is not null, hence coping bean to DTO class");
			BeanUtils.copyProperties(instituteTiming, instituteTimingResponseDto);
		}
		return instituteTimingResponseDto;
	}
}
