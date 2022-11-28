package com.yuzee.app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteType {

	private String name;

	private String description;

	private Boolean isActive;

	private String countryName;
}
