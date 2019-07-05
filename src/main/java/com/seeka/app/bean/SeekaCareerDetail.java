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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((careerCatId == null) ? 0 : careerCatId.hashCode());
		result = prime * result + ((careerText == null) ? 0 : careerText.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobActivity == null) ? 0 : jobActivity.hashCode());
		result = prime * result + ((jobDescription == null) ? 0 : jobDescription.hashCode());
		result = prime * result + ((jobImportance == null) ? 0 : jobImportance.hashCode());
		result = prime * result + ((jobKnowledge == null) ? 0 : jobKnowledge.hashCode());
		result = prime * result + ((jobSkills == null) ? 0 : jobSkills.hashCode());
		result = prime * result + ((levelId == null) ? 0 : levelId.hashCode());
		result = prime * result + ((optionalCareer == null) ? 0 : optionalCareer.hashCode());
		result = prime * result + ((roleDetail == null) ? 0 : roleDetail.hashCode());
		result = prime * result + ((workStyle == null) ? 0 : workStyle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeekaCareerDetail other = (SeekaCareerDetail) obj;
		if (careerCatId == null) {
			if (other.careerCatId != null)
				return false;
		} else if (!careerCatId.equals(other.careerCatId))
			return false;
		if (careerText == null) {
			if (other.careerText != null)
				return false;
		} else if (!careerText.equals(other.careerText))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jobActivity == null) {
			if (other.jobActivity != null)
				return false;
		} else if (!jobActivity.equals(other.jobActivity))
			return false;
		if (jobDescription == null) {
			if (other.jobDescription != null)
				return false;
		} else if (!jobDescription.equals(other.jobDescription))
			return false;
		if (jobImportance == null) {
			if (other.jobImportance != null)
				return false;
		} else if (!jobImportance.equals(other.jobImportance))
			return false;
		if (jobKnowledge == null) {
			if (other.jobKnowledge != null)
				return false;
		} else if (!jobKnowledge.equals(other.jobKnowledge))
			return false;
		if (jobSkills == null) {
			if (other.jobSkills != null)
				return false;
		} else if (!jobSkills.equals(other.jobSkills))
			return false;
		if (levelId == null) {
			if (other.levelId != null)
				return false;
		} else if (!levelId.equals(other.levelId))
			return false;
		if (optionalCareer == null) {
			if (other.optionalCareer != null)
				return false;
		} else if (!optionalCareer.equals(other.optionalCareer))
			return false;
		if (roleDetail == null) {
			if (other.roleDetail != null)
				return false;
		} else if (!roleDetail.equals(other.roleDetail))
			return false;
		if (workStyle == null) {
			if (other.workStyle != null)
				return false;
		} else if (!workStyle.equals(other.workStyle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeekaCareerDetail [id=").append(id).append(", careerText=").append(careerText)
				.append(", levelId=").append(levelId).append(", jobDescription=").append(jobDescription)
				.append(", roleDetail=").append(roleDetail).append(", jobActivity=").append(jobActivity)
				.append(", jobImportance=").append(jobImportance).append(", optionalCareer=").append(optionalCareer)
				.append(", jobSkills=").append(jobSkills).append(", jobKnowledge=").append(jobKnowledge)
				.append(", workStyle=").append(workStyle).append(", careerCatId=").append(careerCatId).append("]");
		return builder.toString();
	}

	
	
}
