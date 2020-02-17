package com.seeka.app.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "seeka_scholarship")
public class SeekaScholarships extends RecordModifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8981672129777348075L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "study_country")
	private String studyCountry;

	@Column(name = "offered_by")
	private String offeredBy;

	@Column(name = "level")
	private String level;

	@Column(name = "amount")
	private String amount;

	@Column(name = "detail")
	private String detail;

	@Column(name = "url")
	private String url;

	@Column(name = "deleted")
	private String deleted;

	@Column(name = "created_date")
	private String createdDate;

	@Column(name = "active")
	private String active;

	@Column(name = "deadline")
	private String deadline;

	@Column(name = "student_type")
	private String studentType;

	@Column(name = "course_type")
	private String courseType;

	@Column(name = "currency_type")
	private String currencyType;

	@Column(name = "gpa")
	private String gpa;

	@Column(name = "scholarship_type")
	private String scholarshipType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudyCountry() {
		return studyCountry;
	}

	public void setStudyCountry(String studyCountry) {
		this.studyCountry = studyCountry;
	}

	public String getOfferedBy() {
		return offeredBy;
	}

	public void setOfferedBy(String offeredBy) {
		this.offeredBy = offeredBy;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getStudentType() {
		return studentType;
	}

	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getGpa() {
		return gpa;
	}

	public void setGpa(String gpa) {
		this.gpa = gpa;
	}

	public String getScholarshipType() {
		return scholarshipType;
	}

	public void setScholarshipType(String scholarshipType) {
		this.scholarshipType = scholarshipType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((courseType == null) ? 0 : courseType.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((currencyType == null) ? 0 : currencyType.hashCode());
		result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((gpa == null) ? 0 : gpa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((offeredBy == null) ? 0 : offeredBy.hashCode());
		result = prime * result + ((scholarshipType == null) ? 0 : scholarshipType.hashCode());
		result = prime * result + ((studentType == null) ? 0 : studentType.hashCode());
		result = prime * result + ((studyCountry == null) ? 0 : studyCountry.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		SeekaScholarships other = (SeekaScholarships) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (courseType == null) {
			if (other.courseType != null)
				return false;
		} else if (!courseType.equals(other.courseType))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (currencyType == null) {
			if (other.currencyType != null)
				return false;
		} else if (!currencyType.equals(other.currencyType))
			return false;
		if (deadline == null) {
			if (other.deadline != null)
				return false;
		} else if (!deadline.equals(other.deadline))
			return false;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (gpa == null) {
			if (other.gpa != null)
				return false;
		} else if (!gpa.equals(other.gpa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (offeredBy == null) {
			if (other.offeredBy != null)
				return false;
		} else if (!offeredBy.equals(other.offeredBy))
			return false;
		if (scholarshipType == null) {
			if (other.scholarshipType != null)
				return false;
		} else if (!scholarshipType.equals(other.scholarshipType))
			return false;
		if (studentType == null) {
			if (other.studentType != null)
				return false;
		} else if (!studentType.equals(other.studentType))
			return false;
		if (studyCountry == null) {
			if (other.studyCountry != null)
				return false;
		} else if (!studyCountry.equals(other.studyCountry))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeekaScholarships [id=").append(id).append(", name=").append(name).append(", studyCountry=")
				.append(studyCountry).append(", offeredBy=").append(offeredBy).append(", level=").append(level)
				.append(", amount=").append(amount).append(", detail=").append(detail).append(", url=").append(url)
				.append(", deleted=").append(deleted).append(", createdDate=").append(createdDate).append(", active=")
				.append(active).append(", deadline=").append(deadline).append(", studentType=").append(studentType)
				.append(", courseType=").append(courseType).append(", currencyType=").append(currencyType)
				.append(", gpa=").append(gpa).append(", scholarshipType=").append(scholarshipType).append("]");
		return builder.toString();
	}

}
