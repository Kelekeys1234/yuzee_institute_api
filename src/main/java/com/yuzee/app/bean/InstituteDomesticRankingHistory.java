package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteDomesticRankingHistory {

	@JsonProperty(value = "domestic_ranking")
	private Integer domesticRanking;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
}
