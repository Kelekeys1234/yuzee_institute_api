package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "education_agent", uniqueConstraints = @UniqueConstraint(columnNames = { "first_name" , "last_name", "email" }, name = "UK_FN_LN_EM"))
public class EducationAgent implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "description")
	private String description;

	@Column(name = "city_name", nullable = false)
	private String cityName;

	@Column(name = "country_name", nullable = false)
	private String countryName;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Column(name = "updated_by", length = 50)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

	@Column(name = "deleted_by", length = 50)
	private String deletedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;
	
	@JsonIgnore
	@OneToMany(mappedBy = "educationAgent" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EducationAgentAccomplishment> educationAgentAccomplishments = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "educationAgent" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EducationAgentPartnerships> educationAgentPartnerships = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "educationAgent" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EducationAgentSkill> educationAgentSkills = new ArrayList<>();
}
