package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class CourseSearchFilterDto {

	@JsonProperty("price")
	private String price;

	@JsonProperty("duration")
	private String duration;

	@JsonProperty("location")
	private String location;

	@JsonProperty("recognition")
	private String recognition;

	@JsonProperty("latest_course")
	private String latestCourse;

	@JsonProperty("institute")
	private String institute;

	@JsonProperty("course")
	private String course;

	@JsonProperty("world_ranking")
	private String worldRanking;

	public CourseSearchFilterDto(String price, String duration, String location, String recognition,
			String latestCourse, String institute, String course, String worldRanking) {
		super();
		this.price = price;
		this.duration = duration;
		this.location = location;
		this.recognition = recognition;
		this.latestCourse = latestCourse;
		this.institute = institute;
		this.course = course;
		this.worldRanking = worldRanking;
	}

}
