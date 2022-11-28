package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.ObjectUtils;

import com.yuzee.common.lib.enumeration.CourseTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
//@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "course")
public class Course implements Serializable {
	private static final long serialVersionUID = 8492390790670110780L;

	@Id
	private String id;

	private String readableId;

	@DBRef
	private Faculty faculty;

	@DBRef
	private Institute institute;

	private String instituteId;

	@DBRef(lazy = true)
	private Level level;

	private String courseCurriculum;

	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private Integer worldRanking;

	@EqualsAndHashCode.Include
	private Integer stars;

	@EqualsAndHashCode.Include
	private String recognition;

	@EqualsAndHashCode.Include
	private String recognitionType;

	@EqualsAndHashCode.Include
	private String website;

	@EqualsAndHashCode.Include
	private String phoneNumber;

	@EqualsAndHashCode.Include
	private String email;

	@EqualsAndHashCode.Include
	private String abbreviation;

	@EqualsAndHashCode.Include
	private String remarks;

	@EqualsAndHashCode.Include
	private String description;

	@EqualsAndHashCode.Include
	private Boolean isActive;

	@EqualsAndHashCode.Include
	private String availabilty;

	@EqualsAndHashCode.Include
	private String currency;

	@EqualsAndHashCode.Include
	private String currencyTime;

	@EqualsAndHashCode.Include
	private Double globalGpa;

	private String createdBy;

	private String updatedBy;

	private Boolean isDeleted;

	private Date recDate;

	private Date createdOn;

	private Date updatedOn;

	private Date deletedOn;

	@EqualsAndHashCode.Include
	private String content;

	@EqualsAndHashCode.Include
	private String examinationBoard;

	@EqualsAndHashCode.Include
	private Double domesticApplicationFee;

	@EqualsAndHashCode.Include
	private Double internationalApplicationFee;

	@EqualsAndHashCode.Include
	private Double domesticEnrollmentFee;

	@EqualsAndHashCode.Include
	private Double internationalEnrollmentFee;

	@EqualsAndHashCode.Include
	private Double usdDomesticApplicationFee;

	@EqualsAndHashCode.Include
	private Double usdInternationalApplicationFee;

	@EqualsAndHashCode.Include
	private Double usdDomesticEnrollmentFee;

	@EqualsAndHashCode.Include
	private Double usdInternationalEnrollmentFee;

	@EqualsAndHashCode.Include
	private String entranceExam;

	@EqualsAndHashCode.Include
	private String audience;

	private String code = "NORMAL COURSE";

	@EqualsAndHashCode.Include
	private String internationalStudentProcedureId;

	@EqualsAndHashCode.Include
	private String domesticStudentProcedureId;

	@EqualsAndHashCode.Include
	private CourseTypeEnum courseType = CourseTypeEnum.ON_CAMPUS;

	private OffCampusCourse offCampusCourse;

	private CoursePayment coursePayment;

	private List<Location> location = new ArrayList<>();

	private List<CourseDeliveryModes> courseDeliveryModes = new ArrayList<>();

	private List<CourseEnglishEligibility> courseEnglishEligibilities = new ArrayList<>();

	private CourseIntake courseIntake;

	// Delete course language model
	private List<String> courseLanguages = new ArrayList<>();

	private List<CourseMinRequirement> courseMinRequirements = new ArrayList<>();

	// Delete CoursePrerequisite model
	private List<String> coursePrerequisites = new ArrayList<>();

	// Delete CourseCareerOutcome model
	@DBRef
	private List<Careers> courseCareerOutcomes = new ArrayList<>();

	private List<CourseSemester> courseSemesters = new ArrayList<>();

	// Delete CourseFunding model
	private List<String> courseFundings = new ArrayList<>();

	// Delete CourseContactPerson model
	private List<String> courseContactPersons = new ArrayList<>();

	// Delete CourseScholarship model
	private String courseScholarshipId;

	private List<CourseProviderCode> courseProviderCodes = new ArrayList<>();

	private CourseVaccineRequirement courseVaccineRequirement;

	private CourseWorkExperienceRequirement courseWorkExperienceRequirement;

	private CourseWorkPlacementRequirement courseWorkPlacementRequirement;

	// Delete CourseResearchProposalRequirement model
	private String courseResearchProposalRequirement;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (ObjectUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}