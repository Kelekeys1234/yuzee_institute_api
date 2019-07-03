package com.seeka.app.bean;import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="seeka_scholarship")
public class SeekaScholarships extends RecordModifier {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8981672129777348075L;

	@Id
    @GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private BigInteger id;
		
	@Column(name ="name")  
	private String name;
	
	@Column(name ="study_country")  
	private String studyCountry;
	
	@Column(name ="offered_by")  
	private String offeredBy;
	
	@Column(name ="level")  
	private String level;
	
	@Column(name ="amount")  
	private String amount;
	
	@Column(name ="detail")  
	private String detail;
	
	@Column(name ="url")  
	private String url;
	
	@Column(name ="deleted")  
	private String deleted;
	
	@Column(name ="created_date")  
	private String createdDate;
	
	@Column(name ="active")  
	private String active;
	
	@Column(name ="deadline")  
	private String deadline;
	
	@Column(name ="student_type")  
	private String studentType;
	
	@Column(name ="course_type")  
	private String courseType;
	
	@Column(name ="currency_type")  
	private String currencyType;
	
	@Column(name ="gpa")  
	private String gpa;
	
	@Column(name ="scholarship_type")  
	private String scholarshipType;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
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

	

	
}
