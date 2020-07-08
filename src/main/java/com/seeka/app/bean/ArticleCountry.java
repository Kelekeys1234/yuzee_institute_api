package com.seeka.app.bean;

import java.io.Serializable;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ArticleCountry generated by hbm2java
 */
@Entity
@Table(name = "article_country")
public class ArticleCountry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6903674843134844883L;
	private String id;
	private String countryName;
	private Articles seekaArticles;

	public ArticleCountry() {
	}

	public ArticleCountry(Articles seekaArticles) {
		this.seekaArticles = seekaArticles;
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "article_id")
	public Articles getSeekaArticles() {
		return this.seekaArticles;
	}

	public void setSeekaArticles(Articles seekaArticles) {
		this.seekaArticles = seekaArticles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ArticleCountry other = (ArticleCountry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		builder.append("ArticleCountry [id=").append(id).append(", seekaArticles=")
				.append(seekaArticles).append("]");
		return builder.toString();
	}

	@Column(name = "country_name")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
