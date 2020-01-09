package com.seeka.app.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "global_search_keyword")
public class GlobalSearchKeyword {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;
	@Column(name = "user_id")
	private BigInteger userId;
	@Column(name = "search_keyword")
	private String searchKeyword;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;
	@Column(name = "created_by", length = 50)
	private BigInteger createdBy;
	@Column(name = "updated_by", length = 50)
	private BigInteger updatedBy;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
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
	
	public BigInteger getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}
	public BigInteger getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(BigInteger updatedBy) {
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
