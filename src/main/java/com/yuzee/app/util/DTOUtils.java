package com.yuzee.app.util;

import java.util.List;

import com.yuzee.app.bean.InstituteFacility;
import com.yuzee.app.bean.InstituteService;
import com.yuzee.app.dto.FacilityDto;
import com.yuzee.app.dto.InstituteFacilityDto;
import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.dto.ServiceDto;

public class DTOUtils {
	
	private DTOUtils () {}

	public static InstituteServiceDto createInstituteServiceResponseDto (List<InstituteService> listOfInstituteService) {
		InstituteServiceDto instituteServiceDto = new InstituteServiceDto();
		listOfInstituteService.stream().forEach(instituteService -> {
			ServiceDto serviceDto = new ServiceDto(instituteService.getService().getId(), instituteService.getService().getName(),instituteService.getService().getDescription(), "");
			instituteServiceDto.getServices().add(serviceDto);
		});
		return instituteServiceDto;
	}
	
	public static InstituteFacilityDto createInstituteFacilityResponseDto (List<InstituteFacility> listOfInstituteFacility) {
		InstituteFacilityDto instituteFacilityDto = new InstituteFacilityDto();
		listOfInstituteFacility.stream().forEach(instituteFacility -> {
			FacilityDto facilityDto = new FacilityDto(instituteFacility.getId(), instituteFacility.getService().getName(),instituteFacility.getService().getId());
			instituteFacilityDto.getFacilities().add(facilityDto);
		});
		return instituteFacilityDto;
	}
	
}