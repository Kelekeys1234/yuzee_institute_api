package com.seeka.app.bean;

import java.io.Serializable;
import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CategorySeekaArticle generated by hbm2java
 */
@Entity
@Table(name = "category_seeka_article")
public class CategorySeekaArticle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5755860681101009294L;
	private BigInteger articleId;
	private Category category;
	private int value;

	public CategorySeekaArticle() {
	}

	public CategorySeekaArticle(Category category, int value) {
		this.category = category;
		this.value = value;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "article_id", unique = true, nullable = false)
	public BigInteger getArticleId() {
		return this.articleId;
	}

	public void setArticleId(BigInteger articleId) {
		this.articleId = articleId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "value", nullable = false)
	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleId == null) ? 0 : articleId.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + value;
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
		CategorySeekaArticle other = (CategorySeekaArticle) obj;
		if (articleId == null) {
			if (other.articleId != null)
				return false;
		} else if (!articleId.equals(other.articleId))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategorySeekaArticle [articleId=").append(articleId).append(", category=").append(category)
				.append(", value=").append(value).append("]");
		return builder.toString();
	}

}
