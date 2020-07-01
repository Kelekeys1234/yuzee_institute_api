package com.seeka.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseEnglishEligibilityDto {
	@JsonProperty("course_eligibility_id")
	private String id;
	
	@JsonProperty("english_type")
	@NotBlank(message = "english_type should not be blank")
	private String englishType;
	
	@JsonProperty("reading")
	private Double reading;
	
	@JsonProperty("writing")
	private Double writing;
	
	@JsonProperty("speaking")
	private Double speaking;
	
	@JsonProperty("listening")
	private Double listening;
	
	@JsonProperty("overall")
	private Double overall;
}
