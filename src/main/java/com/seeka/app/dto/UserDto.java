package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3262142846479885854L;
	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private Date dob;
	private String countryOrgin;
	private String citizenship;
	private String email;
	private String username;
	private String password;
	private String mobileNo;
	private String skypeId;
	private String userEduInfo;

	private Boolean status;
	private String dobStr;
	private String signUpType;
	private String socialAccountId;
	private String userType;
	private boolean enabled;
	private boolean account_expired;
	private boolean credentials_expired;
	private boolean account_locked;
	private String whattsappNo;
	private String city;
	private String state;
	private String postalCode;
	private String address;
	private String imageName;
	private boolean isTempPassword;
	private String currencyCode;
	private String imageURL;
	private String adminRole;
	private Long adminRoleId;

	public UserDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(final Date dob) {
		this.dob = dob;
	}

	public String getCountryOrgin() {
		return countryOrgin;
	}

	public void setCountryOrgin(final String countryOrgin) {
		this.countryOrgin = countryOrgin;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(final String citizenship) {
		this.citizenship = citizenship;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(final String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSkypeId() {
		return skypeId;
	}

	public void setSkypeId(final String skypeId) {
		this.skypeId = skypeId;
	}

	public String getUserEduInfo() {
		return userEduInfo;
	}

	public void setUserEduInfo(final String userEduInfo) {
		this.userEduInfo = userEduInfo;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(final Boolean status) {
		this.status = status;
	}

	public String getDobStr() {
		return dobStr;
	}

	public void setDobStr(final String dobStr) {
		this.dobStr = dobStr;
	}

	public String getSignUpType() {
		return signUpType;
	}

	public void setSignUpType(final String signUpType) {
		this.signUpType = signUpType;
	}

	public String getSocialAccountId() {
		return socialAccountId;
	}

	public void setSocialAccountId(final String socialAccountId) {
		this.socialAccountId = socialAccountId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(final String userType) {
		this.userType = userType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccount_expired() {
		return account_expired;
	}

	public void setAccount_expired(final boolean account_expired) {
		this.account_expired = account_expired;
	}

	public boolean isCredentials_expired() {
		return credentials_expired;
	}

	public void setCredentials_expired(final boolean credentials_expired) {
		this.credentials_expired = credentials_expired;
	}

	public boolean isAccount_locked() {
		return account_locked;
	}

	public void setAccount_locked(final boolean account_locked) {
		this.account_locked = account_locked;
	}

	public String getWhattsappNo() {
		return whattsappNo;
	}

	public void setWhattsappNo(final String whattsappNo) {
		this.whattsappNo = whattsappNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(final String imageName) {
		this.imageName = imageName;
	}

	public boolean isTempPassword() {
		return isTempPassword;
	}

	public void setTempPassword(final boolean isTempPassword) {
		this.isTempPassword = isTempPassword;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(final String imageURL) {
		this.imageURL = imageURL;
	}

	public String getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(final String adminRole) {
		this.adminRole = adminRole;
	}

	public Long getAdminRoleId() {
		return adminRoleId;
	}

	public void setAdminRoleId(final Long adminRoleId) {
		this.adminRoleId = adminRoleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (account_expired ? 1231 : 1237);
		result = prime * result + (account_locked ? 1231 : 1237);
		result = prime * result + (address == null ? 0 : address.hashCode());
		result = prime * result + (adminRole == null ? 0 : adminRole.hashCode());
		result = prime * result + (adminRoleId == null ? 0 : adminRoleId.hashCode());
		result = prime * result + (citizenship == null ? 0 : citizenship.hashCode());
		result = prime * result + (city == null ? 0 : city.hashCode());
		result = prime * result + (countryOrgin == null ? 0 : countryOrgin.hashCode());
		result = prime * result + (credentials_expired ? 1231 : 1237);
		result = prime * result + (currencyCode == null ? 0 : currencyCode.hashCode());
		result = prime * result + (dob == null ? 0 : dob.hashCode());
		result = prime * result + (dobStr == null ? 0 : dobStr.hashCode());
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + (firstName == null ? 0 : firstName.hashCode());
		result = prime * result + (gender == null ? 0 : gender.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (imageName == null ? 0 : imageName.hashCode());
		result = prime * result + (imageURL == null ? 0 : imageURL.hashCode());
		result = prime * result + (isTempPassword ? 1231 : 1237);
		result = prime * result + (lastName == null ? 0 : lastName.hashCode());
		result = prime * result + (middleName == null ? 0 : middleName.hashCode());
		result = prime * result + (mobileNo == null ? 0 : mobileNo.hashCode());
		result = prime * result + (password == null ? 0 : password.hashCode());
		result = prime * result + (postalCode == null ? 0 : postalCode.hashCode());
		result = prime * result + (signUpType == null ? 0 : signUpType.hashCode());
		result = prime * result + (skypeId == null ? 0 : skypeId.hashCode());
		result = prime * result + (socialAccountId == null ? 0 : socialAccountId.hashCode());
		result = prime * result + (state == null ? 0 : state.hashCode());
		result = prime * result + (status == null ? 0 : status.hashCode());
		result = prime * result + (userEduInfo == null ? 0 : userEduInfo.hashCode());
		result = prime * result + (userType == null ? 0 : userType.hashCode());
		result = prime * result + (username == null ? 0 : username.hashCode());
		result = prime * result + (whattsappNo == null ? 0 : whattsappNo.hashCode());
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
		UserDto other = (UserDto) obj;
		if (account_expired != other.account_expired) {
			return false;
		}
		if (account_locked != other.account_locked) {
			return false;
		}
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (adminRole == null) {
			if (other.adminRole != null) {
				return false;
			}
		} else if (!adminRole.equals(other.adminRole)) {
			return false;
		}
		if (adminRoleId == null) {
			if (other.adminRoleId != null) {
				return false;
			}
		} else if (!adminRoleId.equals(other.adminRoleId)) {
			return false;
		}
		if (citizenship == null) {
			if (other.citizenship != null) {
				return false;
			}
		} else if (!citizenship.equals(other.citizenship)) {
			return false;
		}
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (countryOrgin == null) {
			if (other.countryOrgin != null) {
				return false;
			}
		} else if (!countryOrgin.equals(other.countryOrgin)) {
			return false;
		}
		if (credentials_expired != other.credentials_expired) {
			return false;
		}
		if (currencyCode == null) {
			if (other.currencyCode != null) {
				return false;
			}
		} else if (!currencyCode.equals(other.currencyCode)) {
			return false;
		}
		if (dob == null) {
			if (other.dob != null) {
				return false;
			}
		} else if (!dob.equals(other.dob)) {
			return false;
		}
		if (dobStr == null) {
			if (other.dobStr != null) {
				return false;
			}
		} else if (!dobStr.equals(other.dobStr)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (enabled != other.enabled) {
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (imageName == null) {
			if (other.imageName != null) {
				return false;
			}
		} else if (!imageName.equals(other.imageName)) {
			return false;
		}
		if (imageURL == null) {
			if (other.imageURL != null) {
				return false;
			}
		} else if (!imageURL.equals(other.imageURL)) {
			return false;
		}
		if (isTempPassword != other.isTempPassword) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (middleName == null) {
			if (other.middleName != null) {
				return false;
			}
		} else if (!middleName.equals(other.middleName)) {
			return false;
		}
		if (mobileNo == null) {
			if (other.mobileNo != null) {
				return false;
			}
		} else if (!mobileNo.equals(other.mobileNo)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (postalCode == null) {
			if (other.postalCode != null) {
				return false;
			}
		} else if (!postalCode.equals(other.postalCode)) {
			return false;
		}
		if (signUpType == null) {
			if (other.signUpType != null) {
				return false;
			}
		} else if (!signUpType.equals(other.signUpType)) {
			return false;
		}
		if (skypeId == null) {
			if (other.skypeId != null) {
				return false;
			}
		} else if (!skypeId.equals(other.skypeId)) {
			return false;
		}
		if (socialAccountId == null) {
			if (other.socialAccountId != null) {
				return false;
			}
		} else if (!socialAccountId.equals(other.socialAccountId)) {
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
		if (userEduInfo == null) {
			if (other.userEduInfo != null) {
				return false;
			}
		} else if (!userEduInfo.equals(other.userEduInfo)) {
			return false;
		}
		if (userType == null) {
			if (other.userType != null) {
				return false;
			}
		} else if (!userType.equals(other.userType)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		if (whattsappNo == null) {
			if (other.whattsappNo != null) {
				return false;
			}
		} else if (!whattsappNo.equals(other.whattsappNo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDto [id=").append(id).append(", firstName=").append(firstName).append(", middleName=").append(middleName).append(", lastName=")
				.append(lastName).append(", gender=").append(gender).append(", dob=").append(dob).append(", countryOrgin=").append(countryOrgin)
				.append(", citizenship=").append(citizenship).append(", email=").append(email).append(", username=").append(username).append(", password=")
				.append(password).append(", mobileNo=").append(mobileNo).append(", skypeId=").append(skypeId).append(", userEduInfo=").append(userEduInfo)
				.append(", status=").append(status).append(", dobStr=").append(dobStr).append(", signUpType=").append(signUpType).append(", socialAccountId=")
				.append(socialAccountId).append(", userType=").append(userType).append(", enabled=").append(enabled).append(", account_expired=")
				.append(account_expired).append(", credentials_expired=").append(credentials_expired).append(", account_locked=").append(account_locked)
				.append(", whattsappNo=").append(whattsappNo).append(", city=").append(city).append(", state=").append(state).append(", postalCode=")
				.append(postalCode).append(", address=").append(address).append(", imageName=").append(imageName).append(", isTempPassword=")
				.append(isTempPassword).append(", currencyCode=").append(currencyCode).append(", imageURL=").append(imageURL).append(", adminRole=")
				.append(adminRole).append(", adminRoleId=").append(adminRoleId).append("]");
		return builder.toString();
	}

}
