package com.seeka.app.bean;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "chat_conversation")
public class ChatConversation implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7590305810325611394L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "chat_id", nullable = false)
	private Chat chat;

	@Column(name = "message")
	private String message;

	@Column(name = "status")
	private String status;

	/**
	 * USER/SEEKA
	 */
	@Column(name = "initiate_from")
	private String initiateFrom;

	@Column(name = "initiate_from_id")
	private String initiateFromId;

	@Column(name = "initiate_to")
	private String initiateTo;

	@Column(name = "initiate_to_id")
	private String initiateToId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(final Chat chat) {
		this.chat = chat;
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

	public String getInitiateFromId() {
		return initiateFromId;
	}

	public void setInitiateFromId(final String initiateFromId) {
		this.initiateFromId = initiateFromId;
	}

	public String getInitiateTo() {
		return initiateTo;
	}

	public void setInitiateTo(final String initiateTo) {
		this.initiateTo = initiateTo;
	}

	public String getInitiateToId() {
		return initiateToId;
	}

	public void setInitiateToId(final String initiateToId) {
		this.initiateToId = initiateToId;
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
		result = prime * result + (chat == null ? 0 : chat.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (initiateFrom == null ? 0 : initiateFrom.hashCode());
		result = prime * result + (initiateFromId == null ? 0 : initiateFromId.hashCode());
		result = prime * result + (initiateTo == null ? 0 : initiateTo.hashCode());
		result = prime * result + (initiateToId == null ? 0 : initiateToId.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (status == null ? 0 : status.hashCode());
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
		ChatConversation other = (ChatConversation) obj;
		if (chat == null) {
			if (other.chat != null) {
				return false;
			}
		} else if (!chat.equals(other.chat)) {
			return false;
		}
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
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
		if (initiateTo == null) {
			if (other.initiateTo != null) {
				return false;
			}
		} else if (!initiateTo.equals(other.initiateTo)) {
			return false;
		}
		if (initiateToId == null) {
			if (other.initiateToId != null) {
				return false;
			}
		} else if (!initiateToId.equals(other.initiateToId)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChatConversation [id=").append(id).append(", chat=").append(chat).append(", message=").append(message).append(", status=")
				.append(status).append(", initiateFrom=").append(initiateFrom).append(", initiateFromId=").append(initiateFromId).append(", initiateTo=")
				.append(initiateTo).append(", initiateToId=").append(initiateToId).append(", createdOn=").append(createdOn).append("]");
		return builder.toString();
	}

}
