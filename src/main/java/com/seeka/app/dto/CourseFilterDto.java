package com.seeka.app.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseFilterDto {

	private String countryName;
	private String instituteId;
	private String facultyId;
	private String courseId;
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
	private String userCountryId;
}
