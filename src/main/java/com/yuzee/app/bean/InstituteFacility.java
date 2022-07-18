package com.yuzee.app.bean;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteFacility {

	@Id
	private String facilityId;

	@DBRef(lazy = false)
	private Service service;
}
