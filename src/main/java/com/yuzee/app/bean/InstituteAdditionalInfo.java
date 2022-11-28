package com.yuzee.app.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteAdditionalInfo{

	private Integer numberOfStudent=0;

	private Integer numberOfEmployee=0;

	private Integer numberOfTeacher=0;

	private Integer numberOfClassRoom=0;

	private Integer sizeOfCampus=0;

	private Integer numberOfLectureHall=0;

	private Integer numberOfFaculty=0;

	private Integer rateOfEmployment=0;

}
