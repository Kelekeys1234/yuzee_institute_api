package com.seeka.app.dto;

import java.io.Serializable;

/**
 *
 * @author SeekADegree
 *
 */
public class UserDeviceInfoDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8433234322653635466L;
	private Long id;
	private Long userId;
	private String deviceId;
	private String sdkVersionCode;
	private String appVersion;
	private String osVersion;
	private String model;
	private String platform;
	private String ipAddress;
	private boolean pushNotification;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private Long userSessionId;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSdkVersionCode() {
		return sdkVersionCode;
	}

	public void setSdkVersionCode(final String sdkVersionCode) {
		this.sdkVersionCode = sdkVersionCode;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(final String appVersion) {
		this.appVersion = appVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(final String osVersion) {
		this.osVersion = osVersion;
	}

	public String getModel() {
		return model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(final String platform) {
		this.platform = platform;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(final String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isPushNotification() {
		return pushNotification;
	}

	public void setPushNotification(final boolean pushNotification) {
		this.pushNotification = pushNotification;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(final String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(final Long userSessionId) {
		this.userSessionId = userSessionId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDeviceInfoDto [id=").append(id).append(", userId=").append(userId).append(", deviceId=").append(deviceId)
				.append(", sdkVersionCode=").append(sdkVersionCode).append(", appVersion=").append(appVersion).append(", osVersion=").append(osVersion)
				.append(", model=").append(model).append(", platform=").append(platform).append(", ipAddress=").append(ipAddress).append(", pushNotification=")
				.append(pushNotification).append(", firstName=").append(firstName).append(", lastName=").append(lastName).append(", email=").append(email)
				.append(", mobileNo=").append(mobileNo).append(", userSessionId=").append(userSessionId).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (appVersion == null ? 0 : appVersion.hashCode());
		result = prime * result + (deviceId == null ? 0 : deviceId.hashCode());
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (firstName == null ? 0 : firstName.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (ipAddress == null ? 0 : ipAddress.hashCode());
		result = prime * result + (lastName == null ? 0 : lastName.hashCode());
		result = prime * result + (mobileNo == null ? 0 : mobileNo.hashCode());
		result = prime * result + (model == null ? 0 : model.hashCode());
		result = prime * result + (osVersion == null ? 0 : osVersion.hashCode());
		result = prime * result + (platform == null ? 0 : platform.hashCode());
		result = prime * result + (pushNotification ? 1231 : 1237);
		result = prime * result + (sdkVersionCode == null ? 0 : sdkVersionCode.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
		result = prime * result + (userSessionId == null ? 0 : userSessionId.hashCode());
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
		UserDeviceInfoDto other = (UserDeviceInfoDto) obj;
		if (appVersion == null) {
			if (other.appVersion != null) {
				return false;
			}
		} else if (!appVersion.equals(other.appVersion)) {
			return false;
		}
		if (deviceId == null) {
			if (other.deviceId != null) {
				return false;
			}
		} else if (!deviceId.equals(other.deviceId)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (ipAddress == null) {
			if (other.ipAddress != null) {
				return false;
			}
		} else if (!ipAddress.equals(other.ipAddress)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (mobileNo == null) {
			if (other.mobileNo != null) {
				return false;
			}
		} else if (!mobileNo.equals(other.mobileNo)) {
			return false;
		}
		if (model == null) {
			if (other.model != null) {
				return false;
			}
		} else if (!model.equals(other.model)) {
			return false;
		}
		if (osVersion == null) {
			if (other.osVersion != null) {
				return false;
			}
		} else if (!osVersion.equals(other.osVersion)) {
			return false;
		}
		if (platform == null) {
			if (other.platform != null) {
				return false;
			}
		} else if (!platform.equals(other.platform)) {
			return false;
		}
		if (pushNotification != other.pushNotification) {
			return false;
		}
		if (sdkVersionCode == null) {
			if (other.sdkVersionCode != null) {
				return false;
			}
		} else if (!sdkVersionCode.equals(other.sdkVersionCode)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		if (userSessionId == null) {
			if (other.userSessionId != null) {
				return false;
			}
		} else if (!userSessionId.equals(other.userSessionId)) {
			return false;
		}
		return true;
	}

}
