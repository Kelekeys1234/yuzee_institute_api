/*package com.seeka.app.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seeka.app.bean.InstituteFacility;
import com.seeka.app.bean.InstituteService;
import com.seeka.app.bean.Service;
import com.seeka.app.dto.FacilityDto;
import com.seeka.app.dto.InstituteFacilityDto;
import com.seeka.app.dto.InstituteServiceDto;
import com.seeka.app.dto.ServiceDto;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class DTOUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DTOUtils.class);

	public static InstituteServiceDto createInstituteServiceResponseDto (List<InstituteService> listOfInstituteService) {
		InstituteServiceDto instituteServiceDto = new InstituteServiceDto();
		listOfInstituteService.stream().forEach(instituteService -> {
			Service service = instituteService.getService();
			ServiceDto serviceDto = new ServiceDto(instituteService.getId(),service.getId(), service.getName());
			instituteServiceDto.getServices().add(serviceDto);
		});
		return instituteServiceDto;
	}
	
	
	public static InstituteFacilityDto createInstituteFacilityResponseDto(
			List<InstituteFacility> listOfInstituteFacility) {
		InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
		listOfInstituteFacility.stream().forEach(instituteFacility -> {
			Service service = instituteFacility.getService();
			FacilityDto facilityDto = new FacilityDto(instituteFacility.getId(), service.getName(), service.getId());
			instituteFacilityDto.getFacilities().add(facilityDto);
		});
		return instituteFacilityDto;
	}

}
*/