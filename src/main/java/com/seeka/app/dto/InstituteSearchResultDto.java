package com.seeka.app.dto;import java.math.BigInteger;



public class InstituteSearchResultDto {
	
	private BigInteger instituteId;
	private String instituteName;
	private String location;
	
	
	public BigInteger getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(BigInteger instituteId) {
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
