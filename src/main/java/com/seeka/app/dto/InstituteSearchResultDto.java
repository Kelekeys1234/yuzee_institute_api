package com.seeka.app.dto;

import java.util.UUID;

public class InstituteSearchResultDto {
	
	private UUID instituteId;
	private String instituteName;
	private String location;
	
	
	public UUID getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(UUID instituteId) {
		this.instituteId = instituteId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	 
}
