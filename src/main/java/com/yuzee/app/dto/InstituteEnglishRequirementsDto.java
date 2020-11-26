package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class InstituteEnglishRequirementsDto {

	@JsonProperty("institute_english_requirements_id")
	public String instituteEnglishRequirementsId;

	@NotBlank(message = "exam_name is required")
	@JsonProperty("exam_name")
	private String examName;

	@NotNull(message = "reading_marks is required")
	@JsonProperty("reading_marks")
	private Double readingMarks;

	@NotNull(message = "listning_marks is required")
	@JsonProperty("listning_marks")
	private Double listningMarks;

	@NotNull(message = "writing_marks is required")
	@JsonProperty("writing_marks")
	private Double writingMarks;

	@NotNull(message = "oral_marks is required")
	@JsonProperty("oral_marks")
	private Double oralMarks;

	@Setter(AccessLevel.NONE)
	@JsonProperty("over_all_marks")
	private Double overAllMarks;

	public InstituteEnglishRequirementsDto(String instituteEnglishRequirementsId,
			@NotBlank(message = "exam_name is required") String examName,
			@NotNull(message = "reading_marks is required") Double readingMarks,
			@NotNull(message = "listning_marks is required") Double listningMarks,
			@NotNull(message = "writing_marks is required") Double writingMarks,
			@NotNull(message = "oral_marks is required") Double oralMarks) {
		super();
		this.instituteEnglishRequirementsId = instituteEnglishRequirementsId;
		this.examName = examName;
		this.readingMarks = readingMarks;
		this.listningMarks = listningMarks;
		this.writingMarks = writingMarks;
		this.oralMarks = oralMarks;
		setOverallMarks();
	}

	private void setOverallMarks() {
		this.overAllMarks = (this.readingMarks + this.listningMarks + this.writingMarks + this.oralMarks) / 4;
		Double valueAfterPoint = this.overAllMarks - this.overAllMarks.intValue();
		if (valueAfterPoint >= 0.75) {
			valueAfterPoint = 1.0;
		} else if (valueAfterPoint >= 0.25) {
			valueAfterPoint = 0.5;
		} else {
			valueAfterPoint = 0.0;
		}
		this.overAllMarks = this.overAllMarks.intValue() + valueAfterPoint;
	}
}
