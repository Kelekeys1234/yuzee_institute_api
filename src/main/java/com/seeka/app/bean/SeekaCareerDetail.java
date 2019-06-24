package com.seeka.app.bean;import java.math.BigInteger;


import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="seeka_career_details")
public class SeekaCareerDetail extends RecordModifier implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private BigInteger id;
	
	@Column(name = "career_txt")  
	private String careerText;
	
	@Column(name = "course_level_id")  
	private String levelId;
	
	@Column(name = "job_description")  
	private String jobDescription;
	
	@Column(name = "role_detail")  
	private String roleDetail;
	
	@Column(name = "job_activity")  
	private String jobActivity;
	
	@Column(name = "job_importance")  
	private String jobImportance;
	
	@Column(name = "optional_career")  
	private String optionalCareer;
	
	@Column(name = "jobSkills")  
	private String jobSkills;
	
	@Column(name = "job_knowledge")  
	private String jobKnowledge;
	
	@Column(name = "work_style")  
	private String workStyle;
	
	@Column(name = "career_cat_id")  
	private String careerCatId;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getCareerText() {
		return careerText;
	}

	public void setCareerText(String careerText) {
		this.careerText = careerText;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getRoleDetail() {
		return roleDetail;
	}

	public void setRoleDetail(String roleDetail) {
		this.roleDetail = roleDetail;
	}

	public String getJobActivity() {
		return jobActivity;
	}

	public void setJobActivity(String jobActivity) {
		this.jobActivity = jobActivity;
	}

	public String getJobImportance() {
		return jobImportance;
	}

	public void setJobImportance(String jobImportance) {
		this.jobImportance = jobImportance;
	}

	public String getOptionalCareer() {
		return optionalCareer;
	}

	public void setOptionalCareer(String optionalCareer) {
		this.optionalCareer = optionalCareer;
	}

	public String getJobSkills() {
		return jobSkills;
	}

	public void setJobSkills(String jobSkills) {
		this.jobSkills = jobSkills;
	}

	public String getJobKnowledge() {
		return jobKnowledge;
	}

	public void setJobKnowledge(String jobKnowledge) {
		this.jobKnowledge = jobKnowledge;
	}

	public String getWorkStyle() {
		return workStyle;
	}

	public void setWorkStyle(String workStyle) {
		this.workStyle = workStyle;
	}

	public String getCareerCatId() {
		return careerCatId;
	}

	public void setCareerCatId(String careerCatId) {
		this.careerCatId = careerCatId;
	}

	
	
}
