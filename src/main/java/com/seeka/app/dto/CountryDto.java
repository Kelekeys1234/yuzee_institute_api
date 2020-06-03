package com.seeka.app.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CountryDto {

	private String countryId;
	private String name;
	private String description;
	private String countryCode;
	private Date createdOn;
	private List<LevelDto> levels;
}
