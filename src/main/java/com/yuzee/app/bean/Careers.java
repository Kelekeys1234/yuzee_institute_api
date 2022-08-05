package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Document("career")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*
 * @Table(name = "career_list", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "career" }, name = "UK_CAREER_NAME"))
 */
public class Careers implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@EqualsAndHashCode.Include
	private String career;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	// Delete RelatedCareer model
	private List<String> relatedCareers = new ArrayList<>();

	@DBRef
	private List<CareerJob> careerJobs = new ArrayList<>();
}
