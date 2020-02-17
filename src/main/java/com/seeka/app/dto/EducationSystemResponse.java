package com.seeka.app.dto;

import java.util.List;

import com.seeka.app.bean.UserEducationAOLevelSubjects;
import com.seeka.app.bean.UserEnglishScore;

public class EducationSystemResponse {

	private String userId;

	private UserEducationDetailResponseDto educationDetail;

	private List<UserEnglishScore> englishScoresList;

	private List<UserEducationAOLevelSubjects> educationAOLevelSubjectList;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * @return the educationDetail
	 */
	public UserEducationDetailResponseDto getEducationDetail() {
		return educationDetail;
	}

	/**
	 * @param educationDetail the educationDetail to set
	 */
	public void setEducationDetail(final UserEducationDetailResponseDto educationDetail) {
		this.educationDetail = educationDetail;
	}

	/**
	 * @return the englishScoresList
	 */
	public List<UserEnglishScore> getEnglishScoresList() {
		return englishScoresList;
	}

	/**
	 * @param englishScoresList the englishScoresList to set
	 */
	public void setEnglishScoresList(final List<UserEnglishScore> englishScoresList) {
		this.englishScoresList = englishScoresList;
	}

	/**
	 * @return the educationAOLevelSubjectList
	 */
	public List<UserEducationAOLevelSubjects> getEducationAOLevelSubjectList() {
		return educationAOLevelSubjectList;
	}

	/**
	 * @param educationAOLevelSubjectList the educationAOLevelSubjectList to set
	 */
	public void setEducationAOLevelSubjectList(final List<UserEducationAOLevelSubjects> educationAOLevelSubjectList) {
		this.educationAOLevelSubjectList = educationAOLevelSubjectList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (educationAOLevelSubjectList == null ? 0 : educationAOLevelSubjectList.hashCode());
		result = prime * result + (educationDetail == null ? 0 : educationDetail.hashCode());
		result = prime * result + (englishScoresList == null ? 0 : englishScoresList.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
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
		EducationSystemResponse other = (EducationSystemResponse) obj;
		if (educationAOLevelSubjectList == null) {
			if (other.educationAOLevelSubjectList != null) {
				return false;
			}
		} else if (!educationAOLevelSubjectList.equals(other.educationAOLevelSubjectList)) {
			return false;
		}
		if (educationDetail == null) {
			if (other.educationDetail != null) {
				return false;
			}
		} else if (!educationDetail.equals(other.educationDetail)) {
			return false;
		}
		if (englishScoresList == null) {
			if (other.englishScoresList != null) {
				return false;
			}
		} else if (!englishScoresList.equals(other.englishScoresList)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EducationSystemResponse [userId=").append(userId).append(", educationDetail=").append(educationDetail).append(", englishScoresList=")
				.append(englishScoresList).append(", educationAOLevelSubjectList=").append(educationAOLevelSubjectList).append("]");
		return builder.toString();
	}

}