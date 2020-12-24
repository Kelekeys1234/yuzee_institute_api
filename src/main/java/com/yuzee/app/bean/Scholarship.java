package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "scholarship", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "country_name", "level_id",
		"institute_id" }, name = "UK_CN_LE_IN_CN"), indexes = {
				@Index(name = "IDX_LEVEL_ID", columnList = "level_id", unique = false),
				@Index(name = "IDX_INSTITUTE_ID", columnList = "institute_id", unique = false),
				@Index(name = "IDX_COUNTRY_NAME", columnList = "country_name", unique = false) })
public class Scholarship implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "description")
	private String description;

	@Column(name = "scholarship_award")
	private String scholarshipAward;

	@Column(name = "country_name", nullable = false)
	private String countryName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id", nullable = false)
	private Level level;

	@Column(name = "number_of_avaliability")
	private Integer numberOfAvaliability;

	@Column(name = "currency", nullable = false)
	private String currency;

	@Column(name = "scholarship_amount", nullable = false)
	private Double scholarshipAmount;

	@Column(name = "validity", nullable = false)
	private String validity;

	@Column(name = "how_to_apply", nullable = false)
	private String howToApply;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Column(name = "updated_by", length = 50)
	private String updatedBy;

	@Column(name = "website", nullable = false)
	private String website;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institute_id", nullable = false)
	private Institute institute;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "benefits", columnDefinition = "text")
	private String benefits;

	@Column(name = "requirements", columnDefinition = "text")
	private String requirements;

	@Column(name = "conditions", columnDefinition = "text")
	private String conditions;

	@Column(name = "successful_canidates", columnDefinition = "text")
	private String successfulCanidates;

	@OneToMany(mappedBy = "scholarship", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScholarshipIntake> scholarshipIntakes = new ArrayList<>();

	@OneToMany(mappedBy = "scholarship", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScholarshipLanguage> scholarshipLanguages = new ArrayList<>();

	@OneToMany(mappedBy = "scholarship", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScholarshipEligibleNationality> scholarshipEligibleNationalities = new ArrayList<>();

	public void setAuditFields(String userId, Scholarship existingScholarship) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (existingScholarship != null) {
			this.setCreatedBy(existingScholarship.getCreatedBy());
			this.setCreatedOn(existingScholarship.getCreatedOn());
		}else {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}
