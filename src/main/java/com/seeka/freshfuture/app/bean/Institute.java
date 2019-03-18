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
public class Institute implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="institute_type")
	private InstituteType instituteTypeObj; //Institute Type
	
	@Column(name="institute_name")
	private String instName; //Institute Name
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country countryObj; //Country Detail 
	
	@Column(name="city_id")
	private Integer cityId; //City Detail
	
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
	
	@Column(name="schlr_finan_asst")
	private String schlrFinanAsstUrl; //Scholarships and financial assistance
	
	@Column(name="type")
	private String type; //Type: Private or Public
	
	@Column(name="youtube_link")
	private String youtubeLink; //Institute Youtube Link
	
	@Column(name="ttion_fees_p_plan")
	private String tutionFeesPaymentPlan; //Tuition fees payment plan
	
	@Column(name="personal_coun_acad")
	private Boolean perCounsellingAcademic; //Counselling – personal and academic
	
	@Column(name="visa_work_benefits")
	private Boolean visa_work_benefits; //Visa Work Benefits
	
	@Column(name="emp_career_dev")
	private Boolean emp_career_dev; //Employment and career development
	
	@Column(name="course_start")
	private String courseStart; //Course Start Date
	
	@Column(name="study_library_support")
	private Boolean studLibrarySupport; //Study Library  Support 
	
	@Column(name="health_services")
	private Boolean healthServices; //Health services
	
	@Column(name="disability_support")
	private Boolean disabilitySupport; //Disability Support
	
	@Column(name="childcare_centre")
	private Boolean childcareCentre; //Childcare Centre
	
	@Column(name="cult_incl_antiracism_prg")
	private Boolean cultrInclusionAntiRacismProg; //Cultural inclusion/anti-racism programs
	
	@Column(name="tech_serv")
	private Boolean technologyServ; //Technology Services
	
	@Column(name="accommodation")
	private Boolean accommodation; //Accommodation
	
	@Column(name="medical")
	private Boolean medical; //Medical 
	
	@Column(name="leg_service")
	private Boolean legalService; //Legal Services
	
	@Column(name="acct_serv")
	private Boolean acctServ; //Accounting Services
	
	@Column(name="bus")
	private Boolean bus; //Bus
	
	@Column(name="train")
	private Boolean train; //Train
	
	@Column(name="airport_pickup")
	private Boolean airportPickup; //Airport Pickup
	
	@Column(name="swimming_pool")
	private Boolean swimmingPool; //Swimming pool
	
	@Column(name="sports_center")
	private Boolean sportsCenter; //Sports Center
	
	@Column(name="sport_teams")
	private Boolean sportTeams; //Sport Teams
	
	@Column(name="housing_services")
	private Boolean housingServices; //Housing Services
	
	@Column(name="opening_hour")
	private String openingHour; //Opening hour
	
	@Column(name="closing_hour")
	private String closingHour; //Closing hour
	
	@Column(name="enrolment_link")
	private String enrollmentLink; //Enrollment Link
	
	@Column(name="about_us_info")
	private String aboutUsInfo; //About Us - Info
	
	@Column(name="scholarship")
	private Boolean scholarship; //Scolarship Option
	
	@Column(name="govt_loan")
	private Boolean govtLoan; //Government Loan
	
	@Column(name="paymt_plan")
	private Boolean paymtPlan; //Payment Plan
	
	@Column(name="climate_2")
	private String climate; //Climate
	
	@Column(name="avg_cost_of_living")
	private String avgCostOfLiving; //Average Cost of Living
	
	@Column(name="whatsapp_no")
	private String whatsappNo; //Whatsapp No
	
	@Column(name="english_partners")
	private String englishPartners; //English Partners
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstType() {
		return instType;
	}

	public void setInstType(Integer instType) {
		this.instType = instType;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
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

	public String getSchlrFinanAsstUrl() {
		return schlrFinanAsstUrl;
	}

	public void setSchlrFinanAsstUrl(String schlrFinanAsstUrl) {
		this.schlrFinanAsstUrl = schlrFinanAsstUrl;
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

	public Boolean getPerCounsellingAcademic() {
		return perCounsellingAcademic;
	}

	public void setPerCounsellingAcademic(Boolean perCounsellingAcademic) {
		this.perCounsellingAcademic = perCounsellingAcademic;
	}

	public Boolean getVisa_work_benefits() {
		return visa_work_benefits;
	}

	public void setVisa_work_benefits(Boolean visa_work_benefits) {
		this.visa_work_benefits = visa_work_benefits;
	}

	public Boolean getEmp_career_dev() {
		return emp_career_dev;
	}

	public void setEmp_career_dev(Boolean emp_career_dev) {
		this.emp_career_dev = emp_career_dev;
	}

	public String getCourseStart() {
		return courseStart;
	}

	public void setCourseStart(String courseStart) {
		this.courseStart = courseStart;
	}

	public Boolean getStudLibrarySupport() {
		return studLibrarySupport;
	}

	public void setStudLibrarySupport(Boolean studLibrarySupport) {
		this.studLibrarySupport = studLibrarySupport;
	}

	public Boolean getHealthServices() {
		return healthServices;
	}

	public void setHealthServices(Boolean healthServices) {
		this.healthServices = healthServices;
	}

	public Boolean getDisabilitySupport() {
		return disabilitySupport;
	}

	public void setDisabilitySupport(Boolean disabilitySupport) {
		this.disabilitySupport = disabilitySupport;
	}

	public Boolean getChildcareCentre() {
		return childcareCentre;
	}

	public void setChildcareCentre(Boolean childcareCentre) {
		this.childcareCentre = childcareCentre;
	}

	public Boolean getCultrInclusionAntiRacismProg() {
		return cultrInclusionAntiRacismProg;
	}

	public void setCultrInclusionAntiRacismProg(Boolean cultrInclusionAntiRacismProg) {
		this.cultrInclusionAntiRacismProg = cultrInclusionAntiRacismProg;
	}

	public Boolean getTechnologyServ() {
		return technologyServ;
	}

	public void setTechnologyServ(Boolean technologyServ) {
		this.technologyServ = technologyServ;
	}

	public Boolean getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(Boolean accommodation) {
		this.accommodation = accommodation;
	}

	public Boolean getMedical() {
		return medical;
	}

	public void setMedical(Boolean medical) {
		this.medical = medical;
	}

	public Boolean getLegalService() {
		return legalService;
	}

	public void setLegalService(Boolean legalService) {
		this.legalService = legalService;
	}

	public Boolean getAcctServ() {
		return acctServ;
	}

	public void setAcctServ(Boolean acctServ) {
		this.acctServ = acctServ;
	}

	public Boolean getBus() {
		return bus;
	}

	public void setBus(Boolean bus) {
		this.bus = bus;
	}

	public Boolean getTrain() {
		return train;
	}

	public void setTrain(Boolean train) {
		this.train = train;
	}

	public Boolean getAirportPickup() {
		return airportPickup;
	}

	public void setAirportPickup(Boolean airportPickup) {
		this.airportPickup = airportPickup;
	}

	public Boolean getSwimmingPool() {
		return swimmingPool;
	}

	public void setSwimmingPool(Boolean swimmingPool) {
		this.swimmingPool = swimmingPool;
	}

	public Boolean getSportsCenter() {
		return sportsCenter;
	}

	public void setSportsCenter(Boolean sportsCenter) {
		this.sportsCenter = sportsCenter;
	}

	public Boolean getSportTeams() {
		return sportTeams;
	}

	public void setSportTeams(Boolean sportTeams) {
		this.sportTeams = sportTeams;
	}

	public Boolean getHousingServices() {
		return housingServices;
	}

	public void setHousingServices(Boolean housingServices) {
		this.housingServices = housingServices;
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

	public String getEnrollmentLink() {
		return enrollmentLink;
	}

	public void setEnrollmentLink(String enrollmentLink) {
		this.enrollmentLink = enrollmentLink;
	}

	public String getAboutUsInfo() {
		return aboutUsInfo;
	}

	public void setAboutUsInfo(String aboutUsInfo) {
		this.aboutUsInfo = aboutUsInfo;
	}

	public Boolean getScholarship() {
		return scholarship;
	}

	public void setScholarship(Boolean scholarship) {
		this.scholarship = scholarship;
	}

	public Boolean getGovtLoan() {
		return govtLoan;
	}

	public void setGovtLoan(Boolean govtLoan) {
		this.govtLoan = govtLoan;
	}

	public Boolean getPaymtPlan() {
		return paymtPlan;
	}

	public void setPaymtPlan(Boolean paymtPlan) {
		this.paymtPlan = paymtPlan;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getAvgCostOfLiving() {
		return avgCostOfLiving;
	}

	public void setAvgCostOfLiving(String avgCostOfLiving) {
		this.avgCostOfLiving = avgCostOfLiving;
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
