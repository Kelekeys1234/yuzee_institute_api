package com.seeka.app.dto;

import java.math.BigInteger;

public class CourseFilterDto {

	private BigInteger countryId;
	private BigInteger instituteId;
	private BigInteger facultyId;
	private BigInteger courseId;
	private String language;
	private Integer minRanking;
	private Integer maxRanking;
	private Integer maxSizePerPage;
	private Integer pageNumber;
	private String currencyCode;

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(BigInteger countryId) {
		this.countryId = countryId;
	}

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	public BigInteger getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(BigInteger facultyId) {
		this.facultyId = facultyId;
	}

	public BigInteger getCourseId() {
		return courseId;
	}

	public void setCourseId(BigInteger courseId) {
		this.courseId = courseId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getMinRanking() {
		return minRanking;
	}

	public void setMinRanking(Integer minRanking) {
		this.minRanking = minRanking;
	}

	public Integer getMaxRanking() {
		return maxRanking;
	}

	public void setMaxRanking(Integer maxRanking) {
		this.maxRanking = maxRanking;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
