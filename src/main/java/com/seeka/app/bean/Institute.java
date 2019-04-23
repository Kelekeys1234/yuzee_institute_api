package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.google.gson.Gson;

@Entity
@Table(name="institute")
public class Institute extends RecordModifier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name="institute_type_id")
	private UUID instituteTypeId; //Institute Type
					
	@Column(name = "country_id")
	private UUID countryId; //Country Detail 
	
	@Column(name="city_id")
	private UUID cityId; //City Detail
	
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
	
	@Transient
	private InstituteDetails instituteDetailsObj;
	
	@Transient
	private List<InstituteServiceDetails> serviceList;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getInstituteTypeId() {
		return instituteTypeId;
	}

	public void setInstituteTypeId(UUID instituteTypeId) {
		this.instituteTypeId = instituteTypeId;
	}

	public UUID getCountryId() {
		return countryId;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	}

	public UUID getCityId() {
		return cityId;
	}

	public void setCityId(UUID cityId) {
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

	public InstituteDetails getInstituteDetailsObj() {
		return instituteDetailsObj;
	}

	public void setInstituteDetailsObj(InstituteDetails instituteDetailsObj) {
		this.instituteDetailsObj = instituteDetailsObj;
	} 
	
	public List<InstituteServiceDetails> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<InstituteServiceDetails> serviceList) {
		this.serviceList = serviceList;
	}
 
	
	
	public static void main(String[] args) {
		
		Country countryObj = new Country();
		countryObj.setId(UUID.randomUUID());

		InstituteType instituteTypeObj = new InstituteType();
		instituteTypeObj.setId(UUID.randomUUID());
		
		City cityObj = new City();
		cityObj.setId(UUID.randomUUID());
		
		
		Institute institute = new Institute();
		institute.setName("Name of the University");
		institute.setCityId(UUID.fromString("990E603E-9F20-488C-BC0D-8554C50EE500"));
		institute.setCountryId(UUID.fromString("990E603E-9F20-488C-BC0D-8554C50EE500"));
		institute.setAddress("Address of the University");
		institute.setDescription("Description about the university");
		institute.setCreatedBy("Name of the record Creator");
		institute.setUpdatedBy("Name of the record Creator");
		institute.setCreatedOn(new Date());
		institute.setUpdatedOn(new Date());
		institute.setDeletedOn(new Date());
		institute.setIsDeleted(false);	
		
		
		institute.setInsImageCount(10);
		institute.setInstituteTypeId(UUID.fromString("990E603E-9F20-488C-BC0D-8554C50EE500"));
		institute.setInterEmail("internationalemail@email.com");
		institute.setInterPhoneNumber("+91-9884512528");
		institute.setIsActive(true);
		institute.setLatitude("91.084754875");
		institute.setLongitude("-78.074574556");
		institute.setTotalNoOfStudent(2456);
		institute.setWebsite("www.websiteoftheuniversity.com");
		institute.setWorldRanking(590);
		
		InstituteDetails instituteDetails = new InstituteDetails();
		instituteDetails.setAboutUsInfo("About us information about the universities");
		instituteDetails.setAverageCostOfLiving("25000AUD");
		instituteDetails.setClimate2("Climate of the university");
		instituteDetails.setClosingHour("05:00PM");
		instituteDetails.setCourseStart("Course Start");
		instituteDetails.setEnglishPartners("University English Parameters");
		instituteDetails.setEnrolmentLink("www.universityenrolmentlink.com");
		instituteDetails.setOpeningHour("9:30AM");
		instituteDetails.setTutionFeesPaymentPlan("University Payment Plan");
		instituteDetails.setType("Type of university");
		instituteDetails.setWhatsappNo("+91-9884512528");
		instituteDetails.setYoutubeLink("www.youtubelinkofuniversity.com");
		
		institute.setInstituteDetailsObj(instituteDetails);
		
		Gson gson = new Gson();
		
		System.out.println(gson.toJson(institute));
		
		
	}
	
}
