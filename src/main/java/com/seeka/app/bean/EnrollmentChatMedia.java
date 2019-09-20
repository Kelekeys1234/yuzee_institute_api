package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enrollment_chat_media")
public class EnrollmentChatMedia implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7912008613341444471L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "enrollment_chat_conversation_id", nullable = false)
	private EnrollmentChatConversation enrollmentChatConversation;
	@Column(name = "image_name", nullable = false)
	private String imageName;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public EnrollmentChatConversation getEnrollmentChatConversation() {
		return enrollmentChatConversation;
	}

	public void setEnrollmentChatConversation(final EnrollmentChatConversation enrollmentChatConversation) {
		this.enrollmentChatConversation = enrollmentChatConversation;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(final String imageName) {
		this.imageName = imageName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enrollmentChatConversation == null ? 0 : enrollmentChatConversation.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (imageName == null ? 0 : imageName.hashCode());
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
		EnrollmentChatMedia other = (EnrollmentChatMedia) obj;
		if (enrollmentChatConversation == null) {
			if (other.enrollmentChatConversation != null) {
				return false;
			}
		} else if (!enrollmentChatConversation.equals(other.enrollmentChatConversation)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (imageName == null) {
			if (other.imageName != null) {
				return false;
			}
		} else if (!imageName.equals(other.imageName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnrollmentChatImages [id=").append(id).append(", enrollmentChatConversation=").append(enrollmentChatConversation).append(", imageName=")
				.append(imageName).append("]");
		return builder.toString();
	}

}
