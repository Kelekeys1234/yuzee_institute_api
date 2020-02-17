package com.seeka.app.dto;

public class PageLookupDto {
	
	private Integer maxSizePerPage;
	private Integer pageNumber;
	
	public Integer getMaxSizePerPage() {
		return maxSizePerPage;
	}
	public void setMaxSizePerPage(Integer maxSizePerPage) {
		this.maxSizePerPage = maxSizePerPage;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
