package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CourseKeywords generated by hbm2java
 */
@Entity
@Table(name = "course_keywords")
public class CourseKeywords implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -167782154348375697L;
	private BigInteger id;
	private String keyword;
	private String KDesc;

	public CourseKeywords() {
	}

	public CourseKeywords(String keyword, String KDesc) {
		this.keyword = keyword;
		this.KDesc = KDesc;
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

	@Column(name = "keyword", length = 250)
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "k_desc", length = 250)
	public String getKDesc() {
		return this.KDesc;
	}

	public void setKDesc(String KDesc) {
		this.KDesc = KDesc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((KDesc == null) ? 0 : KDesc.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
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
		CourseKeywords other = (CourseKeywords) obj;
		if (KDesc == null) {
			if (other.KDesc != null)
				return false;
		} else if (!KDesc.equals(other.KDesc))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CourseKeywords [id=").append(id).append(", keyword=").append(keyword).append(", KDesc=")
				.append(KDesc).append("]");
		return builder.toString();
	}

}
