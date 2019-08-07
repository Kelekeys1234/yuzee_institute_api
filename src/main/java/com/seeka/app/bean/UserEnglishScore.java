package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.seeka.app.enumeration.EnglishType;

@Entity
@Table(name = "user_edu_iel_tof_score")
public class UserEnglishScore extends RecordModifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1461179559012854449L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private BigInteger id;

	@Column(name = "user_id")
	private BigInteger userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "english_type")
	private EnglishType englishType;

	@Column(name = "reading")
	private Double reading;

	@Column(name = "writing")
	private Double writing;

	@Column(name = "speaking")
	private Double speaking;

	@Column(name = "listening")
	private Double listening;

	@Column(name = "overall")
	private Double overall;

	@Column(name = "is_active")
	private Boolean isActive;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public EnglishType getEnglishType() {
		return englishType;
	}

	public void setEnglishType(EnglishType englishType) {
		this.englishType = englishType;
	}

	public Double getReading() {
		return reading;
	}

	public void setReading(Double reading) {
		this.reading = reading;
	}

	public Double getWriting() {
		return writing;
	}

	public void setWriting(Double writing) {
		this.writing = writing;
	}

	public Double getSpeaking() {
		return speaking;
	}

	public void setSpeaking(Double speaking) {
		this.speaking = speaking;
	}

	public Double getListening() {
		return listening;
	}

	public void setListening(Double listening) {
		this.listening = listening;
	}

	public Double getOverall() {
		return overall;
	}

	public void setOverall(Double overall) {
		this.overall = overall;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((englishType == null) ? 0 : englishType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((listening == null) ? 0 : listening.hashCode());
		result = prime * result + ((overall == null) ? 0 : overall.hashCode());
		result = prime * result + ((reading == null) ? 0 : reading.hashCode());
		result = prime * result + ((speaking == null) ? 0 : speaking.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((writing == null) ? 0 : writing.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEnglishScore other = (UserEnglishScore) obj;
		if (englishType != other.englishType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (listening == null) {
			if (other.listening != null)
				return false;
		} else if (!listening.equals(other.listening))
			return false;
		if (overall == null) {
			if (other.overall != null)
				return false;
		} else if (!overall.equals(other.overall))
			return false;
		if (reading == null) {
			if (other.reading != null)
				return false;
		} else if (!reading.equals(other.reading))
			return false;
		if (speaking == null) {
			if (other.speaking != null)
				return false;
		} else if (!speaking.equals(other.speaking))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (writing == null) {
			if (other.writing != null)
				return false;
		} else if (!writing.equals(other.writing))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserEnglishScore [id=").append(id).append(", userId=").append(userId).append(", englishType=")
				.append(englishType).append(", reading=").append(reading).append(", writing=").append(writing)
				.append(", speaking=").append(speaking).append(", listening=").append(listening).append(", overall=")
				.append(overall).append(", isActive=").append(isActive).append("]");
		return builder.toString();
	}

}
