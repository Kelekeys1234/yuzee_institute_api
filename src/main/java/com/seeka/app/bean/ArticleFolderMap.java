package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article_folder_mapping")
public class ArticleFolderMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	
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

	@Column(name = "folder_id")
	public BigInteger getFolderId() {
		return folderId;
	}

	public void setFolderId(BigInteger folderId) {
		this.folderId = folderId;
	}
	@Column(name = "article_id")
	public BigInteger getArticleId() {
		return articleId;
	}

	public void setArticleId(BigInteger articleId) {
		this.articleId = articleId;
	}

	
	

	
	

}