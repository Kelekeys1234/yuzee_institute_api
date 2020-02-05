package com.seeka.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "scholarship_language")
public class ScholarshipLanguage implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3516597245947004154L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	@Column(name = "name")
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scholarship_id")
	private Scholarship scholarship;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Scholarship getScholarship() {
		return scholarship;
	}

	public void setScholarship(final Scholarship scholarship) {
		this.scholarship = scholarship;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (scholarship == null ? 0 : scholarship.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ScholarshipLanguage other = (ScholarshipLanguage) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (scholarship == null) {
			if (other.scholarship != null) {
				return false;
			}
		} else if (!scholarship.equals(other.scholarship)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ScholarshipLanguage [id=" + id + ", name=" + name + ", scholarship=" + scholarship + "]";
	}

}
