package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("global_student_data")
public class GlobalData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1464188653045286660L;
	/**
	 * This DTO contains total Number of students migrated in a particular country.
	 * Please dont change this DTO if you want to reuse this make another DTO.
	 * -mihir
	 */
	@org.springframework.data.annotation.Id
	private String id;
	private String sourceCountry;
	private Double totalNumberOfStudent;
	private String destinationCountry;
	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceCountry() {
		return sourceCountry;
	}

	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}

	public Double getTotalNumberOfStudent() {
		return totalNumberOfStudent;
	}

	public void setTotalNumberOfStudent(Double totalNumberOfStudent) {
		this.totalNumberOfStudent = totalNumberOfStudent;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GlobalDataDto [id=").append(id).append(", sourceCountry=").append(sourceCountry)
				.append(", totalNumberOfStudent=").append(totalNumberOfStudent).append(", destinationCountry=")
				.append(destinationCountry).append("]");
		return builder.toString();
	}

}
