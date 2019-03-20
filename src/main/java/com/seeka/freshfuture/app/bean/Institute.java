package com.seeka.freshfuture.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="institute")
public class Institute extends RecordModifier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="institute_type_id")
	private InstituteType instituteTypeObj; //Institute Type
					
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country countryObj; //Country Detail 
	
	@Column(name="city_id")
	private Integer cityId; //City Detail
	
	@Column(name="world_ranking")
	private Integer worldRanking; //World Ranking
	
	@Column(name="name")
	private String name; // Name
	
	@Column(name="accredited")
	private String accredited; //Accrediation Details
	
	@Column(name="int_ph_num")
	private String interPhoneNumber; //International Phone Number
	
	@Column(name="int_emails")
	private String interEmail; //International Email
	
	@Column(name="website")
	private String website; //Institute Website
	
	@Column(name="ins_img_cnt")
	private Integer insImageCount; //Institute Image Count
	
	@Column(name="t_num_of_stu")
	private Integer totalNoOfStudent; //Total No of Students
 
	@Column(name="longitude")
	private String longitude; //Institute Location Longitude
	
	@Column(name="latitude")
	private String latitude; //Institute Location latitude
	
	@Column(name="address")
	private String address; //Institute Address
	
	@Column(name="description")
	private String description; //Description
	
	@Column(name="is_active")
	private Boolean isActive; // Is Active

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InstituteType getInstituteTypeObj() {
		return instituteTypeObj;
	}

	public void setInstituteTypeObj(InstituteType instituteTypeObj) {
		this.instituteTypeObj = instituteTypeObj;
	}

	public Country getCountryObj() {
		return countryObj;
	}

	public void setCountryObj(Country countryObj) {
		this.countryObj = countryObj;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getWorldRanking() {
		return worldRanking;
	}

	public void setWorldRanking(Integer worldRanking) {
		this.worldRanking = worldRanking;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccredited() {
		return accredited;
	}

	public void setAccredited(String accredited) {
		this.accredited = accredited;
	}

	public String getInterPhoneNumber() {
		return interPhoneNumber;
	}

	public void setInterPhoneNumber(String interPhoneNumber) {
		this.interPhoneNumber = interPhoneNumber;
	}

	public String getInterEmail() {
		return interEmail;
	}

	public void setInterEmail(String interEmail) {
		this.interEmail = interEmail;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getInsImageCount() {
		return insImageCount;
	}

	public void setInsImageCount(Integer insImageCount) {
		this.insImageCount = insImageCount;
	}

	public Integer getTotalNoOfStudent() {
		return totalNoOfStudent;
	}

	public void setTotalNoOfStudent(Integer totalNoOfStudent) {
		this.totalNoOfStudent = totalNoOfStudent;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

	
    		 
	
}
