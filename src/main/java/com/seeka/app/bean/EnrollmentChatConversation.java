package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "enrollment_chat_conversation")
public class EnrollmentChatConversation implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7590305810325611394L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "enrollment_chat_id", nullable = false)
	private EnrollmentChat enrollmentChat;

	@Column(name = "message")
	private String message;

	@Column(name = "status")
	private String status;

	@Column(name = "initiate_from")
	private String initiateFrom;

	@Column(name = "initiate_from_id")
	private BigInteger initiateFromId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public EnrollmentChat getEnrollmentChat() {
		return enrollmentChat;
	}

	public void setEnrollmentChat(final EnrollmentChat enrollmentChat) {
		this.enrollmentChat = enrollmentChat;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getInitiateFrom() {
		return initiateFrom;
	}

	public void setInitiateFrom(final String initiateFrom) {
		this.initiateFrom = initiateFrom;
	}

	public BigInteger getInitiateFromId() {
		return initiateFromId;
	}

	public void setInitiateFromId(final BigInteger initiateFromId) {
		this.initiateFromId = initiateFromId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (enrollmentChat == null ? 0 : enrollmentChat.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (initiateFrom == null ? 0 : initiateFrom.hashCode());
		result = prime * result + (initiateFromId == null ? 0 : initiateFromId.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
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
		EnrollmentChatConversation other = (EnrollmentChatConversation) obj;
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
			return false;
		}
		if (enrollmentChat == null) {
			if (other.enrollmentChat != null) {
				return false;
			}
		} else if (!enrollmentChat.equals(other.enrollmentChat)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (initiateFrom == null) {
			if (other.initiateFrom != null) {
				return false;
			}
		} else if (!initiateFrom.equals(other.initiateFrom)) {
			return false;
		}
		if (initiateFromId == null) {
			if (other.initiateFromId != null) {
				return false;
			}
		} else if (!initiateFromId.equals(other.initiateFromId)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnrollmentChatConversation [id=").append(id).append(", enrollmentChat=").append(enrollmentChat).append(", message=").append(message)
				.append(", initiateFrom=").append(initiateFrom).append(", initiateFromId=").append(initiateFromId).append(", createdOn=").append(createdOn)
				.append("]");
		return builder.toString();
	}

}
