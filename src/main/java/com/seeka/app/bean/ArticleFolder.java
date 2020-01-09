package com.seeka.app.bean;

import java.math.BigInteger;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.seeka.app.dto.ArticleNameDto;

@Entity
@Table(name = "article_folder")
public class ArticleFolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String folderName;
	private Boolean deleted;
	private Date createdAt;
	private Date updatedAt;
	private List<ArticleNameDto> articles;
	private BigInteger userId;
	
	private String folderImage;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * @return the folderName
	 */
	@Column(name = "folder_name")
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return the deleted
	 */
	@Column(name = "deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the createdAt
	 */
	@Column(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	@Column(name = "updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the articles
	 */
	@Transient
	public List<ArticleNameDto> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(List<ArticleNameDto> articles) {
		this.articles = articles;
	}

	/**
	 * @return the userId
	 */
	@Column(name = "user_id")
	public BigInteger getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articles == null) ? 0 : articles.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((folderName == null) ? 0 : folderName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleFolder other = (ArticleFolder) obj;
		if (articles == null) {
			if (other.articles != null)
				return false;
		} else if (!articles.equals(other.articles))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (folderName == null) {
			if (other.folderName != null)
				return false;
		} else if (!folderName.equals(other.folderName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Transient
    public String getFolderImage() {
        return folderImage;
    }

    public void setFolderImage(String folderImage) {
        this.folderImage = folderImage;
    }
    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleFolder [id=").append(id).append(", folderName=").append(folderName).append(", deleted=")
				.append(deleted).append(", createdAt=").append(createdAt).append(", updatedAt=").append(updatedAt)
				.append(", articles=").append(articles).append(", userId=").append(userId).append("]");
		return builder.toString();
	}

}