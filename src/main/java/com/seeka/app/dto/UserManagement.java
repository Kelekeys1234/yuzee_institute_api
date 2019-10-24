package com.seeka.app.dto;

import java.io.Serializable;
import java.util.List;

public class UserManagement implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4605242055715760089L;

	private EducationSystemResponse educationSystemResponse;
	private UserDto userDto;
	private List<UserAchivements> userAchivementsList;

	public EducationSystemResponse getEducationSystemResponse() {
		return educationSystemResponse;
	}

	public void setEducationSystemResponse(final EducationSystemResponse educationSystemResponse) {
		this.educationSystemResponse = educationSystemResponse;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(final UserDto userDto) {
		this.userDto = userDto;
	}

	public List<UserAchivements> getUserAchivementsList() {
		return userAchivementsList;
	}

	public void setUserAchivementsList(final List<UserAchivements> userAchivementsList) {
		this.userAchivementsList = userAchivementsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (educationSystemResponse == null ? 0 : educationSystemResponse.hashCode());
		result = prime * result + (userAchivementsList == null ? 0 : userAchivementsList.hashCode());
		result = prime * result + (userDto == null ? 0 : userDto.hashCode());
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
		UserManagement other = (UserManagement) obj;
		if (educationSystemResponse == null) {
			if (other.educationSystemResponse != null) {
				return false;
			}
		} else if (!educationSystemResponse.equals(other.educationSystemResponse)) {
			return false;
		}
		if (userAchivementsList == null) {
			if (other.userAchivementsList != null) {
				return false;
			}
		} else if (!userAchivementsList.equals(other.userAchivementsList)) {
			return false;
		}
		if (userDto == null) {
			if (other.userDto != null) {
				return false;
			}
		} else if (!userDto.equals(other.userDto)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserManagement [educationSystemResponse=").append(educationSystemResponse).append(", userDto=").append(userDto)
				.append(", userAchivementsList=").append(userAchivementsList).append("]");
		return builder.toString();
	}

}
