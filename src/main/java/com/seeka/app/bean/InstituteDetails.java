package com.seeka.app.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="institute_details")
public class InstituteDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="institute_id")
	private Integer instituteId; //Institute Details
	
	@Column(name="type")
	private String type; //Type
	
	@Column(name="youtube_link")
	private String youtubeLink; //Youtube Link
	
	@Column(name="ttion_fees_p_plan")
	private String tutionFeesPaymentPlan; //Tution Fees Payment Plan
	
	@Column(name="course_start")
	private String courseStart; //Course Start
	
	@Column(name="opening_hour")
	private String openingHour; // Opening Hour
	
	@Column(name="closing_hour")
	private String closingHour; //Closing Hour 
	
	@Column(name="enrolment_link")
	private String enrolmentLink; //Enrolment Link
	
	@Column(name="about_us_info")
	private String aboutUsInfo; //About Us Info	 
	
	@Column(name="climate_2")
	private String climate2; //Climate2
	
	@Column(name="avg_cost_of_living")
	private String averageCostOfLiving; //Average Cost Of Living
	
	@Column(name="whatsapp_no")
	private String whatsappNo; //Whatsapp No
	
	@Column(name="english_partners")
	private String englishPartners; //English Partners
	
	
	
	public Integer getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(Integer instituteId) {
		this.instituteId = instituteId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public String getTutionFeesPaymentPlan() {
		return tutionFeesPaymentPlan;
	}

	public void setTutionFeesPaymentPlan(String tutionFeesPaymentPlan) {
		this.tutionFeesPaymentPlan = tutionFeesPaymentPlan;
	}

	public String getCourseStart() {
		return courseStart;
	}

	public void setCourseStart(String courseStart) {
		this.courseStart = courseStart;
	}

	public String getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(String openingHour) {
		this.openingHour = openingHour;
	}

	public String getClosingHour() {
		return closingHour;
	}

	public void setClosingHour(String closingHour) {
		this.closingHour = closingHour;
	}

	public String getEnrolmentLink() {
		return enrolmentLink;
	}

	public void setEnrolmentLink(String enrolmentLink) {
		this.enrolmentLink = enrolmentLink;
	}

	public String getAboutUsInfo() {
		return aboutUsInfo;
	}

	public void setAboutUsInfo(String aboutUsInfo) {
		this.aboutUsInfo = aboutUsInfo;
	}

	public String getClimate2() {
		return climate2;
	}

	public void setClimate2(String climate2) {
		this.climate2 = climate2;
	}

	public String getAverageCostOfLiving() {
		return averageCostOfLiving;
	}

	public void setAverageCostOfLiving(String averageCostOfLiving) {
		this.averageCostOfLiving = averageCostOfLiving;
	}

	public String getWhatsappNo() {
		return whatsappNo;
	}

	public void setWhatsappNo(String whatsappNo) {
		this.whatsappNo = whatsappNo;
	}

	public String getEnglishPartners() {
		return englishPartners;
	}

	public void setEnglishPartners(String englishPartners) {
		this.englishPartners = englishPartners;
	}

	
	
}
