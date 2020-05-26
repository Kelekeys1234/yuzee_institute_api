package com.seeka.app.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class NearestInstituteDTO implements Serializable {

	private static final long serialVersionUID = 3205327956325930806L;
	private String instituteId;
	private String instituteName;
	private Integer totalCourseCount;
	private Double minPriceRange;
	private Double maxPriceRange;
	private Double latitute;
	private Double longitude;
	private List<StorageDto> instituteLogoImages;
	private Integer worldRanking;
	private Integer domesticRanking;
	private Integer stars;
}
