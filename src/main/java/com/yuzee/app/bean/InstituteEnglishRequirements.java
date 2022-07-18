package com.yuzee.app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "InstituteEnglishRequirements")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteEnglishRequirements {

	@Id
	private String id;

	private Institute institute;

	private String examName;

	private Double readingMarks;

	private Double listeningMarks;

	private Double writingMarks;

	private Double oralMarks;

	public InstituteEnglishRequirements(String englishRequirementId, String examName, Double readingMarks, Double listeningMarks, Double writingMarks, Double oralMarks) {
		this.id = englishRequirementId;
		this.examName = examName;
		this.readingMarks = readingMarks;
		this.listeningMarks = listeningMarks;
		this.writingMarks = writingMarks;
		this.oralMarks = oralMarks;
	}
}
