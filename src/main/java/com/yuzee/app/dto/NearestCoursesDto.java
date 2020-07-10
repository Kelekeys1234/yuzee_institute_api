package com.yuzee.app.dto;

import java.util.List;

import lombok.Data;

// DTO used in to get course by instituteID

@Data
public class NearestCoursesDto {
	private List<CourseResponseDto> nearestCourses;
	private Integer totalCount;
	private Integer pageNumber;
	private Boolean hasPreviousPage;
	private Boolean hasNextPage;
	private Integer totalPages;
}
