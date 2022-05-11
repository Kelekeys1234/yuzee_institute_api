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

	@NotBlank(message = "{institute_id.is_required}")
	@JsonProperty("institute_id")
	private String instituteId;

	@NotBlank(message = "{exam_name.is_required}")
	@JsonProperty("exam_name")
	private String examName;

	@NotNull(message = "{reading_marks.is_required}")
	@JsonProperty("reading_marks")
	private Double readingMarks;

	@NotNull(message = "{listening_marks.is_required}")
	@JsonProperty("listening_marks")
	private Double listeningMarks;

	@NotNull(message = "{writing_marks.is_required}")
	@JsonProperty("writing_marks")
	private Double writingMarks;

	@NotNull(message = "{oral_marks.is_required}")
	@JsonProperty("oral_marks")
	private Double oralMarks;

	@Setter(AccessLevel.NONE)
	@JsonProperty("over_all_marks")
	private Double overAllMarks;

	public InstituteEnglishRequirementsDto(@NotBlank(message = "{exam_name.is_required}") String examName,
			@NotNull(message = "{reading_marks.is_required}") Double readingMarks,
			@NotNull(message = "{listening_marks.is_required}") Double listeningMarks,
			@NotNull(message = "{writing_marks.is_required}") Double writingMarks,
			@NotNull(message = "{oral_marks.is_required}") Double oralMarks) {
		super();
		this.examName = examName;
		this.readingMarks = readingMarks;
		this.listeningMarks = listeningMarks;
		this.writingMarks = writingMarks;
		this.oralMarks = oralMarks;
		setOverallMarks();
	}

	private void setOverallMarks() {
		this.overAllMarks = (this.readingMarks + this.listeningMarks + this.writingMarks + this.oralMarks) / 4;
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
