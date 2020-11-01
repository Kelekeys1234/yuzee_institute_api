package com.yuzee.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InstituteResponseDto extends InstituteDto {

	@JsonProperty("location")
	private String location;

	@JsonProperty("total_count")
	private Integer totalCount;

	@JsonProperty("about_us")
	private String aboutUs;

	@JsonProperty("visa_requirement")
	private String visaRequirement;

	@JsonProperty("total_available_jobs")
	private String totalAvailableJobs;

	@JsonProperty("storage_list")
	private List<StorageDto> storageList;

	@JsonProperty("distance")
	private Double distance;

	@JsonProperty("min_price_range")
	private Double minPriceRange;

	@JsonProperty("max_price_range")
	private Double maxPriceRange;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("institute_services")
	private List<String> instituteServices;

	@JsonProperty("accrediated_detail")
	private List<AccrediatedDetailDto> accrediatedDetail;

	@JsonProperty("institute_timing")
	private InstituteTimingResponseDto instituteTiming;

}
