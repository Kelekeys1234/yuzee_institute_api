package com.seeka.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseDto {

	@JsonProperty("course_id")
    private String id;
    
	@JsonProperty("level_id")
	@NotBlank(message = "level_id should not be blank")
	private String levelId;
	
	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
    private String name;
	
	@JsonProperty("cost")
    private String cost;
	
	@JsonProperty("world_ranking")
    private String worldRanking;
	
	@JsonProperty("stars")
    private String stars;
	
	@JsonProperty("faculty_name")
    private String facultyName;
	
	@JsonProperty("level_name")
    private String levelName;
	
	@JsonProperty("cost_of_living")
    private String costOfLiving;
	
	@JsonProperty("description")
    private String description;
	
	@JsonProperty("remarks")
    private String remarks;
}
