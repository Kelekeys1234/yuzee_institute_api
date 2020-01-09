package com.seeka.app.bean;

import java.math.BigInteger;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article_folder_map")
public class ArticleFolderMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private BigInteger userId;
	private BigInteger folderId;
	private BigInteger articleId;

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

	/**
	 * @return the folderId
	 */
	@Column(name = "folder_id")

	public BigInteger getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(BigInteger folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return the articleId
	 */
	@Column(name = "article_id")

	public BigInteger getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(BigInteger articleId) {
		this.articleId = articleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleId == null) ? 0 : articleId.hashCode());
		result = prime * result + ((folderId == null) ? 0 : folderId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ArticleFolderMap other = (ArticleFolderMap) obj;
		if (articleId == null) {
			if (other.articleId != null)
				return false;
		} else if (!articleId.equals(other.articleId))
			return false;
		if (folderId == null) {
			if (other.folderId != null)
				return false;
		} else if (!folderId.equals(other.folderId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleFolderMap [id=").append(id).append(", userId=").append(userId).append(", folderId=")
				.append(folderId).append(", articleId=").append(articleId).append("]");
		return builder.toString();
	}

}