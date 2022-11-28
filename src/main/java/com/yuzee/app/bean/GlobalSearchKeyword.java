package com.yuzee.app.bean;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("global_search_keyword")
public class GlobalSearchKeyword {

	@org.springframework.data.annotation.Id
	private String id;
	private String userId;
	private String searchKeyword;
	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GlobalSearchKeyword [id=").append(id).append(", userId=").append(userId)
				.append(", searchKeyword=").append(searchKeyword).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append("]");
		return builder.toString();
	}

}
