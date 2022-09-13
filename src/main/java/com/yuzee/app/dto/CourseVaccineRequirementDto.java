package com.yuzee.app.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.common.VaccinationDto;

import lombok.Data;

@Data
public class CourseVaccineRequirementDto {


	@NotEmpty
	@JsonProperty("description")
	private String description;

	@NotNull
	@JsonProperty("vaccination")
	private Set<VaccinationDto> vaccination;

}
