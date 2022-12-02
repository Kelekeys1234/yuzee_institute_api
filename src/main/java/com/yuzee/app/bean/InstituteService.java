package com.yuzee.app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "institute_service")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteService{

	@Id
	private String instituteServiceId;

	@DBRef(lazy = true)
	@Indexed(unique = true)
	private Service service;

	private String description;

	private Institute institute;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	public InstituteService(String instituteServiceId, Service service, String description, Institute institute) {
		this.instituteServiceId = instituteServiceId;
		this.service = service;
		this.description = description;
		this.institute = institute;
	}
	public InstituteService(Institute institute, Service service, String description, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy) {
		super();
		this.service = service;
		this.description = description;
		this.institute = institute;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
}
