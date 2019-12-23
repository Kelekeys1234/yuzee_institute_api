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

	/**
	 * This is used to get user country Id, as it will be used to determine which
	 * courses to show based on user country like courses with availability A,D,I ,
	 * the above mentioned country will be available as filter while this will be
	 * derived based on logged in user country
	 */
	private BigInteger userCountryId;

	public BigInteger getUserCountryId() {
		return userCountryId;
	}

	public void setUserCountryId(final BigInteger userCountryId) {
		this.userCountryId = userCountryId;
	}

	public BigInteger getCountryId() {
		return countryId;
	}

	public void setCountryId(final BigInteger countryId) {
		this.countryId = countryId;
	}

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(final BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	public BigInteger getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(final BigInteger facultyId) {
		this.facultyId = facultyId;
	}

	public BigInteger getCourseId() {
		return courseId;
	}

	public void setCourseId(final BigInteger courseId) {
		this.courseId = courseId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public Integer getMinRanking() {
		return minRanking;
	}

	public void setMinRanking(final Integer minRanking) {
		this.minRanking = minRanking;
	}

	public Integer getMaxRanking() {
		return maxRanking;
	}

	public void setMaxRanking(final Integer maxRanking) {
		this.maxRanking = maxRanking;
	}

	public Integer getMaxSizePerPage() {
		return maxSizePerPage;
	}

	public void setMaxSizePerPage(final Integer maxSizePerPage) {
		this.maxSizePerPage = maxSizePerPage;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(final Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
