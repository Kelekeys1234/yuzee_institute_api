package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TimingRequestDto {

	@JsonProperty("timing_id")
	private String id;

	@JsonProperty("timings")
	@NotEmpty(message = "timings must not be empty.")
	private List<DayTimingDto> timings;

	@NotEmpty(message = "timing_type must not be empty.")
	@JsonProperty("timing_type")
	private String timingType;

	@NotEmpty(message = "entity_type must not be empty.")
	@JsonProperty("entity_type")
	private String entityType;

	@JsonProperty("entity_id")
	private String entityId;

}
