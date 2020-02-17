package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * CourseDetails generated by hbm2java
 */

public class CourseDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5618988312191735907L;
	private String id;
	private Course course;
	private String wrRange;
	private String availbilty;
	private String partFull;
	private String studyMode;
	private String description;

	private CoursePricing coursePricingObj;

	private Faculty facultyObj;

	private Level LevelObj;

	private FacultyLevel facultyLevelObj;

	private InstituteLevel instituteLevelObj;

	private CourseGradeEligibility gradeObj;
	
	private String intake;
	private String fileUrl;
	private String grades;
	private String contact;
	private String openingHour;
	private String campusLocation;
	private String webiste;
	private String jobFullTime;
	private String jobPartTime;
	private String courseLink;
	private String domesticFee;
	private String internationalFee;

	public CourseDetails() {
	}

	public CourseDetails(Course course) {
		this.course = course;
	}

	public CourseDetails(Course course, String wrRange, String availbilty, String partFull, String studyMode,
			String description) {
		this.course = course;
		this.wrRange = wrRange;
		this.availbilty = availbilty;
		this.partFull = partFull;
		this.studyMode = studyMode;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", nullable = false)
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Column(name = "wr_range", length = 50)
	public String getWrRange() {
		return this.wrRange;
	}

	public void setWrRange(String wrRange) {
		this.wrRange = wrRange;
	}

	@Column(name = "availbilty", length = 250)
	public String getAvailbilty() {
		return this.availbilty;
	}

	public void setAvailbilty(String availbilty) {
		this.availbilty = availbilty;
	}

	@Column(name = "part_full", length = 50)
	public String getPartFull() {
		return this.partFull;
	}

	public void setPartFull(String partFull) {
		this.partFull = partFull;
	}

	@Column(name = "study_mode", length = 50)
	public String getStudyMode() {
		return this.studyMode;
	}

	public void setStudyMode(String studyMode) {
		this.studyMode = studyMode;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the coursePricingObj
	 */
	@Transient
	public CoursePricing getCoursePricingObj() {
		return coursePricingObj;
	}

	/**
	 * @param coursePricingObj the coursePricingObj to set
	 */
	public void setCoursePricingObj(CoursePricing coursePricingObj) {
		this.coursePricingObj = coursePricingObj;
	}

	/**
	 * @return the facultyObj
	 */
	@Transient
	public Faculty getFacultyObj() {
		return facultyObj;
	}

	/**
	 * @param facultyObj the facultyObj to set
	 */
	public void setFacultyObj(Faculty facultyObj) {
		this.facultyObj = facultyObj;
	}

	/**
	 * @return the levelObj
	 */
	@Transient
	public Level getLevelObj() {
		return LevelObj;
	}

	/**
	 * @param levelObj the levelObj to set
	 */
	public void setLevelObj(Level levelObj) {
		LevelObj = levelObj;
	}

	/**
	 * @return the facultyLevelObj
	 */
	@Transient
	public FacultyLevel getFacultyLevelObj() {
		return facultyLevelObj;
	}

	/**
	 * @param facultyLevelObj the facultyLevelObj to set
	 */
	public void setFacultyLevelObj(FacultyLevel facultyLevelObj) {
		this.facultyLevelObj = facultyLevelObj;
	}

	/**
	 * @return the instituteLevelObj
	 */
	@Transient
	public InstituteLevel getInstituteLevelObj() {
		return instituteLevelObj;
	}

	/**
	 * @param instituteLevelObj the instituteLevelObj to set
	 */
	public void setInstituteLevelObj(InstituteLevel instituteLevelObj) {
		this.instituteLevelObj = instituteLevelObj;
	}

	/**
	 * @return the gradeObj
	 */
	@Transient
	public CourseGradeEligibility getGradeObj() {
		return gradeObj;
	}

	/**
	 * @param gradeObj the gradeObj to set
	 */
	public void setGradeObj(CourseGradeEligibility gradeObj) {
		this.gradeObj = gradeObj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LevelObj == null) ? 0 : LevelObj.hashCode());
		result = prime * result + ((availbilty == null) ? 0 : availbilty.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((coursePricingObj == null) ? 0 : coursePricingObj.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((facultyLevelObj == null) ? 0 : facultyLevelObj.hashCode());
		result = prime * result + ((facultyObj == null) ? 0 : facultyObj.hashCode());
		result = prime * result + ((gradeObj == null) ? 0 : gradeObj.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instituteLevelObj == null) ? 0 : instituteLevelObj.hashCode());
		result = prime * result + ((partFull == null) ? 0 : partFull.hashCode());
		result = prime * result + ((studyMode == null) ? 0 : studyMode.hashCode());
		result = prime * result + ((wrRange == null) ? 0 : wrRange.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseDetails other = (CourseDetails) obj;
		if (LevelObj == null) {
			if (other.LevelObj != null)
				return false;
		} else if (!LevelObj.equals(other.LevelObj))
			return false;
		if (availbilty == null) {
			if (other.availbilty != null)
				return false;
		} else if (!availbilty.equals(other.availbilty))
			return false;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (coursePricingObj == null) {
			if (other.coursePricingObj != null)
				return false;
		} else if (!coursePricingObj.equals(other.coursePricingObj))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (facultyLevelObj == null) {
			if (other.facultyLevelObj != null)
				return false;
		} else if (!facultyLevelObj.equals(other.facultyLevelObj))
			return false;
		if (facultyObj == null) {
			if (other.facultyObj != null)
				return false;
		} else if (!facultyObj.equals(other.facultyObj))
			return false;
		if (gradeObj == null) {
			if (other.gradeObj != null)
				return false;
		} else if (!gradeObj.equals(other.gradeObj))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instituteLevelObj == null) {
			if (other.instituteLevelObj != null)
				return false;
		} else if (!instituteLevelObj.equals(other.instituteLevelObj))
			return false;
		if (partFull == null) {
			if (other.partFull != null)
				return false;
		} else if (!partFull.equals(other.partFull))
			return false;
		if (studyMode == null) {
			if (other.studyMode != null)
				return false;
		} else if (!studyMode.equals(other.studyMode))
			return false;
		if (wrRange == null) {
			if (other.wrRange != null)
				return false;
		} else if (!wrRange.equals(other.wrRange))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CourseDetails [id=").append(id).append(", course=").append(course).append(", wrRange=")
				.append(wrRange).append(", availbilty=").append(availbilty).append(", partFull=").append(partFull)
				.append(", studyMode=").append(studyMode).append(", description=").append(description)
				.append(", coursePricingObj=").append(coursePricingObj).append(", facultyObj=").append(facultyObj)
				.append(", LevelObj=").append(LevelObj).append(", facultyLevelObj=").append(facultyLevelObj)
				.append(", instituteLevelObj=").append(instituteLevelObj).append(", gradeObj=").append(gradeObj)
				.append("]");
		return builder.toString();
	}

    /**
     * @return the intake
     */
    public String getIntake() {
        return intake;
    }

    /**
     * @param intake the intake to set
     */
    public void setIntake(String intake) {
        this.intake = intake;
    }

    /**
     * @return the fileUrl
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * @param fileUrl the fileUrl to set
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * @return the grades
     */
    public String getGrades() {
        return grades;
    }

    /**
     * @param grades the grades to set
     */
    public void setGrades(String grades) {
        this.grades = grades;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the openingHour
     */
    public String getOpeningHour() {
        return openingHour;
    }

    /**
     * @param openingHour the openingHour to set
     */
    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    /**
     * @return the campusLocation
     */
    public String getCampusLocation() {
        return campusLocation;
    }

    /**
     * @param campusLocation the campusLocation to set
     */
    public void setCampusLocation(String campusLocation) {
        this.campusLocation = campusLocation;
    }

    /**
     * @return the webiste
     */
    public String getWebiste() {
        return webiste;
    }

    /**
     * @param webiste the webiste to set
     */
    public void setWebiste(String webiste) {
        this.webiste = webiste;
    }

    /**
     * @return the jobFullTime
     */
    public String getJobFullTime() {
        return jobFullTime;
    }

    /**
     * @param jobFullTime the jobFullTime to set
     */
    public void setJobFullTime(String jobFullTime) {
        this.jobFullTime = jobFullTime;
    }

    /**
     * @return the jobPartTime
     */
    public String getJobPartTime() {
        return jobPartTime;
    }

    /**
     * @param jobPartTime the jobPartTime to set
     */
    public void setJobPartTime(String jobPartTime) {
        this.jobPartTime = jobPartTime;
    }

    /**
     * @return the courseLink
     */
    public String getCourseLink() {
        return courseLink;
    }

    /**
     * @param courseLink the courseLink to set
     */
    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    /**
     * @return the domesticFee
     */
    public String getDomesticFee() {
        return domesticFee;
    }

    /**
     * @param domesticFee the domesticFee to set
     */
    public void setDomesticFee(String domesticFee) {
        this.domesticFee = domesticFee;
    }

    /**
     * @return the internationalFee
     */
    public String getInternationalFee() {
        return internationalFee;
    }

    /**
     * @param internationalFee the internationalFee to set
     */
    public void setInternationalFee(String internationalFee) {
        this.internationalFee = internationalFee;
    }

}
