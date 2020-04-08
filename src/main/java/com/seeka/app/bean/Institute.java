package com.seeka.app.bean;

import java.io.Serializable;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Institute generated by hbm2java
 */
@Entity
@Table(name = "institute")
public class Institute implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7598262998124127162L;
	private String id;
	private InstituteType instituteType;
	private String name;
	private Boolean isActive;
	private Date createdOn;
	private Date updatedOn;
	private Date deletedOn;
	private String createdBy;
	private String updatedBy;
	private Boolean isDeleted;

	private List<InstituteService> serviceList;
	private String description;

	private Integer worldRanking;
	private String website;
	private String address;
	private Integer totalStudent;
	private Double latitute;
	private Double longitude;
	private String email;
	private String avgCostOfLiving;
	private String phoneNumber;
	private String campusType;
	private String openingFrom;
	private String openingTo;
	private String campusName;
	private InstituteCategoryType instituteCategoryType;

	private String enrolmentLink;
	private String tuitionFessPaymentPlan;
	private String scholarshipFinancingAssistance;

	private Integer courseCount;
	private String whatsNo;
	private String aboutInfo;
	private String courseStart;
	private String lastUpdated;
	private Integer domesticRanking;
	private String cityName;
	private String countryName;

	@Column(name = "city_name")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "country_name")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Institute() {
	}

	public Institute(final InstituteType instituteType, final String name, final Boolean isActive, final Date createdOn,
			final Date updatedOn, final Date deletedOn, final String createdBy, final String updatedBy, final Boolean isDeleted) {
		this.instituteType = instituteType;
		this.name = name;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.deletedOn = deletedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isDeleted = isDeleted;
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "institute_type_id")
	public InstituteType getInstituteType() {
		return this.instituteType;
	}

	public void setInstituteType(final InstituteType instituteType) {
		this.instituteType = instituteType;
	}

	@Column(name = "name", length = 145)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	public Date getDeletedOn() {
		return this.deletedOn;
	}

	public void setDeletedOn(final Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	@Column(name = "created_by", length = 50)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_by", length = 50)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(final String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "is_deleted")
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(final Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the serviceList
	 */
	@Transient
	public List<InstituteService> getServiceList() {
		return serviceList;
	}

	/**
	 * @param serviceList the serviceList to set
	 */
	public void setServiceList(final List<InstituteService> serviceList) {
		this.serviceList = serviceList;
	}

	/**
	 * @return the description
	 */
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the worldRanking
	 */
	@Column(name = "world_ranking")
	public Integer getWorldRanking() {
		return worldRanking;
	}

	/**
	 * @param worldRanking the worldRanking to set
	 */
	public void setWorldRanking(final Integer worldRanking) {
		this.worldRanking = worldRanking;
	}

	/**
	 * @return the website
	 */
	@Column(name = "website")
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(final String website) {
		this.website = website;
	}

	/**
	 * @return the address
	 */
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * @return the totalStudent
	 */
	@Column(name = "total_student")
	public Integer getTotalStudent() {
		return totalStudent;
	}

	/**
	 * @param totalStudent the totalStudent to set
	 */
	public void setTotalStudent(final Integer totalStudent) {
		this.totalStudent = totalStudent;
	}

	/**
	 * @return the latitute
	 */
	@Column(name = "latitute")
	public Double getLatitute() {
		return latitute;
	}

	/**
	 * @param latitute the latitute to set
	 */
	public void setLatitute(final Double latitute) {
		this.latitute = latitute;
	}

	/**
	 * @return the longitude
	 */
	@Column(name = "longitute")
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the email
	 */
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the avgCostOfLiving
	 */
	@Column(name = "avg_cost_of_living")
	public String getAvgCostOfLiving() {
		return avgCostOfLiving;
	}

	/**
	 * @param avgCostOfLiving the avgCostOfLiving to set
	 */
	public void setAvgCostOfLiving(final String avgCostOfLiving) {
		this.avgCostOfLiving = avgCostOfLiving;
	}

	/**
	 * @return the phoneNumber
	 */
	@Column(name = "phone_number")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the campusType
	 */
	@Column(name = "campus_type")
	public String getCampusType() {
		return campusType;
	}

	/**
	 * @param campusType the campusType to set
	 */
	public void setCampusType(final String campusType) {
		this.campusType = campusType;
	}

	@Column(name = "opening_from")
	public String getOpeningFrom() {
		return openingFrom;
	}

	public void setOpeningFrom(final String openingFrom) {
		this.openingFrom = openingFrom;
	}

	@Column(name = "opening_to")
	public String getOpeningTo() {
		return openingTo;
	}

	public void setOpeningTo(final String openingTo) {
		this.openingTo = openingTo;
	}

	@Column(name = "campus_name")
	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(final String campusName) {
		this.campusName = campusName;
	}

	/**
	 * @return the instituteCategoryType
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "institute_category_type_id")
	public InstituteCategoryType getInstituteCategoryType() {
		return instituteCategoryType;
	}

	/**
	 * @param instituteCategoryType the instituteCategoryType to set
	 */
	public void setInstituteCategoryType(final InstituteCategoryType instituteCategoryType) {
		this.instituteCategoryType = instituteCategoryType;
	}

	/**
	 * @return the enrolment
	 */
	@Column(name = "enrolment_link")
	public String getEnrolmentLink() {
		return enrolmentLink;
	}

	/**
	 * @param enrolment the enrolment to set
	 */
	public void setEnrolmentLink(final String enrolmentLink) {
		this.enrolmentLink = enrolmentLink;
	}

	/**
	 * @return the tuitionFessPaymentPlan
	 */
	@Column(name = "ttion_fees_p_plan")
	public String getTuitionFessPaymentPlan() {
		return tuitionFessPaymentPlan;
	}

	/**
	 * @param tuitionFessPaymentPlan the tuitionFessPaymentPlan to set
	 */
	public void setTuitionFessPaymentPlan(final String tuitionFessPaymentPlan) {
		this.tuitionFessPaymentPlan = tuitionFessPaymentPlan;
	}

	/**
	 * @return the scholarshipFinancingAssistance
	 */
	@Column(name = "scholarship_financing_assistance")
	public String getScholarshipFinancingAssistance() {
		return scholarshipFinancingAssistance;
	}

	/**
	 * @param scholarshipFinancingAssistance the scholarshipFinancingAssistance to
	 *                                       set
	 */
	public void setScholarshipFinancingAssistance(final String scholarshipFinancingAssistance) {
		this.scholarshipFinancingAssistance = scholarshipFinancingAssistance;
	}

	/**
	 * @return the courseCount
	 */
	@Transient
	public Integer getCourseCount() {
		return courseCount;
	}

	/**
	 * @param courseCount the courseCount to set
	 */
	public void setCourseCount(final Integer courseCount) {
		this.courseCount = courseCount;
	}

	/**
	 * @return the whatsNo
	 */
	@Column(name = "whatsapp_no", length = 100)
	public String getWhatsNo() {
		return whatsNo;
	}

	/**
	 * @param whatsNo the whatsNo to set
	 */
	public void setWhatsNo(final String whatsNo) {
		this.whatsNo = whatsNo;
	}

	/**
	 * @return the aboutInfo
	 */
	@Column(name = "about_us_info")
	public String getAboutInfo() {
		return aboutInfo;
	}

	/**
	 * @param aboutInfo the aboutInfo to set
	 */
	public void setAboutInfo(final String aboutInfo) {
		this.aboutInfo = aboutInfo;
	}

	/**
	 * @return the courseStart
	 */
	@Column(name = "course_start", length = 100)
	public String getCourseStart() {
		return courseStart;
	}

	/**
	 * @param courseStart the courseStart to set
	 */
	public void setCourseStart(final String courseStart) {
		this.courseStart = courseStart;
	}

	@Transient
	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name = "domestic_ranking")
	public Integer getDomesticRanking() {
		return domesticRanking;
	}

	public void setDomesticRanking(final Integer domesticRanking) {
		this.domesticRanking = domesticRanking;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aboutInfo == null ? 0 : aboutInfo.hashCode());
		result = prime * result + (address == null ? 0 : address.hashCode());
		result = prime * result + (avgCostOfLiving == null ? 0 : avgCostOfLiving.hashCode());
		result = prime * result + (campusName == null ? 0 : campusName.hashCode());
		result = prime * result + (campusType == null ? 0 : campusType.hashCode());
		result = prime * result + (courseCount == null ? 0 : courseCount.hashCode());
		result = prime * result + (courseStart == null ? 0 : courseStart.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (deletedOn == null ? 0 : deletedOn.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (domesticRanking == null ? 0 : domesticRanking.hashCode());
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (enrolmentLink == null ? 0 : enrolmentLink.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (instituteCategoryType == null ? 0 : instituteCategoryType.hashCode());
		result = prime * result + (instituteType == null ? 0 : instituteType.hashCode());
		result = prime * result + (isActive == null ? 0 : isActive.hashCode());
		result = prime * result + (isDeleted == null ? 0 : isDeleted.hashCode());
		result = prime * result + (lastUpdated == null ? 0 : lastUpdated.hashCode());
		result = prime * result + (latitute == null ? 0 : latitute.hashCode());
		result = prime * result + (longitude == null ? 0 : longitude.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (openingFrom == null ? 0 : openingFrom.hashCode());
		result = prime * result + (openingTo == null ? 0 : openingTo.hashCode());
		result = prime * result + (phoneNumber == null ? 0 : phoneNumber.hashCode());
		result = prime * result + (scholarshipFinancingAssistance == null ? 0 : scholarshipFinancingAssistance.hashCode());
		result = prime * result + (serviceList == null ? 0 : serviceList.hashCode());
		result = prime * result + (totalStudent == null ? 0 : totalStudent.hashCode());
		result = prime * result + (tuitionFessPaymentPlan == null ? 0 : tuitionFessPaymentPlan.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
		result = prime * result + (website == null ? 0 : website.hashCode());
		result = prime * result + (whatsNo == null ? 0 : whatsNo.hashCode());
		result = prime * result + (worldRanking == null ? 0 : worldRanking.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Institute other = (Institute) obj;
		if (aboutInfo == null) {
			if (other.aboutInfo != null) {
				return false;
			}
		} else if (!aboutInfo.equals(other.aboutInfo)) {
			return false;
		}
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (avgCostOfLiving == null) {
			if (other.avgCostOfLiving != null) {
				return false;
			}
		} else if (!avgCostOfLiving.equals(other.avgCostOfLiving)) {
			return false;
		}
		if (campusName == null) {
			if (other.campusName != null) {
				return false;
			}
		} else if (!campusName.equals(other.campusName)) {
			return false;
		}
		if (campusType == null) {
			if (other.campusType != null) {
				return false;
			}
		} else if (!campusType.equals(other.campusType)) {
			return false;
		}
		if (courseCount == null) {
			if (other.courseCount != null) {
				return false;
			}
		} else if (!courseCount.equals(other.courseCount)) {
			return false;
		}
		if (courseStart == null) {
			if (other.courseStart != null) {
				return false;
			}
		} else if (!courseStart.equals(other.courseStart)) {
			return false;
		}
		if (createdBy == null) {
			if (other.createdBy != null) {
				return false;
			}
		} else if (!createdBy.equals(other.createdBy)) {
			return false;
		}
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
			return false;
		}
		if (deletedOn == null) {
			if (other.deletedOn != null) {
				return false;
			}
		} else if (!deletedOn.equals(other.deletedOn)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (domesticRanking == null) {
			if (other.domesticRanking != null) {
				return false;
			}
		} else if (!domesticRanking.equals(other.domesticRanking)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (enrolmentLink == null) {
			if (other.enrolmentLink != null) {
				return false;
			}
		} else if (!enrolmentLink.equals(other.enrolmentLink)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (instituteCategoryType == null) {
			if (other.instituteCategoryType != null) {
				return false;
			}
		} else if (!instituteCategoryType.equals(other.instituteCategoryType)) {
			return false;
		}
		if (instituteType == null) {
			if (other.instituteType != null) {
				return false;
			}
		} else if (!instituteType.equals(other.instituteType)) {
			return false;
		}
		if (isActive == null) {
			if (other.isActive != null) {
				return false;
			}
		} else if (!isActive.equals(other.isActive)) {
			return false;
		}
		if (isDeleted == null) {
			if (other.isDeleted != null) {
				return false;
			}
		} else if (!isDeleted.equals(other.isDeleted)) {
			return false;
		}
		if (lastUpdated == null) {
			if (other.lastUpdated != null) {
				return false;
			}
		} else if (!lastUpdated.equals(other.lastUpdated)) {
			return false;
		}
		if (latitute == null) {
			if (other.latitute != null) {
				return false;
			}
		} else if (!latitute.equals(other.latitute)) {
			return false;
		}
		if (longitude == null) {
			if (other.longitude != null) {
				return false;
			}
		} else if (!longitude.equals(other.longitude)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (openingFrom == null) {
			if (other.openingFrom != null) {
				return false;
			}
		} else if (!openingFrom.equals(other.openingFrom)) {
			return false;
		}
		if (openingTo == null) {
			if (other.openingTo != null) {
				return false;
			}
		} else if (!openingTo.equals(other.openingTo)) {
			return false;
		}
		if (phoneNumber == null) {
			if (other.phoneNumber != null) {
				return false;
			}
		} else if (!phoneNumber.equals(other.phoneNumber)) {
			return false;
		}
		if (scholarshipFinancingAssistance == null) {
			if (other.scholarshipFinancingAssistance != null) {
				return false;
			}
		} else if (!scholarshipFinancingAssistance.equals(other.scholarshipFinancingAssistance)) {
			return false;
		}
		if (serviceList == null) {
			if (other.serviceList != null) {
				return false;
			}
		} else if (!serviceList.equals(other.serviceList)) {
			return false;
		}
		if (totalStudent == null) {
			if (other.totalStudent != null) {
				return false;
			}
		} else if (!totalStudent.equals(other.totalStudent)) {
			return false;
		}
		if (tuitionFessPaymentPlan == null) {
			if (other.tuitionFessPaymentPlan != null) {
				return false;
			}
		} else if (!tuitionFessPaymentPlan.equals(other.tuitionFessPaymentPlan)) {
			return false;
		}
		if (updatedBy == null) {
			if (other.updatedBy != null) {
				return false;
			}
		} else if (!updatedBy.equals(other.updatedBy)) {
			return false;
		}
		if (updatedOn == null) {
			if (other.updatedOn != null) {
				return false;
			}
		} else if (!updatedOn.equals(other.updatedOn)) {
			return false;
		}
		if (website == null) {
			if (other.website != null) {
				return false;
			}
		} else if (!website.equals(other.website)) {
			return false;
		}
		if (whatsNo == null) {
			if (other.whatsNo != null) {
				return false;
			}
		} else if (!whatsNo.equals(other.whatsNo)) {
			return false;
		}
		if (worldRanking == null) {
			if (other.worldRanking != null) {
				return false;
			}
		} else if (!worldRanking.equals(other.worldRanking)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Institute [id=").append(id).append(", instituteType=")
				.append(instituteType).append(", name=").append(name).append(", isActive=").append(isActive).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append(", deletedOn=").append(deletedOn).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append(", isDeleted=").append(isDeleted).append(", serviceList=").append(serviceList)
				.append(", description=").append(description).append(", worldRanking=").append(worldRanking).append(", website=").append(website)
				.append(", address=").append(address).append(", totalStudent=").append(totalStudent).append(", latitute=").append(latitute)
				.append(", longitude=").append(longitude).append(", email=").append(email).append(", avgCostOfLiving=").append(avgCostOfLiving)
				.append(", phoneNumber=").append(phoneNumber).append(", campusType=").append(campusType).append(", openingFrom=").append(openingFrom)
				.append(", openingTo=").append(openingTo).append(", campusName=").append(campusName).append(", instituteCategoryType=")
				.append(instituteCategoryType).append(", enrolmentLink=").append(enrolmentLink).append(", tuitionFessPaymentPlan=")
				.append(tuitionFessPaymentPlan).append(", scholarshipFinancingAssistance=").append(scholarshipFinancingAssistance).append(", courseCount=")
				.append(courseCount).append(", whatsNo=").append(whatsNo).append(", aboutInfo=").append(aboutInfo).append(", courseStart=").append(courseStart)
				.append(", lastUpdated=").append(lastUpdated).append(", domesticRanking=").append(domesticRanking).append("]");
		return builder.toString();
	}

}