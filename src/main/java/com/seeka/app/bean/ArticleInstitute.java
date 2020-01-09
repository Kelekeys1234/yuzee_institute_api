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
 * ArticleInstitute generated by hbm2java
 */
@Entity
@Table(name = "article_institute")
public class ArticleInstitute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8193692047761850471L;
	private BigInteger id;
	private Institute institute;
	private SeekaArticles seekaArticles;

	public ArticleInstitute() {
	}

	public ArticleInstitute(Institute institute, SeekaArticles seekaArticles) {
		this.institute = institute;
		this.seekaArticles = seekaArticles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "institute_id")
	public Institute getInstitute() {
		return this.institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "article_id")
	public SeekaArticles getSeekaArticles() {
		return this.seekaArticles;
	}

	public void setSeekaArticles(SeekaArticles seekaArticles) {
		this.seekaArticles = seekaArticles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((institute == null) ? 0 : institute.hashCode());
		result = prime * result + ((seekaArticles == null) ? 0 : seekaArticles.hashCode());
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
		ArticleInstitute other = (ArticleInstitute) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (institute == null) {
			if (other.institute != null)
				return false;
		} else if (!institute.equals(other.institute))
			return false;
		if (seekaArticles == null) {
			if (other.seekaArticles != null)
				return false;
		} else if (!seekaArticles.equals(other.seekaArticles))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleInstitute [id=").append(id).append(", institute=").append(institute)
				.append(", seekaArticles=").append(seekaArticles).append("]");
		return builder.toString();
	}

}
