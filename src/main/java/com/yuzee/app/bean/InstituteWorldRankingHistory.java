package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteWorldRankingHistory{

	@JsonProperty(value = "world_ranking")
	private Integer worldRanking;

	private Date createdOn;

	private String createdBy;

	private Date updatedOn;

	private String updatedBy;
}
