package com.yuzee.app.util;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yuzee.app.bean.InstituteFacility;
import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.dto.FacilityDto;
import com.yuzee.app.dto.InstituteFacilityDto;
import com.yuzee.app.dto.ScholarshipDto;

public class DTOUtils {

	private DTOUtils() {
	}

	public static InstituteFacilityDto createInstituteFacilityResponseDto(
			List<InstituteFacility> listOfInstituteFacility) {
		InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
		listOfInstituteFacility.stream().forEach(instituteFacility -> {
			FacilityDto facilityDto = new FacilityDto(instituteFacility.getId(),
					instituteFacility.getService().getName(), instituteFacility.getService().getId());
			instituteFacilityDto.getFacilities().add(facilityDto);
		});
		return instituteFacilityDto;
	}
	
	public static ScholarshipDto createScholarshipDtoFromModel(Scholarship scholarship) {
		ScholarshipDto scholarshipDto = new ScholarshipDto();
		BeanUtils.copyProperties(scholarship, scholarshipDto);
		scholarshipDto.setCountryName(scholarship.getCountryName() != null ? scholarship.getCountryName() : null);
		scholarshipDto.setLevelId(scholarship.getLevel() != null ? scholarship.getLevel().getId() : null);
		scholarshipDto.setLevelName(scholarship.getLevel() != null ? scholarship.getLevel().getName() : null);
		scholarshipDto.setLevelCode(scholarship.getLevel() != null ? scholarship.getLevel().getCode() : null);
		scholarshipDto.setInstituteName(scholarship.getInstitute() != null ? scholarship.getInstitute().getName() : null);
		return scholarshipDto;
	}

}