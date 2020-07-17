package com.yuzee.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NearestCoursesDto {
	
	@JsonProperty("nearest_courses")
	private List<CourseResponseDto> nearestCourses;
	
	@JsonProperty("total_count")
	private Integer totalCount;
	
	@JsonProperty("page_number")
	private Integer pageNumber;
	
	@JsonProperty("has_previous_page")
	private Boolean hasPreviousPage;
	
	@JsonProperty("has_next_page")
	private Boolean hasNextPage;
	
	@JsonProperty("total_pages")
	private Integer totalPages;
	
	public NearestCoursesDto(List<CourseResponseDto> nearestCourses, Integer totalCount, Integer pageNumber, Boolean hasPreviousPage,
			Boolean hasNextPage, Integer totalPages) {
		this.nearestCourses = nearestCourses;
		this.totalCount = totalCount;
		this.pageNumber = pageNumber;
		this.hasPreviousPage = hasPreviousPage;
		this.hasNextPage = hasNextPage;
		this.totalPages = totalPages;
	}
}
