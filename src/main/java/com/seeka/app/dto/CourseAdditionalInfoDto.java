package com.seeka.app.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseAdditionalInfoDto {
	private String id;
	private String deliveryType;
	private Integer duration;
	private String durationTime;
	private Double domesticFee;
	private Double internationalFee;
	private String studyMode;
	private Double usdDomesticFee;
	private Double usdInternationalFee;
	private String courseId;
}
