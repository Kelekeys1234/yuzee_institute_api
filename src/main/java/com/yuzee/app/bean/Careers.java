package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Document("career_list")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*@Table(name = "career_list", uniqueConstraints = @UniqueConstraint(columnNames = { "career" }, name = "UK_CAREER_NAME")) */
public class Careers implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private UUID id;
	
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
