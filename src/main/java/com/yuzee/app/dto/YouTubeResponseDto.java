package com.yuzee.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class YouTubeResponseDto {
	private List<YouTubeVideoDto> youTubeVideosList;
	private Integer totalCount;
	private Integer pageNumber;
	private Boolean hasPreviousPage;
	private Boolean hasNextPage;
	private Integer totalPages;
}
