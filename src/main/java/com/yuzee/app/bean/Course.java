package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.yuzee.app.enumeration.CourseTypeEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "course", uniqueConstraints = @UniqueConstraint(name = "UK_F_L_I_N_C", columnNames = { "faculty_id",
		"level_id", "institute_id", "name", "code" }), indexes = {
				@Index(name = "IDX_FACULTY_ID", columnList = "faculty_id", unique = false),
				@Index(name = "IDX_INSTITUTE_ID", columnList = "institute_id", unique = false),
				@Index(name = "IDX_LEVEL_ID", columnList = "level_id", unique = false),
				@Index(name = "IDX_COURSE_NAME", columnList = "name", unique = false),
				@Index(name = "IDX_COURSE_CURRICULUM", columnList = "curriculum_id", unique = false) })
public class Course implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_id", nullable = false)
	private Faculty faculty;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institute_id", nullable = false)
	private Institute institute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id", nullable = false)
	private Level level;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curriculum_id")
	private CourseCurriculum courseCurriculum;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "world_ranking")
	private Integer worldRanking;

	@Column(name = "stars")
	private Integer stars;

	@Column(name = "recognition")
	private String recognition;

	@Column(name = "recognition_type")
	private String recognitionType;

	@Column(name = "website", columnDefinition = "TEXT")
	private String website;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "abbreviation", columnDefinition = "TEXT")
	private String abbreviation;

	@Column(name = "remarks", columnDefinition = "TEXT")
	private String remarks;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "availabilty", nullable = false)
	private String availabilty;

	@Column(name = "currency", nullable = false)
	private String currency;

	@Column(name = "currency_time", nullable = false)
	private String currencyTime;

	@Column(name = "global_gpa")
	private Double globalGpa;

	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Column(name = "updated_by", length = 50)
	private String updatedBy;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rec_date", length = 19)
	private Date recDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@Column(name = "examination_board")
	private String examinationBoard;

	@Column(name = "domestic_application_fee")
	private Double domesticApplicationFee;

	@Column(name = "international_application_fee")
	private Double internationalApplicationFee;

	@Column(name = "domestic_enrollment_fee")
	private Double domesticEnrollmentFee;

	@Column(name = "international_enrollment_fee")
	private Double internationalEnrollmentFee;

	@Column(name = "usd_domestic_application_fee")
	private Double usdDomesticApplicationFee;

	@Column(name = "usd_international_application_fee")
	private Double usdInternationalApplicationFee;

	@Column(name = "usd_domestic_enrollment_fee")
	private Double usdDomesticEnrollmentFee;

	@Column(name = "usd_international_enrollment_fee")
	private Double usdInternationalEnrollmentFee;

	@Column(name = "entrance_exam")
	private String entranceExam;

	@Column(name = "code")
	private String code = "NORMAL COURSE";

	@Enumerated(EnumType.STRING)
	@Column(name = "course_type")
	private CourseTypeEnum courseType = CourseTypeEnum.ON_CAMPUS;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course", orphanRemoval = true)
	private OffCampusCourse offCampusCourse;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course", orphanRemoval = true)
	private CoursePayment coursePayment;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseDeliveryModes> courseDeliveryModes = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseEnglishEligibility> courseEnglishEligibilities = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseIntake> courseIntakes = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseLanguage> courseLanguages = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseMinRequirement> courseMinRequirements = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CoursePrerequisite> coursePrerequisites = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseCareerOutcome> courseCareerOutcomes = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseSubject> courseSubjects = new ArrayList<>();

	@OneToMany(mappedBy = "course" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseFunding> courseFundings = new ArrayList<>();

	@OneToMany(mappedBy = "course" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseContactPerson> courseContactPersons = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course", orphanRemoval = true)
	private CourseScholarship courseScholarship;
	
	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}