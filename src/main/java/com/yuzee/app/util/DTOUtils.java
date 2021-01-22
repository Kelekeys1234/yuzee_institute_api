package com.yuzee.app.util;

import java.util.List;

import com.yuzee.app.bean.InstituteFacility;
import com.yuzee.app.dto.FacilityDto;
import com.yuzee.app.dto.InstituteFacilityDto;

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
}