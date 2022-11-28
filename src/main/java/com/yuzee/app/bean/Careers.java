package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "career_list")
@ToString

@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Careers implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@EqualsAndHashCode.Include
	private String career;
	@EqualsAndHashCode.Include
	private Date createdOn;
	@EqualsAndHashCode.Include
	private Date updatedOn;
	@EqualsAndHashCode.Include
	private String createdBy;
	@EqualsAndHashCode.Include
	private String updatedBy;

	// Delete RelatedCareer model
	private List<String> relatedCareers = new ArrayList<>();

	@DBRef
	private List<CareerJob> careerJobs = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

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

	public List<String> getRelatedCareers() {
		return relatedCareers;
	}

	public void setRelatedCareers(List<String> relatedCareers) {
		this.relatedCareers = relatedCareers;
	}

	public List<CareerJob> getCareerJobs() {
		return careerJobs;
	}

	public void setCareerJobs(List<CareerJob> careerJobs) {
		this.careerJobs = careerJobs;
	}

	public Careers() {
		super();
	}

}
