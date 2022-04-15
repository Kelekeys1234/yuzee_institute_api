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

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteEnglishRequirements {

	private String examName;

	private Double readingMarks;

	private Double listeningMarks;

	private Double writingMarks;

	private Double oralMarks;

	public InstituteEnglishRequirements(String instituteId, String examName, Double readingMarks, Double listningMarks, Double writingMarks, Double oralMarks, Date date, Date date1, String api, String api1) {
	}
}
