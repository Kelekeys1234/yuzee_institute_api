package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class ArticleFilterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8904624208329190436L;

	private Integer pageNumber;
	private Integer pageSize; 
	private String sortByField;
	private String sortByType;
	private String searchKeyword;
	private List<BigInteger> categoryId;
	private List<String> tags;
	private String status;
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortByField() {
		return sortByField;
	}
	public void setSortByField(String sortByField) {
		this.sortByField = sortByField;
	}
	public String getSortByType() {
		return sortByType;
	}
	public void setSortByType(String sortByType) {
		this.sortByType = sortByType;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public List<BigInteger> getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(List<BigInteger> categoryId) {
		this.categoryId = categoryId;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
