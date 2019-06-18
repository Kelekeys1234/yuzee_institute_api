package com.seeka.app.dto;import java.math.BigInteger;

public class PaginationDto {
	
	private Integer totalCount;
	private Boolean showMore;
	
	public PaginationDto(Integer totalCount,Boolean showMore) {
		this.showMore=showMore;
		this.totalCount=totalCount;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Boolean getShowMore() {
		return showMore;
	}
	public void setShowMore(Boolean showMore) {
		this.showMore = showMore;
	}
}
