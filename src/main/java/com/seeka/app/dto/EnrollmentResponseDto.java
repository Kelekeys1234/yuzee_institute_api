package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class EnrollmentResponseDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2176239464548120692L;

	private String id;
	private String instituteId;
	private String instituteName;
	private BigInteger userId;
	private String userName;
	private String citizenship;
	private String courseId;
	private String courseName;
	private Boolean isInternationalStudent;
	private String instituteTypeId;
	private String instituteTypeName;
	private String previousInstitute;
	private String previousCourseName;
	private Date graduationDate;
	private String status;
	private String englishQualificaitonTest;
	private String latestAcademicQualification;

	/**
	 * Visa
	 */
	private String purposeOfStay;
	private String firstName;
	private String lastName;
	private String gender;
	private Date birthDate;
	private String passportNumber;
	private Date passportIssueDate;
	private Date passportExpiryDate;
	private String officeOfIssue;
	private String otherName;
	private String address;
	private String town;
	private String state;
	private String countryId;
	private String countryName;
	private String zipCode;
	private String phoneNumber;
	private String email;
	private BigInteger assignee;
	private String assigneeName;

	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;

	private List<StorageDto> enrollmentImages;
	private Date deadLine;
	private Boolean isArchive;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(final String instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(final String instituteName) {
		this.instituteName = instituteName;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(final String citizenship) {
		this.citizenship = citizenship;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(final String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(final String courseName) {
		this.courseName = courseName;
	}

	public Boolean getIsInternationalStudent() {
		return isInternationalStudent;
	}

	public void setIsInternationalStudent(final Boolean isInternationalStudent) {
		this.isInternationalStudent = isInternationalStudent;
	}

	public String getInstituteTypeId() {
		return instituteTypeId;
	}

	public void setInstituteTypeId(final String instituteTypeId) {
		this.instituteTypeId = instituteTypeId;
	}

	public String getInstituteTypeName() {
		return instituteTypeName;
	}

	public void setInstituteTypeName(final String instituteTypeName) {
		this.instituteTypeName = instituteTypeName;
	}

	public String getPreviousInstitute() {
		return previousInstitute;
	}

	public void setPreviousInstitute(final String previousInstitute) {
		this.previousInstitute = previousInstitute;
	}

	public String getPreviousCourseName() {
		return previousCourseName;
	}

	public void setPreviousCourseName(final String previousCourseName) {
		this.previousCourseName = previousCourseName;
	}

	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(final Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getEnglishQualificaitonTest() {
		return englishQualificaitonTest;
	}

	public void setEnglishQualificaitonTest(final String englishQualificaitonTest) {
		this.englishQualificaitonTest = englishQualificaitonTest;
	}

	public String getLatestAcademicQualification() {
		return latestAcademicQualification;
	}

	public void setLatestAcademicQualification(final String latestAcademicQualification) {
		this.latestAcademicQualification = latestAcademicQualification;
	}

	public String getPurposeOfStay() {
		return purposeOfStay;
	}

	public void setPurposeOfStay(final String purposeOfStay) {
		this.purposeOfStay = purposeOfStay;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(final String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Date getPassportIssueDate() {
		return passportIssueDate;
	}

	public void setPassportIssueDate(final Date passportIssueDate) {
		this.passportIssueDate = passportIssueDate;
	}

	public Date getPassportExpiryDate() {
		return passportExpiryDate;
	}

	public void setPassportExpiryDate(final Date passportExpiryDate) {
		this.passportExpiryDate = passportExpiryDate;
	}

	public String getOfficeOfIssue() {
		return officeOfIssue;
	}

	public void setOfficeOfIssue(final String officeOfIssue) {
		this.officeOfIssue = officeOfIssue;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(final String otherName) {
		this.otherName = otherName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getTown() {
		return town;
	}

	public void setTown(final String town) {
		this.town = town;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(final String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(final String countryName) {
		this.countryName = countryName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public BigInteger getAssignee() {
		return assignee;
	}

	public void setAssignee(final BigInteger assignee) {
		this.assignee = assignee;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(final String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(final String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<StorageDto> getEnrollmentImages() {
		return enrollmentImages;
	}

	public void setEnrollmentImages(final List<StorageDto> enrollmentImages) {
		this.enrollmentImages = enrollmentImages;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(final Date deadLine) {
		this.deadLine = deadLine;
	}

	public Boolean getIsArchive() {
		return isArchive;
	}

	public void setIsArchive(final Boolean isArchive) {
		this.isArchive = isArchive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (address == null ? 0 : address.hashCode());
		result = prime * result + (assignee == null ? 0 : assignee.hashCode());
		result = prime * result + (assigneeName == null ? 0 : assigneeName.hashCode());
		result = prime * result + (birthDate == null ? 0 : birthDate.hashCode());
		result = prime * result + (citizenship == null ? 0 : citizenship.hashCode());
		result = prime * result + (countryId == null ? 0 : countryId.hashCode());
		result = prime * result + (countryName == null ? 0 : countryName.hashCode());
		result = prime * result + (courseId == null ? 0 : courseId.hashCode());
		result = prime * result + (courseName == null ? 0 : courseName.hashCode());
		result = prime * result + (createdBy == null ? 0 : createdBy.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (deadLine == null ? 0 : deadLine.hashCode());
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (englishQualificaitonTest == null ? 0 : englishQualificaitonTest.hashCode());
		result = prime * result + (enrollmentImages == null ? 0 : enrollmentImages.hashCode());
		result = prime * result + (firstName == null ? 0 : firstName.hashCode());
		result = prime * result + (gender == null ? 0 : gender.hashCode());
		result = prime * result + (graduationDate == null ? 0 : graduationDate.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (instituteId == null ? 0 : instituteId.hashCode());
		result = prime * result + (instituteName == null ? 0 : instituteName.hashCode());
		result = prime * result + (instituteTypeId == null ? 0 : instituteTypeId.hashCode());
		result = prime * result + (instituteTypeName == null ? 0 : instituteTypeName.hashCode());
		result = prime * result + (isArchive == null ? 0 : isArchive.hashCode());
		result = prime * result + (isInternationalStudent == null ? 0 : isInternationalStudent.hashCode());
		result = prime * result + (lastName == null ? 0 : lastName.hashCode());
		result = prime * result + (latestAcademicQualification == null ? 0 : latestAcademicQualification.hashCode());
		result = prime * result + (officeOfIssue == null ? 0 : officeOfIssue.hashCode());
		result = prime * result + (otherName == null ? 0 : otherName.hashCode());
		result = prime * result + (passportExpiryDate == null ? 0 : passportExpiryDate.hashCode());
		result = prime * result + (passportIssueDate == null ? 0 : passportIssueDate.hashCode());
		result = prime * result + (passportNumber == null ? 0 : passportNumber.hashCode());
		result = prime * result + (phoneNumber == null ? 0 : phoneNumber.hashCode());
		result = prime * result + (previousCourseName == null ? 0 : previousCourseName.hashCode());
		result = prime * result + (previousInstitute == null ? 0 : previousInstitute.hashCode());
		result = prime * result + (purposeOfStay == null ? 0 : purposeOfStay.hashCode());
		result = prime * result + (state == null ? 0 : state.hashCode());
		result = prime * result + (status == null ? 0 : status.hashCode());
		result = prime * result + (town == null ? 0 : town.hashCode());
		result = prime * result + (updatedBy == null ? 0 : updatedBy.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
		result = prime * result + (userName == null ? 0 : userName.hashCode());
		result = prime * result + (zipCode == null ? 0 : zipCode.hashCode());
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
		EnrollmentResponseDto other = (EnrollmentResponseDto) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (assignee == null) {
			if (other.assignee != null) {
				return false;
			}
		} else if (!assignee.equals(other.assignee)) {
			return false;
		}
		if (assigneeName == null) {
			if (other.assigneeName != null) {
				return false;
			}
		} else if (!assigneeName.equals(other.assigneeName)) {
			return false;
		}
		if (birthDate == null) {
			if (other.birthDate != null) {
				return false;
			}
		} else if (!birthDate.equals(other.birthDate)) {
			return false;
		}
		if (citizenship == null) {
			if (other.citizenship != null) {
				return false;
			}
		} else if (!citizenship.equals(other.citizenship)) {
			return false;
		}
		if (countryId == null) {
			if (other.countryId != null) {
				return false;
			}
		} else if (!countryId.equals(other.countryId)) {
			return false;
		}
		if (countryName == null) {
			if (other.countryName != null) {
				return false;
			}
		} else if (!countryName.equals(other.countryName)) {
			return false;
		}
		if (courseId == null) {
			if (other.courseId != null) {
				return false;
			}
		} else if (!courseId.equals(other.courseId)) {
			return false;
		}
		if (courseName == null) {
			if (other.courseName != null) {
				return false;
			}
		} else if (!courseName.equals(other.courseName)) {
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
		if (deadLine == null) {
			if (other.deadLine != null) {
				return false;
			}
		} else if (!deadLine.equals(other.deadLine)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (englishQualificaitonTest == null) {
			if (other.englishQualificaitonTest != null) {
				return false;
			}
		} else if (!englishQualificaitonTest.equals(other.englishQualificaitonTest)) {
			return false;
		}
		if (enrollmentImages == null) {
			if (other.enrollmentImages != null) {
				return false;
			}
		} else if (!enrollmentImages.equals(other.enrollmentImages)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (gender == null) {
			if (other.gender != null) {
				return false;
			}
		} else if (!gender.equals(other.gender)) {
			return false;
		}
		if (graduationDate == null) {
			if (other.graduationDate != null) {
				return false;
			}
		} else if (!graduationDate.equals(other.graduationDate)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (instituteId == null) {
			if (other.instituteId != null) {
				return false;
			}
		} else if (!instituteId.equals(other.instituteId)) {
			return false;
		}
		if (instituteName == null) {
			if (other.instituteName != null) {
				return false;
			}
		} else if (!instituteName.equals(other.instituteName)) {
			return false;
		}
		if (instituteTypeId == null) {
			if (other.instituteTypeId != null) {
				return false;
			}
		} else if (!instituteTypeId.equals(other.instituteTypeId)) {
			return false;
		}
		if (instituteTypeName == null) {
			if (other.instituteTypeName != null) {
				return false;
			}
		} else if (!instituteTypeName.equals(other.instituteTypeName)) {
			return false;
		}
		if (isArchive == null) {
			if (other.isArchive != null) {
				return false;
			}
		} else if (!isArchive.equals(other.isArchive)) {
			return false;
		}
		if (isInternationalStudent == null) {
			if (other.isInternationalStudent != null) {
				return false;
			}
		} else if (!isInternationalStudent.equals(other.isInternationalStudent)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (latestAcademicQualification == null) {
			if (other.latestAcademicQualification != null) {
				return false;
			}
		} else if (!latestAcademicQualification.equals(other.latestAcademicQualification)) {
			return false;
		}
		if (officeOfIssue == null) {
			if (other.officeOfIssue != null) {
				return false;
			}
		} else if (!officeOfIssue.equals(other.officeOfIssue)) {
			return false;
		}
		if (otherName == null) {
			if (other.otherName != null) {
				return false;
			}
		} else if (!otherName.equals(other.otherName)) {
			return false;
		}
		if (passportExpiryDate == null) {
			if (other.passportExpiryDate != null) {
				return false;
			}
		} else if (!passportExpiryDate.equals(other.passportExpiryDate)) {
			return false;
		}
		if (passportIssueDate == null) {
			if (other.passportIssueDate != null) {
				return false;
			}
		} else if (!passportIssueDate.equals(other.passportIssueDate)) {
			return false;
		}
		if (passportNumber == null) {
			if (other.passportNumber != null) {
				return false;
			}
		} else if (!passportNumber.equals(other.passportNumber)) {
			return false;
		}
		if (phoneNumber == null) {
			if (other.phoneNumber != null) {
				return false;
			}
		} else if (!phoneNumber.equals(other.phoneNumber)) {
			return false;
		}
		if (previousCourseName == null) {
			if (other.previousCourseName != null) {
				return false;
			}
		} else if (!previousCourseName.equals(other.previousCourseName)) {
			return false;
		}
		if (previousInstitute == null) {
			if (other.previousInstitute != null) {
				return false;
			}
		} else if (!previousInstitute.equals(other.previousInstitute)) {
			return false;
		}
		if (purposeOfStay == null) {
			if (other.purposeOfStay != null) {
				return false;
			}
		} else if (!purposeOfStay.equals(other.purposeOfStay)) {
			return false;
		}
		if (state == null) {
			if (other.state != null) {
				return false;
			}
		} else if (!state.equals(other.state)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (town == null) {
			if (other.town != null) {
				return false;
			}
		} else if (!town.equals(other.town)) {
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
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		if (zipCode == null) {
			if (other.zipCode != null) {
				return false;
			}
		} else if (!zipCode.equals(other.zipCode)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnrollmentResponseDto [id=").append(id).append(", instituteId=").append(instituteId).append(", instituteName=").append(instituteName)
				.append(", userId=").append(userId).append(", userName=").append(userName).append(", citizenship=").append(citizenship).append(", courseId=")
				.append(courseId).append(", courseName=").append(courseName).append(", isInternationalStudent=").append(isInternationalStudent)
				.append(", instituteTypeId=").append(instituteTypeId).append(", instituteTypeName=").append(instituteTypeName).append(", previousInstitute=")
				.append(previousInstitute).append(", previousCourseName=").append(previousCourseName).append(", graduationDate=").append(graduationDate)
				.append(", status=").append(status).append(", englishQualificaitonTest=").append(englishQualificaitonTest)
				.append(", latestAcademicQualification=").append(latestAcademicQualification).append(", purposeOfStay=").append(purposeOfStay)
				.append(", firstName=").append(firstName).append(", lastName=").append(lastName).append(", gender=").append(gender).append(", birthDate=")
				.append(birthDate).append(", passportNumber=").append(passportNumber).append(", passportIssueDate=").append(passportIssueDate)
				.append(", passportExpiryDate=").append(passportExpiryDate).append(", officeOfIssue=").append(officeOfIssue).append(", otherName=")
				.append(otherName).append(", address=").append(address).append(", town=").append(town).append(", state=").append(state).append(", countryId=")
				.append(countryId).append(", countryName=").append(countryName).append(", zipCode=").append(zipCode).append(", phoneNumber=")
				.append(phoneNumber).append(", email=").append(email).append(", assignee=").append(assignee).append(", assigneeName=").append(assigneeName)
				.append(", createdOn=").append(createdOn).append(", updatedOn=").append(updatedOn).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append(", enrollmentImages=").append(enrollmentImages).append(", deadLine=").append(deadLine)
				.append(", isArchive=").append(isArchive).append("]");
		return builder.toString();
	}

}
