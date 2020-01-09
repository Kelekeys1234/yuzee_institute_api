package com.seeka.app.dto;import java.math.BigInteger;



import java.math.BigInteger;

import com.seeka.app.bean.UserCourseReview;

public class UserReviewRequestDto {
	
	private UserCourseReview reviewObj;
	private BigInteger userId;
	private BigInteger levelId;
	private BigInteger courseId;
	private BigInteger instituteId;
	private Integer maxSizePerPage;
	private Integer pageNumber;

	public UserCourseReview getReviewObj() {
		return reviewObj;
	}

	public void setReviewObj(UserCourseReview reviewObj) {
		this.reviewObj = reviewObj;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getCourseId() {
		return courseId;
	}

	public void setCourseId(BigInteger courseId) {
		this.courseId = courseId;
	}

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	public Integer getMaxSizePerPage() {
		return maxSizePerPage;
	}

	public void setMaxSizePerPage(Integer maxSizePerPage) {
		this.maxSizePerPage = maxSizePerPage;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public BigInteger getLevelId() {
		return levelId;
	}

	public void setLevelId(BigInteger levelId) {
		this.levelId = levelId;
	}
	
}
