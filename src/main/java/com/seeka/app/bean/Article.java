package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="seeka_articles")
public class Article implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private String addType;
	private String heading;
	private String content;
	private String url;
	private String imagePath;
	private Integer type;
	private Boolean active;
	private Boolean deleted;
	private Date createdDate;
	private Integer shared;
	private Integer reviewed;
	private Integer likes;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="user_id")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name="add_type")
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	@Column(name="heading")
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="imagePath")
	public String getImagePath() {
		imagePath = "https://www.seoclerk.com/pics/645213-1jFXX81544832051.jpg";
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Column(name="type")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name="active")
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	@Column(name="deleted")
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	@Column(name="created_date")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="shared")
	public Integer getShared() {
		return shared;
	}
	public void setShared(Integer shared) {
		this.shared = shared;
	}
	@Column(name="reviewed")
	public Integer getReviewed() {
		return reviewed;
	}
	public void setReviewed(Integer reviewed) {
		this.reviewed = reviewed;
	}
	@Column(name="likes")
	public Integer getLikes() {
		return likes;
	}
	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	
	
	
	
	
}
